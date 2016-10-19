package com.clickatell.example.run;

import com.clickatell.entity.CoverageResponse;
import com.clickatell.entity.Message;
import com.clickatell.example.Tester;
import com.clickatell.exxeption.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by tymoshenkol on 19-Oct-16.
 */
@Slf4j
public class TestMessageOptions extends Tester {

	@Test
	public void testCoverage () {
		// Lets do a coverage test:
		log.debug("\nTESTING COVERAGE");

		CoverageResponse reply = click.getMessageCoverage("27820909090");
		assertThat(reply.isRoutable()).isTrue();

		assertThat(reply.getMinimumCharge()).isGreaterThan(0);
		log.debug("Coverage: {}", reply);

	}

	@Test(expectedExceptions = ApiException.class, expectedExceptionsMessageRegExp = "Invalid or missing parameter: apiMessageId")
	public void testMsgStatusError () throws Exception {
		Message msg = click.getMessageStatus("");
		log.debug("--msg: {}", msg);
	}

	@Test
	public void testMsgStatus () throws Exception {
		Message msg = click.getMessageStatus("9bc219770cdea28e3b7727d2f23589e8");
		log.debug("msg: {}", msg);
		assertThat(msg.getMessageStatus()).isEqualTo("003");
		assertThat(msg.getCharge()).isGreaterThan(0);
	}
}
