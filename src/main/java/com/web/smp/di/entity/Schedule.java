package com.web.smp.di.entity;

import java.sql.Time;
import java.util.Date;

public class Schedule {
	private int schedule_seq; // Schedule 테이블 시퀀스
	private int content_no; // Content 테이블 시퀀스 번호 불러오기
	private String user_id; // 유저 아이디
	private String rsv_date; // 예약 날짜
	private Time start_time; // 시작 시간
	private Time end_time; // 종료 시간
	
	public Schedule() {
		super();
	}

	public Schedule(int schedule_seq, int content_no, String user_id, String rsv_date, Time start_time, Time end_time) {
		super();
		this.schedule_seq = schedule_seq;
		this.content_no = content_no;
		this.user_id = user_id;
		this.rsv_date = rsv_date;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public int getSchedule_seq() {
		return schedule_seq;
	}

	public void setSchedule_seq(int schedule_seq) {
		this.schedule_seq = schedule_seq;
	}

	public int getContent_no() {
		return content_no;
	}

	public void setContent_no(int content_no) {
		this.content_no = content_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRsv_date() {
		return rsv_date;
	}

	public void setRsv_date(String rsv_date) {
		this.rsv_date = rsv_date;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	@Override
	public String toString() {
		return "Schedule [schedule_seq=" + schedule_seq + ", content_no=" + content_no + ", user_id=" + user_id
				+ ", rsv_date=" + rsv_date + ", start_time=" + start_time + ", end_time=" + end_time + "]";
	}
	
	
}
