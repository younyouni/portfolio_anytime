package com.naver.anytime.controller;

import java.io.File;
import java.security.Principal;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Member;
import com.naver.anytime.domain.Photo;
import com.naver.anytime.domain.Post;
import com.naver.anytime.domain.PostLike;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostLikeService;
import com.naver.anytime.service.PostPhotoService;
import com.naver.anytime.service.PostService;

@Controller
@RequestMapping(value = "/post")
public class PostController {
	
   private static final Logger logger = LoggerFactory.getLogger(PostController.class);
   
   private PostService postService;
   
   private BoardService boardService;
   
   private CommentService commentService;
   
   private MemberService memberService;
   
   private PostLikeService postLikeService;
   
   private PostPhotoService postPhotoService;
   
   @Value("${my.savefolder}")
   private String saveFolder;
   
   @Autowired
   public PostController(PostService postService, BoardService boardService, CommentService commentService, MemberService memberService, 
		   				 PostLikeService postLikeService, PostPhotoService postPhotoService) {
	  this.postService = postService;
      this.boardService = boardService;
      this.commentService = commentService;
      this.memberService = memberService;
      this.postLikeService = postLikeService;
      this.postPhotoService = postPhotoService;
   }
   
/* -------------------------------------------------------------- ▼Created By UniUni▼ -------------------------------------------------------------- */

   /* -------------------------------- ▼post/detail 상세페이지▼ -------------------------------- */ 
   @GetMapping("/detail") // http://localhost:9700/anytime/post/detail?post_id=1 주소예시입니다.
   public ModelAndView postDetail(
	   @RequestParam(value = "post_id", required = false) int post_id,
	   ModelAndView mv,
	   HttpServletRequest request, Principal userPrincipal) {
	   
	   HttpSession session = request.getSession(); 
	   
	   //현재 로그인된 유저아이디를 가져오기 위한 코드입니다.
	   String id = userPrincipal.getName(); 
	   Member member = memberService.getLoginMember(id);
	   int currentUserId = member.getUser_id();
	   
	   
	   
	   Post post = postService.getDetail(post_id); // post테이블 정보 가져오기위한 메소드입니다.
	   Board board = boardService.getBoardDetail(post.getBOARD_ID()); // board테이블 정보 가져오기위한 메소드입니다.
	   
	   
	      // post = null; //error 페이지 이동 확인하고자 임의로 지정합니다.
	      if (post == null) {
	         logger.info("상세보기 실패");
	         mv.setViewName("error/error");
	         mv.addObject("url", request.getRequestURL());
	         mv.addObject("message", "상세보기 실패입니다.");
	      }else {
	    	  logger.info("★ 상세보기 성공 ★");
	    	  
	    	  logger.info("현재로그인 유저아이디 => " + userPrincipal.getName());
	   	   	  logger.info("유저학교(SCHOOL) 고유번호 => " + memberService.getSchoolId(id));
	    	  
	    	  if (board.getANONYMOUS() == 0) {
	    		   member = memberService.findMemberByUserId(post.getUSER_ID());
	              if (member != null){
	                  String nickname = member.getNickname();
	                  mv.addObject("nickname", nickname);
	              }
	          }
	    	  
	    	  mv.setViewName("post/postDetail");
	    	  mv.addObject("postdata", post);
	    	  mv.addObject("boardtest", board);
	    	  mv.addObject("currentUserId", currentUserId);
	    	  mv.addObject("anonymous", board.getANONYMOUS());
	    	  System.out.println("post테스트=>"+post);
	    	  
	      }
	      return mv;
	     
	   }
   
