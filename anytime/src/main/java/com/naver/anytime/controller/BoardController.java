package com.naver.anytime.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private BoardService boardService;

	private CommentService commentService;
	
	private MemberService memberService;

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
	public List<Board> getBoardList(@RequestParam("SCHOOL_ID") int school_id,
			HttpSession session
			) {
		
	    List<Board> responseData = boardService.getBoardList(school_id);
	    // 생성된 JSON 데이터를 클라이언트에 응답으로 전송
	    return responseData;
	}
	
//	@PostMapping(value = "/create",produces = "application/json")
//	@ResponseBody
//	public List<Board> insertBoard(@RequestParam("NAME") String name) {
//		
//		// 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성
//		//List<Board> responseData = boardService.getBoardList();
//		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
//		return responseData;
//	}
	
	@RequestMapping(value = "/getboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public List<Board> getBoardContent(@RequestParam("board_id") int board_id){
		
		System.out.println("BoardController 에서 content 받아오는 메서드 보드넘 테스트 " + board_id);
		List<Board> boardContentData = boardService.getBoardContent(board_id);
		return boardContentData;
	}
	
	@RequestMapping(value = "/updateboardcontent", method = RequestMethod.GET)
	@ResponseBody
	public int updateBoardSetting(
			@RequestParam("board_id") int board_id,
			@RequestParam("content") String content){
		
		// db 변경하기 귀찮아서 임시 설정 content 제약조건 notnull 변경해야함
		if(content.equals("")) {
			content = "없음";
		}
		
		int updateData = boardService.updateBoardContent(board_id, content);
		System.out.println("BoardController에서 content 업데이트 성공 여부 테스트 " + updateData);
		return updateData;
	}
	
	@RequestMapping(value = "/managercheck", method = RequestMethod.GET)
	@ResponseBody
	public int boardManagerCheck(
			@RequestParam("board_id") int board_id,
			@RequestParam("LOGIN_ID") String login_id) {
		
		int user_id = memberService.getUserId(login_id);
		
		int managerCheck = boardService.getboardManager(board_id, user_id);
		
		return managerCheck;
	}
	

}