package com.naver.anytime.domain;

public class Board {
    private int BOARD_ID;
    private int SCHOOL_ID;
    private int USER_ID;
    private int TYPE;
    private String NAME;
    private String CONTENT;
    private int ANONYMOUS;
    private int STATUS;
		
    public int getBOARD_ID() {
		return BOARD_ID;
	}
	public void setBOARD_ID(int bOARD_ID) {
		BOARD_ID = bOARD_ID;
	}
	public int getSCHOOL_ID() {
		return SCHOOL_ID;
	}
	public void setSCHOOL_ID(int sCHOOL_ID) {
		SCHOOL_ID = sCHOOL_ID;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public int getTYPE() {
		return TYPE;
	}
	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public int getANONYMOUS() {
		return ANONYMOUS;
	}
	public void setANONYMOUS(int aNONYMOUS) {
		ANONYMOUS = aNONYMOUS;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	
	
}