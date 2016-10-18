package com.clickatell.sdk;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * This is the Message class that gets used as return values for some of the
 * functions.
 *
 * @author Dominic Schaff <dominic.schaff@gmail.com>
 */
@Data
@Accessors(fluent = true)
public class Message {


	public String number = null, message_id = null, content = null,
			charge = null, status = null, error = null, statusString;

	public Integer statusToInt(){
		return Integer.valueOf(status);
	}
	public Message (String message_id) {
		this.message_id = message_id;
	}
	public Message (String message_id,  String status_id) {
		this(message_id); status = status_id;
	}

	public Message () {
	}

	public String toString () {
		if (message_id != null) {
			return number + ": " + message_id;
		}
		return number + ": " + error;
	}
}
