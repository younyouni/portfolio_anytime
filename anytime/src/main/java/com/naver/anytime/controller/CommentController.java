package com.naver.anytime.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.domain.Comments;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;

@RestController // @Controller + @ResponseBody
public class CommentController {
	private CommentService commentService;
	private MemberService memberService;

	@Autowired
	public CommentController(CommentService commentService, MemberService memberService) {
		this.commentService = commentService;
		this.memberService = memberService;
	}

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@PostMapping(value = "/commentlist")
	public Map<String, Object> CommentList(int post_id) {
		List<Comments> postlist = commentService.getCommentList(post_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postlist", postlist);
		return map;
	}

	@PostMapping(value = "/commentadd")
	public int CommentAdd(Comments co, Principal principal) {
		co.setUser_id(memberService.getUserId(principal.getName()));
		return commentService.commentsInsert(co);
	}

	@PostMapping(value = "/commentupdate")
	public int CommentUpdate(Comments co) {
		return commentService.commentsUpdate(co);
	}

	@PostMapping(value = "/commentdelete")
	public int CommentDelete(int num) {
		return commentService.commentsDelete(num);
	}

}
