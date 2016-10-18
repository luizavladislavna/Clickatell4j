package com.clickatell.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by tymoshenkol on 07-Oct-16.
 */
public class JsonUtil {
	public static String asJsonString (final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
