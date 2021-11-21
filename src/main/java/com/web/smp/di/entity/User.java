package com.web.smp.di.entity;

import java.util.Date;

public class User {
	private int user_seq; // User 테이블 시퀀스
	private String user_type; // 유저 타입
	private String user_id; // 유저 아이디
	private String user_name; // 유저 이름
	private String pwd; // 유저 비밀번호
	
	public User() {
		super();
	}

	public User(int user_seq, String user_type, String user_id, String user_name, String pwd) {
		super();
		this.user_seq = user_seq;
		this.user_type = user_type;
		this.user_id = user_id;
		this.user_name = user_name;
		this.pwd = pwd;
	}

	public int getUser_seq() {
		return user_seq;
	}

	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User [user_seq=" + user_seq + ", user_type=" + user_type + ", user_id=" + user_id + ", user_name="
				+ user_name + ", pwd=" + pwd + "]";
	}


}
