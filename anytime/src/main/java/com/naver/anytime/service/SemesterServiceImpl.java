package com.naver.anytime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Semester;
import com.naver.anytime.mybatis.mapper.SemesterMapper;

@Service
public class SemesterServiceImpl implements SemesterService {
	private SemesterMapper dao;
	
	@Autowired
	public SemesterServiceImpl(SemesterMapper dao) {
		this.dao = dao;
		
	}
	@Override
	public int insert(Semester semester) {
		return dao.insert(semester);
	}
	

}
