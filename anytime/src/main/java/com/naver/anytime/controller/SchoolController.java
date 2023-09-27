package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.SchoolService;

@Controller
public class SchoolController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController2.class);

	private SchoolService schoolService;
	private BoardService boardService;
	private PostService postService;

	@Autowired
	public SchoolController(SchoolService schoolService, BoardService boardService, PostService postService) {

		this.schoolService = schoolService;
		this.boardService = boardService;
		this.postService = postService;
	}

	// 학교별 커뮤니티 페이지 접근 주소 @PathVariable 사용
	// {schoolName}은 school database에 저장된 학교별 도메인 값
	// {schoolName}을 활용하여 학교 별 게시판 & 게시물 등을 출력
	@RequestMapping(value = "/{schoolName}", method = RequestMethod.GET)
	public String getPage(@PathVariable String schoolName) {

		return "/main/community";
	}

//	@RequestMapping(value = "/{schoolName}", method = RequestMethod.GET)
//	public ModelAndView getPage2(@PathVariable String schoolName, ModelAndView mv) {
//
//		//int commonBoardNums = boardService.getCommonBoardNums(schoolName);
//		mv.setViewName("main/community");
//
//		return mv;
//	}
}
