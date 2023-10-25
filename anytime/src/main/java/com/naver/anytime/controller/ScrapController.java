package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ScrapService;


@RestController
public class ScrapController {

	private static final Logger logger = LoggerFactory.getLogger(ScrapController.class);
	
	private ScrapService scrapService;
	private PostService postService;	   
	private BoardService boardService;
	private MemberService memberService;
	
	@Autowired
	public ScrapController(ScrapService scrapService, PostService postService, BoardService boardService, MemberService memberService) {
		this.scrapService = scrapService;
		this.postService = postService;
	    this.boardService = boardService;
	    this.memberService = memberService;
	}
	
	@RequestMapping(value = "/myscrap")
	@ResponseBody
	public ModelAndView Scrap(
			ModelAndView mv
			) {
		mv.setViewName("scrap/scrap");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/scrap", method = RequestMethod.POST)
	@ResponseBody
	public int insertScrap(
			@RequestParam(value = "post_id") int post_id,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		
		int scrapResult = 0;
		
		int check = scrapService.isScrap(user_id, post_id);
		
		if(check == 0) {
			scrapService.insertMyScrap(user_id, post_id);
			scrapResult = 1;
		} else if(check == 1) {
			scrapService.deleteMyScrap(user_id, post_id);
			scrapResult = 2;
		}
		
		return scrapResult;
	}
	
	@RequestMapping(value = "/scraplist")
	@ResponseBody
	public Map<String, Object> getScrapList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		
											System.out.println("유저 번호 = " + user_id);
		
		// 제한 수
		int limit = 10;
		
		// 총 리스트 수
		int listcount = scrapService.getMyScrapCount(user_id);

		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;
		
										    System.out.println("페이지 수 : " + page);
										    System.out.println("리스트 수 : " + listcount + "/ 총 페이지 수 : " + maxpage + "/ 시작 페이지 수 : " + startpage);
		
	    // 스크랩 게시물 리스트
		List<Post> list = postService.getMyScrapList(page, limit, user_id);
											logger.info("List size: " + list.size());
		
	    // JSON 응답을 구성
	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("list", list);
	    response.put("start", startpage);
	    response.put("end", endpage);
	    response.put("max", maxpage);
		
	    System.out.println("스크랩 불러오기 체크");		
		return response;
	}
}
