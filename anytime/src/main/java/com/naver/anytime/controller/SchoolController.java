package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.SchoolService;

@Controller
public class SchoolController {

	private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

	private SchoolService schoolService;
	private BoardService boardService;
	private PostService postService;
	private MemberService memberService;

	@Autowired
	public SchoolController(SchoolService schoolService, BoardService boardService, PostService postService,
			MemberService memberService) {

		this.schoolService = schoolService;
		this.boardService = boardService;
		this.postService = postService;
		this.memberService = memberService;
	}

	// 학교별 커뮤니티 페이지 접근 주소 @PathVariable 사용
	// {schoolName}은 school database에 저장된 학교별 도메인 값
	// {schoolName}을 활용하여 학교 별 게시판 & 게시물 등을 출력
//	@RequestMapping(value = "/{schoolName}", method = RequestMethod.GET)
//	public String getCommunityPage(@PathVariable String schoolName) {
//
//		return "/main/community";
//	}

	@RequestMapping(value = "/{schoolDomain}", method = RequestMethod.GET)
	public ModelAndView getLoginCommunityPage(@PathVariable("schoolDomain") String schoolDomain, ModelAndView mv,
			Principal userPrincipal) {

		mv.setViewName("main/community");

		if (userPrincipal != null) {
			String id = userPrincipal.getName();
			logger.info("인증된 사용자 : " + userPrincipal.getName());
			logger.info("유저 학교 도메인 : " + memberService.getSchoolDomain(id));

			if (!schoolDomain.equals(memberService.getSchoolDomain(id))) {
				mv.setViewName("redirect:/" + memberService.getSchoolDomain(id));
				logger.info("다른학교 출입");
			}
			Member m = memberService.getLoginMember(id);
			int[] board_ids = boardService.getBoardIds(id);
			List<List<Post>> commonPostsByBoard = postService.getPostListByBoard(board_ids);

			mv.addObject("member", m);
			mv.addObject("list", commonPostsByBoard);

//			for (List<Post> board : commonPostsByBoard) {
//				for (Post post : board) {
//					logger.info("게시판이름: " + post.getBOARDNAME());
//					logger.info("게시글제목: " + post.getSUBJECT());
//					logger.info("게시글작성일자: " + post.getPOST_DATE());
//				}
//			}
		}

		return mv;
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
