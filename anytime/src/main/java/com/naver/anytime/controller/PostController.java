package com.naver.anytime.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;


@Controller
@RequestMapping(value = "/post")
public class PostController {
	
   private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
   
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
   
//   @GetMapping(value = "/list") 
//   public String postList() {
//      return "post/postList";
//   }
   
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
   @RequestParam(value = "board_id", required = false) int board_id, 
   ModelAndView mv) {
		
	   	int limit = 10;
	   	
		// 총 리스트 수
		int listcount = postService.getListCount(board_id);
		
		// 게시판 이름
		String board = boardService.getBoardName(board_id);
		
		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 게시글 리스트
	    List<Post> postlist = postService.getPostList(page, limit, board_id);
	    
	    // 유저 닉네임
	    List<Post> username = postService.getUserNickname();
	    
	    // 익명성
	    int anonymous = boardService.getBoardAnonymous(board_id);
	    
	    // 익명성 체크
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
	    mv.addObject("board_id", board_id);
	    
	    // 글이 없을때 체크 (1= 일반, 2= 검색)
	    if(postlist != null) {
	    	mv.addObject("emptycheck", 1);
	    }
	      
	    System.out.println("보드넘테스트" + board_id);
	    System.out.println("값테스트" + postlist);
		return mv;
	}
   
   //검색기능
   @RequestMapping(value = "/search", method = RequestMethod.POST)
   public ModelAndView postsearch(
		   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
		   @RequestParam(value = "board_id", required = false) int board_id, 
		   @RequestParam(value = "search_field")int search_field,
		   @RequestParam(value = "search_word")String search_word,
		   ModelAndView mv){
	   
		int limit = 10;

		// 총 리스트 수
		int listcount = postService.getListCount(board_id, search_field, search_word);
		
		// 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;

	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;

	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;

	    if (endpage > maxpage)
	       endpage = maxpage;

	    // 게시글 리스트
	    List<Post> postlist = postService.getSearchPostList(page, limit, board_id, search_field, search_word);
	    
	    // 유저 닉네임
	    List<Post> username = postService.getUserNickname();
	    
	    // 익명성
	    int anonymous = boardService.getBoardAnonymous(board_id);
	    
	    // 익명성 체크
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
	    mv.addObject("allsearchcheck", 0);
	    mv.addObject("emptycheck", 0);
	    mv.addObject("board_id", board_id);
	    
	    // 글이 없을때 체크 (1= 일반, 2= 검색)
	    if(postlist != null) {
	    	mv.addObject("emptycheck", 2);
	    }
	      
	    System.out.println("보드넘테스트" + board_id);
	    System.out.println("값테스트" + postlist);
		return mv;
   }
	
}
