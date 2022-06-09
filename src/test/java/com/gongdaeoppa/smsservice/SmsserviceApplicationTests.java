package com.gongdaeoppa.smsservice;

import com.gongdaeoppa.smsservice.util.SMSService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class SmsserviceApplicationTests {

	@Test
	void 카페24SMS() {
		new SMSService().sendSMSAsync("공대오빠 " + UUID.randomUUID().toString(), "010");
	}

}
