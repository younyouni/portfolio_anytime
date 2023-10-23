package com.naver.anytime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.mybatis.mapper.TimeTableMapper;
import com.naver.anytime.mybatis.mapper.TimeTable_detailMapper;

@Service
public class TimeTable_detailServiceImpl implements TimeTable_detailService{
    private TimeTable_detailMapper timeDetailDao;
    
    @Autowired
	public TimeTable_detailServiceImpl(TimeTable_detailMapper timeDetailDao) {
		this.timeDetailDao = timeDetailDao;
    }
}
