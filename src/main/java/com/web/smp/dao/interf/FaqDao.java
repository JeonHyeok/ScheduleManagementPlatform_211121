package com.web.smp.dao.interf;

import java.util.List;

import com.web.smp.di.entity.Faq;

public interface FaqDao {
	List<Faq> getFaq();
	
	int insertFaq(String question, String answer);
}
