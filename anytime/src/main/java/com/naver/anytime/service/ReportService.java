package com.naver.anytime.service;

import java.util.List;

import com.naver.anytime.domain.Report;

public interface ReportService {

	int insertReport(int id, int report_user_id, String reason);	// id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

	List<Report> getReportRequest(int order);

}
