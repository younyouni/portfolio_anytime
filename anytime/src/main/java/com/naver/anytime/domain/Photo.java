package com.naver.anytime.domain;

public class Photo {
	private int PHOTO_ID; // 사진 첨부파일 고유번호
	private int POST_ID; // 게시물 고유번호
	private String PATH; // 경로
	
	public int getPHOTO_ID() {
		return PHOTO_ID;
	}
	public void setPHOTO_ID(int pHOTO_ID) {
		PHOTO_ID = pHOTO_ID;
	}
	public int getPOST_ID() {
		return POST_ID;
	}
	public void setPOST_ID(int pOST_ID) {
		POST_ID = pOST_ID;
	}
	public String getPATH() {
		return PATH;
	}
	public void setPATH(String pATH) {
		PATH = pATH;
	}
	
	
	
}
