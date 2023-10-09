package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Post;

public interface BoardService {

	// postlist.jsp 에서 post 테이블의 board_id 로 name 구하기
	public List<Board> getBoardName(int board_id);
	
	//보드 리스트
	public List<Board> getBoardList(int school_id);

	public Board getBoardDetail(int board_id);

	// 보드 익명 체크
	public int getBoardAnonymous(int board_id);

	// ********************************= 윤희 =********************************
	public int[] getBoardIds(String id);

	public int[] getBoardIdsByDomain(String schoolDomain);

	public int insertBoard(Board board);
	
	// ********************************= 윤희 =********************************
	
	public List<Board> getBoardContent(int board_id);
	
	public int updateBoardContent(int board_id, String content);
	
	public int getboardManager(int board_id, int user_id);


}
