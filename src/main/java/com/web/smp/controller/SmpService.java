package com.web.smp.controller;

import java.util.List;

import com.web.smp.di.entity.AllViewEntity;
import com.web.smp.di.entity.Content;
import com.web.smp.di.entity.Faq;
import com.web.smp.di.entity.Schedule;
import com.web.smp.di.entity.User;

public interface SmpService {
	/* User Dao */
	boolean loginAvailability(String id,String pwd);

	User getUser(String id);

	String getUserName(String id);

	String getUserType(String id);
	
	List<User> getUserList(String query);

	/* Content Dao */
	Content getContent(int content_seq);

	List<Content> getContentList(String query);

	List<String> getMainCategory(int category);

	List<String> getSubCategory(String query);

	/* Schdule Dao */
	Schedule getSchedule(int schedule_seq);

	List<AllViewEntity> getScheduleList(String query,String category);
	
	int insertSchedule(String userId, String main_content, String sub_content, String rsv_date, String startTime, String endTime);
	
	/* Faq */
	List<Faq> getFaq();
	
	int insertFaq(String question, String answer);
}
