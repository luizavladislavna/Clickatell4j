package com.clickatell.example;

import com.clickatell.sdk.ClickatellRest;
import com.clickatell.sdk.ClickatellSdk;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;

/**
 * Created by tymoshenkol on 18-Oct-16.
 */
@Slf4j
public abstract class Tester extends ApiConfig {

	protected ClickatellSdk click;

	@BeforeTest
	public void init () {
		click = ClickatellRest.of(APIKEY);
	}

}
