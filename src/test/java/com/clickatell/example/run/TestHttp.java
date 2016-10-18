package com.clickatell.example.run;

import com.clickatell.example.Tester;
import com.clickatell.sdk.ClickatellHttp;
import com.clickatell.sdk.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Slf4j
public class TestHttp extends Tester {
	@Override
	public void init () {
		click = new ClickatellHttp(USERNAME, APIID, PASSWORD);
	}

	@Override
	public void testSendSingleMessage () {

	}

	@Test
	public void testAuth() throws UnknownHostException {
		// Using click, test auth:
		log.debug("TESTING GET AUTH");
		assertTrue(click.checkAuth());
	}

	/*
	@Test
	public void testSendSingleMessage () {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING SEND SINGLE MESSAGE");
		try {
			Message response = click.sendMessage("27821234567",
					"Hello, this is a test message!");
			log.debug("response: {}", response);
			if (response.error != null) {
				log.error(response.error);
			} else {
				log.debug("\nTESTING GET STATUS:");
				log.debug("getMessageStatus: {}", click.getMessageStatus(response.message_id));
				log.debug("\nTESTING STOP:");
				log.debug("stopMessage: {}", click.stopMessage(response.message_id));
				log.debug("\nTESTING GET CHARGE:");
				Message replies = click
						.getMessageCharge(response.message_id);
				log.debug("Charge: {}" , replies.charge);
				log.debug("Status: {}" , replies.status);
			}
		} catch (UnknownHostException e) {
			log.error("Host could not be found");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}



	@Test
	public void testMultipleNumbers () {
		super.testMultipleNumbersImpl(click);
	}
*/
	@Test
	public void testGetBalance () {
		super.testGetBalanceImpl(click);
	}

	@Test
	public void testCoverage () {
		super.testCoverageImpl(click);
	}

	@Override
	public void testMultipleNumbers () {

	}

}
