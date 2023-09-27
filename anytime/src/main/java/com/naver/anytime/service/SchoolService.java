package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.School;

public interface SchoolService {
	
	List<School> getSchoolList();

	public String getSchoolDomain(String SchoolName);
}