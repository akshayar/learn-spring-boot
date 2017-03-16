/**
 * 
 */
package com.sapient.spring.boot.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
	
	private static final Logger logger=Logger.getLogger(SecurityController.class);
	
	@Value("${project.name}")
	private String name;

	@RequestMapping(path="security/ping")
	public String ping(HttpServletRequest request){
		logger.info("Pinged from "+request.getRemoteAddr());
		return "OK "+name;
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="security")
	public Security create(){
		Security security= new Security();
		security.setName("SECURITY");
		return security;

		
	}
}
