package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.domain.TimeTable_detail;

public interface TimeTable_detailService {

	List<TimeTable_detail> getsubject(String timetableId);

	List<TimeTable_detail> getTimetableDetails(int timetable_id);
    
}
