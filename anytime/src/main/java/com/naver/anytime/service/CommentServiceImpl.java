package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Comments;
import com.naver.anytime.mybatis.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{
	private CommentMapper dao;
	
	@Autowired
	public CommentServiceImpl(CommentMapper dao) {
		this.dao=dao;
	}
	

	@Override
	public int getListCount(int board_num) {
		return dao.getListCount(board_num);
	}

	@Override
	public int commentsInsert(Comments c) {
		return dao.commentsInsert(c);
	}

	@Override
	public int commentsUpdate(Comments co) {
		return dao.commentsUpdate(co);
	}
	
	@Override
	public int commentsDelete(int num) {
		return dao.commentsDelete(num);
	}


	@Override
	public List<Comments> getCommentList(int post_id) {
		return dao.getCommentList(post_id);
	}

}
