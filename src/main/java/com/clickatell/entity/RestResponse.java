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
import java.io.Serializable;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestResponse extends RestErrorResponse {
	RestDataResponse data;

	public static RestResponse fromJson(final String json, final ObjectMapper objectMapper) {
		// Convert all checked exceptions to Runtime
		try {
			return objectMapper.readValue(json, RestResponse.class);
		} catch (final JsonMappingException | JsonParseException e) {
			throw new ApiException(e.getMessage(), e);
		} catch (final IOException e) {
			throw new ApiConnectionException(e.getMessage(), e);
		}
	}
}
