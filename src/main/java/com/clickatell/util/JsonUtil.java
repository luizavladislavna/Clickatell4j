package com.clickatell.util;

import com.clickatell.exxeption.ApiConnectionException;
import com.clickatell.exxeption.ApiException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by tymoshenkol on 07-Oct-16.
 */
public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String asJsonString (final Object obj) {
		try {
			final String jsonContent = objectMapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Object fromJson (final String json, Class<T> clazz) {
		// Convert all checked exceptions to Runtime
		try {
			return objectMapper.readValue(json, clazz);
		} catch (final JsonMappingException | JsonParseException e) {
			throw new ApiException(e.getMessage(), e);
		} catch (final IOException e) {
			throw new ApiConnectionException(e.getMessage(), e);
		}
	}
}
