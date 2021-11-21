package com.web.smp.dao.interf;

import java.util.List;

import com.web.smp.di.entity.User;

public interface UserDao {
	boolean loginAvailability(String id, String pwd);
	
	User getUser(String id);
	
	String getUserName(String id);
	String getUserType(String id);
	
	List<User> getUserList(String query);
}
