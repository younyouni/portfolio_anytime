package com.naver.anytime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Report;
import com.naver.anytime.mybatis.mapper.ReportMapper;

@Service
public class ReportServiceImpl implements ReportService {
	private ReportMapper dao;

	public ReportServiceImpl(ReportMapper dao) {
		this.dao = dao;
	}

	@Override
	public int insertReport(int id, int report_user_id, String reason) { // id = post_id or comment_id
		return dao.insertReport(id, report_user_id, reason);
	}

	@Override
	public int updatePostReportCount(int post_id) {
		return dao.updatePostReportCount(post_id);
	}

	@Override
	public int updateCommentReportCount(int comment_id) {
		return dao.updateCommentReportCount(comment_id);
	}

	/* =============================== 윤희 =============================== */
	@Override
	public List<Report> getReportRequest(int order) {
		return dao.getReportRequest(order);
	}

}
