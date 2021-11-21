package com.web.smp.controller.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.smp.controller.SmpService;
import com.web.smp.di.entity.User;

public class ApiUserController implements ControllerInterface {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		String returnMassage=null;
		ObjectMapper mapper = new ObjectMapper();
		
		String method = request.getMethod().toUpperCase();//요청메소드를 모두 대문자로반환 post -> POST
		String path = request.getRequestURI();
		String[] temp=path.split("/");
		String query=request.getQueryString();
		System.out.println("apiUserController path >>"+path+"?"+query);
		
		Base64 base64 = new Base64();
		
		// GET
		// /api/users - user전체목록반환
		// /api/users/201796 - user 중 id = 201796 만 반환
		// /api/users/login?id=22&pwd=11 - 로그인 유효성검사확인하기
		
		// POST
		// /api/users - user 하나 새로 생성

		// UPDATE
		// /api/users/201796 - user 중 id = 201796인 유저 정보 수정

		// DELETE
		// /api/users/201796 - user 중 id = 201796인 유저 삭제
		
		if (temp.length == 3) {// /api/users
			if (method.equals("GET")) {// users 전체목록 가져오기
				//필요하지 않음
				System.out.println("모든 user정보 조회 - ApiUserController - GET");
			} else if (method.equals("POST")) {// 새로운 user정보 생성하기
				//구현예정 없음
				System.out.println("새로운 user정보 생성 - ApiUserController - POST");
			}
		} else if (temp.length > 3) {// /api/users/temp[4] ? query
			if (temp[3].equals("login")) {
				// 로그인 유효성 검사해주기
				String id=new String(base64.decode(request.getParameter("id").getBytes()));//디코딩된값
				String pwd=new String(base64.decode( request.getParameter("pwd").getBytes()));
				System.out.println(id+"와"+pwd+"를 유효성검사중");
				boolean loginCheck = smpService.loginAvailability(id,pwd);//로그인 유효성 검사
				if (loginCheck) {/* login 성공일때 쿠키생성 */
					String userName=smpService.getUserName(id);
					String userType=smpService.getUserType(id);
					
					Cookie cookieId=new Cookie("userId",id);
					cookieId.setPath("/");//쿠키를 모든 위치에서 사용가능
					cookieId.setMaxAge(3600);//60s*6*10 = 60M = 1H
					
					Cookie cookieName=new Cookie("userName",userName);
					cookieName.setPath("/");//쿠키를 모든 위치에서 사용가능
					cookieName.setMaxAge(3600);//60s
					
					Cookie cookieType=new Cookie("userType",userType);
					cookieType.setPath("/");//쿠키를 모든 위치에서 사용가능
					cookieType.setMaxAge(3600);//60s
					
					response.addCookie(cookieId);
					response.addCookie(cookieName);
					response.addCookie(cookieType);
					
					HttpSession session=request.getSession(true);//가져올 세션이 없다면 새로 생성
					session.setAttribute("userId", id);
					session.setAttribute("userName", userName);
					session.setAttribute("userType", userType);
				}
				
				returnMassage=loginCheck?"true":"false";
			} else {// temp[3] = 특정 USER ID
				if (method.equals("GET")) {// 특정 user 정보가져오기
					System.out.println("contents user정보 조회 - ApiUserController - GET");
					
				} else if (method.equals("UPDATE")) {// 특정 user정보 수정하기
					
				} else if (method.equals("DELETE")) {// 특정 user정보 삭제하기
					
				}
			}
		}
		return returnMassage;
	}

}
