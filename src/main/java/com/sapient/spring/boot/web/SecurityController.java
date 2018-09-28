/**
 * 
 */
package com.sapient.spring.boot.web;

import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.spring.boot.dto.Security;

/**
 * @author arawa3
 *
 */
@RestController
public class SecurityController {
	
	private int randomBoundMs=100;
	
	ThreadLocalRandom random=ThreadLocalRandom.current();
	
	private static final Logger logger=LoggerFactory.getLogger(SecurityController.class);
	
	@Value("${project.name}")
	private String name;

	@RequestMapping(path="security/ping")
	public String ping(HttpServletRequest request){
		logger.info("From "+request.getRemoteAddr()+request.getRequestURL());
		return "OK "+name;
	}
	
	@RequestMapping(path="security/random-latency")
	public String pingRandomLatency(HttpServletRequest request){
		
		logger.info("From "+request.getRemoteAddr()+request.getRequestURL());
		sleep();
		return "OK "+name;
	}

	private void sleep() {
		try {
			Thread.sleep(random.nextInt(randomBoundMs));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="security")
	public Security create(){
		Security security= new Security();
		security.setName("SECURITY");
		return security;

		
	}
}
