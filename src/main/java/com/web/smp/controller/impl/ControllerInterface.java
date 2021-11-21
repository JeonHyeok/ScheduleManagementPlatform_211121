package com.web.smp.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.smp.controller.SmpService;

public interface ControllerInterface {
	String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService);
}
