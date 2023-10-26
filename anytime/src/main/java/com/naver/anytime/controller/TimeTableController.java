package com.naver.anytime.controller;

import java.security.Principal;
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
	public List<TimeTable> getTimetableBySemester(@RequestParam(value = "semester") String semester,
			Principal userPrincipal) {
		String id = userPrincipal.getName();
		if (id == null) {
			throw new RuntimeException("사용자가 로그인하지 않았습니다.");
		}

		List<TimeTable> timetable = timeTableService.getTimetableByUserIdAndSemester(memberService.getUserId(id),
				semester);

		if (timetable.isEmpty()) {
			// DB에 데이터가 없을 경우 기본 시간표를 생성하고 반환
			TimeTable defaultTimeTable = timeTableService.createDefaultTimeTable(memberService.getUserId(id), semester);
			timetable.add(defaultTimeTable);
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
		
		logger.info("Created new timetable with key: {}", key);
		
		return timeTableService.getNewTimetable(key);
	}

}
