package com.naver.anytime.service;

import java.util.HashMap;
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
	public int insertReport(int id, int report_user_id, int reason) { // id = post_id or comment_id
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
//	@Override
//	public Map<String, Object> getReportRequest(int order) {
//		List<Report> reportrequest = dao.getReportRequest();
//		Map<String, Object> map = new HashMap<>();
//		map.put("reportrequest", reportrequest);
//		return map;
//	}

	@Override
	public int getListCount() {
		return dao.getListCount();
	}

	@Override
	public List<Report> getReportTotalList(int order, int page, int limit) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("order", order);
		map.put("start", startrow);
		map.put("end", endrow);
		
		return dao.getReportTotalList(map);
	}

}
