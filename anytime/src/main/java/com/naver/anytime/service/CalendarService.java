package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Calendar;

public interface CalendarService {

	public List<Calendar> getCalendarList(int user_id);

}
