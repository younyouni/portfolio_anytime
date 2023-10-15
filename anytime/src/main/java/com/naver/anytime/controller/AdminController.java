package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.PostService;

@Controller
//@RequestMapping(value = "/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private BoardService boardService;
	private PostService postService;

	@Autowired
	public AdminController(BoardService boardService, PostService postService) {
		this.boardService = boardService;
		this.postService = postService;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage() {
		return "/admin/dashboard";
	}

	@RequestMapping(value = "/boardAdmin", method = RequestMethod.GET)
	public String getBoardAdmin() {
		return "/admin/boardAdmin";
	}
	
	@RequestMapping(value = "/reportAdmin", method = RequestMethod.GET)
	public String getReportAdmin() {
		return "/admin/reportAdmin";
	}

}
