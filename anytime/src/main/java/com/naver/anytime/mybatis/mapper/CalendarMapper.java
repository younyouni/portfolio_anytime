package com.naver.anytime.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Calendar;

@Mapper
public interface CalendarMapper {

	public List<Calendar> getCalendarList(int user_id);

}
