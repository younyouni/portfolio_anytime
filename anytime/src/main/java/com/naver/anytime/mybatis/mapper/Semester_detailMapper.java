package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Semester_detail;

@Mapper
public interface Semester_detailMapper {

	int insert(Semester_detail semester_detail);

}
