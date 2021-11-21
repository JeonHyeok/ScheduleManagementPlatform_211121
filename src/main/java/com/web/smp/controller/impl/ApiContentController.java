package com.web.smp.controller.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.smp.controller.SmpService;

public class ApiContentController implements ControllerInterface {
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		String returnMassage=null;
		ObjectMapper mapper = new ObjectMapper();
		
		String method = request.getMethod().toUpperCase();//요청메소드를 모두 대문자로반환 post -> POST
		String path = request.getRequestURI();
		String[] temp=path.split("/");
		String query=request.getQueryString();
		System.out.println("ApiContentController path >>"+path+"?"+query);
		
		int categoryNo= Integer.parseInt(temp[3]);
		
		String mainContent=request.getParameter("mainContent");
		if(mainContent!=null) {
			try {//카테고리이름 한글 디코딩처리 - URLDecoder.decode(NAME, "UTF-8")
				mainContent = URLDecoder.decode(request.getParameter("mainContent"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		
		// GET
		// /api/contents - content 전체목록반환
		// /api/contents/[카테고리번호] - 특정 category 에서만 사용되는 contents 정보가져오기
		// /api/contents/[카테고리번호] ? mainCategory= '웹캠' - 특정 category 에서 매인카테고리에 해당되는 서브카테고리 contents 정보가져오기
		
		// POST
		// /api/contents - 새로운 contents 정보 생성하기
		
		// UPDATE
		// /api/contents/[카테고리번호] ? contentNO=17 - 특정 contents 정보 수정하기
		
		// DELETE
		// /api/contents/[카테고리번호] ? contentNO=17 - 특정 contents 정보  삭제하기
		
		if (temp.length == 3) {// /api/contents
			if (method.equals("GET")) {// contents 전체목록 가져오기
				//필요하지 않음
				System.out.println("모든 contents정보 조회 - ApiContentController - GET");
			} else if (method.equals("POST")) {// 새로운 contents 정보 생성하기
				//구현예정 없음
				System.out.println("새로운 contents정보 생성 - ApiContentController - POST");
			}
		} else if (temp.length > 3) {// /api/contents/temp[4] ? query
			// temp[4] = 특정 category정보
			if (method.equals("GET")) {
				if (query == null) {// /api/contents/[카테고리번호] - 특정 category 에서만 사용되는 contents 정보가져오기

				} else if (query != null) {// /api/contents/[카테고리번호] ? mainContent= '웹캠' - 매인카테고리에 해당되는 서브카테고리 contents 정보가져오기
					String sql = "category="+categoryNo+" AND main_content ='"+mainContent+"'";
					System.out.println(sql);
					List<String> scheduleList = smpService.getSubCategory(sql);
					try {
						returnMassage = mapper.writeValueAsString(scheduleList);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}

			} else if (method.equals("UPDATE")) {// 특정 contents 정보 수정하기

			} else if (method.equals("DELETE")) {// 특정 contents 정보 삭제하기

			}
		}
		System.out.println("returnMassage >>"+returnMassage);
		return returnMassage;
	}
}
