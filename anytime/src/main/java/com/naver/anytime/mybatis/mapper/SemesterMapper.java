package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Semester;

@Mapper
public interface SemesterMapper {

	int insert(Semester semester);

}
