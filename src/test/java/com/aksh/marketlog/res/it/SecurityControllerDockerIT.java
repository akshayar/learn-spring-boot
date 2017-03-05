package com.aksh.marketlog.res.it;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
@RunWith(SpringRunner.class)
@IfProfileValue(name="integration.env",value="docker")
public class SecurityControllerDockerIT {


	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl="http://localhost:8080";
	
	@Before
	public void setup(){
		System.out.println(System.getenv());
		System.out.println(System.getProperties());
		serverUrl="http://"+System.getProperty("docker.host.address")+":8080";
		System.out.println("In unit test server url:"+serverUrl);
	}

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity(serverUrl+"/security/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		assertEquals("OK DOCKER",pingResponse.getBody());
	}
}
