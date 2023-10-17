package com.naver.anytime.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.PostService;
import com.naver.constants.AnytimeConstants;

@RestController
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
	public int updateBoardStatus(int board_id, int approvalStatus, String rejectionreason) {
		logger.info("board_id : " + board_id + "approvalStatus : " + approvalStatus);
		int result = 0;
		//if (approvalStatus != AnytimeConstants.BOARD_APPROVAL_SCHEDULED_FOR_DENIAL) {
			result = boardService.updateBoardStatus(board_id, approvalStatus);
		//}
//		else {
//			result = 1;
//		}
		logger.info("result : " + result);
		return result;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public String updateBoardStatusComplete() {

		return "0";
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
