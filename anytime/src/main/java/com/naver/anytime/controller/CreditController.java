package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditController {
	
	private static final Logger logger = LoggerFactory.getLogger(CreditController.class);
	public CreditController() {
		
	}

	@RequestMapping(value ="/calculator", method =RequestMethod.GET)
	public String main(Model model){
		// security에서 id값 불러오기
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				String id = auth.getName();
				logger.info(id);
		        model.addAttribute("login_id", id );
				
		return "credit/credit";
		
	}
	
	
	
	
}
