package com.naver.anytime.domain;

public class TimeTable_detail {
	private int SUBJECT_ID;
	private int TIMETABLE_ID;
	private String DAY;
	private int START_TIME;
	private int END_TIME;
	private String CLASSROOM;
	private String SUBJECT;
	private String PROFESSOR;
	
	public int getSUBJECT_ID() {
		return SUBJECT_ID;
	}
	public void setSUBJECT_ID(int sUBJECT_ID) {
		SUBJECT_ID = sUBJECT_ID;
	}
	public int getTIMETABLE_ID() {
		return TIMETABLE_ID;
	}
	public void setTIMETABLE_ID(int tIMETABLE_ID) {
		TIMETABLE_ID = tIMETABLE_ID;
	}
	public String getDAY() {
		return DAY;
	}
	public void setDAY(String dAY) {
		DAY = dAY;
	}
	public int getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(int sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public int getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(int eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getCLASSROOM() {
		return CLASSROOM;
	}
	public void setCLASSROOM(String cLASSROOM) {
		CLASSROOM = cLASSROOM;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getPROFESSOR() {
		return PROFESSOR;
	}
	public void setPROFESSOR(String pROFESSOR) {
		PROFESSOR = pROFESSOR;
	}
	
	
}
