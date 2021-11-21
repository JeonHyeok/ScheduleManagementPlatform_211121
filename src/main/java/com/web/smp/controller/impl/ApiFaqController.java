package com.web.smp.controller.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.smp.controller.SmpService;
import com.web.smp.di.entity.AllViewEntity;
import com.web.smp.di.entity.Faq;

public class ApiFaqController implements ControllerInterface {
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		String returnMassage = null;
		ObjectMapper mapper = new ObjectMapper();
		
		String method = request.getMethod().toUpperCase();//요청메소드를 모두 대문자로반환 post -> POST
		String path = request.getRequestURI();
		String[] temp = path.split("/");
		String query = request.getQueryString();
		System.out.println("ApiFaqController path >>"+path+"?"+query);
		
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		
		System.out.println(question + " " + answer);
		
		if (temp.length == 3) {// /api/faq
			if (method.equals("GET")) {// faq 전체목록 가져오기
				System.out.println("모든 faq정보 조회 - ApiFaqController - GET");
				List<Faq> getFaq = smpService.getFaq();
				try {
					returnMassage = mapper.writeValueAsString(getFaq);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			} else if (method.equals("POST")) {		
			}
		} else if (temp.length > 3) {// /api/faq/temp[4]
			if (query == null) {
				
			} else if (query != null) {
				if(question != null && answer != null) {
					if (method.equals("GET")) {
						System.out.println("새로운 FAQ 삽입!");
						
						int result = smpService.insertFaq(question, answer);
						
						returnMassage = result == 1 ? "true" : "false";
					}
				}
			}
		}
		System.out.println("returnMassage >>"+returnMassage);
		return returnMassage;
	}
}
