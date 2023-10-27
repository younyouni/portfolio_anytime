package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@Controller
public class TimeTableController {

	private static final Logger logger = LoggerFactory.getLogger(TimeTableController.class);

	private TimeTableService timeTableService;
	private TimeTable_detailService timeTable_detailService;
	private MemberService memberService;

	@Autowired
	public TimeTableController(TimeTableService timeTableService, TimeTable_detailService timeTable_detailService,
			MemberService memberService) {
		this.timeTableService = timeTableService;
		this.timeTable_detailService = timeTable_detailService;
		this.memberService = memberService;
	}

	// 시간표 View 출력
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	public String timeTable() {
		return "timetable/timeTable";
	}

	@RequestMapping(value = "/getTimetableByUserIdAndSemester", method = RequestMethod.POST)
	@ResponseBody
	public List<TimeTable> getTimetableBySemester(
			@RequestParam(value = "semester", defaultValue = "2023년 2학기", required = false) String semester,
			Principal userPrincipal) {
		String id = userPrincipal.getName();
		int user_id = memberService.getUserId(id);
		logger.info("유저아이디" + user_id);

		if (id == null) {
			throw new RuntimeException("사용자가 로그인하지 않았습니다.");
		}

		int timetable_check = timeTableService.checkTimetable(user_id, semester);
		List<TimeTable> timetable = new ArrayList<TimeTable>();

		if (timetable_check > 0) {
			timetable = timeTableService.getTimetableByUserIdAndSemester(user_id, semester);

		} else {
			int key = timeTableService.createDefaultTimeTable(user_id, semester);

			TimeTable new_timetable = timeTableService.getNewTimetable(key);
			timetable.add(new_timetable);
		}
		return timetable;
	}

	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> changeName(@RequestParam int timetable_id, @RequestParam String newName) {
		try {
			timeTableService.changeNameAndTime(timetable_id, newName);
			return new ResponseEntity<>("이름 변경 성공", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("이름 변경 실패: ", e);
			return new ResponseEntity<>("이름 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/createNewTimeTable", method = RequestMethod.POST)
	@ResponseBody
	public TimeTable createNewTimeTable(@RequestParam(value = "semester") String semester, Principal userPrincipal) {
		String id = userPrincipal.getName();
		int key = timeTableService.createNewTimeTable(memberService.getUserId(id), semester);

		return timeTableService.getNewTimetable(key);
	}

}
