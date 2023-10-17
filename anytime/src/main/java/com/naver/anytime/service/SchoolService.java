package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.School;

public interface SchoolService {
	
	List<School> getSchoolList();

	public String getSchoolDomain(String SchoolName);

	String getSchoolName(String schoolDomain);

	School getSchool(String schoolDomain);

	School getSchoolByUserId(int user_id);
	
	
}