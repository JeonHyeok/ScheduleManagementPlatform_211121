package com.web.smp.controller.impl;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.smp.controller.SmpService;
import com.web.smp.di.entity.AllViewEntity;

public class ApiScheduleController implements ControllerInterface {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, SmpService smpService) {
		String returnMassage=null;
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session=request.getSession(false);//가져올 세션이 없다면 false
		
		String method = request.getMethod().toUpperCase();//요청메소드를 모두 대문자로반환 post -> POST
		String path = request.getRequestURI();
		String[] temp=path.split("/");
		String query=request.getQueryString();
		System.out.println("ApiScheduleController path >>"+path+" ? "+query);
		
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		int week=99;
		if(request.getParameter("week")!=null) {
			week=Integer.parseInt(request.getParameter("week"));
		}
		String id=request.getParameter("id");
		String mainContent=request.getParameter("mainContent");
		
		String searchSelect = request.getParameter("searchSelect");
		String textValue = request.getParameter("textValue");
		if (textValue != null) {
			try {// 카테고리이름 한글 디코딩처리 - URLDecoder.decode(NAME, "UTF-8")
				textValue = URLDecoder.decode(textValue, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		if(mainContent!=null) {
			try {//카테고리이름 한글 디코딩처리 - URLDecoder.decode(NAME, "UTF-8")
				mainContent = URLDecoder.decode(request.getParameter("mainContent"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		String subContent=request.getParameter("subContent");//subcategory
		String categoryNo = null;

		if(temp.length>3) {
			categoryNo=temp[3];
		}//maincategory - index로가져오기
		
		String sql=null;
		// GET
		// /api/schedules - schedules 전체목록반환
		// /api/schedules/[카테고리번호] - 특정 category 에서만 사용되는 schedules 정보가져오기
		// /api/schedules/[카테고리번호] ? id=2018  &  year=x & month=x
		// /api/schedules/[카테고리번호] ? year=x & month=x & week=0  
		// /api/schedules/[카테고리번호] ? serchSelect = user_name & textValue=홍길동
		
		// POST
		// /api/schedules - 새로운 schedules 정보 생성하기

		// UPDATE
		// /api/schedules/[카테고리번호] ? - 특정 schedules 정보 수정하기

		// DELETE
		// /api/schedules/[카테고리번호] ?  - 특정 schedules 정보 삭제하기
		
		if (temp.length == 3) {// /api/schedules
			if (method.equals("GET")) {// schedules 전체목록 가져오기
				System.out.println("모든 schedules정보 조회 - ApiScheduleController - GET");
			} else if (method.equals("POST")) {// 새로운 schedules 정보 생성하기
				// POST : /api/schedules
				// OR
				// POST : /api/schedules/[카테고리번호]
				System.out.println("새로운 schedules정보 생성 - ApiScheduleController - POST");
				AllViewEntity allViewEntity = null;

				String userId = null;
			    String json = null;

			    try {
			        BufferedReader reader = request.getReader();
			        json = reader.readLine();
			    }catch(Exception e) {
			        System.out.println("Error reading JSON string: " + e.toString());
			    }
			    System.out.println(json);
			    //{"main_content":"국제관","sub_content":"202","rsv_date":"2021년 11월 2일","start_time":"09:00","end_time":"10:00"}
			    try {
					allViewEntity = mapper.readValue(json, AllViewEntity.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			    Cookie[] cookies = request.getCookies() ;
			    for(int i=0; i<cookies.length;i++) {
			    	System.out.println(cookies[i].getName());
			    	if(cookies[i].getName().equals("userId")) {
			    		System.out.println(cookies[i].getValue()); 
			    		userId =cookies[i].getValue();
			    	}else {
			    		userId=(String) session.getAttribute("userId");
			    	}
			    }

			    String rsv_date = allViewEntity.getRsv_date();
			    rsv_date = rsv_date.replaceAll("년 ", "-");
			    rsv_date = rsv_date.replaceAll("월 ", "-");
			    rsv_date = rsv_date.replaceAll("일", "");
			    System.out.println(rsv_date);


				
				  int result = smpService.insertSchedule(userId,
				  allViewEntity.getMain_content(), allViewEntity.getSub_content(), rsv_date,
				  allViewEntity.getStart_time(), allViewEntity.getEnd_time());
				 

				
				  if(result == 1) { returnMassage = "Insert success"; }
				 
			}
		} else if (temp.length > 3) {// /api/schedules/temp[4] ? query
			if (query == null) {// /api/schedules/[카테고리번호]
				if (method.equals("GET")) {// schedules 카테고리번호것만 가져오기
					System.out.println("카테고리 번호 : " + categoryNo);
					List<AllViewEntity> ScheduleList = smpService.getScheduleList("schedule_seq > 0", categoryNo);
					try {
						returnMassage = mapper.writeValueAsString(ScheduleList);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				} 	
			} else if (query != null) {//week=0 -> montly, week=1~6 -> weekly
				// /api/schedules? year=2021 & month=10 & week=0 & subContent=사범관 & subContent=202
				// /api/schedules? year=2021 & month=10 & week=0 & subContent= all
				// /api/schedules? year=2021 & month=10 & id= 201795032
				// /api/schedules? id= 201795032
				if(id!=null&&year!=null) { // id정보로 해당 DATE정보로 schedules 내용 조회
					if (method.equals("GET")) {//특정 id의 schedules 정보가져오기
						System.out.println("특정 id의  schedules정보 조회 - ApiScheduleController - GET");
						sql = "year(rsv_date) = " + year + " AND month(rsv_date) = " + month+" AND user_id ="+id; 
						List<AllViewEntity> scheduleList = smpService.getScheduleList(sql, categoryNo);// 해당카테고리중 특정 id만
						try {
							returnMassage = mapper.writeValueAsString(scheduleList);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
					}
				}else if(id!=null&&year==null) {// id정보로 ALL schedules 내용 조회
					if (method.equals("GET")) {//특정 id의 schedules 정보가져오기
						System.out.println("특정 id의  schedules정보 조회 - ApiScheduleController - GET");
						sql = "user_id ="+id; 
						List<AllViewEntity> scheduleList = smpService.getScheduleList(sql, categoryNo);// 해당카테고리중 특정 id만
						try {
							returnMassage = mapper.writeValueAsString(scheduleList);
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
					} else if (method.equals("UPDATE")) {// 특정 id의 schedule 정보 수정하기

					} else if (method.equals("DELETE")) {// 특정 id의 schedule 정보 삭제하기

					}
				}else if (searchSelect != null) {
					System.out.println(searchSelect);
					System.out.println(textValue);
					textValue = "'" + textValue + "'";
					sql = searchSelect + "=" + textValue;
					System.out.println(sql);
					List<AllViewEntity> scheduleList = smpService.getScheduleList(sql, categoryNo);// 해당카테고리중 특정 id만
					try {
						returnMassage = mapper.writeValueAsString(scheduleList);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				} else { // 날짜 정보로 해당 schedules 내용 조회
					if (method.equals("GET")) {
						if (week==0) { // monthly DATA 구하기
							// /api/schedules/1 ? year=2021&month=11&week=0&subContent=all
							if(subContent.equals("all")) {
								sql = "year(rsv_date) = " + year + " AND month(rsv_date) = " + month;
							}else {
								sql = "year(rsv_date) = " + year + " AND month(rsv_date) = " + month+
										" AND main_content = '"+mainContent+"' AND sub_content = "+subContent;
							}
							List<AllViewEntity> scheduleList = smpService.getScheduleList(sql, categoryNo);// 해당카테고리에 전체
							try {
								returnMassage = mapper.writeValueAsString(scheduleList);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
						} else {// weekly DATA 구하기
							// /api/schedules/1 ? year=2021&month=11&week=1&subContent=all
							/* 주차구하기 
							 * 2021-1-1-금요일 : 1주차 기준
							 * 2021-1-4-월요일 : 2주차 기준
							 */
						    LocalDate inputMonth = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),1); 
						    WeekFields weekFields = WeekFields.of(Locale.getDefault());
						    int tempWeek=inputMonth.get(weekFields.weekOfWeekBasedYear());
						    System.out.println(tempWeek);
						    //2021-11-1-월요일의 주차 -> 45주차
							/*  
							 * 해당 주차의 월요일 날짜 구하기 
							 * 2021-1-1-금요일 : X
							 * 2021-1-4-월요일 : 1주차 기준
							 */
							LocalDate desiredDate = inputMonth
							            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, tempWeek+week-2)//주기준 연도내의 주
							            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));//DayOfWeek.MONDAY = 해당 주차에 월요일
							//결과 : 2021-11-1-월요일
							System.out.println(desiredDate);
							sql = "rsv_date BETWEEN date('" + desiredDate + "')AND date('" + desiredDate.plusDays(6)
									+ "')" + " AND main_content = '" + mainContent+"' AND sub_content = " + subContent;
							System.out.println(sql);
							List<AllViewEntity> rsvAllList = smpService.getScheduleList(sql, categoryNo);
							try {
								returnMassage = mapper.writeValueAsString(rsvAllList);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
		}
		System.out.println("returnMassage >>"+returnMassage);
		return returnMassage;
	}
	
	  
	 

}
