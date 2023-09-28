package com.naver.anytime.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Post;
import com.naver.anytime.mybatis.mapper.BoardMapper;
import com.naver.anytime.mybatis.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	private PostMapper postDao;
	private BoardMapper boardDao;

	@Autowired
	public PostServiceImpl(PostMapper postDao, BoardMapper boardDao) {
		this.postDao = postDao;
		this.boardDao = boardDao;
	}

	@Override
	public String getPostlist(String SchoolName) {
		return postDao.getPostlist(SchoolName);
	}

	@Override
	public int getListCount(int board_id) {
		return 0;
	}

	@Override
	public List<Post> getPostList(int board_num, int page, int limit) {
		return null;
	}

	@Override
	public List<Post> getPostList(int board_num, String search_field, String search_word, int page, int limit) {
		return null;
	}

	@Override
	public List<Post> findSchoolBoardlistcount(int school_num) {
		return null;
	}

	@Override
	public int getSearchListCount(List<Post> bnc, String search_word) {
		return 0;
	}

	@Override
	public List<Post> getSearchPostList(List<Post> bnc, String search_word, int page, int limit) {
		return null;
	}

	@Override
	public String findNickname(int num) {
		return null;
	}

	@Override
	public String findBoardname(int board_num) {
		return null;
	}

	@Override
	public int boardAnonymousCheck(int board_num) {
		return 0;
	}

	@Override
	public int getListCount(int board_num, String search_field, String search_word) {
		return 0;
	}

	@Override
	public Post getDetail(int post_id) {
		return postDao.getDetail(post_id);
	}

	@Override
	public int getHotListCount(int school_num) {
		return 0;
	}

	@Override
	public List<Post> getHotList(int school_num, int page, int limit) {
		return null;
	}

	@Override
	public int getBestListCount(int school_num) {
		return 0;
	}

	@Override
	public List<Post> getBestList(int school_num, int page, int limit) {
		return null;
	}

	@Override
	public boolean postInsert(Post postdata, String userid, String filename) {
		return false;
	}

	@Override
	public boolean postDataInsert(Connection con, Post postdata, String userid) {
		return false;
	}

	@Override
	public boolean photoDataInsert(Connection con, int post_num, String filename) {
		return false;
	}

	@Override
	public ArrayList<Post> getPostSimpleList(int school_num, int board_num) {
		return null;
	}

	@Override
	public List<ArrayList<Post>> getPostTotalList(int school_num, int[] board_nums) {
		return null;
	}

	@Override
	public boolean postModify(Post modifypost, String filename) {
		return false;
	}

	@Override
	public int postDelete(int post_num) {
		return 0;
	}
}