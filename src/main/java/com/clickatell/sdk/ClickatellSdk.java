package com.clickatell.sdk;

import com.clickatell.entity.CoverageResponse;
import com.clickatell.entity.Message;
import com.clickatell.entity.AcceptedMessages;

import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
public interface ClickatellSdk {

	Message sendMessage (String number, String message);

	Message sendMessage (String number, String message, String from);

	AcceptedMessages sendMessage (List<String> numbers, String message);

	AcceptedMessages sendMessage (List<String> numbers, String message, String from);

	CoverageResponse getMessageCoverage (String number);

	double getMessageCoverageCharge (String number);

	double getBalance ();

	Message getMessageStatus (String messageId);

	Message stopMessage (String messageId);

}
