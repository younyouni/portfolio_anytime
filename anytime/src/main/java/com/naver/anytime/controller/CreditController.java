package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.service.CreditService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SemesterService;
import com.naver.anytime.service.Semester_detailService;

@Controller
public class CreditController {

	private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

	private MemberService memberservice;
	private CreditService creditservice;
	private SemesterService semesterservice;
	private Semester_detailService semester_detailservice;

	@Autowired
	public CreditController(MemberService memberservice, CreditService creditservice, SemesterService semesterservice,
			Semester_detailService semester_detailservice) {

		this.memberservice = memberservice;
		this.creditservice = creditservice;
		this.semesterservice = semesterservice;
		this.semester_detailservice = semester_detailservice;
	}

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView mv, Principal principal) {
		int user_id = memberservice.getUserId(principal.getName());

		List<Semester> semesters = semesterservice.getSemestersByUserId(user_id);
		mv.addObject("semesters", semesters);
		mv.setViewName("credit/credit");
		
		return mv;
	}

	@RequestMapping(value = "/getsemester_detail", method = RequestMethod.GET)
	@ResponseBody
	public List<Semester_detail> getsemester_detail(Principal principal, int semester_id) {
		logger.info("ajax 실행");
//	    int user_id = memberservice.getUserId(principal.getName());
//	    List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);	

		List<Semester_detail> details = creditservice.getSemesterDetailsBySemesterId(semester_id);

		return details;
	}

}
