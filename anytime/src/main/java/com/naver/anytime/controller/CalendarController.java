package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		int user_id = memberService.getUserId(login_id);
		
		List<Calendar> Result = calendarService.getCalendarList(user_id);
		
		System.out.println("이거 됨? " + Result);
		return Result;
	}
	
	
	
	
}
