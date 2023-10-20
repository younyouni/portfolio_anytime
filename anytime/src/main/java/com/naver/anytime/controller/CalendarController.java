package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Calendar;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CalendarService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;

@RestController
public class CalendarController {

	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
	
	private CalendarService calendarService;
	private ReportService reportService;
	private PostService postService;	   
	private BoardService boardService;
	private CommentService commentService;
	private MemberService memberService;
	
	@Autowired
	public CalendarController(CalendarService calendarService, ReportService reportService, PostService postService, BoardService boardService, CommentService commentService, MemberService memberService) {
		this.calendarService = calendarService;
		this.reportService = reportService;
		this.postService = postService;
	    this.boardService = boardService;
	    this.commentService = commentService;
	    this.memberService = memberService;
	}
	
	@RequestMapping(value = "/calendar")
	@ResponseBody
	public ModelAndView Calendar(
			ModelAndView mv
			) {
		mv.setViewName("calendar/Calendar");
		return mv;
	}
	
	@RequestMapping(value = "/calendarlist")
	@ResponseBody
	public List<Calendar> CalendarList(
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		List<Calendar> Result = calendarService.getCalendarList(user_id);
		
		if(Result != null) {
			System.out.println("캘린더 리스트 출력 완료");
		}else {
			System.out.println("캘린더 리스트 출력 실패");
		}
		return Result;
	}
	
	@RequestMapping(value = "/calendaradd", method = RequestMethod.POST)
	@ResponseBody
	public int insertCalendar(
			@RequestParam("title") String title,
			@RequestParam("color") String color,
			@RequestParam("start") String start,
			@RequestParam("end") String end,
			@RequestParam(name = "allday", defaultValue = "0") int allday,
			@RequestParam("description") String description,		
			Principal principal
			) {
		int Result = 0;
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);			//로그인한 유저 user_id
		
		int addcheck = calendarService.insertCalendar(title, user_id, color, start, end, allday, description);
		
		if(addcheck == 1) {
			Result = 1;
		}
		
		return Result;
	}
	
	
	
}
