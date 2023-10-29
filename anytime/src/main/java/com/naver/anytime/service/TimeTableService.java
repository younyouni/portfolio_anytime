package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.TimeTable;

public interface TimeTableService {

	List<TimeTable> gettimetable(int user_id);

	public List<TimeTable> getTimetableByUserId(int user_id);

//	public void changeName(int timetable_id, String newName);

	public void updateTimetable(int user_id,int timetable_id, String newName, int status);

	public int createNewTimeTable(int user_id, String semester);

	public int getLastTimeTableId(int user_id);

	public List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester);

	public int createDefaultTimeTable(int user_id, String semester);

	public TimeTable getNewTimetable(int key);

	int checkTimetable(int userId, String semester);
	
	// 시간표 링크진입 실험용
	public TimeTable getTimeTableById(int timetable_id);
	
	public void deleteTimetable(int userId, int timetable_id, Integer status);
	
	

}
