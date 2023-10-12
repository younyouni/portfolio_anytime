package com.naver.anytime.service;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Semester_detail;
import com.naver.anytime.mybatis.mapper.Semester_detailMapper;

@Service
public class Semester_detailServiceImpl implements Semester_detailService {
	private Semester_detailMapper dao;
	
	public Semester_detailServiceImpl(Semester_detailMapper dao) {
		this.dao  = dao;
	}

	public int insert(Semester_detail semester_detail) {
		return dao.insert(semester_detail);
	}
}
