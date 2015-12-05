package com.sapient.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.spring.boot.dto.Trade;

@EnableAutoConfiguration
@RestController
public class FirstApplication {

	@RequestMapping("ping")
	public String ping(){
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="trade")
	private Trade create(){
		Trade trade=new Trade();
		trade.setSymbol("FIRST");
		return trade;
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(new Class[]{FirstApplication.class}, args);
	}
}
