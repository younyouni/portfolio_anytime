package com.naver.anytime.domain;

public class Semester_detail {

	private int semester_id;
	
	private String subject;
	private int credit = 0;
	private String grade = "A+";
	private boolean is_major = false;
	
	
	
	public boolean isIs_major() {
		return is_major;
	}
	public void setIs_major(boolean is_major) {
		this.is_major = is_major;
	}
	public int getSemester_id() {
		return semester_id;
	}
	public void setSemester_id(int semester_id) {
		this.semester_id = semester_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
