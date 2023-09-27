package com.naver.anytime.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.PostService;


@Controller
@RequestMapping(value = "/post")
public class PostController {
	
   private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
   
   private PostService postService;
   
   private BoardService boardService;

   private CommentService commentService;
   
   @Autowired
   public PostController(PostService postService, BoardService boardService, CommentService commentService) {
	  this.postService = postService;
      this.boardService = boardService;
      this.commentService = commentService;
   }
   
   @GetMapping(value = "/list") 
   public String postList() {
      return "post/postList";
   }
   
   @GetMapping("/detail") // board/write
   public ModelAndView postDetail( 
	   int post_id,  ModelAndView mv,
	   HttpServletRequest request) {
//	   @RequestHeader(value="referer", required=false) String beforeURL) 
	   
   /*
       1. String beforeURL = request.getHeader("referer"); 의미로
       	  어느 주소에서 detail로 이동했는지 header의 정보 중에서 "referer"를 통해 알 수 있습니다.
       2. 수정 후 이곳으로 이동하는 경우 조회수는 증가하지 않도록 합니다.
       3. myhome4/board/list에서 제목을 클릭한 경우 조회수가 증가하도록 합니다.
       4. 주소창에서 http://localhost:8088/myhome4/board/detail?num=5 엔터
       	  referer는 header에 존재하지 않아 오류 발생하므로
       	   required=false로 설정 합니다. 이 경우 beforeURL의 값은 null입니다.
    */
		   
	   /*logger.info("referer:" + beforeURL);
	   if(beforeURL != null && beforeURL.endsWith("list")) {
			 postService.setReadCountUpdate(num); 
	   }*/
	   
	   Post post = postService.getDetail(post_id);
	      // board = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
	      if (post == null) {
	         logger.info("상세보기 실패");
	         mv.setViewName("error/error");
	         mv.addObject("url", request.getRequestURL());
	         mv.addObject("message", "상세보기 실패입니다.");
	      }else {
	    	  logger.info("상세보기 성공");
	    	  //int count = commentService.getListCount(num);
	    	  mv.setViewName("post/postDetail");
	    	  //mv.addObject("count", count);
	    	  mv.addObject("postdata", post);
	    	  System.out.println("테스트데이터"+ post);
	      }
	      return mv;
	     
	   }
   
}
