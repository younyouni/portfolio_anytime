package com.naver.anytime.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;


@RestController
public class ScrapController {

	private static final Logger logger = LoggerFactory.getLogger(ScrapController.class);
	
	private PostService postService;	   
	private BoardService boardService;
	private MemberService memberService;
	
	@Autowired
	public ScrapController(PostService postService, BoardService boardService, MemberService memberService) {
		this.postService = postService;
	    this.boardService = boardService;
	    this.memberService = memberService;
	}
	
	@RequestMapping(value = "/scrap")
	@ResponseBody
	public int insertReport(
			@RequestParam(value = "post_id") int post_id,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int report_user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		
		int scrapResult = 0;
		
		
		
		
		
		return scrapResult;
	}
}
