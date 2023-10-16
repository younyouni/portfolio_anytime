package com.naver.anytime.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {

	int insertReport(int id, int report_user_id, String reason); // id = post_id or comment_id

	int updatePostReportCount(int post_id);

	int updateCommentReportCount(int comment_id);

}
