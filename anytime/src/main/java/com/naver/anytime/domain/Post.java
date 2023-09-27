package com.naver.anytime.domain;

public class Post {
	private int    POST_ID;    		// 게시물 고유번호
	private int    BOARD_ID;   		// 게시판 고유번호
	private int	   USER_ID;   		// 유저 고유번호
	private String SUBJECT;			// 글 제목
	private String CONTENT;			// 글 내용
	private String POST_FILE;   	// 첨부파일
	private String POST_DATE; 		// 글 작성 시간
	private int    LIKE_COUNT; 		// 공감 수
	private int    SCRAP_COUNT;		// 스크랩 수
	private int    REPORT_COUNT; 	// 신고 수
	private int	   STATUS;			// 게시물 상태
	
	/* 게시물(POST) 테이블 별도로 추가부분 */	
	private int    comment_count;	//코멘트 수
	private String nickname;		//유저 번호로 닉네임 매칭
	private String boardname;		//게시판 번호로 게시판이름 매칭
	
	/* ---------- POST테이블 Getter/Setter ---------- */
	public int getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}
	public int getBOARD_ID() {
		return BOARD_ID;
	}
	public void setBOARD_ID(int bOARD_ID) {
		BOARD_ID = bOARD_ID;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getPOST_FILE() {
		return POST_FILE;
	}
	public void setPOST_FILE(String pOST_FILE) {
		POST_FILE = pOST_FILE;
	}
	public String getPOST_DATE() {
		return POST_DATE;
	}
	public void setPOST_DATE(String pOST_DATE) {
		POST_DATE = pOST_DATE;
	}
	public int getLIKE_COUNT() {
		return LIKE_COUNT;
	}
	public void setLIKE_COUNT(int lIKE_COUNT) {
		LIKE_COUNT = lIKE_COUNT;
	}
	public int getSCRAP_COUNT() {
		return SCRAP_COUNT;
	}
	public void setSCRAP_COUNT(int sCRAP_COUNT) {
		SCRAP_COUNT = sCRAP_COUNT;
	}
	public int getREPORT_COUNT() {
		return REPORT_COUNT;
	}
	public void setREPORT_COUNT(int rEPORT_COUNT) {
		REPORT_COUNT = rEPORT_COUNT;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getBoardname() {
		return boardname;
	}
	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}
	
	/* ---------- POST테이블 toString ---------- */
	@Override
	public String toString() {
		return "Post [POST_ID=" + POST_ID + ", BOARD_ID=" + BOARD_ID + ", USER_ID=" + USER_ID + ", SUBJECT=" + SUBJECT
				+ ", CONTENT=" + CONTENT + ", POST_FILE=" + POST_FILE + ", POST_DATE=" + POST_DATE + ", LIKE_COUNT="
				+ LIKE_COUNT + ", SCRAP_COUNT=" + SCRAP_COUNT + ", REPORT_COUNT=" + REPORT_COUNT + ", STATUS=" + STATUS
				+ ", comment_count=" + comment_count + ", nickname=" + nickname + ", boardname=" + boardname + "]";
	}
	
	
}
