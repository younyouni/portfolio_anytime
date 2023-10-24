package com.naver.anytime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.TimeTable_detail;
import com.naver.anytime.mybatis.mapper.TimeTableMapper;
import com.naver.anytime.mybatis.mapper.TimeTable_detailMapper;

@Service
public class TimeTable_detailServiceImpl implements TimeTable_detailService{
    private TimeTable_detailMapper timeDetailDao;
    
    @Autowired
	public TimeTable_detailServiceImpl(TimeTable_detailMapper timeDetailDao) {
		this.timeDetailDao = timeDetailDao;
    }

	@Override
	public List<TimeTable_detail> getsubject(String timetableId) {
		return timeDetailDao.getsubject(timetableId);
	}
}
