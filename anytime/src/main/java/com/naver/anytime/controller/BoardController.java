package com.naver.anytime.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.constants.AnytimeConstants;

@RestController
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private BoardService boardService;
	private MemberService memberService;
	private CommentService commentService;


	@Value("${my.savefolder}")
	private String saveFolder;

	@Autowired
	public BoardController(BoardService boardService, CommentService commentService, MemberService memberService) {
		this.boardService = boardService;
		this.commentService = commentService;
		this.memberService = memberService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getBoardList(@RequestParam("school_id") int school_id) {

		List<Board> responseData = boardService.getBoardList(school_id);
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return responseData;
	}

	@PostMapping(value = "/create")
	public String insertBoard(Board board, String LOGIN_ID, Model mv, RedirectAttributes rattr,
			HttpServletRequest request) {
		String url = "";
		int user_id = memberService.getUserId(LOGIN_ID);
		board.setUSER_ID(user_id);

		if (board.getTYPE() == AnytimeConstants.GROUP_BOARD || board.getTYPE() == AnytimeConstants.DEPARTMENT_BOARD) {
			board.setSTATUS(AnytimeConstants.STATUS_INACTIVE);
		} else {
			board.setSTATUS(AnytimeConstants.STATUS_ACTIVE);
		}

		int result = boardService.insertBoard(board);
		if (result == AnytimeConstants.INSERT_COMPLETE) {
			logger.info("게시판 생성 완료");
			rattr.addFlashAttribute("result", "insertBoardSuccess");
			url = "redirect:/member/boardlist";
		} else {
			logger.info("게시판 생성 실패");                  
			mv.addAttribute("url", request.getRequestURL());
			mv.addAttribute("message", "게시판 생성 실패");
			url = "error/error";

		}

		// int result = boardService.insertBoard()
		// 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성
		// List<Board> responseData = boardService.getBoardList();
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return url;
	}

	@RequestMapping(value = "/getboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getBoardContent(
			@RequestParam("board_id") int board_id
			) {
		System.out.println("BoardController 에서 content 받아오는 메서드 테스트 [board_id] = " + board_id);
		List<Board> boardContentData = boardService.getBoardContent(board_id);
		return boardContentData;
	}
	
	@RequestMapping(value = "/updateboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public int updateBoardSetting(
			@RequestParam("board_id") int board_id,
			@RequestParam("content") String content
			) {
		// db 변경하기 귀찮아서 임시 설정 content 제약조건 notnull 변경해야함 /////////////////////////////////////////////
		if(content.equals("")) {
			content = "없음";
		}

		int updateData = boardService.updateBoardContent(board_id, content);
		System.out.println("BoardController에서 content 업데이트 성공 여부 테스트 [0 or 1] = " + updateData);
		return updateData;
	}
	
	@RequestMapping(value = "/managercheck", method = RequestMethod.GET)
	@ResponseBody
	public int boardManagerCheck(
			@RequestParam("board_id") int board_id,
			@RequestParam("LOGIN_ID") String login_id
			) {
		int managerCheck = 0;
		int user_id = memberService.getUserId(login_id);
		int check = boardService.getBoardManager(board_id, user_id);

		if(check != 0) {
			managerCheck = 1;
		}
		
		System.out.println("BoardController에서 보드 권한 체크 테스트 [0 or 1] = " + managerCheck);
		
		return managerCheck;
	}
	
	@RequestMapping(value = "/deleteboard", method = RequestMethod.GET)
	@ResponseBody
	public int deleteBoard(
			@RequestParam(name = "board_id", defaultValue = "-1") int board_id,
	        @RequestParam("board_name") String board_name,
	        @RequestParam("LOGIN_ID") String login_id
			) {
	    int deleteResult = 0;
	    int boardnamecheck = boardService.getBoardName2(board_name, board_id);

	    if(board_id > 0) {
		    if(boardnamecheck == 1) {
		    	System.out.println("[1/3](1/2) board_id 체크 " + board_id);
		        int user_id = memberService.getUserId(login_id);
		        int check_auth = boardService.deleteBoardAuth(board_id);

		        System.out.println("[1/3](2/2) 유저 아이디 [" + user_id + "] 체크 권한 [" + check_auth + "]");
		        	
			        if(check_auth == 1) {
				    	System.out.println("[2/3](1/2) board_auth 삭제 체크");
				    	int check = boardService.deleteBoard(board_name, user_id);
						System.out.println("[2/3](2/2) 보드 삭제 [" + check + "]");
																					
							if (check_auth == 1 && check == 1) {
								System.out.println("[3/3] 최종 완료");
								deleteResult = 1;
							}
									
			        } else {
			        	deleteResult = 3;	//권한 삭제 문제 생겼을때
			        }
		    } else {
		    	deleteResult = 2;	//보드 이름이 다를때
		    }
	    }

	    	
	    System.out.println("보드네임 체크 [" + boardnamecheck + "]");
	    System.out.println("보내는값 체크 [" + deleteResult + "]");
	    System.out.println("BoardController 삭제 테스트에서 가져온 보드id [" + board_id + "] 가져온 보드name [" + board_name + "] 가져온 로그인id [" + login_id + "]");
	    
	    return deleteResult;
	}
	
	@RequestMapping(value = "/updatemanagerboard", method = RequestMethod.GET)
	@ResponseBody
	public int updateManagerBoard(
			
			) {
		int updateManagerBoardResult = 0;
		
		
		
		return updateManagerBoardResult;
	}

}