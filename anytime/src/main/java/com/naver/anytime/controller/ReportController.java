package com.naver.anytime.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.CommentService;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;

@RestController
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	private ReportService reportService;
	private PostService postService;	   
	private BoardService boardService;
	private CommentService commentService;
	private MemberService memberService;
	
	@Autowired
	public ReportController(ReportService reportService, PostService postService, BoardService boardService, CommentService commentService, MemberService memberService) {
		this.reportService = reportService;
		this.postService = postService;
	    this.boardService = boardService;
	    this.commentService = commentService;
	    this.memberService = memberService;
	}
	
	@RequestMapping(value = "/report")
	@ResponseBody
	public int insertReport(
			@RequestParam(value = "post_id", required = false, defaultValue = "0") int post_id,
			@RequestParam(value = "comment_id", required = false, defaultValue = "0") int comment_id,
			@RequestParam("reportnum") int reportnum,
			Principal principal
			) {
		String login_id = principal.getName();						//로그인한 유저 login_id
		int report_user_id = memberService.getUserId(login_id);		//로그인한 유저 user_id 구하기
		
		int reportResult = 0;
		String reason = "";
		
		switch(reportnum) {
		case 1:
			reason ="게시판 성격에 부적절함";
			break;
		case 2:
			reason ="욕설/비하";
			break;
		case 3:	
			reason ="음란물/불건전한 만남 및 대화";
			break;
		case 4:	
			reason ="상업적 광고 및 판매";
			break;
		case 5:	
			reason ="유출/사칭/사기";
			break;
		case 6:	
			reason ="낚시/놀람/도배";
			break;
		case 7:	
			reason ="정당/정치인 비하 및 선거운동";
			break;
		}
		
		if(post_id > 0) {
			int postreport = reportService.insertReport(post_id,report_user_id,reason);
			int postreportupdate = reportService.updatePostReportCount(post_id);
			if(postreport == 1 && postreportupdate == 1) {
				reportResult = 1;			
			}
		}else if(comment_id > 0) {
			int commentreport = reportService.insertReport(comment_id,report_user_id,reason);
			int commentreportupdate = reportService.updateCommentReportCount(comment_id);
			if(commentreport == 1 && commentreportupdate == 1) {
				reportResult = 1;			
			}
		}

		return reportResult;
	}
}
