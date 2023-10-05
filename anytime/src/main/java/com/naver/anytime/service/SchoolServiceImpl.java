package com.naver.anytime.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.School;
import com.naver.anytime.mybatis.mapper.SchoolMapper;

@Service
public class SchoolServiceImpl implements SchoolService {
	private static final Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);
	private SchoolMapper dao;

	@Autowired
	public SchoolServiceImpl(SchoolMapper schoolMapper) {
		this.dao = schoolMapper;
	}

	@Override
    public List<School> getSchoolList() {
        List<School> schools = dao.getSchoolList();
        
        // 에러 출력용
        for (School school : schools) {
            logger.info("Name: {}, Total Count: {}", school.getName(), school.getTotal_count());
        }
        
        return schools;
    }

	@Override
	public String getSchoolDomain(String SchoolName) {
		return dao.getSchoolDomain(SchoolName);
	}

	@Override
	public String getSchoolName(String SchoolDomain) {
		return dao.getSchoolName(SchoolDomain);
	}
}
