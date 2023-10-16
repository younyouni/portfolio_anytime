package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.domain.School;
import com.naver.anytime.domain.Semester;
import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.service.CreditService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.service.SemesterService;
import com.naver.anytime.service.Semester_detailService;

@Controller
public class CreditController {

	private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

	private MemberService memberservice;
	private SchoolService schoolservice;
	private CreditService creditservice;
	private SemesterService semesterservice;
	private Semester_detailService semester_detailservice;

	@Autowired
	public CreditController(MemberService memberservice, SchoolService schoolservice, CreditService creditservice, SemesterService semesterservice,
			Semester_detailService semester_detailservice) {

		this.memberservice = memberservice;
		this.schoolservice = schoolservice;
		this.creditservice = creditservice;
		this.semesterservice = semesterservice;
		this.semester_detailservice = semester_detailservice;
	}

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView mv, Principal principal) {
		int user_id = memberservice.getUserId(principal.getName());

		List<Semester> semesters = semesterservice.getSemestersByUserId(user_id);
		mv.addObject("semesters", semesters);
		
		School school = schoolservice.getSchoolByUserId(user_id);
		Credit credit = creditservice.getCreditByUserId(user_id); 
		
		
		
		if(school != null) {
			mv.addObject("school", school);
		}
		if(credit != null) {
			mv.addObject("credit", credit);
		}
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
	
    
    @RequestMapping(value ="/updateGraduateCredit")
	public String uodateGraduateCredit(@RequestParam("graduate_credit") int graduate_credit, Model model){
		
		int result =creditservice.updateGraduateCredit(graduate_credit);
		if(result == 1) {
		return "redirect:/calculator";
		}else{
			// 실패한 경우 에러 메시지를 모델에 추가하고 에러 페이지로 이동
            model.addAttribute("message", "Update failed");
            return "error";
		}
	}
    /*
     // 총점 구하기
    @RequestMapping(value = "/updateTotal", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDetails(int semester_id) {
     
    	int totalAcquisition= creditservice.getTotalAcquisition(semester_id);
		
	   
    	List<Semester_detail> semesterDetails = semester_detailservice.getSemesterDetailsBySemesterId(semester_id);
        double totalGradePoints = 0.0;
        double majorGradePoints = 0.0;
        int majorCredits = 0;

        Map<String, Double> gradeValueMap = new HashMap<>() {{
            put("A+", 4.5); put("A0", 4.3); put("A-", 4.0); 
            put("B+", 3.5); put("B0", 3.3); put("B-", 3.0);
            
            //... 나머지 성적도 이와 같이 추가...
        }};

        for (Semester_detail detail : semesterDetails) {
            double gradeValue = gradeValueMap.get(detail.getGrade());
            totalGradePoints += gradeValue * detail.getCredit();

            if (detail.isMajor()) {
                majorGradePoints += gradeValue * detail.getCredit();
                majorCredits += detail.getCredit();
            }
        }

       double totalGpa = totalAcquisition > 0 ? (totalGradePoints / totalAcquisition) : 0;
       double majorGpa = majorCredits > 0 ? (majorGradePoints / majorCredits) : 0;

       Map<String, Object> result = new HashMap<>();
       result.put("totalAcquisition", totalAcquisition);
       result.put("totalgpa", totalGpa);
       result.put("totalmajor", majorGpa);

       return result;
    }
    */
    
    
    
    
	
}
