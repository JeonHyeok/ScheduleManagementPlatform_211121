package com.web.smp.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.web.smp.controller.SmpService;

public class MainController implements ControllerInterface {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		HttpSession session = request.getSession(true);
		
		String path = request.getRequestURI(); // /main/studio or /main/rental or /main/consulting
		String[] temp=path.split("/");
		String query=request.getQueryString();
		System.out.println("MainController path >>"+path+"?"+query);
		int category=0;
		
		switch (temp[2]) {
		 case "studio" :
			 category=1;	break;
		 case "rental" :
			 category=2; 	 break;
		 case "consulting" :
			 category=3; 	break;
		}
		
		// /main?category=1?id=2202
		session.setAttribute("categorySession", category);//매번 Main들어올때마다 리셋
		
		//대분류 정보 request에 담기
		List<String> mainContentList = smpService.getMainCategory(category);
		request.setAttribute("mainContentList", mainContentList);

		return "calendar.jsp";
	}

}
