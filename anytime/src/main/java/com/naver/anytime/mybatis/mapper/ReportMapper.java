package com.naver.anytime.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Report;

@Mapper
public interface ReportMapper {

	int insertReport(int id, int report_user_id, String reason); // id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

	/* =============================== 윤희 =============================== */
	
	List<Report> getReportRequest(int order);

}
