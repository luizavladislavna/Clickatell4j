package com.clickatell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestDataResponse{
	private List<RestMessage> message;
}
