package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.TimeTable;

public interface TimeTableService {

	List<TimeTable> gettimetable(int user_id);

	public List<TimeTable> getTimetableByUserId(int user_id);

	public void changeName(int timetable_id, String newName);

	public void changeNameAndTime(int timetable_id, String newName);

	public int createNewTimeTable(int user_id, String semester);

	public int getLastTimeTableId(int user_id);

	public List<TimeTable> getTimetableByUserIdAndSemester(int user_id, String semester);

	public TimeTable createDefaultTimeTable(int user_id, String semester);

	public TimeTable getNewTimetable(int key);
	
	

}
