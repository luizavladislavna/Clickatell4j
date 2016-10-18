package com.clickatell.example;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Slf4j
public abstract class ApiConfig {

	public static String USERNAME, APIID, PASSWORD, APIKEY;

	static{
		load();
	}

	public static void load () {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = ApiConfig.class.getClassLoader().getResourceAsStream("test.properties");

			// load a properties file
			prop.load(input);
			USERNAME = prop.getProperty("USERNAME");
			APIID = prop.getProperty("APIID");
			PASSWORD = prop.getProperty("PASSWORD");
			APIKEY = prop.getProperty("APIKEY");

		} catch (IOException ex) {
			ex.printStackTrace();
			log.error(ex.getMessage(), ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
