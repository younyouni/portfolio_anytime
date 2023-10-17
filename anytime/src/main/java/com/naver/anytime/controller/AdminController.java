package com.naver.anytime.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
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
	public ModelAndView getBoardAdmin(ModelAndView mv) {
		List<Board> boardrequest = null;
		boardrequest = boardService.getBoardRequest();

		mv.addObject("boardrequest", boardrequest);
		mv.setViewName("/admin/boardAdmin");
		return mv;
	}

	@RequestMapping(value = "/updateBoardStatus", method = RequestMethod.POST)
	@ResponseBody
	public int updateBoardStatus(@RequestParam("board_id") int board_id,
			@RequestParam("approvalStatus") int approvalStatus,
			@RequestParam("rejectionreason") String rejectionreason) {
		return boardService.updateApprovalStatus(board_id, approvalStatus, rejectionreason);
	}

	@Scheduled(cron = "0 0 * * * ?" /*매일마다*/) 
	// "0 0/5 * 1/1 * ?" 5분마다
	/*
	 * 0: 초 0/5: 5분 간격 (매 5분) 1/1: 매일 * : 매월 ?: 요일을 지정하지 않음
	 */
	public int updateBoardStatusComplete() {

		return boardService.updateBoardStatusComplete();
	}

	@RequestMapping(value = "/reportAdmin", method = RequestMethod.GET)
	public String getReportAdmin() {
		return "/admin/reportAdmin";
	}

	@RequestMapping(value = "/adminNotice", method = RequestMethod.GET)
	public String getAdminNotice() {
		return "/admin/adminNotice";
	}

}
