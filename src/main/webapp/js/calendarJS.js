$(document).ready(function() {//DOM Tree 생성 완료 후 호출 (즉, 먼저 실행, 순차적실행)
});

$(window).load(function() {//모든 페이지 구성요소 페인팅 완료 후 호출 ONLY ONE
	function showMonth() {
		$(".monthlyCalendar").show();
		$(".weeklyCalendar").hide();
		$(".userScheduleContainer").hide();
	}
	function showWeek() {
		$(".monthlyCalendar").hide();
		$(".weeklyCalendar").show();
		$(".userScheduleContainer").hide();
		var str="";
		for( var i=0; i<140;i++){
			str+='<div class="weekItem"></div>';
		}
		$(".weekItemContainer").empty();
		$(".weekItemContainer").append(str);
	}
	function showSchedule() {
		$(".monthlyCalendar").hide();
		$(".weeklyCalendar").hide();
		$(".userScheduleContainer").show();
	}
	/*************************RIGHT*************************/
	$(".calendar_day").on("click",function(){
		alert("click");
		rsvDialog.dialog("open");
		$("#rsv-dialog-date").text(`${currentDate.getFullYear()}년 ${String(currentDate.getMonth()+1).padStart(2,'0')}월 ${$(this).children().first().text()}일`);
		$("#dialog-rsvList").html($(this).children().last().html());
	});
	
	var path = window.location.href;
	var path_ = path.split('/').reverse()[0];
	var categoryName=['studio','rental','consulting'];
	var categoryNo=categoryName.indexOf(path_)+1; // 사용시 ${categoryNo}이렇게 사용할 것
	
	var rsvDialog, rsvForm;
	
	const today = new Date(); // 현재 오늘 날짜 (변함 x , 고정값)
	
	let currentDate=today;//페이지에서의 날짜 - CD (2021-11-6)
	
	let firstDate_CD;//CD 달의 첫 날 (2021-11-1)
	let lastDate_CD;//CD 달의 마지막 날 (2021-11-30)
	let prev_lastDate_CD;//CD 달의 지난 달의 마지막 날 (2021-10-31)
	let weekDate;//weekly 날짜 저장
	/* 알아보는 날짜 : 2021년 11월 1일 월요일
				Mon Nov 01 2021 01:24:19 GMT+0900 (한국 표준시)
				getFullYear : 2021
	(0~11)		getMonth : 10
				getDate : 1
	(0일~6토)		getDay : 1
	*/
	showMonth();
	buildMonth();
	//////////////////////////////////////////////달력 - Monthly	
	function buildMonth() {
		firstDate_CD =new Date(currentDate);
        firstDate_CD.setDate(1);
		//헤더 변경
		$(".currentCalendarHeader").html(`${currentDate.getFullYear()}년&nbsp;&nbsp;&nbsp;&nbsp;${currentDate.getMonth() + 1}월&nbsp;(월)`);
		
		var daySet = makeElementMonth(firstDate_CD);
		monthlySetting(daySet);

		fetchData(`/api/schedules/${categoryNo}
				?year=${currentDate.getFullYear()}&month=${String(currentDate.getMonth()+1).padStart(2,'0')}
				&week=0&subContent=all`,'monthly');
	}
	function buildMonthMy() {
		if(getCookie('userId')==null){
			alert("쿠키에 로그인정보없음");			
		}else{
			fetchData(`/api/schedules/${categoryNo}
					?id=${getCookie('userId')}`,'myRsv');
		}
	}
	
	function makeElementMonth(firstDate_CD) { // 11/6
		let startDayCount = 1;
		let lastDayCount = 1;
		let firstDayName = null;//첫주 시작 월요일로 잡음
		if (firstDate_CD.getDay() == 0) firstDayName = 6; //첫날이 일요일이면 firstDayName = 6
		else firstDayName = firstDate_CD.getDay() - 1; //첫날이 월요일이면 0 ~ 토요일이면 5
		
		lastDate_CD =new Date(currentDate);
        lastDate_CD.setMonth(lastDate_CD.getMonth()+1);
        lastDate_CD.setDate(0); // 11/30
        
		prev_lastDate_CD =new Date(firstDate_CD);
		prev_lastDate_CD.setDate(0); // 10/31

		var daySet = [];
		var cnt = 0;
		for (let i = 0; i < 6; i++) {//1~6주차를 위해 6번 반복        
			for (let j = 0; j < 7; j++) {//일요일~토요일을 위해 7번 반복
				// <<1주차>> j < firstDayName: 이번 달 시작 요일 이전 일 때
				if (i == 0 && j < firstDayName) {
					daySet[cnt++] = `${(prev_lastDate_CD.getDate() - (firstDayName - 1) + j)}`;
				}
				// <<1주차>> j == firstDayName: 이번 달 시작 요일일 때
				else if (i == 0 && j == firstDayName) {
					daySet[cnt++] = `${startDayCount++}`;
				}
				//<<1주차>> j > firstDayName: 이번 달 시작 요일 이후 일 때
				else if (i == 0 && j > firstDayName) {
					daySet[cnt++] = `${startDayCount++}`;
				}
				// startDayCount <= lastDay: 이번 달의 마지막 날이거나 이전일 때
				else if (i > 0 && startDayCount <= lastDate_CD.getDate()) {
					daySet[cnt++] = `${startDayCount++}`;
				}
				// startDayCount > lastDay: 이번 달의 마지막 날 이후일 때
				else if (startDayCount > lastDate_CD.getDate()) {
					daySet[cnt++] = `${lastDayCount++}`;
				}
			}
		}
		return daySet;
	}
	//////////달력 - weekly	
	function buildWeek(week) {
		currentDate.setDate(currentDate.getDate() + week * 7); // 0 현재, -1 저번주, +1 다음주		
		weekDate=new Date(currentDate); 
		
		let title = "&nbsp;" + weekDate.getFullYear() + "년&nbsp;&nbsp;&nbsp;&nbsp;" + (weekDate.getMonth() + 1) + "월&nbsp;(주)";
		for (let i = 0; i < 7; i++) {
			if (weekDate.getDate() == 1)
				title += " ~&nbsp;&nbsp;&nbsp;&nbsp; " + weekDate.getFullYear() + "년&nbsp;&nbsp;&nbsp;&nbsp;"
					+ (weekDate.getMonth() + 1) + "월";
			weekDate.setDate(weekDate.getDate() + 1);
		}
		weekDate.setDate(weekDate.getDate() - 7);//원상복구
		//monthly header 변경
		$(".currentCalendarHeader").html(title);        

		//rightSection칸에 weekly 소스 채우기
		var daySet = makeElementWeek();
		weeklySetting(daySet);		
		var v1 = $("#checkedMainCategory").val();
		var v2 = $("#checkedSubCategory").val();
		if(v1!='none' && v2!='none'){
			$("#checkedContentBtn").click();
		}else{
			alert("카테고리를 선택하시면 스케쥴이 보여집니다.");
		}
	}
	function makeElementWeek() {
		var daySet = [];
		
        if (weekDate.getDay() == 0) {
			weekDate.setDate(weekDate.getDate() - 6);
		} else {
			weekDate.setDate(weekDate.getDate() - (weekDate.getDay() - 1));
		}//첫주 시작 월요일로 잡기

		for (let cnt = 0; cnt < 7; cnt++) {//0~6
			daySet[cnt] = `${weekDate.getDate()}`;
			weekDate.setDate(weekDate.getDate() + 1);//날짜 증가시키기
        }
		console.log("weekly dayset >> "+daySet[0]+" ~ "+daySet[6]);
		return daySet;
    }
	function monthlySetting(daySet) {
		var cnt = 0;
		for (let i = 1; i < 7; i++) {//1~6주차를 위해 6번 반복     
			for (let j = 0; j < 7; j++) {//일요일~토요일을 위해 7번 반복
				$(".week" + i).children(":eq(" + j + ")").children().first().text(daySet[cnt++]);
				$(".week" + i).children(":eq(" + j + ")").children().last().html('<ul class="dayRsvList"></ul>');
			}
		}
		for(let i=0;i<7;i++){
			var a=$(".week1").children(":eq(" + i + ")").children().first().text()*1;
			var b=$(".week5").children(":eq(" + i + ")").children().first().text()*1;
			var c=$(".week6").children(":eq(" + i + ")").children().first().text()*1;
			if (a > 8) {
				$(".week1").children(":eq(" + i + ")").css('background-color', 'gray');
				$(".week1").children(":eq(" + i + ")").off('click');
			}
			if (b < 20) {
				$(".week5").children(":eq(" + i + ")").css('background-color', 'gray');
				$(".week5").children(":eq(" + i + ")").off('click');
			}
			if (c < 20) {
				$(".week6").children(":eq(" + i + ")").css('background-color', 'gray');
				$(".week6").children(":eq(" + i + ")").off('click');
			}
		}
	}
	function weeklySetting(daySet) {
		var cnt = 0;
		for (let i = 0; i < 7; i++) {//0~6
			$(".weekDayHeaderContainer").children(":eq(" + i + ")").children().last().html(`${daySet[cnt++]}`);
		}
	}
	
	function getWeekOfMonth(date) {//date가 한달중 몇째주인지 (월요일~일요일 이 1주임을 기준으로 계산) //20210805를 구하기위해 20210705데이터 IN
		var selectedDay = date.getDate();//05
		var first = new Date(date.getFullYear() + '/' + (date.getMonth() + 1) + '/01');//20210805데이터를 집어넣고 내부는 20210705
		var monthFirstDateDay = (first.getDay() == 0) ? 6 : first.getDay() - 1; // 월요일이면 0 일요일이면 6이 반환됨

		return Math.ceil((selectedDay + monthFirstDateDay) / 7);
	}
	
	async function fetchData(url, pageVal){
		const response = await fetch(url);
		const json = await response.json();
		if (json != null) {
			if (pageVal === 'monthly') {
				for (var value of json) {
					var day = value.rsv_date.substr(8, 2);
					var oneday = new Date(currentDate);
					oneday.setDate(day);
					if (oneday.getDay() == 0) {//일요일
						$(".week" + getWeekOfMonth(oneday)).children(":eq(6)").
						children().last().children().append(`<li>&nbsp&nbsp[ ${value.main_content} - ${value.sub_content} ] ${value.start_time}~${value.end_time}</li>`);
					} else {//1~6 월~토
						$(".week" + getWeekOfMonth(oneday)).children(":eq(" + (oneday.getDay() - 1) + ")").
						children().last().children().append(`<li>&nbsp&nbsp[ ${value.main_content} - ${value.sub_content} ] ${value.start_time}~${value.end_time}</li>`);
					}
				}
				for(var i=0;i<5;i++){
					for(var j=0;j<6;j++){
						var h=$(".week" + i).children(":eq("+j+")").children().last().children().height();
						if(h>71) $(".week" + i).children(":eq("+j+")").children().last().css('overflow-y','scroll');
					}
				}
			} else if (pageVal === 'weekly'){
				var temp = "";
				var countingTime=0;
				var count=1;
				var divNo=140;
			
				for (var value of json) {
					/*
					{"schedule_seq":33,
					"category":1,
					"main_content":"사범관",
					"sub_content":"202",
					"user_type":"P",
					"user_id":"600548",
					"user_name":"홍길동",
					"rsv_date":"2021-11-08", -> inputDate.getDay() = 월요일 = 1 
					"start_time":"10:00",
					"end_time":"15:00"}						
					*/
					let inputDayNumber = value.rsv_date.substr(8, 2);// rsv_date : 2021-11-08 -> 08

					for (let cnt = 0; cnt < 7; cnt++) {
						let dayNumber = String($('.dayoftheweek' + cnt).text()).padStart(2, '0'); //header의 숫자 2자리로만들기
						if (inputDayNumber === dayNumber) {// 08 === 08
							// start time 추출 - 10:00
							let s_hour = `${value.start_time}`;
							s_hour = s_hour.substr(0, 2); //10
							let s_minute = `${value.start_time}`;
							s_minute = s_minute.substr(3, 2); //00

							// end time 추출 - 15:00
							let e_hour = `${value.end_time}`;
							e_hour = e_hour.substr(0, 2);//15
							let e_minute = `${value.end_time}`;
							e_minute = e_minute.substr(3, 2);//00
							console.log("startTime : " +s_hour+" : "+s_minute+" // endTime : " +e_hour +" : "+ e_minute);

							// 색깔 칠하기
							let colorCode = "#" + Math.round(Math.random() * 0xffffff).toString(16); // 랜덤 색상 생성
							$(".weekItemContainer :nth-child("+count+")").css('background-color',colorCode);
							
							//요일에 따른 grid-column 설정 : 일요일만 7/8 나머지는 getDay()/getDay()+1
							var inputDate=new Date(`${value.rsv_date}`);
							var inputDay=inputDate.getDay();//월 1 ~ 토 6, 일요일 0
							if(inputDay==0) $(".weekItemContainer :nth-child("+count+")").css('grid-column','7/8');
							else $(".weekItemContainer :nth-child("+count+")").css('grid-column',`${inputDay}/${inputDay+1}`);
							
							// 시간에 따른 grid-row 설정 : 30분은 X 1시간째부터 +1칸
							var	s_no=2*s_hour-17;
							var	e_no=2*e_hour-17;
							if (s_minute==30) s_no+=1;
							if (e_minute==30) e_no+=1;
							$(".weekItemContainer :nth-child("+count+")").css('grid-row',`${s_no}/${e_no}`);
							
							//TEXT값 넣기
							$(".weekItemContainer :nth-child("+count+")").html(`<span>[ ${value.main_content} - ${value.sub_content} ] </span><br> <span>${value.start_time}~${value.end_time}</span>`);
							
							// 생성된 시간에 따른 div태그 삭제
							countingTime=e_hour-s_hour;//10~15시 => 5
							if(s_minute<e_minute) countingTime+=0.5//10시~15시 30분 => 5.5 
							else if(s_minute>e_minute) countingTime-=0.5//10시30분 ~ 15시 => 4.5
							
							var countingTime_=(countingTime-0.5)/0.5;//5시간 => 9칸, 2시간 => 3칸
							for(var i=0;i<countingTime_;i++){//지정 개수만큼 Grid에서 div태그지우기
								$(".weekItemContainer :nth-child("+divNo+")").remove();
								divNo--;
							}
						}
					}
					count++;
				}
			}else if (pageVal === 'category') {
				$("#checkedSubCategory").find("option").remove();//기존 옵션 제거하고 선택된 메인카테고리에 따른 서브카테고리불러올 것
				for (var value of json) {
					$('#checkedSubCategory').append($("<option></option>").attr("value", `${value}`).text(`${value}`));
				}
			}  else if (pageVal === 'categoryDialog') {
				$("#checkedSubDialog").find("option").remove();//기존 옵션 제거하고 선택된 메인카테고리에 따른 서브카테고리불러올 것
				for (var value of json) {
					$('#checkedSubDialog').append($("<option></option>").attr("value", 2).text(`${value}`));
				}
			}else if (pageVal === 'myRsv'){
				for (var value of json) {
					var str = "<tr id='seq"+value.schedule_seq+"'>"+
					"<td>"+value.schedule_seq+"</td>"+
					"<td>"+value.main_content+"</td>"+
					"<td>"+value.sub_content+"</td>"+
					"<td>"+value.rsv_date+"</td>"+
					"<td>"+value.start_time+"</td>"+
					"<td>"+value.end_time+"</td>"+
					"<td><input type='button' class='change' value='수정' style='margin: 1px;'/></td>"+
					"<td><input type='button' class='cancle' value='삭제'/></td></tr>";
					//console.log(str);
					$("#myRsvTable > tbody:last").append(str);
				}
			}
		}
	}
	//////////HEADER부분 날짜 변경 버튼 함수
	function clickMonth(cnt) {//////
		currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + cnt, currentDate.getDate());
		buildMonth();
	}

	function removeDataMonth() {//Monthly내부에 달력내용만 지우기
		var ps = document.querySelectorAll("#monthData");
		for (i = 0; i < ps.length; i++) {
			ps[i].innerHTML="<ul class='dayRsvList'></ul>"; 
		}
		//$(".calendar_day").children().append('<span id="m"></span>');
	}
	/************************LEFT*************************/
	
	function resetMonthly(){
		for (var i = 0; i < 5; i++) {
			for (var j = 0; j < 6; j++) {
				var h = $(".week" + i).children(":eq(" + j + ")").children().last().children().height();
				if (h > 71) $(".week" + i).children(":eq(" + j + ")").children().last().css('overflow-y', 'inherit');
			}
		}
		for(let i=0;i<7;i++){
			var a=$(".week1").children(":eq(" + i + ")").children().first().text()*1;
			var b=$(".week5").children(":eq(" + i + ")").children().first().text()*1;
			var c=$(".week6").children(":eq(" + i + ")").children().first().text()*1;
			if (a > 8) {
				$(".week1").children(":eq(" + i + ")").css('background-color', '#ABD0CE');
				$(".week1").children(":eq(" + i + ")").on('click');
			}
			if (b < 20) {
				$(".week5").children(":eq(" + i + ")").css('background-color', '#ABD0CE');
				$(".week5").children(":eq(" + i + ")").on('click');
			}
			if (c < 20) {
				$(".week6").children(":eq(" + i + ")").css('background-color', '#ABD0CE');
				$(".week6").children(":eq(" + i + ")").on('click');
			}
		}
	}
	$("#prevBtn").click(function() {
		if ($("#mwBtn").val() == 'weekly') {
			resetMonthly();
			clickMonth(-1);
		} else if ($("#mwBtn").val() == 'monthly') {
			buildWeek(-1);
		} 
	});
	$("#nextBtn").click(function() {
		if ($("#mwBtn").val() == 'weekly') {
			resetMonthly();
			clickMonth(1);
		} else if ($("#mwBtn").val() == 'monthly') {
			buildWeek(1);
		}
	});
	$("#todayBtn").click(function() {
	});
	
	$("#checkedMainCategory").change(function() {//메인 카테고리에 따른 서브카테고리목록 반환
		var v1 = $("#checkedMainCategory").val();
		console.log("checkedMainCategory값 변동 함수 작동 >>" +`/api/contents/${categoryNo}?mainContent=${encodeURI(encodeURIComponent(v1))}`);
		//한글깨짐 인코딩처리 - encodeURI(encodeURIComponent(v))
		fetchData(`/api/contents/${categoryNo}?mainContent=${encodeURI(encodeURIComponent(v1))}`, 'category');
	});
	
	$("#checkedContentBtn").click(function() {//카테고리 선택 후 선택된 카테고리 내용만 보기
		var v1 = $("#checkedMainCategory").val();
		var v2 = $("#checkedSubCategory").val();
		if(v1!='none' && v2!='none'){
			if ($("#mwBtn").val() == 'weekly'){//한달 캘린더
				removeDataMonth();
				fetchData(`/api/schedules/${categoryNo}
					?year=${currentDate.getFullYear()}&month=${String(currentDate.getMonth()+1).padStart(2,'0')}
					&week=0&mainContent=${encodeURI(encodeURIComponent(v1))}&subContent=${v2}`,'monthly');
			}else if ($("#mwBtn").val() == 'monthly'){//주간 캘린더
					$("#allContentBtn").toggleClass('active');
					showWeek();
					fetchData(`/api/schedules/${categoryNo}
					?year=${currentDate.getFullYear()}&month=${String(currentDate.getMonth()+1).padStart(2,'0')}
					&week=${getWeekOfMonth(currentDate)}&mainContent=${encodeURI(encodeURIComponent(v1))}&subContent=${v2}`,'weekly');
			}
		}else{
			alert("카테고리를 먼저 선택해주세요");
		}		
	});
	$("#allContentBtn").click(function() {
		if ($("#mwBtn").val() == 'weekly'){//전체보기 기능은 한달보기에서만 존재
			$("#allContentBtn").toggleClass('active');
			removeDataMonth();
			fetchData(`/api/schedules/${categoryNo}
				?year=${currentDate.getFullYear()}&month=${String(currentDate.getMonth()+1).padStart(2,'0')}
				&week=0&subContent=all`,'monthly');
		}
	});
	
	$("#mwBtn").click(function() {
		if ($("#mwBtn").val() == 'weekly') {
			$("#mwBtn").val('monthly');// 버튼 라벨 바꾸고
			showWeek();
			buildWeek(0);//화면에 weekly띄우기
			$("#allContentBtn").hide();
		} else {
			$("#mwBtn").val('weekly');// 버튼 라벨 바꾸고
			showMonth();
			buildMonth();//화면에 monthly띄우기
			$("#allContentBtn").show();
			$("#checkedSubCategory").val('none');
		}
	});
	$("#rsvBtn").click(function() {
		showSchedule();
		buildMonthMy();
	});
/*	$('#myRsvTable').DataTable({
		lengthChange : false,
		searching: false,
		ordering: false,
		info: false,
		paging: false
	});*/
	/*************************RSV dialog *************************/
	$("#checkedMainDialog").change(function() {
		var v = $("#checkedMainDialog").val();
		//한글깨짐 인코딩처리 - encodeURI(encodeURIComponent(v))
		fetchData(`/api/contents/${categoryNo}?mainContent=${encodeURI(encodeURIComponent(v))}`, 'categoryDialog');
	});	
	$('#startTime').timepicki({
		show_meridian:false,
		min_hour_value:9,
		max_hour_value:19,
		increase_direction:'up',
		start_time: ["09", "00"],
		step_size_minutes:30
	});
	$('#endTime').timepicki({
		show_meridian:false,
		min_hour_value:9,
		max_hour_value:19,
		increase_direction:'up',
		start_time: ["19", "00"],
		step_size_minutes:30
	});
	
	$('#dialogSubmit').on("click", function() {
		fetch('/api/schedules', {
			method: "POST",
			headers: {
				"Content-Type": "application/json; charset=UTF-8"
			},
			body: JSON.stringify({
				main_content: $('select[name=checkedMainDialog] option:selected').text(),
				sub_content: $('select[name=checkedSubDialog] option:selected').text(),
				rsv_date: $('#rsv-dialog-date').text(),
				start_time: $('#startTime').val(),
				end_time: $('#endTime').val()
			}),
			dataType: 'json'
		})
			.then(response => console.log(response))
			.then(rsvDialog.dialog("close"))
			.then(alert("예약되었습니다"))
			.then(buildMonth())//location.reload()
			.catch(error => console.log(error))
	});

	$('#dialogCancle').on("click", function() {
		rsvDialog.dialog("close");
	});
	
	rsvDialog = $("#rsv-dialog-form").dialog({
		autoOpen : false,
		height : 850,
		width : 800,
		modal : true
	});

	rsvForm = rsvDialog.find("form").on("submit", function(event) {
		var valid = true;

		if (valid) {
			rsvDialog.dialog("close");
		}
		return valid;
	});
	function getCookie(cookie_name) {
		//document.cookie => userId=600548; userName=홍길동; login=true
		var x, y;
		var val = document.cookie.split(';');
		for (var item of val) {
			x = item.substr(0, item.indexOf('='));
			y = item.substr(item.indexOf('=') + 1);
			x = x.replace(/^\s+|\s+$/g, '');// 앞과 뒤의 공백 제거하기 
			if (x == cookie_name) { return unescape(y); }// unescape로 디코딩 후 값 리턴 
		}
	} 
	function delAllCookie() {
		var x, y;
		var val = document.cookie.split(';');
		for (var item of val) {
			x = item.substr(0, item.indexOf('='));
			y = item.substr(item.indexOf('=') + 1);
			x = x.replace(/^\s+|\s+$/g, '');// 앞과 뒤의 공백 제거하기 
			document.cookie = `${x}=;Expires=Thu, 01 Jan 1970 00:00:00 GMT`;
		}
	}

});