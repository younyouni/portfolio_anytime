package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
   //	int user_id = memberservice.getUserId(principal.getName());
   //	List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);	

		List<Semester_detail> details = creditservice.getSemesterDetailsBySemesterId(semester_id);

		return details;
	}

	
    @RequestMapping(value = "/updatesemester_detail", method = RequestMethod.POST)
    public String updatesemester_detail(@ModelAttribute Semester_detail semester_detail, 
    		Model model,
    		HttpServletRequest request, 
    		RedirectAttributes rattr) {
    	int result = semester_detailservice.update(semester_detail);
    	
    	// 업데이트가 성공적으로 수행된 경우
        if (result == 1) {
            return "redirect:/calculator";  // 홈 페이지로 리다이렉트
        } else {
            // 실패한 경우 에러 메시지를 모델에 추가하고 에러 페이지로 이동
            model.addAttribute("message", "Update failed");
            return "error";
        }
    }
	
	
	
}
