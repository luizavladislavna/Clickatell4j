package com.clickatell.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestMessage   extends RestErrorResponse  {
	private String  to, apiMessageId;
	private  boolean accepted;
}
