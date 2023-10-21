package com.naver.anytime.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TimeTableController {
	
	@RequestMapping(value = "/timetable")
	@ResponseBody
	public ModelAndView timeTable(
			ModelAndView mv
			) {
		mv.setViewName("timetable/timeTable");
		return mv;
	}
}
