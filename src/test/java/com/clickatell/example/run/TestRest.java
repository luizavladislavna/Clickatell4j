package com.clickatell.example.run;

import com.clickatell.entity.RestMessage;
import com.clickatell.entity.RestMessages;
import com.clickatell.example.Tester;
import com.clickatell.exxeption.ApiException;
import com.clickatell.sdk.ClickatellRest;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Slf4j
public class TestRest extends Tester {

	@BeforeTest
	public void init () {
		click = new ClickatellRest(APIKEY);
	}

	@Test
	public void testMultipleNumbers () {
		// Lets send one message to multiple people:
		log.debug("\nTESTING SEND MULTIPLE NUMBERS ONE MESSAGE");
		List toNumbers = Arrays.asList(new String[]{"380950447816", "000"});
		RestMessages replies = click.sendMessage(toNumbers, "ApiConfig message");

		assertThat(replies.getError()).isNotEmpty();
		assertThat(replies.getError().size()).isEqualTo(1);
		replies.getError().forEach(m -> assertThat(m.isAccepted()).isFalse());

		assertThat(replies.getSuccess()).isNotEmpty();
		assertThat(replies.getError().size()).isEqualTo(1);
		replies.getSuccess().forEach(m -> assertThat(m.isAccepted()).isTrue());
	}


	@Test(expectedExceptions = ApiException.class, expectedExceptionsMessageRegExp = "Cannot route message")
	public void testSendSingleMessageWithError () {
		click.sendMessage("123", "Hello, this is a test message!");
	}

	@Test
	public void testSendSingleMessage () {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING SEND SINGLE MESSAGE");

		RestMessage response = click.sendMessage("380950447816",
				"Hello, this is a test message!");
		log.debug("response: {}", response);
		assertThat(response.getError()).isNull();
		assertThat(response.isAccepted()).isTrue();



				/*
				log.debug("\nTESTING STOP:");
				log.debug("stopMessage: {}",  click.stopMessage(response.message_id));
				log.debug("\nTESTING GET STATUS:");
				Message msg = click
						.getMessageStatus(response.message_id);
				log.debug("ID:" + msg.message_id);
				log.debug("Status:" + msg.status);
				log.debug("Status Description:" + msg.statusString);
				log.debug("Charge:" + msg.charge);
				log.debug("\nTESTING STOP MESSAGE");

				Message msgStatus = click
						.stopMessage(response.message_id);
				log.debug("ID:" + msgStatus.message_id);
				log.debug("Status:" + msgStatus.status);
				log.debug("Status Description:"
						+ msgStatus.statusString);
						*/
	}

	@Test
	public void testCoverage () {
		super.testCoverageImpl(click);
	}


	@Test
	public void testGetBalance () {
		super.testGetBalanceImpl(click);
	}

}
