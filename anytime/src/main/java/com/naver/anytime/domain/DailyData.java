package com.naver.anytime.domain;

public class DailyData {
	private String DAY;
	private int SCHOOLS;
	private int MEMBERS;
	private int BOARDS;
	private int POSTS;
	private int NEW_MEMBERS;
	private int WITHDRAWN_MEMBERS;

	public String getDAY() {
		return DAY;
	}

	public void setDAY(String dAY) {
		DAY = dAY;
	}

	public int getSCHOOLS() {
		return SCHOOLS;
	}

	public void setSCHOOLS(int sCHOOLS) {
		SCHOOLS = sCHOOLS;
	}

	public int getMEMBERS() {
		return MEMBERS;
	}

	public void setMEMBERS(int mEMBERS) {
		MEMBERS = mEMBERS;
	}

	public int getBOARDS() {
		return BOARDS;
	}

	public void setBOARDS(int bOARDS) {
		BOARDS = bOARDS;
	}

	public int getPOSTS() {
		return POSTS;
	}

	public void setPOSTS(int pOSTS) {
		POSTS = pOSTS;
	}

	public int getNEW_MEMBERS() {
		return NEW_MEMBERS;
	}

	public void setNEW_MEMBERS(int nEW_MEMBERS) {
		NEW_MEMBERS = nEW_MEMBERS;
	}

	public int getWITHDRAWN_MEMBERS() {
		return WITHDRAWN_MEMBERS;
	}

	public void setWITHDRAWN_MEMBERS(int wITHDRAWN_MEMBERS) {
		WITHDRAWN_MEMBERS = wITHDRAWN_MEMBERS;
	}

}