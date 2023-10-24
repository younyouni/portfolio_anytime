package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.TimeTable;

@Mapper
public interface TimeTableMapper {
	
	public List<TimeTable> gettimetable(int user_id);
	
	public List<TimeTable> getTimetableByUserId(int user_id);

	public void changeName(int timetable_id, String newName);

	public void changeNameAndTime(int timetable_id, String newName);

	public void changeNameAndTime(TimeTable timetable);
	
	public TimeTable createNewTimeTable(int user_id);

	public int getLastTimeTableId(int user_id);

	public void insert(TimeTable newTimeTable);
	
}
