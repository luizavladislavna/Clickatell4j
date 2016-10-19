package com.clickatell.sdk;

import com.clickatell.entity.CoverageResponse;
import com.clickatell.entity.DataBalanceResponse;
import com.clickatell.entity.DataCoverageResponse;
import com.clickatell.entity.DataMessageResponse;
import com.clickatell.entity.DataStatusResponse;
import com.clickatell.entity.Message;
import com.clickatell.entity.ErrorResponse;
import com.clickatell.entity.AcceptedMessage;
import com.clickatell.entity.AcceptedMessages;
import com.clickatell.entity.SendMessageRequest;
import com.clickatell.exxeption.ApiException;
import com.clickatell.util.HttpMethod;
import com.clickatell.util.JsonUtil;
import com.clickatell.util.NetworkHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * This is an example of how to use the Clickatell REST API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 */
@Slf4j
public class ClickatellRest implements ClickatellSdk {

	private static NetworkHttpClient httpClient;

	/**
	 * Create a REST object, and set the auth, but not test the auth.
	 */
	private ClickatellRest (String apiKey) {
		this.httpClient = NetworkHttpClient.of(apiKey);
	}

	public static ClickatellRest of (String apiKey) {return new ClickatellRest(apiKey);}

	/**
	 * This will attempt to get your current balance.
	 *
	 * @return Your balance.
	 * @throws Exception This will be thrown if your auth details were incorrect.
	 */

	@Override
	public double getBalance () {
		// Send Request:
		String responseJson = this.excute("account/balance", HttpMethod.GET, null);
		DataBalanceResponse response = DataBalanceResponse.of(responseJson);
		CheckForError(response);
		return response.getData().getBalance();
	}

	/**
	 * This sends a single message.
	 *
	 * @param number  The number that you wish to send to. This should be in
	 *                international format.
	 * @param message The message you want to send,
	 * @return A Message object which will contain the information from the
	 * request.
	 */
	public Message sendMessage (String number, String message) {
		return sendMessage(number, message, null);
	}

	public Message sendMessage (String number, String message, String from) {
		String jsonResponse = this.excute("message", HttpMethod.POST,
				JsonUtil.asJsonString(SendMessageRequest.of(from, number, message))
		);

		DataMessageResponse response = DataMessageResponse.of(jsonResponse);
		log.debug("response : {}", response);
		CheckForError(response);
		Optional<AcceptedMessage> responseMsg = response.getData().getMessage().stream().findFirst();
		if (responseMsg.isPresent()) {
			AcceptedMessage msg = responseMsg.get();
			CheckForError(responseMsg.get());
			return getMessageStatus(msg.getApiMessageId());
		} else {
			throw new ApiException("No Feedback from Clickatell!");
		}
	}

	/**
	 * This is to send the same message to multiple people.
	 *
	 * @param numbers The array of numbers that are to be sent to.
	 * @param message The message that you would like to send.
	 * @return An Array of Message objects which will contain the information
	 * from the request.
	 * @throws Exception This gets thrown on auth errors.
	 */

	public AcceptedMessages sendMessage (List<String> numbers, String message) {
		return sendMessage(numbers, message, null);
	}

	public AcceptedMessages sendMessage (List<String> numbers, String message, String from) {
		String jsonResponse = this.excute("message", HttpMethod.POST,
				JsonUtil.asJsonString(SendMessageRequest.of(from, numbers, message))
		);
		DataMessageResponse response = DataMessageResponse.of(jsonResponse);
		log.debug("!!!response : {}", response);
		CheckForError(response);

		AcceptedMessages messagesReturn = new AcceptedMessages();
		List<AcceptedMessage> messages = response.getData()
				.getMessage().stream()
				.filter(msg -> isNull(msg.getError()) || msg.isAccepted())
				.collect(Collectors.toList());
		messagesReturn.setSuccess(messages);

		List<AcceptedMessage> errors = response.getData()
				.getMessage().stream()
				.filter(msg -> nonNull(msg.getError()) || !msg.isAccepted())
				.collect(Collectors.toList());
		messagesReturn.setError(errors);
		return messagesReturn;
	}

	/**
	 * This will get the status and charge of the message given by the
	 * messageId.
	 *
	 * @param messageId The message ID that should be searched for.
	 * @return A Message object which will contain the information from the
	 * request.
	 * @throws Exception If there was an error with the request.
	 */
	@Override
	public Message getMessageStatus (String messageId) {
		String responseJson = this.excute("message/" + messageId, HttpMethod.GET, null);
		DataStatusResponse response = DataStatusResponse.of(responseJson);
		CheckForError(response);
		return response.getData();
	}


	/**
	 * This will try to stop a message that has been sent. Note that only
	 * messages that are going to be sent in the future can be stopped. Or if by
	 * some luck you message has not been sent to the operator yet.
	 *
	 * @param messageId The message ID that is to be stopped.
	 * @return A Message object which will contain the information from the
	 * request.
	 * @throws Exception If there was something wrong with the request.
	 */
	@Override
	public Message stopMessage (String messageId) {
		// Send Request:
		String responseJson = this.excute("message/" + messageId, HttpMethod.DELETE, null);
		DataStatusResponse response = DataStatusResponse.of(responseJson);
		CheckForError(response);
		return response.getData();
	}


	/**
	 * This attempts to get coverage data for the given number. A -1 means no
	 * coverage, all else is the minimum cost the message could charge.
	 *
	 * @param number The number the lookup should be done on.
	 * @return The minimum possible cost, or a -1 on error.
	 * @throws Exception If there was something wrong with the request.
	 */
	@Override
	public double getMessageCoverageCharge (String number) {
		return getMessageCoverage(number).getMinimumCharge();
	}

	@Override
	public CoverageResponse getMessageCoverage (String number) {
		String responseJson = this.excute("coverage/" + number, HttpMethod.GET, null);
		log.debug("response: {}", responseJson);
		DataCoverageResponse response = DataCoverageResponse.of(responseJson);
		CheckForError(response);
		return response.getData();
	}


	/**
	 * This executes a POST query with the given parameters.
	 *
	 * @param resource The URL that should get hit.
	 * @return The content of the request.
	 * @throws UnknownHostException
	 */
	private String excute (String resource, HttpMethod method, String dataPacket) {
		return httpClient.makeRequest(resource, method, dataPacket);
	}

	/**
	 * This is an internal function used to shorten other functions. Checks for
	 * an error object, and throws it.
	 *
	 * @param response The object that needs to be checked.
	 * @throws ApiException The exception that was found.
	 */
	private void CheckForError (ErrorResponse response) {
		if (nonNull(response.getError())) {
			throw new ApiException(response.getError().getDescription(), response.getError().codeToInt());
		}
	}


}
