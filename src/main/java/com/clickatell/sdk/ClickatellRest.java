package com.clickatell.sdk;

import com.clickatell.entity.RestErrorResponse;
import com.clickatell.entity.RestMessage;
import com.clickatell.entity.RestMessages;
import com.clickatell.entity.RestReguest;
import com.clickatell.entity.RestResponse;
import com.clickatell.exxeption.ApiConnectionException;
import com.clickatell.exxeption.ApiException;
import com.clickatell.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * This is an example of how to use the Clickatell REST API. NOTE: this is not
 * the only way, this is just an example. This class can also be used as a
 * library if you wish.
 *
 * @author Dominic Schaff <dominic.schaff@gmail.com>
 * @date Dec 2, 2014
 */
@Slf4j
public class ClickatellRest implements ClickatellSdk {
	/**
	 * @var The URL to use for the base of the REST API.
	 */
	private static final String CLICKATELL_REST_BASE_URL = "https://api.clickatell.com/rest/";
	private static final String POST = "POST", GET = "GET", DELETE = "DELETE";
	private final ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * @var The three private variables to use for authentication.
	 */
	private String apiKey;

	/**
	 * Create a REST object, and set the auth, but not test the auth.
	 */
	public ClickatellRest (String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * This will attempt to get your current balance.
	 *
	 * @return Your balance.
	 * @throws Exception This will be thrown if your auth details were incorrect.
	 */
	@Override
	public double getBalance () throws Exception {
		// Send Request:
		String response = this.excute("account/balance", GET, null);
		JSONObject obj = (JSONObject) JSONValue.parse(response);

		CheckForError(obj);

		JSONObject objData = (JSONObject) obj.get("data");
		String balance = (String) objData.get("balance");
		return Double.parseDouble(balance);
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

	public RestMessage sendMessage (String number, String message) {
		String jsonResponse = this.excute("message", POST,
				JsonUtil.asJsonString(RestReguest.of(number, message))
		);
		log.debug("!!!response : {}", jsonResponse);

		RestResponse response = RestResponse.fromJson(jsonResponse, objectMapper);
		log.debug("!!!response : {}", response);
		CheckForError(response);
		Optional<RestMessage> responseMsg = response.getData().getMessage().stream().findFirst();
		if (responseMsg.isPresent()) {
			RestMessage msg = responseMsg.get();
			CheckForError(responseMsg.get());
			return msg;
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
	@Override
	public RestMessages sendMessage (List<String> numbers, String message) {
		String jsonResponse = this.excute("message", POST,
				JsonUtil.asJsonString(RestReguest.of(numbers, message))
		);
		RestResponse response = RestResponse.fromJson(jsonResponse, objectMapper);
		log.debug("!!!response : {}", response);
		CheckForError(response);

		RestMessages messagesReturn = new RestMessages();
		List<RestMessage> messages = response.getData()
				.getMessage().stream()
				.filter(msg -> nonNull(msg.getError()))
				.collect(Collectors.toList());
		messagesReturn.setSuccess(messages);

		List<RestMessage> errors = response.getData()
				.getMessage().stream()
				.filter(msg -> isNull(msg.getError()))
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
	public Message getMessageStatus (String messageId) throws Exception {
		String response = this.excute("message/" + messageId, GET, null);
		JSONObject obj = (JSONObject) JSONValue.parse(response);

		CheckForError(obj);
		Message msg = new Message();
		JSONObject objData = (JSONObject) obj.get("data");
		msg.message_id = (String) objData.get("apiMessageId");
		msg.charge = String.valueOf(objData.get("charge"));
		msg.status = (String) objData.get("messageStatus");
		msg.statusString = (String) objData.get("description");

		return msg;
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
	public Message stopMessage (String messageId) throws Exception {
		// Send Request:
		String response = this.excute("message/" + messageId, DELETE, null);
		log.debug("stopMessage response: {}", response);
		JSONObject obj = (JSONObject) JSONValue.parse(response);

		CheckForError(obj);
		Message msg = new Message();
		JSONObject objData = (JSONObject) obj.get("data");
		msg.message_id = (String) objData.get("apiMessageId");
		msg.status = (String) objData.get("messageStatus");
		msg.statusString = (String) objData.get("description");

		return msg;
	}

	/**
	 * This will allow you to use any feature of the API. Note that you can do
	 * more powerful things with this function. And as such should only be used
	 * once you have read the documentation, as the parameters are passed
	 * directly to the API.
	 *
	 * @param numbers  The list of numbers that must be sent to.
	 * @param message  The message that is to be sent.
	 * @param features The extra features that should be included.
	 * @return An Array of Message objects which will contain the information
	 * from the request.
	 * @throws Exception If there is anything wrong with the submission this will get
	 *                   thrown.
	 */
	@Override
	public Message[] sendAdvancedMessage (String[] numbers, String message,
	                                      HashMap<String, String> features) throws Exception {
		ArrayList<Message> messages = new ArrayList<Message>();
		String dataPacket = "{\"to\":[\"" + numbers[0];
		for (int x = 1; x < numbers.length; x++) {
			dataPacket += "\",\"" + numbers[x];
		}
		dataPacket += "\"],\"text\":\"" + message + "\"";
		for (Entry<String, String> entry : features.entrySet()) {
			dataPacket += ",\"" + entry.getKey() + "\":\"" + entry.getValue()
					+ "\"";
		}
		dataPacket += "}";

		// Send Request:
		String response = this.excute("message", POST, dataPacket);
		JSONObject obj = (JSONObject) JSONValue.parse(response);

		CheckForError(obj);
		JSONObject objData = (JSONObject) obj.get("data");
		JSONArray msgArray = (JSONArray) objData.get("message");
		for (int i = 0; i < msgArray.size(); i++) {
			Message msg = new Message();
			JSONObject firstMsg = (JSONObject) msgArray.get(i);
			msg.number = (String) firstMsg.get("to");
			if (!((boolean) firstMsg.get("accepted"))) {
				try {
					CheckForError(firstMsg);
				} catch (Exception e) {
					msg.error = e.getMessage();
				}
			} else {
				msg.message_id = (String) firstMsg.get("apiMessageId");
			}
			messages.add(msg);
		}
		return messages.toArray(new Message[0]);
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
	public double getCoverage (String number) throws Exception {
		String response = this.excute("coverage/" + number, GET, null);
		JSONObject obj = (JSONObject) JSONValue.parse(response);

		CheckForError(obj);
		JSONObject objData = (JSONObject) obj.get("data");

		if (!((boolean) objData.get("routable"))) {
			return -1;
		}
		return Double.parseDouble(String.valueOf(objData
				.get("minimumCharge")));
	}


	/**
	 * This executes a POST query with the given parameters.
	 *
	 * @param resource The URL that should get hit.
	 * @return The content of the request.
	 * @throws UnknownHostException
	 */
	private String excute (String resource, String method, String dataPacket) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(CLICKATELL_REST_BASE_URL + resource);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("X-Version", "1");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			String l = "0";
			if (dataPacket != null) {
				l = Integer.toString(dataPacket.getBytes().length);
			}
			connection.setRequestProperty("Content-Length", "" + l);
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(dataPacket != null);

			// Send request
			if (dataPacket != null) {
				DataOutputStream wr = new DataOutputStream(
						connection.getOutputStream());
				wr.writeBytes(dataPacket);
				wr.flush();
				wr.close();
			}

			// Get Response
			connection.getResponseCode();
			InputStream stream = connection.getErrorStream();
			if (stream == null) {
				stream = connection.getInputStream();
			}
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(stream));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			rd.close();
			return response.toString().trim();
		} catch (IOException e) {
			throw new ApiConnectionException(e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * This is an internal function used to shorten other functions. Checks for
	 * an error object, and throws it.
	 *
	 * @param obj The object that needs to be checked.
	 * @throws Exception The exception that was found.
	 */
	private void CheckForError (JSONObject obj) throws Exception {
		log.debug("obj: {}", obj);
		JSONObject objError = (JSONObject) obj.get("error");
		if (objError != null) {
			throw new Exception((String) objError.get("description"));
		}
	}

	private void CheckForError (RestErrorResponse response) {
		if (nonNull(response.getError())) {
			throw new ApiException(response.getError().getDescription(), response.getError().codeToInt());
		}
	}

	@Override
	public Message getMessageCharge (String messageId) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean checkAuth () throws UnknownHostException {
		throw new UnsupportedOperationException();
	}


}
