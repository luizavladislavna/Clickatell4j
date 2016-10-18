package com.clickatell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestMessages {
	private List<RestMessage> success;
	private List<RestMessage> error;

}
