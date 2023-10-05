package com.naver.anytime.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.School;

/*
 *  Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
 *  MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 SQL 처리가 되는 클래스를 자동으로 생성합니다.
 */
@Mapper
public interface SchoolMapper {

	List<School> getSchoolList();

	public String getSchoolDomain(String SchoolName);

	String getSchoolName(String schoolDomain);

	School getSchool(String schoolDomain);
}
