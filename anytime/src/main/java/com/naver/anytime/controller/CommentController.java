package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.domain.Comments;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.constants.AnytimeConstants;

@RestController // @Controller + @ResponseBody
@RequestMapping(value = "/comment")
public class CommentController {
	private CommentService commentService;
	private MemberService memberService;

	@Autowired
	public CommentController(CommentService commentService, MemberService memberService) {
		this.commentService = commentService;
		this.memberService = memberService;
	}

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@PostMapping(value = "/list")
	public Map<String, Object> CommentList(int post_id) {
		List<Comments> commentlist = commentService.getCommentList(post_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentlist", commentlist);
		return map;
	}

	@PostMapping(value = "/add")
	public int CommentAdd(Comments co, Principal principal) {
		co.setUser_id(memberService.getUserId(principal.getName()));
		return commentService.insertComment(co);
	}

	@PostMapping(value = "/commentupdate")
	public int CommentUpdate(Comments co) {
		return commentService.commentsUpdate(co);
	}

	@PostMapping(value = "/commentdelete")
	public int CommentDelete(int num) {
		return commentService.commentsDelete(num);
	}

	@PostMapping(value = "/reply")
	public int CommentReply(Principal principal, Comments co) {
		int result = 0;
		Map<String, Object> map = new HashMap<>();
		map.put("re_ref", co.getRe_ref());
		map.put("re_seq", co.getRe_seq());
		logger.info("대댓글 진입");
		int updateResult = commentService.updateDepth(map);
		if (updateResult == AnytimeConstants.UPDATE_COMPLETE) {
			co.setUser_id(memberService.getUserId(principal.getName()));
			result = commentService.replyComment(co);
		}else {
			logger.info("depth 변경 실패");
		}
		return result;
	}

}