   /* -------------------------------- ▼post/updateGet 상세페이지-수정_데이터값받기(GET방식)▼ -------------------------------- */
   @GetMapping("/updateGet")
   @ResponseBody
   public Map<String, Object> updateGet(
       @RequestParam(value = "post_id", required = false) Integer post_id,
       HttpServletRequest request, Principal userPrincipal) {

       // 현재 로그인된 유저아이디를 가져오기 위한 코드입니다.
       String id = userPrincipal.getName();
       Member member = memberService.getLoginMember(id);
       int currentUserId = member.getUser_id();

       Post post = postService.getDetail(post_id); // post테이블 정보 가져오기위한 메소드입니다.
       
       if (post == null) {
           logger.info("상세보기 실패");
           return Collections.singletonMap("error", "상세보기 실패입니다.");
       } else {
           logger.info("★ 상세보기 성공 ★");

           Map<String, Object> responseMap = new HashMap<>();
           responseMap.put("SUBJECT", post.getSUBJECT());
           responseMap.put("CONTENT", post.getCONTENT());
           
           return responseMap;
      }
   }
   
   
   
   
//   /* -------------------------------- ▼post/write 글 작성 액션▼ -------------------------------- */
//   @ResponseBody
//   @RequestMapping(value = "/write", method = RequestMethod.POST)
//   public ResponseEntity<Map<String, Object>> insert(
//	   @RequestParam(value = "LOGIN_ID", required=false) String USER_ID, 
//	   Post post, HttpServletRequest request) {
//       Map<String, Object> result = new HashMap<>();
//       
//       HttpSession session = request.getSession();
//       // 세션으로부터 BOARD_ID 및 USER_ID 값을 얻어옵니다.
//       // 여기서 "boardId" 와 "userId" 는 세션에 저장된 실제 키 이름에 따라 변경되어야 합니다.
//       int boardId = (Integer) session.getAttribute("board_id");
//      String login_id =USER_ID;
//       
//      int user_id = memberService.getUserId(login_id);
//       post.setBOARD_ID(boardId);
//       post.setUSER_ID(user_id);
//       
//       try {
//           postService.insertPost(post);
//           result.put("statusCode", 1);
//       } catch (Exception e) {
//           result.put("statusCode", -1);
//           result.put("errorMessage", e.getMessage());
//       }
//       return new ResponseEntity<>(result, HttpStatus.OK);
//   }
   
   /* -------------------------------- ▼post/write 글 작성 액션 첨부파일 포함 실험용▼ -------------------------------- */
   @ResponseBody
   @PostMapping(value = "/write")
   public ResponseEntity<Map<String, Object>> writePost(
      @RequestParam(value = "LOGIN_ID", required=false) String USER_ID,
      @RequestPart(value = "file[]", required = false) MultipartFile[] files,
      Post post, HttpServletRequest request) {

    Map<String, Object> result = new HashMap<>();

    HttpSession session = request.getSession();
    int boardId = (Integer) session.getAttribute("board_id");
    String login_id =USER_ID;

    int user_id = memberService.getUserId(login_id);
    post.setBOARD_ID(boardId);
    post.setUSER_ID(user_id);
    
    StringBuilder sbFiles = new StringBuilder();

    try {

        // 이미지 파일 저장
        if(files != null && files.length >0){
            for(MultipartFile file : files){
                if(!file.isEmpty()){
                    Photo photo=new Photo();
                    photo.setPOST_ID(post.getPOST_ID());
                    
                    String originalFilename=file.getOriginalFilename();// 원래 파일명
                    System.out.println("Processing file: " + originalFilename);
                    //파일 경로 설정 및 실제 파일을 디스크에 저장하는 로직.
                    String saveFolder="c:/upload/";
                    
                    String fileDBName=fileDBName(originalFilename, saveFolder); 
                    
                    file.transferTo(new File(saveFolder + fileDBName));

                    photo.setPATH(saveFolder+fileDBName);
                    
                     post.setPOST_FILE(originalFilename);

                   postPhotoService.insertPhoto(photo); 
                   
                   sbFiles.append(originalFilename).append(",");
                }
            }
        }
        
        if(sbFiles.length() > 0) {
            sbFiles.setLength(sbFiles.length() - 1);
        }
        
        post.setPOST_FILE(sbFiles.toString());
        // 게시글 저장
        postService.insertPost(post);
        
        result.put("statusCode", 1);

     } catch (Exception e) {
         result.put("statusCode", -1);
         result.put("errorMessage", e.getMessage());
     }

     return new ResponseEntity<>(result, HttpStatus.OK);
   }


