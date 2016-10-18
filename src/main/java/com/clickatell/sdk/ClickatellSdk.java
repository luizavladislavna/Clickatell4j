package com.clickatell.sdk;

import com.clickatell.entity.RestMessage;
import com.clickatell.entity.RestMessages;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
public interface ClickatellSdk {

	double getBalance () throws Exception;

	boolean checkAuth () throws UnknownHostException;

	double getCoverage (String number) throws Exception;

	//Message sendMessage (String number, String message) throws Exception;

	//Message[] sendMessage (String[] numbers, String message) throws Exception;

	Message getMessageStatus (String messageId) throws Exception;

	Message stopMessage (String messageId) throws Exception;

	Message[] sendAdvancedMessage (String[] numbers, String message,
	                               HashMap<String, String> features) throws Exception;


	Message getMessageCharge (String messageId) throws Exception;


	// Refactored:
	RestMessage sendMessage (String number, String message);

	RestMessages sendMessage (List<String> numbers, String message);
}
