package com.naver.anytime.mybatis.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendarMapper {

	Map<String, Object> getCalendarList(int user_id);

}