   private String fileDBName(String fileName, String saveFolder) {
       Calendar c=Calendar.getInstance();
       int year=c.get(Calendar.YEAR);// 오늘 년도 구합니다.
       int month=c.get(Calendar.MONTH)+1;// 오늘 월 구합니다.
       int date=c.get(Calendar.DATE);// 오늘 일 구합니다.

       String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
       
       File path1=new File(homedir);
       if(!(path1.exists())) {
           path1.mkdir();// 새로운 폴더를 생성
       }

       Random r=new Random();
       int random=r.nextInt(100000000);

       
      
   	int index=fileName.lastIndexOf(".");
   	
   	String fileExtension=fileName.substring(index+1);

   	String refileName="bbs"+year+month+date+random+"."+fileExtension;

   	
   	String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
   	

   	return fileDBName;
   }


   
   
//   private String fileDBName(String fileName, String saveFolder) {
//	      // 새로운 폴더 이름 : 오늘 년 + 월 + 일
//	      Calendar c = Calendar.getInstance();
//	      int year = c.get(Calendar.YEAR);// 오늘 년도 구합니다.
//	      int month = c.get(Calendar.MONTH) + 1;// 오늘 월 구합니다.
//	      int date = c.get(Calendar.DATE);// 오늘 일 구합니다.
//
//	      String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
//	      logger.info(homedir);
//	      File path1 = new File(homedir);
//	      if (!(path1.exists())) {
//	         path1.mkdir();// 새로운 폴더를 생성
//	      }
//
//	      // 난수를 구합니다.
//	      Random r = new Random();
//	      int random = r.nextInt(100000000);
//
//	      /**** 확장자 구하기 시작 ****/
//	      int index = fileName.lastIndexOf(".");
//	      // 문자열에서 특정 문자열의 위치 값(index)를 반환합니다.
//	      // indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
//	      // lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환합니다.
//	      // (파일명에 점에 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴합니다.
//	      logger.info("index = " + index);
//
//	      String fileExtension = fileName.substring(index + 1);
//	      logger.info("fileExtension = " + fileExtension);
//
//	      // 새로운 파일명
//	      String refileName = "bbs" + year + month + date + random + "." + fileExtension;
//	      logger.info("refileName = " + refileName);
//
//	      // 오라클 디비에 저장될 파일 명
//	      // String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
//	      String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
//	      logger.info("fileDBName = " + fileDBName);
//
//	      return fileDBName;
//	   }
   
   /* -------------------------------- ▼post/updatePost 상세페이지-수정_데이터값출력(POST방식)▼ -------------------------------- */
   @PostMapping("/updatePost")
   @ResponseBody
   public Map<String, Object> updatePost(
       @RequestParam(value = "LOGIN_ID", required=false) String USER_ID,
       @RequestParam(value = "POST_ID") Integer post_id,
       @RequestParam(value = "SUBJECT") String subject,
       @RequestParam(value = "CONTENT") String content,
       HttpServletRequest request, Principal userPrincipal) {

      // 현재 로그인된 유저아이디를 가져오기 위한 코드입니다.
      String id = userPrincipal.getName();
      Member member = memberService.getLoginMember(id);
      int currentUserId = member.getUser_id();

      Post post = new Post();
      
      // 요청으로 받은 파라미터로 post 객체 설정
      post.setPOST_ID(post_id);
      
      if (USER_ID != null) {
          int userId = memberService.getUserId(USER_ID);
          post.setUSER_ID(userId);
      } else {
          post.setUSER_ID(currentUserId);  // USER_ID가 없다면 현재 사용자 ID 사용
      }
      
      post.setSUBJECT(subject);
      post.setCONTENT(content);

     Map<String, Object> result = new HashMap<>();
     
     try {
         postService.updatePost(post);
         result.put("statusCode", 1);
     } catch (Exception e) {
         result.put("statusCode", -1);
         result.put("errorMessage", e.getMessage());
     }
     
     return result;
   }
   
