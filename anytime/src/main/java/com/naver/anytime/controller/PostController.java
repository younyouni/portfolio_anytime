package com.naver.anytime.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
   
/* -------------------------------------------------------------- ▼Created By UniUni▼ -------------------------------------------------------------- */

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
   
   
   /* -------------------------------- ▼post/write 글 작성 액션▼ -------------------------------- */
   @ResponseBody
   @RequestMapping(value = "/write", method = RequestMethod.POST)
   public ResponseEntity<Map<String, Object>> insert(@RequestParam(value = "LOGIN_ID") String USER_ID, 
		   Post post, HttpServletRequest request) {
       Map<String, Object> result = new HashMap<>();
       
       HttpSession session = request.getSession();
       // 세션으로부터 BOARD_ID 및 USER_ID 값을 얻어옵니다.
       // 여기서 "boardId" 와 "userId" 는 세션에 저장된 실제 키 이름에 따라 변경되어야 합니다.
       int boardId = (Integer) session.getAttribute("board_id");
       
      String login_id =USER_ID;
       
      int user_id = memberService.getUserId(login_id);
       post.setBOARD_ID(boardId);
       post.setUSER_ID(user_id);
       
       try {
           postService.insertPost(post);
           result.put("statusCode", 1);
       } catch (Exception e) {
           result.put("statusCode", -1);
           result.put("errorMessage", e.getMessage());
       }
       return new ResponseEntity<>(result, HttpStatus.OK);
   }


   

   
/* -------------------------------------------------------------- ▲Created By UniUni▲ -------------------------------------------------------------- */
   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   //테스트 페이지
   @RequestMapping(value = "/test", method = RequestMethod.GET)
   public String test(HttpSession session) {
	   session.setAttribute("school_id", 1);										//테스트
	   return "post/test";
   }
      
   //게시물 리스트 출력
   @RequestMapping(value = "/list", method = RequestMethod.GET)
   public ModelAndView postlist(
   @RequestParam(value = "page", defaultValue = "1", required = false) int page, 
   @RequestParam(value = "board_id", required = false) int board_id,
   HttpSession session,
   ModelAndView mv) {
		
	   session.setAttribute("board_id", board_id);
	   session.setAttribute("user_id", 1);
	   	int limit = 10;
	   	
		// 총 리스트 수
		int listcount = postService.getListCount(board_id);
		
		// 게시판 이름
		List<Board> board = boardService.getBoardName(board_id);
		
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
	    System.out.println("이거 뭐갖고가는거야" + board);
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
	    System.out.println("학교번호" + session.getAttribute("school_id"));						//테스트
		return mv;
	}

   //검색기능
   @RequestMapping(value = "/search")
   public ModelAndView postsearch(
	
		   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
		   @RequestParam(value = "search_field", defaultValue = "0") int search_field,
		   @RequestParam(value = "search_word", defaultValue = "") String search_word,
		   HttpSession session,
		   ModelAndView mv){
	   
//	   session.setAttribute("board_id", board_id);
	   session.setAttribute("search_word", search_word);
//	   session.setAttribute("search_field", search_field);
	   int board_id = (int) session.getAttribute("board_id");
//	   search_field = (int) session.getAttribute("search_field");
	   search_word = (String) session.getAttribute("search_word");
	   
	   int school_id = (int) session.getAttribute("school_id");
	   
	   int limit = 10;
	   int listcount = 0;
	   List<Post> postlist = null;
	   int searchcheck = 0;
	   int allsearchcheck = 0;
	   
	   
	   //일반 리스트 출력 or 검색 리스트 출력 or 전체 검색 리스트 출력
	    if(search_word == null || search_word.equals("")) {
			// 총 리스트 수
			listcount = postService.getListCount(board_id);
		    // 게시글 리스트
		    postlist = postService.getPostList(page, limit, board_id);	    	
	    } else if(search_word != null && search_field < 4){
	    	searchcheck = 1;
	    	// 총 리스트 수
	    	listcount = postService.getSearchListCount(board_id, search_field, search_word);
	    	// 게시글 리스트
	    	postlist = postService.getSearchPostList(page, limit, board_id, search_field, search_word);	    	
	    } else if(search_field == 4){
	    	allsearchcheck = 1;
	    	// 총 리스트 수
	    	listcount = postService.getAllSearchListCount(school_id, search_word);
	    	// 게시글 리스트
	    	postlist = postService.getAllSearchPostList(page, limit, school_id, search_word);	
	    	System.out.println("전체검색 테스트");
	    }
	    
	    // 게시판 이름
	    List<Board> board = boardService.getBoardName(board_id);

	    // 총 페이지 수
	    int maxpage = (listcount + limit - 1) / limit;
	    
	    // 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
	    int startpage = ((page - 1) / 10) * 10 + 1;
	    
	    // 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
	    int endpage = startpage + 10 - 1;
	    
	    if (endpage > maxpage)
	    	endpage = maxpage;
	    
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
	    mv.addObject("boardname", board);

	    mv.addObject("un", username);
	    mv.addObject("allsearchcheck", allsearchcheck);
	    mv.addObject("emptycheck", 0);
	    
	    mv.addObject("searchcheck", searchcheck);
	    
	    // 글이 없을때 체크 (1= 일반, 2= 검색)
	    if(postlist != null) {
	    	mv.addObject("emptycheck", 2);
	    }
	    
	      
	    System.out.println("!======= 보드넘테스트[" + board_id + "] / 서치체크[" + searchcheck + "] / 올서치체크 [" + allsearchcheck + "]");
	    System.out.println("!======= 값테스트" + postlist);
		return mv;
   }
}
