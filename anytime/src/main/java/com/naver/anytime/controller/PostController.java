package com.naver.anytime.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;


@Controller
@RequestMapping(value = "/post")
public class PostController {
	
   private static final Logger logger = LoggerFactory.getLogger(PostController.class);
   
   private PostService postService;
   
   private BoardService boardService;
   
   private CommentService commentService;
   
   private MemberService memberService;
   
   @Autowired
   public PostController(PostService postService, BoardService boardService, CommentService commentService, MemberService memberService) {
	  this.postService = postService;
      this.boardService = boardService;
      this.commentService = commentService;
      this.memberService = memberService;
   }
   

   /* -------------------------------- ▼post/detail 상세페이지▼ -------------------------------- */ 
   @GetMapping("/detail") // http://localhost:9700/anytime/post/detail?post_id=1 주소예시입니다.
   public ModelAndView postDetail( 
	   @RequestParam(value = "post_id", required = false) int post_id,
	   ModelAndView mv,
	   HttpServletRequest request) {
	   
	   // 현재 사용자의 아이디 가져오기
       HttpSession session = request.getSession();
       int currentUserNum = 0; // 기본값 0으로 설정
       Object userIdObject = session.getAttribute("userId");
       
       if (userIdObject != null) {
           currentUserNum = Integer.parseInt(userIdObject.toString());
       }
	   
	   Post post = postService.getDetail(post_id); // post테이블 정보 가져오기위한 메소드입니다.
	   
	   Board board = boardService.getBoardDetail(post.getBOARD_ID()); // board테이블 정보 가져오기위한 메소드입니다.

	      // post = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
	      if (post == null) {
	         logger.info("상세보기 실패");
	         mv.setViewName("error/error");
	         mv.addObject("url", request.getRequestURL());
	         mv.addObject("message", "상세보기 실패입니다.");
	      }else {
	    	  logger.info("상세보기 성공");
	    	  
	    	  if (board.getANONYMOUS() == 0) {
	              Member member = memberService.findMemberByUserId(post.getUSER_ID());
	              if (member != null){
	                  String nickname = member.getNickname();
	                  mv.addObject("nickname", nickname);
	              }
	          }
	    	  
	    	  mv.setViewName("post/postDetail");
	    	  mv.addObject("postdata", post);
	    	  mv.addObject("boardtest", board);
	    	  System.out.println("post테스트=>"+post);
	    	  
	    	
	    	  
	      }
	      return mv;
	     
	   }
   
   
   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   //테스트 페이지
   @RequestMapping(value = "/test", method = RequestMethod.GET)
   public String test() {
	   return "post/test";
   }
   
   
   //게시물 리스트 출력
   @RequestMapping(value = "/list", method = RequestMethod.GET)
   public ModelAndView postlist(
   @RequestParam(value = "page", defaultValue = "1", required = false) int page, 
   @RequestParam(value = "board_id", required = false) int b, 
   ModelAndView mv) {
		
	   	int limit = 10;
		//
		// 총 리스트 수를 받아옴
		int listcount = postService.getListCount(b);
		String board = boardService.getBoardName(b);
		
		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 리스트를 받아옴
	    List<Post> postlist = postService.getPostList(page, limit, b);
	    List<Post> username = postService.getUserNickname();
	    int anonymous = boardService.getBoardAnonymous(b);
	    
	    if(anonymous == 1) {
	    	for(Post post : postlist) {
	    		post.setNICKNAME("익명");
	    	}
	    } else {
	    	for(Post post : postlist) {
	    		for(Post post2 : username) {
	    			if(post.getUSER_ID() == post2.getUSER_ID()) {
	    				post.setNICKNAME(post2.getNICKNAME());
	    			}
	    		}
	    	} 	
	    }


	    mv.setViewName("post/postList");
	    mv.addObject("page", page);
	    mv.addObject("maxpage", maxpage);
	    mv.addObject("startpage", startpage);
	    mv.addObject("endpage", endpage);
	    mv.addObject("listcount", listcount);
	    mv.addObject("postlist", postlist);
	    mv.addObject("limit", limit);
	    
	    mv.addObject("un", username);
	    mv.addObject("boardname", board);
	    mv.addObject("allsearchcheck", 0);
	    mv.addObject("emptycheck", 0);
	    mv.addObject("board_id", b);
	    
	    if(postlist != null) {
	    	mv.addObject("emptycheck", 1);
	    }
	      
	    System.out.println("보드넘테스트" + b);
	    System.out.println("값테스트" + postlist);
		return mv;
	}
	
}
