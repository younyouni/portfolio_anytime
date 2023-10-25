package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@RestController
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
	
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<List<TimeTable>> getTimetablesBySemester(
    		@RequestParam(value = "semester") String semester, 
    		Principal userPrincipal) {
		
		String id = userPrincipal.getName(); 
		Member member = memberService.getLoginMember(id);
		
        try {
            List<TimeTable> timetable = timeTableService.getTimetablesBySemester(semester, member.getUser_id());
            return new ResponseEntity<>(timetable, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("학기별 시간표 조회 실패: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
	
//	// 시간표 View 출력
//	@RequestMapping(value = "/timetable")
//	@ResponseBody
//	public ModelAndView timeTable(
//			ModelAndView mv, Principal userPrincipal
//	    ) {
//		String id = userPrincipal.getName(); 
//		
//		Member member = memberService.getLoginMember(id);
//	    
//		List<TimeTable> timetable = timeTableService.getTimetableByUserId(member.getUser_id());
//		
//	    System.out.println("timetable" + timetable);
//	    
//	    mv.addObject("timetable", timetable);
//		mv.setViewName("timetable/timeTable");
//		return mv;
//	}
//	
//	@RequestMapping(value = "/changeName", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<String> changeName(@RequestParam int timetable_id, @RequestParam String newName) {
//	    try {
//	        timeTableService.changeNameAndTime(timetable_id, newName);
//	        return new ResponseEntity<>("이름 변경 성공", HttpStatus.OK);
//	    } catch (Exception e) {
//	        logger.error("이름 변경 실패: ", e);
//	        return new ResponseEntity<>("이름 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//
//	@RequestMapping(value = "/createNewTimeTable", method = RequestMethod.POST)
//	@ResponseBody
//    public TimeTable createNewTimeTable(Principal userPrincipal) {
//        String id = userPrincipal.getName(); 
//        Member member = memberService.getLoginMember(id);
//        return timeTableService.createNewTimeTable(member.getUser_id());
//    }
//	
//	@RequestMapping(value = "/getTimetableByUserIdAndSemester", method = RequestMethod.POST)
//	@ResponseBody
//	public List<TimeTable> getTimetableBySemester(@RequestParam String semester,
//	    Principal userPrincipal) {
//	    String id = userPrincipal.getName();
//	    if (id == null) {
//	        throw new RuntimeException("사용자가 로그인하지 않았습니다.");
//	    }
//	    Member member = memberService.getLoginMember(id);
//	    List<TimeTable> timetable = timeTableService.getTimetableByUserIdAndSemester(member.getUser_id(), semester);
//
//	    if (timetable.isEmpty()) {
//	        // DB에 데이터가 없을 경우 기본 시간표를 생성하고 반환
//	        TimeTable defaultTimeTable = timeTableService.createDefaultTimeTable(member.getUser_id(), semester);
//	        timetable.add(defaultTimeTable);
//	    }
//
//	    return timetable;
//	}


	
	 
}
