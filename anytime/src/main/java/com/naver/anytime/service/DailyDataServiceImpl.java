package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.DailyData;
import com.naver.anytime.mybatis.mapper.DailyDataMapper;

@Service
public class DailyDataServiceImpl implements DailyDataService {

	private DailyDataMapper dao;

	@Autowired
	public DailyDataServiceImpl(DailyDataMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<DailyData> getDailyData() {
		// TODO Auto-generated method stub
		return null;
	}

}