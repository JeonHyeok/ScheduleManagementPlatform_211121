package com.web.smp.di.entity;

import java.sql.Time;

public class AllViewEntity {
	private int schedule_seq; // Schedule 테이블 시퀀스
	private int category; //category 번호
	private String main_content; // 메인 카테고리 
	private String sub_content; // 서브 카테고리 
	private String user_type; // 유저 타입
	private String user_id; // 유저 아이디
	private String user_name; // 유저 이름
	private String rsv_date; // 예약 날짜
	private String start_time; // 시작 시간
	private String end_time; // 종료 시간
	
	public AllViewEntity() {}
	public AllViewEntity(int schedule_seq, int category, String main_content, String sub_content, String user_type,
			String user_id, String user_name, String rsv_date, String start_time, String end_time) {
		super();
		this.schedule_seq = schedule_seq;
		this.category = category;
		this.main_content = main_content;
		this.sub_content = sub_content;
		this.user_type = user_type;
		this.user_id = user_id;
		this.user_name = user_name;
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


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public String getMain_content() {
		return main_content;
	}


	public void setMain_content(String main_content) {
		this.main_content = main_content;
	}


	public String getSub_content() {
		return sub_content;
	}


	public void setSub_content(String sub_content) {
		this.sub_content = sub_content;
	}


	public String getUser_type() {
		return user_type;
	}


	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getRsv_date() {
		return rsv_date;
	}


	public void setRsv_date(String rsv_date) {
		this.rsv_date = rsv_date;
	}


	public String getStart_time() {
		return start_time;
	}


	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}


	public String getEnd_time() {
		return end_time;
	}


	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	@Override
	public String toString() {
		return "AllViewEntity [schedule_seq=" + schedule_seq + ", category=" + category + ", main_content="
				+ main_content + ", sub_content=" + sub_content + ", user_type=" + user_type + ", user_id=" + user_id
				+ ", user_name=" + user_name + ", rsv_date=" + rsv_date + ", start_time=" + start_time + ", end_time="
				+ end_time + "]";
	}
}
