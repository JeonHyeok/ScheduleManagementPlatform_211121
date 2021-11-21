package com.web.smp.controller;

import java.util.List;

import com.web.smp.dao.interf.ContentDao;
import com.web.smp.dao.interf.FaqDao;
import com.web.smp.dao.interf.ScheduleDao;
import com.web.smp.dao.interf.UserDao;
import com.web.smp.di.entity.AllViewEntity;
import com.web.smp.di.entity.Content;
import com.web.smp.di.entity.Faq;
import com.web.smp.di.entity.Schedule;
import com.web.smp.di.entity.User;

public class SmpServiceImpl implements SmpService {
	private UserDao userDao = null;
	private ContentDao contentDao = null;
	private ScheduleDao scheduleDao = null;
	private FaqDao faqDao = null;

	public SmpServiceImpl() {
	}

	public SmpServiceImpl(UserDao userDao, ContentDao contentDao, ScheduleDao scheduleDao, FaqDao faqDao) {
		this.userDao = userDao;
		this.contentDao = contentDao;
		this.scheduleDao = scheduleDao;
		this.faqDao = faqDao;
	}
	/* User Dao */
	@Override
	public boolean loginAvailability(String id, String pwd) {
		return userDao.loginAvailability(id, pwd);
	}

	@Override
	public User getUser(String id) {
		return userDao.getUser(id);
	}

	@Override
	public String getUserName(String id) {
		return userDao.getUserName(id);
	}
	@Override
	public String getUserType(String id) {
		return userDao.getUserType(id);
	}

	@Override
	public List<User> getUserList(String query) {
		return userDao.getUserList(query);
	}
	
	/* Content Dao */

	@Override
	public Content getContent(int content_seq) {
		return contentDao.getContent(content_seq);
	}

	@Override
	public List<Content> getContentList(String query) {
		return contentDao.getContentList(query);
	}

	@Override
	public List<String> getMainCategory(int category) {
		return contentDao.getMainCategory(category);
	}

	@Override
	public List<String> getSubCategory(String query) {
		return contentDao.getSubCategory(query);
	}

	/* Schdule Dao */
	@Override
	public Schedule getSchedule(int schedule_seq) {
		return scheduleDao.getSchedule(schedule_seq);
	}

	@Override
	public List<AllViewEntity> getScheduleList(String query, String categoryNo) {
		return scheduleDao.getScheduleList(query,categoryNo);
	}

	@Override
	public int insertSchedule(String userId, String main_content, String sub_content, String rsv_date, String startTime,
			String endTime) {
		return scheduleDao.insertSchedule(userId, main_content, sub_content, rsv_date, startTime, endTime);
	}
	
	/* Faq Dao*/
	@Override
	public List<Faq> getFaq() {
		return faqDao.getFaq();
	}

	@Override
	public int insertFaq(String question, String answer) {
		return faqDao.insertFaq(question, answer);
	}
}
