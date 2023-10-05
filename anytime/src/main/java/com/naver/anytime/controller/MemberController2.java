package com.naver.anytime.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Member;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.task.SendMail;

@Controller
public class MemberController2 {
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory;

	private static final Logger logger = LoggerFactory.getLogger(MemberController2.class);

	private MemberService memberservice;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired
	public MemberController2(MemberService memberservice, PasswordEncoder passwordEncoder, SendMail sendMail) {
		this.memberservice = memberservice;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView info(Principal principal, ModelAndView mv) {
		String id = principal.getName();
		
		if(id == null) {
			mv.setViewName("redirect:login");
			logger.info("id is null");
		} else {
			Member m = memberservice.getLoginMember(id);
			mv.setViewName("member/memberAccount");
			mv.addObject("member", m);
		}
		return mv;
	}

	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String updatePassword() {
		// memberservice.delete(id);
		return "/member/updatePwd";
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String updateMember() {
		// memberservice.delete(id);
		return "/member/updateMember";
	}

//	@RequestMapping(value = "/{schoolName}", method = RequestMethod.GET)
//	public String getPage() {
//		// memberservice.delete(id);
//		return "/member/updateMember";
//	}

	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
	public String getBoardlist() {
		// memberservice.delete(id);
		return "/member/memberBoardlist";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteMember() {
		// memberservice.delete(id);
		return "/member/deleteMember";
	}

	// --------------------------------지원-----------------------------------------

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/login";
	}

}