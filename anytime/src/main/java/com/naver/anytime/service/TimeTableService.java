package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.TimeTable;

public interface TimeTableService {

	List<TimeTable> gettimetable(int user_id);

	public List<TimeTable> getTimetableByUserId(int user_id);

	public void changeName(int timetable_id, String newName);

    

	
}
