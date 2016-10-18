package com.clickatell.entity;

import com.clickatell.exxeption.ApiConnectionException;
import com.clickatell.exxeption.ApiException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */

@Data
@NoArgsConstructor
@IJsonEntity
public class RestErrorResponse {
	private RestError error;
}
