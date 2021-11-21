package com.web.smp.di.entity;

public class Content {
	private int content_seq; // Content 테이블 시퀀스
	private int category; //category 번호
	private String main_content; // 메인 카테고리 
	private String sub_content; // 서브 카테고리 
	
	public Content() {
	}
	
	public Content(int content_seq, int category, String main_content, String sub_content) {
		this.content_seq = content_seq;
		this.category = category;
		this.main_content = main_content;
		this.sub_content = sub_content;
	}


	public int getContent_seq() {
		return content_seq;
	}

	public void setContent_seq(int content_seq) {
		this.content_seq = content_seq;
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

	@Override
	public String toString() {
		return "Content [content_seq=" + content_seq + ", category=" + category + ", main_content=" + main_content
				+ ", sub_content=" + sub_content + "]";
	}

	
	
}
