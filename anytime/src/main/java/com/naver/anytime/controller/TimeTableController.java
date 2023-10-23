package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@RestController
public class TimeTableController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeTableController.class);
	
	private TimeTableService timeTableService;
	private TimeTable_detailService timeTable_detailService;
	
	@Autowired
	public TimeTableController(TimeTableService timeTableService, TimeTable_detailService timeTable_detailService) {
		this.timeTableService = timeTableService;
		this.timeTable_detailService = timeTable_detailService;
	}
	
	@RequestMapping(value = "/timetable")
	@ResponseBody
	public ModelAndView timeTable(
			ModelAndView mv
			) {
		mv.setViewName("timetable/timeTable");
		return mv;
	}
}
