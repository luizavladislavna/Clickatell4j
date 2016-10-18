package com.clickatell.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Data
@NoArgsConstructor
@IJsonEntity
public class RestError {
	String code, documentation, description;
	public Integer codeToInt(){
		return Integer.valueOf(code);
	}
}
