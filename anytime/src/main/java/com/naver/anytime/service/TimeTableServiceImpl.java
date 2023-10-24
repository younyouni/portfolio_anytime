package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.mybatis.mapper.TimeTableMapper;

@Service
public class TimeTableServiceImpl implements TimeTableService{
    private TimeTableMapper timeDao;
    
    @Autowired
	public TimeTableServiceImpl(TimeTableMapper timeDao) {
		this.timeDao = timeDao;
    }
    
    @Override
	public List<TimeTable> gettimetable(int user_id) {
		return timeDao.gettimetable(user_id);
	}
    
	@Override
	public List<TimeTable> getTimetableByUserId(int user_id) {
		return timeDao.getTimetableByUserId(user_id);
	}

	@Override
	public void changeName(int timetable_id, String newName) {
		timeDao.changeName(timetable_id, newName);
		
	}
	
	

	
}
