package com.naver.anytime.domain;

public class Report {
	private int REPORT_ID;
	private String REPORTER;
	private int REPORTED_ORIGIN;
	private String REASON;
	private String NOTE_DATE;
	private int ADMIN_ID;
	private String POST_R_ACTION;
	private String USER_R_ACTION;

	public int getREPORT_ID() {
		return REPORT_ID;
	}

	public void setREPORT_ID(int rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}

	public String getREPORTER() {
		return REPORTER;
	}

	public void setREPORTER(String rEPORTER) {
		REPORTER = rEPORTER;
	}

	public int getREPORTED_ORIGIN() {
		return REPORTED_ORIGIN;
	}

	public void setREPORTED_ORIGIN(int rEPORTED_ORIGIN) {
		REPORTED_ORIGIN = rEPORTED_ORIGIN;
	}

	public String getREASON() {
		return REASON;
	}

	public void setREASON(String rEASON) {
		REASON = rEASON;
	}

	public String getNOTE_DATE() {
		return NOTE_DATE;
	}

	public void setNOTE_DATE(String nOTE_DATE) {
		NOTE_DATE = nOTE_DATE;
	}

	public int getADMIN_ID() {
		return ADMIN_ID;
	}

	public void setADMIN_ID(int aDMIN_ID) {
		ADMIN_ID = aDMIN_ID;
	}

	public String getPOST_R_ACTION() {
		return POST_R_ACTION;
	}

	public void setPOST_R_ACTION(String pOST_R_ACTION) {
		POST_R_ACTION = pOST_R_ACTION;
	}

	public String getUSER_R_ACTION() {
		return USER_R_ACTION;
	}

	public void setUSER_R_ACTION(String uSER_R_ACTION) {
		USER_R_ACTION = uSER_R_ACTION;
	}
}
