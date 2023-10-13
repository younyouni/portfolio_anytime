package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Semester;

@Mapper
public interface SemesterMapper {

	public int insert(Semester semester);

	public List<Semester> getSemestersByUserId(int userId);

}