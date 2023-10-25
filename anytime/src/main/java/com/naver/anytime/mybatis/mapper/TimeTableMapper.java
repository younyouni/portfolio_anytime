package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.naver.anytime.domain.TimeTable;

@Mapper
public interface TimeTableMapper {
	
	public List<TimeTable> gettimetable(int user_id);
	
	List<TimeTable> getTimetablesBySemester(@Param("semester") String semester, @Param("user_id") int user_id);
	
//	public List<TimeTable> getTimetableByUserId(int user_id);
//
//	public void changeName(int timetable_id, String newName);
//
//	public void changeNameAndTime(int timetable_id, String newName);
//
//	public void changeNameAndTime(TimeTable timetable);
//	
//	public TimeTable createNewTimeTable(int user_id);
//
//	public int getLastTimeTableId(Integer user_id);
//
//	public void insert(TimeTable newTimeTable);
//	
//	List<TimeTable> getTimetableByUserIdAndSemester(@Param("user_id") Integer user_id, @Param("semester") String semester);
//	public TimeTable createDefaultTimeTable(Integer user_id, String semester);
//	public void createDefaultTimeTable(TimeTable defaultTimeTable);
	
	
}
