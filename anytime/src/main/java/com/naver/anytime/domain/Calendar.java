package com.naver.anytime.domain;

public class Calendar {
	private int CALENDAR_ID;
	private int USER_ID;
	private String SUBJECT;
	private String TYPE;
	private String START_TIME;
	private String END_TIME;
	private String CONTENT;
	private int ALL_TIME;
	
	public int getCALENDAR_ID() {
		return CALENDAR_ID;
	}
	public void setCALENDAR_ID(int cALENDAR_ID) {
		CALENDAR_ID = cALENDAR_ID;
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
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public int getALL_TIME() {
		return ALL_TIME;
	}
	public void setALL_TIME(int aLL_TIME) {
		ALL_TIME = aLL_TIME;
	}
	
	
	
}
