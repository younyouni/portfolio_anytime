package com.naver.anytime.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.mybatis.mapper.CalendarMapper;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	private CalendarMapper dao;
	
	@Autowired
	public CalendarServiceImpl(CalendarMapper dao) {
		this.dao = dao;
	}

	@Override
	public Map<String, Object> getCalendarList(int user_id) {
		return dao.getCalendarList(user_id);
	}
	
	

}
