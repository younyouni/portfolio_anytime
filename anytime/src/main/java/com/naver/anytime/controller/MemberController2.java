package com.naver.anytime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.task.SendMail;

@Controller
@RequestMapping(value = "/{schoolDomain}") // http://localhost:9700/anytime/{schoolName}/youn로 시작하는 주소 매핑
public class MemberController2 {
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory;

	private static final Logger logger = LoggerFactory.getLogger(MemberController2.class);

	private MemberService memberservice;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired
	public MemberController2(MemberService memberservice, PasswordEncoder passwordEncoder,
			SendMail sendMail) {
		this.memberservice = memberservice;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public String info(@PathVariable("schoolDomain") String schoolDomain) {
		// memberservice.delete(id);
		return "/member/memberAccount";
	}

	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String updatePassword(@PathVariable String schoolName) {
		// memberservice.delete(id);
		return "/member/updatePwd";
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String updateMember(@PathVariable String schoolName) {
		// memberservice.delete(id);
		return "/member/updateMember";
	}
	
//	@RequestMapping(value = "/{schoolName}", method = RequestMethod.GET)
//	public String getPage(@PathVariable String schoolName) {
//		// memberservice.delete(id);
//		return "/member/updateMember";
//	}

	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
	public String getBoardlist(@PathVariable String schoolName) {
		// memberservice.delete(id);
		return "/member/memberBoardlist";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteMember(@PathVariable String schoolName) {
		// memberservice.delete(id);
		return "/member/deleteMember";
	}

}