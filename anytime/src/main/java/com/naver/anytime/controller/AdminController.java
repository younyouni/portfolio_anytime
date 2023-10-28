package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.DailyData;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;
import com.naver.anytime.domain.Report;
import com.naver.anytime.domain.School;
import com.naver.anytime.domain.UserCustom;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.DailyDataService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;
import com.naver.constants.AnytimeConstants;

@Controller
//@RequestMapping(value = "/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private BoardService boardService;
	private PostService postService;
	private ReportService reportService;
	private CommentService commentService;
	private MemberService memberService;
	private DailyDataService dailyDataService;

	@Autowired
	public AdminController(BoardService boardService, PostService postService, ReportService reportService,
			CommentService commentService, MemberService memberService, DailyDataService dailyDataService) {
		this.boardService = boardService;
		this.postService = postService;
		this.reportService = reportService;
		this.commentService = commentService;
		this.memberService = memberService;
		this.dailyDataService = dailyDataService;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage(@AuthenticationPrincipal UserCustom user, ModelAndView mv) {
		String login_id = user.getUsername();
		String email = user.getEmail();

		// 일별 데이터
		DailyData dataTrend = dailyDataService.getDataTrend();

		// 회원 가입자 추이
		List<DailyData> registrationTrend = dailyDataService.getRegistrationTrend();

		// 신고 사유 데이터
		List<Report> reportCount = reportService.getReportCount();

		// 학교 랭킹
		List<School> schoolRanking = dailyDataService.getSchoolRanking();

		// 게시판 랭킹
		List<Board> boardRanking = dailyDataService.getBoardRanking();

		// 게시판 승인 to do list
		DailyData todoList = dailyDataService.getTodoList();
		logger.info("로그인아이디 : " + login_id);
		logger.info("이메일 : " + email);

		mv.addObject("login_id", login_id);
		mv.addObject("email", email);
		mv.addObject("dataTrend", dataTrend);
		mv.addObject("registrationTrend", registrationTrend);
		mv.addObject("reportCount", reportCount);
		mv.addObject("schoolRanking", schoolRanking);
		mv.addObject("boardRanking", boardRanking);
		mv.addObject("todoList", todoList);
		mv.setViewName("/admin/dashboard");
		return mv;
	}
	
	@Scheduled(cron = "0 0 * * * ?" /* 매일마다 */)
	public int insertDailyDataScheduled() {
		return dailyDataService.insertDailyData();
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

	// "0 0/5 * 1/1 * ?" 5분마다
	// 0: 초 0/5: 5분 간격 (매 5분) 1/1: 매일 * : 매월 ?: 요일을 지정하지 않음
	@Scheduled(cron = "0 0 * * * ?" /* 매일마다 */)
	public int updateBoardStatusCompleteScheduled() {
		return boardService.updateBoardStatusComplete();
	}

	@RequestMapping(value = "/updateBoardStatusImmediately", method = RequestMethod.GET)
	@ResponseBody
	public int updateBoardStatusImmediately(@RequestParam("board_id") int board_id) {
		int approvalStatus = 0;
		int currentStatus = boardService.getBoardStatus(board_id);

		if (currentStatus == 1) {
			approvalStatus = 2;
		} else {
			approvalStatus = 1;
		}
		return boardService.updateBoardStatusImmediately(board_id, approvalStatus);
	}

	@RequestMapping(value = "/boardtotal", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBoardTotalList(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam("searchKey") int searchKey,
			@RequestParam(value = "keyword", required = true) String keyword) {

		if (keyword.equals("null")) {
			keyword = null;
		}
		int limit = 10;

		int listcount = boardService.getListCount(searchKey, keyword);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Board> boardTotal = boardService.getBoardTotalList(page, limit, searchKey, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardTotal", boardTotal);
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("limit", limit);

		return map;
	}

	@RequestMapping(value = "/reportAdmin", method = RequestMethod.GET)
	public String getReportAdmin() {
		return "/admin/reportAdmin";
	}

	@RequestMapping(value = "/reportListAdmin", method = RequestMethod.GET)
	@ResponseBody
	public List<Report> getReportList(
			@RequestParam(value = "order", defaultValue = "1", required = false) Integer order) {
		logger.info("order : " + order);
		return reportService.getReportRequest(order);
	}

	@RequestMapping(value = "/reportTotal", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReporttotalList(
			@RequestParam(value = "order", defaultValue = "1", required = false) int order,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page) {

		int limit = 10;

		int listcount = reportService.getListCount();

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Report> reportTotal = reportService.getReportTotalList(order, page, limit);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("reportTotal", reportTotal);
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("limit", limit);

		return map;
	}

	@RequestMapping(value = "/reportProcess", method = RequestMethod.POST)
	@ResponseBody
	public int reportProcess(int content_id, String content_action, String user_action, Principal principal) {
		int admin_id = memberService.getUserId(principal.getName());
		String admin_login_id = principal.getName();
		int isContent = 0;
		int result = 0;

		if (postService.getPost(content_id) != 0) {
			isContent = AnytimeConstants.IS_POST;
		} else {
			isContent = AnytimeConstants.IS_COMMENT;
		}

		if (!content_action.equals("반려")) {
			if (isContent == AnytimeConstants.IS_POST) {
				// 게시물 신고
				postService.updatePostStatus(content_id);
			} else {
				// 코멘트 신고
				commentService.updateCommentStatus(content_id);
			}
		}

		if (!user_action.equals("반려")) {
			// 멤버 정지
			memberService.updateStatusByContentId(content_id, isContent);
		}
		logger.info(Integer.toString(admin_id));
		result = reportService.updateReport(content_id, content_action, user_action, admin_id, admin_login_id);// 반려인 경우
																												// 아닌 경우
																												// 모두 처리
		logger.info("result : " + result);
		return result;
	}

	@RequestMapping(value = "/adminNotice", method = RequestMethod.GET)
	public String getAdminNotice() {
		return "/admin/adminNotice";
	}

	@RequestMapping(value = "/adminNoticeList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAdminNoticeList(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "searchKey", defaultValue = "0", required = false) int searchKey,
			@RequestParam(value = "keyword", defaultValue = "", required = true) String keyword, ModelAndView mv) {

		if (keyword.equals("null")) {
			keyword = null;
		}

		int board_id = 121;// 공지사항 board_id로 변경

		int limit = 10;

		int listcount = postService.getPostTotalListCount(board_id, searchKey, keyword);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Post> notice = postService.getPostTotalList(board_id, page, limit, searchKey, keyword);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("notice", notice);
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("limit", limit);

		return map;
	}

}