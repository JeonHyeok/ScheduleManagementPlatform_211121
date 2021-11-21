
$(window).load(function() {//모든 페이지 구성요소 페인팅 완료 후 호출 ONLY ONE
	$(".weekContainer").hide();
	$(".userScheduleContainer").hide();
	
	// Week 처리를 위한 변수
	let month;
	let firstWeekDay;
	let lastWeekDay
	let temp_month;
    let temp_year;
	
	// json 데이터 처리를 위한 변수
	let temp_loc;
	let temp_locno;

	const today = new Date(); // 현재 오늘 날짜

	let currentDate=today;//페이지에서의 날짜 - CD (2021-11-6)
	let firstDate_CD;//CD 달의 첫 날 (2021-11-1)
	let lastDate_CD;//CD 달의 마지막 날 (2021-11-30)
	let prev_lastDate_CD;//CD 달의 지난 달의 마지막 날 (2021-10-31)
	let weekDate;
	
	buildMonth();

	function buildMonth() {
		//firstDate_CD = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1, currentDate.getDay());
        firstDate_CD =new Date(currentDate);
        firstDate_CD.setDate(1);

		lastDate_CD =new Date(currentDate);
        lastDate_CD.setMonth(lastDate_CD.getMonth()+1);
        lastDate_CD.setDate(0);
        
		prev_lastDate_CD =new Date(firstDate_CD);
		prev_lastDate_CD.setDate(0);       
        buildWeek(0);
        
        
	}

    function buildWeek(week) {
        weekDate=new Date(currentDate);
		weekDate.setDate(weekDate.getDate() + week * 7);
        // 0 현재, -1 저번주, +1 다음주		 
        
		//rightSection칸에 week소스 채우기
		var daySet = makeElementWeek();
		//alert(daySet);
		//alert(`/api/schedules/${$("#categoryNo").text()}?year=${firstDate.getFullYear()}&month=${month}&week=1&fwd=${firstWeekDay}&lwd=${lastWeekDay}&t_month=${temp_month}`);
	}

    function makeElementWeek() {
		var daySet = [];
        //weekDate = 11/6
		if (weekDate.getDay() == 0) { // 일요일이라면
			weekDate.setDate(weekDate.getDate() - 6);
		} else {
			weekDate.setDate(weekDate.getDate() - (weekDate.getDay() - 1));
		} //첫주 시작 월요일로 잡기
        //weekDate : 첫주시작 // 11/1
        // weekDate.getMonth() = Nov, 10
        // month = weekDate.getMonth() + 1 -> 11
        
		month = weekDate.getMonth() + 1;
		
		for (let cnt = 0; cnt < 7; cnt++) {//0~6 
			daySet[cnt] = `${weekDate.getDate()}`;			
			if (weekDate.getDate() == 1 && daySet[0] != 1) {				
				temp_month = weekDate.getMonth() + 1;
			}
			//alert(daySet[cnt]);
			weekDate.setDate(weekDate.getDate() + 1);//날짜 증가시키기
		}
		weekDate.setDate(weekDate.getDate()-7);
		$("#imgContatiner2").html( // 11/6
            ` ${weekDate}<br>`
        );
		// 해당 주 첫번째 날, 마지막 날 저장
		firstWeekDay = daySet[0];
		lastWeekDay = daySet[6];
		
		return daySet;
	}
});