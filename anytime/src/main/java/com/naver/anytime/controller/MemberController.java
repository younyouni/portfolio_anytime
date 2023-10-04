package com.naver.anytime.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.School;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.SchoolService;
import com.naver.anytime.task.SendMail;

// 이 컨트롤러는 회원 가입시 비밀번호 암호화를 하는 컨트롤러입니다.

@Controller
@RequestMapping(value = "/member") // http://localhost:9700/anytime/member/로 시작하는 주소 매핑
public class MemberController {
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private SchoolService schoolService;
	private MemberService memberservice;
	private PasswordEncoder passwordEncoder;
	private SendMail sendMail;

	@Autowired
	public MemberController(MemberService memberservice, PasswordEncoder passwordEncoder, SendMail sendMail,
			SchoolService schoolService) {
		this.memberservice = memberservice;
		this.schoolService = schoolService;
		this.passwordEncoder = passwordEncoder;
		this.sendMail = sendMail;
	}

	// http://localhost:9700/anytime/member/main
	// 회원가입 폼 이동
	//   @RequestMapping(value = "/main", method = RequestMethod.GET)
	//   public String main() {
	//      return "member/anytimeMain";// WEB-IF/views/member/anytimeMain.jsp
	//   }


	/*
	 * @CookieValue(value="saveid", required=false) Cookie readCookie 이름이 saveid인
	 * 쿠키를 Cookie 타입으로 전달받습니다. 지정한 이름의 쿠키가 존재하지 않을 수도 있기 때문에 required=false로 설정해야
	 * 합니다. 즉 id 기억하기를 선택하지 않을 수도 있기 때문에 required=false로 설정해야 합니다. required=true
	 * 상태에서 지정한 이름을 가진 쿠키가 존재하지 않으면 스프링 MVC는 익셉션을 발생시킵니다.
	 */

