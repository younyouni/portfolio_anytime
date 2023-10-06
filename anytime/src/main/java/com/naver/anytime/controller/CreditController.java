package com.naver.anytime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditController {
	
	public CreditController() {
		
	}

	@RequestMapping(value ="/calculator", method =RequestMethod.GET)
	public String main(){
		return "credit/credit";
		
	}
	
	
}
