package com.naver.anytime.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Board;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface BoardMapper {

	// 보드 이름 구하기
	public String getBoardName(int board_id);

	// 보드 리스트
	public List<Board> getBoardList(HashMap<String, Integer> map);

	public Board getBoardDetail(int board_id);

	// 보드 익명 체크
	public int getBoardAnonymous(int board_id);

	// ********************************= 윤희 =********************************
	public int[] getBoardNums(String id);
}
