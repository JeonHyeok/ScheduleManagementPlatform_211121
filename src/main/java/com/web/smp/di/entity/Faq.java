package com.web.smp.di.entity;

import java.sql.Time;

public class Faq {
	private int fqa_seq; // faq 테이블 시퀀스
	private String question; // 질문
	private String answer; // 답변
	
	public Faq() {}
	
	public Faq(int fqa_seq, String question, String answer) {
		super();
		this.fqa_seq = fqa_seq;
		this.question = question;
		this.answer = answer;
	}

	public int getFqa_seq() {
		return fqa_seq;
	}
	
	public void setFqa_seq(int fqa_seq) {
		this.fqa_seq = fqa_seq;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
