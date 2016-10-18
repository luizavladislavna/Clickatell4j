package com.clickatell.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestReguest {

	List<String> to;
	String text;

	private RestReguest (List<String> to, String text) {
		this.to = to;
		this.text = text;
	}

	public static RestReguest of (List<String> to, String text) {return new RestReguest(to, text);}
	public static RestReguest of (String to, String text) {
		RestReguest request = new RestReguest(new ArrayList<>(), text);
		request.to.add(to);
		return request;
	}
}
