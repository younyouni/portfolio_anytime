package com.naver.anytime.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;

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
	public List<Board> getBoardList(@RequestParam("NAME") String name, HttpSession session) {

		int school_id = (int) session.getAttribute("school_id");

		// 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성

		List<Board> responseData = boardService.getBoardList(school_id);
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return responseData;
	}

	@PostMapping(value = "/create", produces = "application/json")
	public String insertBoard(int school_id, String login_id, int type, String name, String content, String purpose, int anony) {
		
		Board board = new Board();
		int user_id = memberService.getUserId(login_id);
		//board.get

		//int result = boardService.insertBoard()
		// 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성
		// List<Board> responseData = boardService.getBoardList();
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return "main/community";
	}

}