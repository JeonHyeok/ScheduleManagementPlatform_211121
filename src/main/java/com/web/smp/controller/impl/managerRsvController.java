package com.web.smp.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.smp.controller.SmpService;

public class managerRsvController implements ControllerInterface {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		// TODO Auto-generated method stub
		return "managerRsv.jsp";
	}

}