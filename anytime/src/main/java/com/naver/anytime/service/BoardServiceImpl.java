package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Board;
import com.naver.anytime.mybatis.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

   private BoardMapper dao;

   @Autowired
   public BoardServiceImpl(BoardMapper boardDao) {
      this.dao = boardDao;
   }
	
	
   @Override
	public String getBoardName(int board_id) {
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


	//보드 익명 체크
	public int getBoardAnonymous(int board_id) {
		return dao.getBoardAnonymous(board_id);
	}
  
	

}