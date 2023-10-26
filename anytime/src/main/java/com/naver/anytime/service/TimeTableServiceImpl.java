package com.naver.anytime.service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.mybatis.mapper.TimeTableMapper;

@Service
public class TimeTableServiceImpl implements TimeTableService {
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

	@Override
	public int createNewTimeTable(int user_id, String semester) {

		String lastName = getLastTimeTableName(user_id, semester);
		System.out.println(lastName);
		int lastIndex = Integer.parseInt(lastName.replaceAll("[^0-9]", ""));

		String newName = "시간표" + (lastIndex + 1);
		
		return timeDao.insertNewTimetable(user_id, newName, semester);
		
	}

	private String getLastTimeTableName(int user_id, String semester) {
		return timeDao.getLastTimeTableName(user_id, semester);
	}

	private TimeTable getLastTimeTable(int user_id) {
		return timeDao.getLastTimeTable(user_id);
	}

	@Override
	public List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester) {
		return timeDao.getTimetableByUserIdAndSemester(user_id, semester);
	}

	@Override
	public TimeTable createDefaultTimeTable(int user_id, String semester) {
		TimeTable defaultTimeTable = new TimeTable();
		int newTimeTableId = timeDao.getLastTimeTableId(user_id) + 1;
		defaultTimeTable.setTIMETABLE_ID(newTimeTableId);
		defaultTimeTable.setUSER_ID(user_id);
		defaultTimeTable.setNAME("시간표 1");
		defaultTimeTable.setSEMESTER(semester); // 학기 정보 설정
		defaultTimeTable.setSTATUS(1);
		timeDao.createDefaultTimeTable(defaultTimeTable);
		return defaultTimeTable;
	}

	@Override
	public int getLastTimeTableId(int user_id) {
		return timeDao.getLastTimeTableId(user_id);
	}

	@Override
	public TimeTable getNewTimetable(int key) {
		return timeDao.getNewTimetable(key);
	}

}
