package com.clickatell.example;

import com.clickatell.sdk.ClickatellSdk;
import com.clickatell.sdk.Message;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;


import java.net.UnknownHostException;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Slf4j
public abstract class Tester extends ApiConfig {

	protected ClickatellSdk click;

	public abstract void init ();

	public abstract void testSendSingleMessage ();
	public abstract void testCoverage ();
	public abstract void testMultipleNumbers();
	public abstract void testGetBalance();

	protected void testCoverageImpl (ClickatellSdk click){
		// Lets do a coverage test:
		log.debug("\nTESTING COVERAGE");
		try {
			double reply = click.getCoverage("27820909090");
			if (reply < 0) {
				log.debug("Route coverage failed");
			} else {
				log.debug("Route coverage succeded, message could cost:"
						+ reply);
			}
		} catch (UnknownHostException e) {
			log.error("Host could not be found");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected void testGetBalanceImpl (ClickatellSdk click) {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING GET BALANCE");
		try {
			double response = click.getBalance();
			log.debug("response: {}", response);
		} catch (UnknownHostException e) {
			log.error("Host could not be found");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	public void testMultipleNumbersImpl (ClickatellSdk click) {
		// Lets send one message to multiple people:
		log.debug("\nTESTING SEND MULTIPLE NUMBERS ONE MESSAGE");
		try {
			Message replies[] = click.sendMessage(new String[]{
					"27821234567", "27829876543"}, "ApiConfig message");
			for (Message s : replies) {
				log.debug("Message: {}",s);
			}
		} catch (UnknownHostException e) {
			log.error("Host could not be found");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	*/
}
