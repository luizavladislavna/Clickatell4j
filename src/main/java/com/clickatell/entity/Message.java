package com.clickatell.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class Message {
	String messageStatus;
	String apiMessageId;
	Double charge;
	String description;

	public Integer status(){
		requireNonNull(messageStatus);
		return Integer.valueOf(messageStatus);
	}


}
