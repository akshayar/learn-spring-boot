/**
 * 
 */
package com.sapient.spring.boot.web;

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
	
	@Value("${project.name}")
	private String name;

	@RequestMapping(path="security/ping")
	public String ping(){
		return "OK "+name;
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="security")
	public Security create(){
		Security security= new Security();
		security.setName("SECURITY");
		return security;

		
	}
}
