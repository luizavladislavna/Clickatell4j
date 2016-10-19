package com.clickatell.example.run;

import com.clickatell.entity.Message;
import com.clickatell.entity.AcceptedMessages;
import com.clickatell.example.Tester;
import com.clickatell.exxeption.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by tymoshenkol on 19-Oct-16.
 */
@Slf4j
public class TestMessageSend extends Tester {


	@Test(expectedExceptions = ApiException.class, expectedExceptionsMessageRegExp = "Cannot route message")
	public void testSendSingleMessageWithError () {
		click.sendMessage("123", "Hello, this is a test message!");
	}

	@Test
	public void testSendSingleMessage () {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING SEND SINGLE MESSAGE");

		Message response = click.sendMessage("380950447816",
				"Hello, this is a test message!");
		log.debug("response: {}", response);
		assertThat(response).isNotNull();
		assertThat(response.getMessageStatus()).isEqualTo("003");
	}

	@Test
	public void testStopMessage () {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING SEND AND STOP SINGLE MESSAGE");

		Message response = click.sendMessage("380950447816", "Hello!");

		assertThat(response).isNotNull();
		assertThat(response.getMessageStatus()).isEqualTo("003");

		response = click.stopMessage(response.getApiMessageId());
		log.debug("response: {}",response);
	}

	@Test
	public void testMultipleNumbers () {
		// Lets send one message to multiple people:
		log.debug("\nTESTING SEND MULTIPLE NUMBERS ONE MESSAGE");
		List toNumbers = Arrays.asList(new String[]{"380950447816", "000"});
		AcceptedMessages replies = click.sendMessage(toNumbers, "ApiConfig message");

		assertThat(replies.getError()).isNotEmpty();
		assertThat(replies.getError().size()).isEqualTo(1);
		replies.getError().forEach(m -> assertThat(m.isAccepted()).isFalse());

		assertThat(replies.getSuccess()).isNotEmpty();
		assertThat(replies.getError().size()).isEqualTo(1);
		replies.getSuccess().forEach(m -> assertThat(m.isAccepted()).isTrue());
	}

}
