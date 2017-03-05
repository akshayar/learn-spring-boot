/**
 * 
 */
package com.sapient.spring.boot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.spring.boot.dto.Trade;

/**
 * @author arawa3
 *
 */
@RestController
public class TradeController {
	
	@RequestMapping("trade/ping")
	public String ping(){
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.PUT,path="trade")
	private Trade create(){
		Trade trade=new Trade();
		trade.setSymbol("FIRST");
		return trade;
		
	}

}
