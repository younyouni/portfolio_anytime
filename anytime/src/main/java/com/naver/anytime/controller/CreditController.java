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
	public CreditController(MemberService memberservice, SchoolService schoolservice, CreditService creditservice,
			SemesterService semesterservice, Semester_detailService semester_detailservice) {
        
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
		List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);

		int totalAcquisition = creditservice.getTotalAcquisition(user_id);
		mv.addObject("totalAcquisition", totalAcquisition);
		mv.addObject("semesters", semesters);

		School school = schoolservice.getSchoolByUserId(user_id);
		Credit credit = creditservice.getCreditByUserId(user_id);

		if (school != null) {
			mv.addObject("school", school);
		}
		if (credit != null) {
			mv.addObject("credit", credit);
		}
/////
		// 학점 계산 로직 추가
		double totalGradePoints = 0.0;
		double majorGradePoints = 0.0;
		int majorCredits = 0;

		Map<String, Double> gradeValueMap = new HashMap<>() {
			{
				put("A+", 4.5); put("A0", 4.3); put("A-", 4.0);
				put("B+", 3.5); put("B0", 3.3); put("B-", 3.0);
				put("C+", 2.5); put("C0", 2.3); put("C-", 2.0);
				put("D+", 1.5); put("D0", 1.3); put("D-", 1.0);
				put("F", 0.0); put("P", 0.0); put("NP", 0.0);

			}
		};

		for (Semester_detail detail : details) {
			double gradeValue = gradeValueMap.get(detail.getGrade());
			totalGradePoints += gradeValue * detail.getCredit();

			if (detail.isMajor()) {
				majorGradePoints += gradeValue * detail.getCredit();
				majorCredits += detail.getCredit();
			}
		}

		double totalGpa = totalAcquisition > 0 ? (totalGradePoints / totalAcquisition) : 0;
		double majorGpa = majorCredits > 0 ? (majorGradePoints / majorCredits) : 0;

		// 소수점 두 자리까지 반올림하여 문자열로 변환
		String formattedTotalGpa = String.format("%.2f", totalGpa);
		String formattedMajorGpa = String.format("%.2f", majorGpa);

		mv.addObject("totalgpa", formattedTotalGpa);
		mv.addObject("totalmajor", formattedMajorGpa);

		mv.setViewName("credit/credit");

		return mv;
	}

	@RequestMapping(value = "/getsemester_detail", method = RequestMethod.GET)
	@ResponseBody
	public List<Semester_detail> getsemester_detail(Principal principal, int semester_id) {
		logger.info("ajax 실행");
		// int user_id = memberservice.getUserId(principal.getName());

		List<Semester_detail> details = creditservice.getSemesterDetailsBySemesterId(semester_id);

		return details;
	}

	@RequestMapping(value = "/updatesemester_detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatesemester_detail(@ModelAttribute Semester_detail semester_detail, ModelAndView mv,
			HttpServletRequest request,Principal principal, RedirectAttributes rattr) {
		
		int result = semester_detailservice.update(semester_detail);
		Map<String, String> map = new HashMap<>();

		// 업데이트가 성공적으로 수행된 경우
		if (result == 1) {
			int user_id = memberservice.getUserId(principal.getName());
			int totalAcquisition = creditservice.getTotalAcquisition(user_id);
			List<Semester_detail> details = creditservice.getSemesterDetailsByUserId(user_id);
			
			double totalGradePoints = 0.0;
			double majorGradePoints = 0.0;
			int majorCredits = 0;

			Map<String, Double> gradeValueMap = new HashMap<>() {
				{
					put("A+", 4.5); put("A0", 4.3); put("A-", 4.0);
					put("B+", 3.5); put("B0", 3.3); put("B-", 3.0);
					put("C+", 2.5); put("C0", 2.3); put("C-", 2.0);
					put("D+", 1.5); put("D0", 1.3); put("D-", 1.0);
					put("F", 0.0); put("P", 0.0); put("NP", 0.0);

				}
			};

			for (Semester_detail detail : details) {
				double gradeValue = gradeValueMap.get(detail.getGrade());
				totalGradePoints += gradeValue * detail.getCredit();

				if (detail.isMajor()) {
					majorGradePoints += gradeValue * detail.getCredit();
					majorCredits += detail.getCredit();
				}
			}

			double totalGpa = totalAcquisition > 0 ? (totalGradePoints / totalAcquisition) : 0;
			double majorGpa = majorCredits > 0 ? (majorGradePoints / majorCredits) : 0;

			// 소수점 두 자리까지 반올림하여 문자열로 변환
			String formattedTotalGpa = String.format("%.2f", totalGpa);
			String formattedMajorGpa = String.format("%.2f", majorGpa);

			
			map.put("totalgpa", formattedTotalGpa);
			map.put("totalmajor", formattedMajorGpa);
			map.put("totalAcquisition", Integer.toString(totalAcquisition));
		} 
		return map;
	}


	@RequestMapping(value = "/updateGraduateCredit")
	public String uodateGraduateCredit(@RequestParam("graduate_credit") int graduate_credit, Model model) {

		int result = creditservice.updateGraduateCredit(graduate_credit);
		if (result == 1) {
			return "redirect:/calculator";
		} else {
			// 실패한 경우 에러 메시지를 모델에 추가하고 에러 페이지로 이동
			model.addAttribute("message", "Update failed");
			return "error";
		}
	}

}
