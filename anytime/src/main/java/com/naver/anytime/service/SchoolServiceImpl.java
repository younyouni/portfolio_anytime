package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.School;
import com.naver.anytime.mybatis.mapper.SchoolMapper;

@Service
public class SchoolServiceImpl implements SchoolService {

	private SchoolMapper dao;

	@Autowired
	public SchoolServiceImpl(SchoolMapper schoolMapper) {
		this.dao = schoolMapper;
	}

	@Override
	public List<School> getSchoolList() {
		return dao.getSchoolList();
	}

	@Override
	public String getSchoolDomain(String SchoolName) {
		return dao.getSchoolDomain(SchoolName);
	}
}