	// http://localhost:9700/anytime/member/login
	// 로그인 폼이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, 
			@CookieValue(value = "autologin", required = false) Cookie readCookie,
			HttpSession session, 
			Principal userPrincipal) {
		if (readCookie != null) {
			logger.info("저장된 아이디 :" + userPrincipal.getName());// principal.getName() : 로그인한 아이디 값을 알 수 있어요
			mv.setViewName("redirect:/main/home");
		} else {
			mv.setViewName("member/loginForm");
			mv.addObject("loginfail", session.getAttribute("loginfail"));// 세션에 저장된 값을 한 번만 실행될 수 있도록 mv에 저장합니다
			session.removeAttribute("loginfail");// 세션의 값은 제거합니다.
		}
		return mv;
	}

	// 로그인 처리
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginProcess(@RequestParam("login_id") String id,
			@RequestParam("password") String password,
			@RequestParam(value = "autologin", defaultValue = "", required = false) 
	        String autologin,
			HttpServletResponse response, 
			HttpSession session, 
			RedirectAttributes rattr) {

		int result = memberservice.isId(id, password);
		logger.info("결과 :" + result);

		if (result == 1) {
			// 로그인 성공
			session.setAttribute("login_id", id);
			Cookie savecookie = new Cookie("saveid", id);
			if (!autologin.equals("")) {
				savecookie.setMaxAge(60 * 60);
				logger.info("쿠키저장 : 60*60");
			} else {
				logger.info("쿠키저장 : 0 ");
				savecookie.setMaxAge(0);
			}
			response.addCookie(savecookie);

			return "redirect:/main/home";
		} else {// 로그인 실패
			rattr.addFlashAttribute("result", result);
			return "redirect:login"; // http://localhost:8088/myhome4/member/login
		}
	}

	// http://localhost:9700/anytime/member/register
	// 학교,학번등록 폼 이동
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		List<School> schools = schoolService.getSchoolList();
		model.addAttribute("schoolList", schools);

		return "member/registerForm";// WEB-IF/views/member/registerForm.jsp
	}

	// http://localhost:9700/anytime/member/agreement
	// 이용약관 폼 이동
	@RequestMapping(value = "/agreement", method = RequestMethod.POST)
	public String agreement(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("enter_year", request.getParameter("enter_year"));
		session.setAttribute("campus_name", request.getParameter("campus_name"));

		return "member/agreementForm";// WEB-IF/views/member/agreementForm.jsp
	}

	// http://localhost:9700/anytime/member/mailcheckForm
	// 이메일 본인인증 폼 이동
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email() {
		logger.info("mail 화면 출력 성공");
		return "member/mailcheckForm";// WEB-IF/views/member/mailcheckForm.jsp
	}

	@ResponseBody // 응답으로 문자열 반환
	@RequestMapping(value = "/emailsend", method = RequestMethod.POST)
	public String emailsend(@RequestParam("email") String email, HttpSession session) {
		// 난수(인증번호) 생성 (예: 6자리 난수)
		logger.info("email :" + email);
		String authCode = generateAuthCode();
		logger.info("authCode : " + authCode);

		// 세션에 인증번호 저장 (나중에 검증할 때 사용)
		session.setAttribute("authCode", authCode);

		// 이메일 발송
		String subject = "애니타임 가입 인증";

		try {
			sendMail.sendAuthEmail(email, subject, authCode);
		} catch (Exception e) {
			logger.error("메일 전송 실패", e);
		}

		return authCode;
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

	// http://localhost:9700/anytime/member/join
	// 회원가입 입력폼 이동
	@RequestMapping(value = "/join", method = {RequestMethod.GET, RequestMethod.POST})
	public String join() {
		return "member/joinForm";// WEB-IF/views/member/joinForm.jsp
	}

	// 회원가입폼에서 아이디 중복 검사
	@ResponseBody // @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어
					// HTTP Response BODY에 설정됩니다.
	@RequestMapping(value = "/idcheck", method = RequestMethod.GET)
	public int idcheck(@RequestParam("login_id") String id) {
		return memberservice.isId(id);// WEB-IF/views/member/oinForm.jsp
	}

	
	 // 회원가입폼에서 닉네임 중복 검사
     @ResponseBody // @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어
					// HTTP Response BODY에 설정됩니다.
	 @RequestMapping(value = "/nicknamecheck", method = RequestMethod.GET)
	 public int nicknamecheck(@RequestParam("nickname") String nickname) {
	     return memberservice.isNickname(nickname);// WEB-IF/views/member/joinForm.jsp
		}
	
	
	
	// 회원가입 처리
	@RequestMapping(value = "/joinProcess", method = RequestMethod.POST)
	public String joinProcess(Member member,
							  RedirectAttributes rattr, 
			                  Model model,
			                  HttpServletRequest request) {

		// 비밀번호 암호화 추가
        String encPassword = passwordEncoder.encode(member.getPassword());
        logger.info(encPassword);
        member.setPassword(encPassword);
		
		// HttpSession 객체를 가져오기
        HttpSession session = request.getSession();
        
        String enterYear = (String) session.getAttribute("enter_year");
        String campusName = (String) session.getAttribute("campus_name");
        
        // 값이 제대로 들어있는지 확인
    	if (enterYear == null || campusName == null) {
    		model.addAttribute("url", request.getRequestURL());
    		model.addAttribute("message", "세션 값이 없습니다.");
    		return "error/error";
    	} 
        
    	int admission_year = Integer.parseInt(enterYear);
    	member.setAdmission_year(admission_year);

    	int school_id = memberservice.getSchoolIdByName(campusName);
    	member.setSchool_id(school_id);
    	
    	
		// MemberService를 사용하여 회원을 데이터베이스에 추가합니다. 
		int result = memberservice.insert(member);
		// result=0;
		
		/*
		 * 스프링에서 제공하는 RedirectAttributes는 기존의 Servlet에서 사용되던 response.sendRedirect()를
		 * 사용할 때와 동일한 용도로 사용합니다. 리다이렉트로 전송하면 파라미터를 전달하고자 할 때 addAttribute()나
		 * addFlashAttribute()를 사용합니다. 예) response.sendRedirect("/test?result=1"); =>
		 * rattr.addAttribute("result",1);
		 */
		
		// 삽입이 된 경우
		// 회원 가입 결과에 따라 리다이렉트할 페이지를 결정합니다.
		if (result == 1) {

			rattr.addFlashAttribute("result", "joinSuccess");
			model.addAttribute("message", "회원가입 성공입니다.");
			return "redirect:login"; // 로그인 페이지로 이동
		} else {
			model.addAttribute("url", request.getRequestURL());
			model.addAttribute("message", "회원가입 실패입니다.");
			return "error/error"; // 회원 가입 폼 페이지로 이동
		}
	}

	// http://localhost:9700/anytime/member/forgotid
	// 아이디 찾기 폼 이동
	@RequestMapping(value = "/forgotid", method = RequestMethod.GET)
	public String forgotid() {
		return "member/forgotId";// WEB-IF/views/member/forgorId.jsp
	}

	
	// 아이디 찾기 이메일로 아이디 보내기 
		@RequestMapping(value = "/forgotid_email", method = RequestMethod.POST)
		public String forgotid_email(@RequestParam("email") String email,
                Model model, RedirectAttributes rattr) {
			logger.info("email :" + email);
			
			// db에서 해당 email로 가입되어진 id 조회
			String foundId = memberservice.findIdByEmail(email);
			logger.info("foundId : " + foundId);

		    if (foundId != null) {
		        // 조회된 ID가 있으면, 해당 ID를 포함한 이메일 전송 
		        String subject = "애니타임 아이디 찾기";
		        try {
		            sendMail.sendFindIdEmail(email, subject, foundId);

					rattr.addFlashAttribute("result", "Success");
					model.addAttribute("message", "아이디 정보가 전송되었습니다.");
					return "redirect:/member/login"; // 로그인 페이지로 이동
		        } catch (Exception e) {
		            logger.error("아이디 찾기 메일 전송 실패", e);
		            return "error/error";
		        }
		    } else {
		        return "해당 이메일에 가입된 정보가 없습니다.";
		    }
		}
			
			
	
		
	
	
	
	// http://localhost:9700/anytime/member/forgotpwd
	// 비밀번호 찾기 폼 이동
	@RequestMapping(value = "/forgotpwd", method = RequestMethod.GET)
	public String forgotpwd() {
		return "member/forgotPwd";// WEB-IF/views/member/forgotpwd.jsp
	}

	// http://localhost:9700/anytime/member/forgotpwd_mailcheck
	// 비밀번호 찾기 본인인증 이동
	@RequestMapping(value = "/forgotpwd_mailcheck", method = RequestMethod.GET)
	public String forgotpwd_mailcheck() {
		return "member/forgotPwd_mailcheck";// WEB-IF/views/member/forgotpwd_mailcheck.jsp
	}

	// http://localhost:9700/anytime/member/forgotpwd_result
	// 비밀번호 찾기 비번 변경폼 이동
	@RequestMapping(value = "/forgotpwd_result", method = RequestMethod.GET)
	public String forgotpwd_result() {
		return "member/forgotPwd_result";// WEB-IF/views/member/forgotpwd_result.jsp
	}

	//
	@RequestMapping(value = "/forgotpwd_resultProcess", method = RequestMethod.GET)
	public String forgotpwdresultProcess() {
		return "redirect:login";
	}

	/*
	 * 회원정보 수정폼1.(HttpSession 이용)
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.GET) public
	 * ModelAndView member_update(HttpSession session, ModelAndView mv) {
	 * 
	 * String id = (String) session.getAttribute("id");
	 * 
	 * 
	 * if(id == null) { mv.setViewName("redirect:login"); logger.info("id is null");
	 * }else { Member m = memberservice.member_info(id);
	 * mv.setViewName("member/member_updateForm"); mv.addObject("memberinfo", m); }
	 * return mv; }
	 */

	// 회원 정보 수정폼2
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView member_update(Principal principal, ModelAndView mv) {
		String id = principal.getName();

		if (id == null) {
			mv.setViewName("redirect:login");
			logger.info("id is null");
		} else {
			Member m = memberservice.member_info(id);
			mv.setViewName("member/member_updateForm");
			mv.addObject("memberinfo", m);
		}
		return mv;
	}

	// 수정처리
	@RequestMapping(value = "/updateProcess", method = RequestMethod.POST)
	public String updateProcess(Member member, Model model, HttpServletRequest request, RedirectAttributes rattr) {

		int result = memberservice.update(member);
		if (result == 1) {
			rattr.addFlashAttribute("result", "updateSuccess");
			return "redirect:/board/list";
		} else {
			model.addAttribute("url", request.getRequestURL());
			model.addAttribute("message", "정보 수정 실패");
			return "error/error";

		}

	}
	/*
	 * 1.header.jsp에서 이동하는 경우 href="${pageContext.request.contextPath}/member/list"
	 * 
	 * 2. member_list.jsp에서 이동하는 경우 <a href
	 * ="list?page=2&search_field=-1&search word=" class="page-lick">2</a>
	 * 
	 */

	@RequestMapping(value = "/list")
	public ModelAndView memberList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "limit", defaultValue = "3", required = false) int limit, ModelAndView mv,
			@RequestParam(value = "search_field", defaultValue = "1", required = false) int index,
			@RequestParam(value = "search_word", defaultValue = "", required = false) String search_word) {

		int listcount = memberservice.getSearchListCount(index, search_word);
		List<Member> list = memberservice.getSearchList(index, search_word, page, limit);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		mv.setViewName("member/member_list");
		mv.addObject("page", page);
		mv.addObject("maxpage", maxpage);
		mv.addObject("startpage", startpage);
		mv.addObject("endpage", endpage);
		mv.addObject("listcount", listcount);
		mv.addObject("memberlist", list);
		mv.addObject("search_field", index);
		mv.addObject("search_word", search_word);
		return mv;

	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView member_info(@RequestParam("id") String id, ModelAndView mv, HttpServletRequest request) {

		Member m = memberservice.member_info(id);
		// m=null;//오류 확인하는 값
		if (m != null) {
			mv.setViewName("member/member_info");
			mv.addObject("memberinfo", m);
		} else {
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "해당 정보가 없습니다.");
			mv.setViewName("error/error");

		}
		return mv;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String member_delete(String id) {

		memberservice.delete(id);
		return "redirect:list";
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}