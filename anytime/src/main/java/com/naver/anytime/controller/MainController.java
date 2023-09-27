package com.naver.anytime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naver.anytime.domain.School;
import com.naver.anytime.service.SchoolService;

@Controller
@RequestMapping(value = "/main")
public class MainController {

	private  SchoolService schoolService;
	
	@Autowired
	public MainController(SchoolService schoolService) {
		this.schoolService =schoolService;
	}
	
	
	   // http://localhost:9700/anytime/main/home
	   // 회원가입 폼 이동
	   @RequestMapping(value = "/home", method = RequestMethod.GET)
	   public String main(Model model) {
		   List<School> schools = schoolService.getSchoolList(); 
		   model.addAttribute("schoolList", schools);
		   
	      return "main/anytimeMain";// WEB-IF/views/main/anytimeMain.jsp
	   }
	
	
	
}
