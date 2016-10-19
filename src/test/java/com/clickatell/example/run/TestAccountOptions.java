package com.clickatell.example.run;

import com.clickatell.example.Tester;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by tymoshenkol on 19-Oct-16.
 */
@Slf4j
public class TestAccountOptions extends Tester {

	@Test
	public void testGetBalance () {
		// Assuming the auth was successful, lets send one message, to one
		// recipient:
		log.debug("\nTESTING GET BALANCE");

		double response = click.getBalance();
		log.debug("balance: {}", response);
		assertThat(response).isGreaterThan(0);

	}
}
