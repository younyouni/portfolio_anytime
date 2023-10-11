package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.service.MemberService;

@RestController
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	private MemberService memberService;
	
	public MessageController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping(value = "/message")
	@ResponseBody
	public ModelAndView message(
			ModelAndView mv	
			) {

		mv.setViewName("message/Message");
		return mv;
	}
}
