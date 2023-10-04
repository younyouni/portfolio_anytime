package com.naver.anytime.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Member;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface MemberMapper {

	public Member isId(String id);

	public Member isNickname(String nickname);

	public int getSchoolIdByName(String campusName);

	public int insert(Member m);

	public int update(Member m);

	public void delete(String id);

	public int getSearchListCount(Map<String, String> map);

	public List<Member> getSearchList(Map<String, Object> map);

	public Member findMemberByUserId(int user_id);

	public String findIdByEmail(String email);

	// 윤희
	public String getSchoolDomain(String id);
}
