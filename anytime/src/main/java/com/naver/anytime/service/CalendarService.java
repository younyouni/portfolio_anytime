package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Calendar;

public interface CalendarService {

	public List<Calendar> getCalendarList(int user_id);

	public int insertCalendar(String title, int user_id, String type, String color, String start, String end, int allday, String description);

}
