
package com.naver.anytime.service;

import java.util.List;
import java.util.Map;

import com.naver.anytime.domain.Comments;

public interface CommentService {

	// 글의 갯수 구하기
	public int getListCount(int board_num);

	// 댓글 삭제
	public int commentsDelete(int num);

	// 댓글 수정
	public int commentsUpdate(Comments co);

	// 댓글 목록 가져오기
	public List<Comments> getCommentList(int post_id);

	// 댓글 등록하기
	public int insertComment(Comments co);

	// 대댓글 달기
	public int replyComment(Comments co);

	// 대댓글 시퀀스, 리퍼런스로 접근
	public int updateDepth(Map<String, Object> map);

	public int insertReplyComment(Comments co);
}