   /* -------------------------------- ▼post/delete 글 삭제 액션▼ -------------------------------- */
   @ResponseBody
   @GetMapping("/delete")
   public ResponseEntity<?> deletePost(@RequestParam("post_id") int post_id) {
       int result = postService.updatePostStatus(post_id);
       if (result > 0) {
           return new ResponseEntity<>("게시글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
       } else {
           return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
//   /* -------------------------------- ▼post/postLike 글 공감 액션▼ -------------------------------- */
//   @ResponseBody
//   @PostMapping("/likePost")
//   public ResponseEntity<Map<String, Object>> likePost(
//       @RequestParam(value = "post_id") Integer post_id,
//       HttpServletRequest request, Principal userPrincipal) {
//
//       String id = userPrincipal.getName();
//       Member member = memberService.getLoginMember(id);
//       int currentUserId = member.getUser_id();
//
//       Map<String, Object> result = new HashMap<>();
//       
//       PostLike existingLike = postLikeService.findExistingLike(post_id, currentUserId);
//
//       if(existingLike == null) {
//           try {
//               postService.increaseLike(post_id, currentUserId); 
//
//               Post post= postService.getDetail(post_id); // 게시글 상세 정보 다시 조회
//               int like_count = post.getLIKE_COUNT(); // 최신의 공감 수 가져오기
//
//               result.put("statusCode", 1);
//               result.put("like_count", like_count); 
//           } catch(Exception e) {
//               result.put("statusCode", -1);
//               result.put("errorMessage", e.getMessage());
//           }
//      } else { 
//          // 이미 공감한 경우
//          result.put("statusCode", 3);
//          return new ResponseEntity<>(result, HttpStatus.OK);
//      }
//
//      return new ResponseEntity<>(result, HttpStatus.OK);
//   }
   		
   /* -------------------------------- ▼post/postLike 글 공감 액션 실험용▼ -------------------------------- */
   
   @PostMapping("/likePost")
   public ResponseEntity<Map<String, Object>> likePost(
		   @RequestParam(value = "POST_ID", required=false) Integer post_id,
           Principal userPrincipal) {
       String id = userPrincipal.getName();
       Member member = memberService.getLoginMember(id);
       int currentUserId = member.getUser_id();

       Map<String, Object> resultMap = new HashMap<>();

       PostLike postLike = new PostLike();
       logger.info("Received POST_ID: " + post_id);  // POST_ID 값 로그 출력
       logger.info("Initial PostLike: " + postLike); // 초기 PostLike 객체 상태 로그 출력
       postLike.setPOST_ID(post_id);
       postLike.setUSER_ID(currentUserId);

       int likeCount = postLikeService.checkIfUserAlreadyLiked(postLike);

       if(likeCount == 0) {
           try {
               postLikeService.addNewlike(postLike); 

               resultMap.put("statusCode", 1);
               resultMap.put("like_count", postService.getPostLikes(post_id));
               
          } catch(Exception e) {
             resultMap.put("statusCode", -1);
             resultMap.put("errorMessage", e.getMessage());
          }
           
      } else { 
          try {
              postLikeService.removeExistinglike(postLike); 

              resultMap.put("statusCode", 2);
              resultMap.put("like_count", postService.getPostLikes(post_id));
              
          } catch(Exception e) {
             resultMap.put("statusCode", -1);
             resultMap.put("errorMessage", e.getMessage());
          }
      }

     return new ResponseEntity<>(resultMap, HttpStatus.OK); 
  }





   
/* -------------------------------------------------------------- ▲Created By UniUni▲ -------------------------------------------------------------- */
   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
   //게시물 리스트 출력
   @RequestMapping(value = "/list", method = RequestMethod.GET)
	   public ModelAndView postlist(
	   @RequestParam(value = "page", defaultValue = "1", required = false) int page, 
	   @RequestParam(value = "board_id", required = false) int board_id,
	   HttpSession session,
	   ModelAndView mv
	   ) {
		
	   session.setAttribute("board_id", board_id);
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
	    				if(memberService.getStatusCheck2(post2.getUSER_ID()) == 1) {
	    					post.setNICKNAME(post2.getNICKNAME());	    					
	    				}else {
	    					post.setNICKNAME("알 수 없음");	
	    				}
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
   @RequestMapping(value = "/search")
   public ModelAndView postsearch(
		   @RequestParam(value = "page", defaultValue = "1", required = false) int page,
		   @RequestParam(value = "search_field", defaultValue = "0") int search_field,
		   @RequestParam(value = "search_word", defaultValue = "") String search_word,
		   HttpSession session,
		   ModelAndView mv,
		   Principal principal
		   ) {
	   
//	   session.setAttribute("board_id", board_id);
	   session.setAttribute("search_word", search_word);
//	   session.setAttribute("search_field", search_field);
	   int board_id = (int) session.getAttribute("board_id");
//	   search_field = (int) session.getAttribute("search_field");
	   search_word = (String) session.getAttribute("search_word");
	   
	   String login_id = principal.getName();
	   
	   int school_id = memberService.getSchoolId(login_id);
	   
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
