package com.naver.anytime.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Post;
import com.naver.anytime.mybatis.mapper.BoardMapper;
import com.naver.anytime.mybatis.mapper.PostMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper dao;
	private PostMapper postDao;

	@Autowired
	public BoardServiceImpl(BoardMapper boardDao) {
		this.dao = boardDao;
	}

	@Override
	public List<Board> getBoardName(int board_id) {
		return dao.getBoardName(board_id);
	}

   @Override
	public List<Board> getBoardList(int school_id){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("school_id", school_id);
		return dao.getBoardList(map);
	}

	@Override
	public Board getBoardDetail(int board_id) {
		return dao.getBoardDetail(board_id);
	}

	// 보드 익명 체크
	public int getBoardAnonymous(int board_id) {
		return dao.getBoardAnonymous(board_id);
	}

	// ********************************= 윤희 =********************************
	@Override
	public int[] getBoardIds(String id) {
		return dao.getBoardIds(id);
	}

	@Override
	public int[] getBoardIdsByDomain(String schoolDomain) {
		return dao.getBoardIdsByDomain(schoolDomain);
	}

	@Override
	public List<Board> getBoardContent(int board_id) {
		return dao.getBoardContent(board_id);
	}

	@Override
	public int updateBoardContent(int board_id, String content) {
		return dao.updateBoardContent(board_id, content);
	}

	@Override
	public int getboardManager(int board_id, int user_id) {
		return dao.getboardManager(board_id, user_id);
	}

}