package com.naver.anytime.domain;

public class Message {
    private int MESSAGE_ID;
    private int MESSAGEALL_ID;
    private int SENDER;
    private int RECEIVER;
    private String CONTENT;
    private String MESSAGE_DATE;

    
    private int DIRECTION;

	public int getMESSAGE_ID() {
		return MESSAGE_ID;
	}


	public void setMESSAGE_ID(int mESSAGE_ID) {
		MESSAGE_ID = mESSAGE_ID;
	}


	public int getMESSAGEALL_ID() {
		return MESSAGEALL_ID;
	}


	public void setMESSAGEALL_ID(int mESSAGEALL_ID) {
		MESSAGEALL_ID = mESSAGEALL_ID;
	}


	public int getSENDER() {
		return SENDER;
	}


	public void setSENDER(int sENDER) {
		SENDER = sENDER;
	}


	public int getRECEIVER() {
		return RECEIVER;
	}


	public void setRECEIVER(int rECEIVER) {
		RECEIVER = rECEIVER;
	}


	public String getCONTENT() {
		return CONTENT;
	}


	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}


	public String getMESSAGE_DATE() {
		return MESSAGE_DATE;
	}


	public void setMESSAGE_DATE(String mESSAGE_DATE) {
		MESSAGE_DATE = mESSAGE_DATE;
	}


	public int getDIRECTION() {
		return DIRECTION;
	}


	public void setDIRECTION(int dIRECTION) {
		DIRECTION = dIRECTION;
	}





	
}
