package com.clickatell.entity;

import com.clickatell.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class DataBalanceResponse extends ErrorResponse {
	BalanceResponse data;

	public static DataBalanceResponse of (final String json) {
		return (DataBalanceResponse) JsonUtil.fromJson(json, DataBalanceResponse.class);
	}


}
