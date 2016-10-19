package com.clickatell.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class SendMessageRequest {

	List<String> to;
	String text;
	String from;

	Integer validityPeriod;

	private SendMessageRequest (String from, List<String> to, String text) {
		this.to = to;
		this.text = text;
		this.from = from;
	}

	private SendMessageRequest (String from, String to, String text) {
		this(from, Arrays.asList(to), text);
	}

	private SendMessageRequest (List<String> to, String text) {
		this(null, to, text);
	}

	private SendMessageRequest (String to, String text) {
		this(null, Arrays.asList(to),text);
	}

	public static SendMessageRequest of (String from, List<String> to, String text) {return new SendMessageRequest(from, to, text);}

	public static SendMessageRequest of (List<String> to, String text) {return new SendMessageRequest(to, text);}

	public static SendMessageRequest of (String to, String text) { return new SendMessageRequest(to, text); }

	public static SendMessageRequest of (String from, String to, String text) {return new SendMessageRequest(from, to, text);}
}
