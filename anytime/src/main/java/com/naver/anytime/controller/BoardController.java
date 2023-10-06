package com.naver.anytime.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.anytime.domain.Board;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private BoardService boardService;

	private CommentService commentService;

	@Value("${my.savefolder}")
	private String saveFolder;

	@Autowired
	public BoardController(BoardService boardService, CommentService commentService) {
		this.boardService = boardService;
		this.commentService = commentService;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Board> getBoardList(@RequestParam("NAME") String name) {
		
	    // 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성
	    List<Board> responseData = boardService.getBoardList();
	    // 생성된 JSON 데이터를 클라이언트에 응답으로 전송
	    return responseData;
	}
	
	@PostMapping(value = "/create",produces = "application/json")
	@ResponseBody
	public List<Board> insertBoard(@RequestParam("NAME") String name) {
		
		// 여기에서 데이터베이스 조회 또는 다른 로직을 수행하여 JSON 형식의 응답 데이터를 생성
		List<Board> responseData = boardService.getBoardList();
		// 생성된 JSON 데이터를 클라이언트에 응답으로 전송
		return responseData;
	}


}