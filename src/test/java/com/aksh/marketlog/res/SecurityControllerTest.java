package com.aksh.marketlog.res;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.spring.boot.FirstApplication;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=FirstApplication.class)
public class SecurityControllerTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity("/security/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals("OK SpringBootUT",pingResponse.getBody());
	}

}
