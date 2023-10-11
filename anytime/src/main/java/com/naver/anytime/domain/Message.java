package com.naver.anytime.domain;

public class Message {
    private int MESSAGE_ID;
    private int SENDER;
    private int RECEIVER;
    private String CONTENT;
    private String MESSAGE_DATE;
    private String READ_DATE;
    private int DIRECTION;
       
	public String getREAD_DATE() {
		return READ_DATE;
	}
	public void setREAD_DATE(String rEAD_DATE) {
		READ_DATE = rEAD_DATE;
	}
	public int getDIRECTION() {
		return DIRECTION;
	}
	public void setDIRECTION(int dIRECTION) {
		DIRECTION = dIRECTION;
	}
	public int getMESSAGE_ID() {
		return MESSAGE_ID;
	}
	public void setMESSAGE_ID(int mESSAGE_ID) {
		MESSAGE_ID = mESSAGE_ID;
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
    
    
}
