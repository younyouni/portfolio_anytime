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
	public List<TimeTable> getTimetablesBySemester(String semester, int user_id) {
		return timeDao.getTimetablesBySemester(semester, user_id);
	}
    
//	@Override
//	public List<TimeTable> getTimetableByUserId(int user_id) {
//		return timeDao.getTimetableByUserId(user_id);
//	}
//
//	@Override
//	public void changeName(int timetable_id, String newName) {
//		timeDao.changeName(timetable_id, newName);
//		
//	}
//
//	@Override
//	public void changeNameAndTime(int timetable_id, String newName) {
//		TimeTable timetable = new TimeTable();
//        timetable.setTIMETABLE_ID(timetable_id);
//        timetable.setNAME(newName);
//        timetable.setTIMETABLE_DATE(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        timeDao.changeNameAndTime(timetable);
//	}
//
////	@Override
////	public TimeTable createNewTimeTable(int user_id) {
////		TimeTable newTimeTable = new TimeTable();
////        newTimeTable.setUSER_ID(user_id);
////        newTimeTable.setNAME("시간표 " + (timeDao.getLastTimeTableId(user_id) + 1));
////        newTimeTable.setSTATUS(0);
////        timeDao.insert(newTimeTable);
////        return newTimeTable;
////	}
//	
//	@Override
//	public TimeTable createNewTimeTable(int user_id) {
//	    TimeTable newTimeTable = new TimeTable();
//	    newTimeTable.setUSER_ID(user_id);
//	    
//	    // 가장 최근에 생성된 시간표의 ID에 1을 더한 값을 새로운 시간표의 ID로 설정
//	    int newTimeTableId = timeDao.getLastTimeTableId(user_id) + 1;
//	    newTimeTable.setTIMETABLE_ID(newTimeTableId);
//	    
//	    newTimeTable.setNAME("시간표 " + newTimeTableId);
//	    
//	    // semester 필드에 적절한 값을 설정해주세요.
//	    newTimeTable.setSEMESTER("적절한 값");
//	    
//	    newTimeTable.setSTATUS(0);
//	    timeDao.insert(newTimeTable);
//	    return newTimeTable;
//	}
//
//
//	@Override
//	public List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester) {
//	    List<TimeTable> timetables = timeDao.getTimetableByUserIdAndSemester(user_id, semester);
//	    if (timetables.isEmpty()) {
//	        // 해당 학기의 시간표가 없을 경우, 기본 시간표를 생성하도록 수정
//	        TimeTable defaultTimeTable = createDefaultTimeTable(user_id, semester);
//	        timetables.add(defaultTimeTable);
//	    }
//	    return timetables;
//	}
//
//	@Override
//	public TimeTable createDefaultTimeTable(int user_id, String semester) {
//	    TimeTable defaultTimeTable = new TimeTable();
//	    int newTimeTableId = timeDao.getLastTimeTableId(user_id) + 1;
//	    defaultTimeTable.setTIMETABLE_ID(newTimeTableId);
//	    defaultTimeTable.setUSER_ID(user_id);
//	    defaultTimeTable.setNAME("시간표 1");
//	    defaultTimeTable.setSEMESTER(semester);  // 학기 정보 설정
//	    defaultTimeTable.setSTATUS(1);
//	    timeDao.createDefaultTimeTable(defaultTimeTable);
//	    return defaultTimeTable;
//	}




	
}
