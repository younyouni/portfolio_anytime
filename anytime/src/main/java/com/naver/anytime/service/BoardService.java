package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Post;

public interface BoardService {

	//postlist.jsp 에서 post 테이블의 board_id 로 name 구하기
	public String getBoardName(int board_id);
	
	//보드 리스트
	public List<Board> getBoardList();
	
	public Board getBoardDetail(int board_id);
	
	//보드 익명 체크
	public int getBoardAnonymous(int board_id);


}
