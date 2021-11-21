<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/views/header.jsp" %>
<script src="/js/calendarJS.js?d" defer></script>
<script type='text/javascript'src='/js/timepicki.js' defer></script>

<div class="sectionContainer">
	<!------------- LEFT --------------->
	<div class="leftSection">
		<div class="calendarHeaderContainer">
			<h1 class="currentCalendarHeader"> </h1>
			<div id="prevBtn"  class="calendarHeaderBtn" > < </div>
			<div id="todayBtn"  class="calendarHeaderBtn" > today </div>
			<div id="nextBtn" class="calendarHeaderBtn"> > </div>
		</div>
		<div class="contentContainer">
			<h2>카테고리 선택</h2> 
			<form class="contentForm">
				<select name="checkedMain" id="checkedMainCategory" class="checkedContentSelect" style="cursor:pointer">
					<option value="none">= 매인 카테고리 =&nbsp&nbsp</option>
					<c:forEach var="i" begin="0" end="${fn:length(mainContentList)-1}">
						<option value="${mainContentList[i]}">${mainContentList[i]}</option>
					</c:forEach>
				</select> 
				<select name="checkedSub" id="checkedSubCategory" class="checkedContentSelect" style="cursor:pointer">
					<option value="none">= 매인을 먼저 선택해주세요 =&nbsp&nbsp</option>
				</select>
			</form>
			<input type="button" value="선택 카테고리 보기" id="checkedContentBtn" class="contentBtn" style="cursor:pointer" />
			<input type="button" value="모든 카테고리 보기" id="allContentBtn" class="contentBtn" style="cursor:pointer"/>
		</div>
		<div class="btnContainer">
			<input type="button" value="weekly" id="mwBtn" class="contentBtn" style="cursor:pointer"/>
			<input type="button" value="My Reservation" id="rsvBtn" class="contentBtn" style="cursor:pointer"/>
		</div>

	</div>
	<!--------------- RIGHT  --------------->
	<div class="rightSection">
	<!-- month form -->
		<div class="monthlyCalendar">
			<div class="dayHeaderContainer">
				<c:forEach var="item" items="${days}" varStatus="idx">
					<div class="dayHeader">${item}</div>
				</c:forEach>
			</div>
			<c:forEach var="i" begin="0" end="5">
				<div class='calendarWeekContainer week${i+1}'>
					<!-- week 1 ~ 6 -->
					<c:forEach var="j" begin="0" end="6">
						<div class='calendar_day'>
							<span></span><span id="monthData"></span>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
		
		<!-- week form -->
		<div class="weeklyCalendar">
			<div class="timeLineContainer">
				<div class="timeLineTitle">TIME</div>
				<!-- 9시~18시 -->
				<c:forEach var="i" begin="9" end="18">
					<div class="timeLineItem">${i}:00</div>
					<div class="timeLineItem">--</div>
				</c:forEach>
					<div class="timeLineItem">19:00</div>
			</div>
			<div class="timeTableContainer">				
				<div class="weekDayHeaderContainer">
					<c:forEach var="item" items="${days}" varStatus="idx">
						<div class="weekDayHeader">
							<span>${item}&nbsp&nbsp</span> 
							<span class="dayoftheweek${idx.index}">NoneData</span>
						</div>
					</c:forEach>
				</div>

				<div class="weekItemContainer">
					
				</div>				
			</div>
		</div>

		<!-- schedule form -->
		<div class="userScheduleContainer">
			<div id="scheduleList">
				<table id="myRsvTable">
					<thead>
						<tr>
							<th>예약 번호</th>
							<th>대분류</th>
							<th>소분류</th>
							<th>예약 날짜</th>
							<th>시작 시간</th>
							<th>종료 시간</th>
							<th>수정</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		
	</div><!-- rightSection END -->
</div><!-- sectionContainer END -->

<!-------------------------- RSV dialog ------------------------->
	<div id="rsv-dialog-form" class="dialog">
		<div id="dialogHeader">
			<h2 id ="rsv-dialog-date"></h2><br>
			<h2>스케쥴 현황</h2>
		</div>
		
		<div id=border1>
		<h2 id=rsvList>예약현황</h2>
		<h4 id="dialog-rsvList">list</h4><br>
		</div>
		
		<div id=border2>
		<h2 id=dorsv>예약하기</h2>
		<form>
		<div id=mainCategory>
			<label class = category>메인 카테고리 : </label>
			<select name="checkedMainDialog" id="checkedMainDialog"
				class="checkedContent">
				<option value="none" style="opacity : 0.4">메인 카테고리 선택</option>
				<c:forEach var="i" begin="0" end="${fn:length(mainContentList)-1}">
					<option value="${mainContentList[i]}">${mainContentList[i]}</option>
				</c:forEach>
			</select>
		</div>
		<br>
		<div id=subCategory>
			<label class = category>서브 카테고리 : </label>
			<select name="checkedSubDialog" id="checkedSubDialog" class="checkedContent">
				<option value="none">메인 카테고리를 먼저 선택해주세요</option>
			</select>
		</div>	
		<div id=timeSet>
			<label id = start>시작시간 : </label> <input id='startTime' type='text'name='timepicker' class="timepicker" value="클릭하여 시작시간 선택"/>
			<label id = end>종료시간 : </label> <input id='endTime' type='text'name='timepicker' class="timepicker" value="클릭하여 종료시간 선택"/>
		</div>
		</form>
		</div>
		<input type='button' id='dialogSubmit' value='확인' class="btn"><input type='button' id='dialogCancle' value='취소' class="btn">
	</div><!-- rsv-dialog-form END-->

<%@include file="/WEB-INF/views/footer.jsp"%>