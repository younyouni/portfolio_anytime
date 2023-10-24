package com.naver.anytime.service;

import java.util.Date;
import java.text.SimpleDateFormat;
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

	@Override
	public void changeNameAndTime(int timetable_id, String newName) {
		TimeTable timetable = new TimeTable();
        timetable.setTIMETABLE_ID(timetable_id);
        timetable.setNAME(newName);
        timetable.setTIMETABLE_DATE(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        timeDao.changeNameAndTime(timetable);
	}

//	@Override
//	public TimeTable createNewTimeTable(int user_id) {
//		TimeTable newTimeTable = new TimeTable();
//        newTimeTable.setUSER_ID(user_id);
//        newTimeTable.setNAME("시간표 " + (timeDao.getLastTimeTableId(user_id) + 1));
//        newTimeTable.setSTATUS(0);
//        timeDao.insert(newTimeTable);
//        return newTimeTable;
//	}
	
	@Override
	public TimeTable createNewTimeTable(int user_id) {
	    TimeTable newTimeTable = new TimeTable();
	    newTimeTable.setUSER_ID(user_id);
	    
	    // 가장 최근에 생성된 시간표의 ID에 1을 더한 값을 새로운 시간표의 ID로 설정
	    int newTimeTableId = timeDao.getLastTimeTableId(user_id) + 1;
	    newTimeTable.setTIMETABLE_ID(newTimeTableId);
	    
	    newTimeTable.setNAME("시간표 " + newTimeTableId);
	    newTimeTable.setSTATUS(0);
	    timeDao.insert(newTimeTable);
	    return newTimeTable;
	}

	
	

	
}
