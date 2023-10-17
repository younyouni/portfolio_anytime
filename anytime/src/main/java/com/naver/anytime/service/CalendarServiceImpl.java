package com.naver.anytime.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Calendar;
import com.naver.anytime.mybatis.mapper.CalendarMapper;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	private CalendarMapper dao;
	
	public CalendarServiceImpl(CalendarMapper dao) {
		this.dao = dao;
	}

	@Override
	public List<Calendar> getCalendarList(int user_id) {
		return dao.getCalendarList(user_id);
	}
	
	

}
