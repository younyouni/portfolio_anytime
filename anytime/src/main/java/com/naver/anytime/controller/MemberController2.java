package com.naver.anytime.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	private SchoolService schoolservice;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired
	public MemberController2(MemberService memberservice, SchoolService schoolservice, PasswordEncoder passwordEncoder,
			SendMail sendMail) {
		this.memberservice = memberservice;
		this.schoolservice = schoolservice;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public String info() {
		// memberservice.delete(id);
		return "/member/memberAccount";
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

	@RequestMapping(value = "/certificate", method = RequestMethod.GET)
	public String certificate() {
		return "/member/memberCertificate";
	}

	@ResponseBody
	@RequestMapping(value = "/certificate_mailsend", method = RequestMethod.POST)
	public Map<String, Object> certificate_mailsend(@RequestParam("webemail") String email, HttpSession session) {
		// 난수(인증번호) 생성 (예: 6자리 난수)
		logger.info("email :" + email);
		String authCode = generateAuthCode();
		logger.info("authCode : " + authCode);

		// 세션에 인증번호 저장 (나중에 검증할 때 사용)
		session.setAttribute("authCode", authCode);

		// 이메일 발송
		String subject = "애니타임 학교 웹메일 인증";

		try {
			sendMail.sendAuthEmail(email, subject, authCode);
			 // 응답 데이터 생성
	        Map<String, Object> response = new HashMap<>();
	        response.put("verificationCode", authCode);  // 클라이언트에서 기대하는 필드 이름으로 설정

	        return response;
	        
		} catch (Exception e) {
			logger.error("메일 전송 실패", e);
			 return Collections.singletonMap("error", "메일 전송 실패");
		}
		
	}

	// 6자리 인증코드(난수) 생성 메서드
	private String generateAuthCode() {
		Random random = new Random();
		int range = (int) Math.pow(10, 6); // 10의 6승
		int trim = (int) Math.pow(10, 5); // 10의 5승
		int result = random.nextInt(range) + trim;

		if (result > range) {
			result -= trim;
		}
		logger.info(Integer.toString(result));
		return Integer.toString(result);
	}

	
	
	
	@RequestMapping(value = "/certificate_mailcheck", method = {RequestMethod.GET, RequestMethod.POST})
	public String certificate_mailcheck() {
		return "/member/memberCertificate_mailcheck";
	}

	
	@RequestMapping(value = "/certificateProcess", method = RequestMethod.GET)
	public String certificateProcess(Member member, RedirectAttributes rattr, Model model) {

		// security에서 id값 불러오기 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String id = auth.getName();
		 logger.info(id);
		
		int result = memberservice.updateschoolcheck(id);

		
		// 업데이트 된 경우
		if (result == 1) {
			rattr.addFlashAttribute("result", "schoolcheckSuccess");
			model.addAttribute("message", "학교 웹메일 인증 성공입니다.");
			return "redirect:/my"; // 학교 메인페이지로 이동
		} else {
			model.addAttribute("message", "학교 웹메일 인증 실패입니다.");
			return "error/error";
		}
	}
	
	
	
	
}