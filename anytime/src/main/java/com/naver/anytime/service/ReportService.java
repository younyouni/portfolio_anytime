package com.naver.anytime.service;

public interface ReportService {

	int insertReport(int id, int report_user_id, String reason);	// id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

}
