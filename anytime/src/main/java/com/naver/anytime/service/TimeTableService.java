package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.domain.TimeTable_detail;

public interface TimeTableService {

	List<TimeTable> gettimetable(int user_id);

	
}
