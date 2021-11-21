<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/managerMain.jsp"%>
<script src="/js/adminSetting.js"></script>
<script src="/js/adminLogout.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css" />

<script>
$(function() {
	$("#mainContatiner").hide();
	
	$('#rsvList').DataTable({
		lengthChange : false,
		searching: false,
		ordering: false,
		info: false,
		paging: false
	});
	
	$('.contentBtn').click(function(){
		fetchData('/api/schedules/'+$(this).attr('id').substr(8));
		$('#CheckedButton').text($(this).attr('id').substr(8));
		$("#rsvList > tbody:last").empty();
	})
	
	async function fetchData(url) {
		const response = await fetch(url);
		const data = await response.json();
		console.log(data);
		if(data !=null){
			columns : await test(data)	 
		}
		$('.cancle').click(function(){
			console.log('취소');
			console.log($(this).parent().parent().attr('id')); //seq1
			console.log($(this).parent().parent().attr('id').substr(3)); //1
			console.log('/api/schedules?seq='+$(this).parent().parent().attr('id').substr(3));
			
			fetch('/api/schedules/delete?seq='+$(this).parent().parent().attr('id').substr(3))
				.then(response=>response.json())
				.then(data=>console.log(data))
				.then($(this).parent().parent().remove())
				.catch(error=>console.log(error))
	
			
			//fetchData('/api/schedules/'+$('#CheckedButton').text());
					
		})
	}
	
	function test(data) {
		for (var value of data) {
			// category
			var category = String(value.category);
			
			switch (category){
		      case '1':
		    	  category = "studio"
		          break;
		      case '2':
		    	  category = "rental"
		          break;
		      case '3':
		    	  category = "consulting"
		          break;
		      default :
		          break;
		    }
			
			// type
			var type = String(value.user_type);
			
			switch (type){
		      case 'S':
		    	  type = "student"
		          break;
		      case 'P':
		    	  type = "professor"
		          break;
		      case 'M':
		    	  type = "manager"
		          break;
		      default :
		          break;
		    }
			
			// start_time, end_time
			var start_time = String(value.start_time);
			var end_time = String(value.end_time);
			var temp_time = start_time + " ~ " + end_time;
			
			var str = 
				"<tr id='seq"+value.schedule_seq+"'><td align=center class='valuetd'>"+value.schedule_seq+"</td><td align=center class='valuetd'>"+category+"</td><td align=center class='valuetd'>"+value.main_content+"</td><td align=center class='valuetd'>"+value.sub_content+"</td><td align=center class='valuetd'>"
				+type+"</td><td align=center class='valuetd'>"+value.user_id+"</td><td align=center class='valuetd'>"+value.user_name+"</td><td align=center class='valuetd'>"+value.rsv_date+"</td>"
				 + "<td align=center class='valuetd'>" + value.start_time + " ~ " + value.end_time + "</td>"
				+"<td align=center class='valuetd'><input type='button' class='change' value='수정' style='margin: 1px;'/></td><td align=center class='valuetd'><input type='button' class='cancle' value='삭제'/></td></tr>";
				
			$("#rsvList > tbody:last").append(str);
			
		}
		
	}
	
	$('#search').click(function(){
		var searchSelect = $('#searchSelect').val();
		var textValue = $('#searchBox').val();
		var CheckedButton = $('#CheckedButton').text();
		$("#rsvList > tbody:last").empty();
		console.log('/api/schedules/'+CheckedButton+'?searchSelect='+searchSelect+'&textValue='+encodeURI(encodeURIComponent(textValue)));
		getSearchData('/api/schedules/'+CheckedButton+'?searchSelect='+searchSelect+'&textValue='+encodeURI(encodeURIComponent(textValue)));
		//url = /api/schdules/btn1
	})
	
	async function getSearchData(url){
		const response = await fetch(url);
		const json = await response.json();
		console.log(json);
		if(json !=null){
			for (var value of json) {
				// category
				var category = String(value.category);
				
				switch (category){
			      case '1':
			    	  category = "studio"
			          break;
			      case '2':
			    	  category = "rental"
			          break;
			      case '3':
			    	  category = "consulting"
			          break;
			      default :
			          break;
			    }
				
				// type
				var type = String(value.user_type);
				
				switch (type){
			      case 'S':
			    	  type = "student"
			          break;
			      case 'P':
			    	  type = "professor"
			          break;
			      case 'M':
			    	  type = "manager"
			          break;
			      default :
			          break;
			    }
				
				var str = 
					"<tr><td align=center>"+value.schedule_seq+"</td><td align=center>"+category+"</td><td align=center>"+value.main_content+"</td><td align=center>"+value.sub_content+"</td><td align=center>"
					+type+"</td><td align=center>"+value.user_id+"</td><td align=center>"+value.user_name+"</td><td align=center>"+value.rsv_date+"</td><td align=center>"
					+value.start_time+"</td><td align=center>"+value.end_time+"</td>"
					+"<td align=center><input type='button' class='change' value='수정' style='margin: 1px;'/></td><td align=center><input type='button' class='cancle' value='취소'/></td></tr>";
					
				/* var str2 = `<tr><td>${value.rsvno}</td></tr>`; */
				$("#rsvList > tbody:last").append(str);
			}
		}
	}
});
</script>
<style>
.selectview {
	display: table-cell;
	vertical-align: middle;
	width: 550px;
	height: 70px;
	padding-left: 600px;
}

.selectBtn {
	margin: 5px;
	font-size: 12px;
	font-weight: bold;
	height: 30px;
	width: 60px;
	background-color: gray;
	border-radius: 4px;
	padding: 5px;
}

.contentBtn {
	font-size: 15px;
	font-weight: bold;
}

.toptd {
	font-size: 20px;
	font-weight: bold;
	color: black;
}

.valuetd {
	font-size: 15px;
	color: black;
}
</style>
</head>
<body>
	<div style="text-align: center;">
		<div class="selectview">
			<input type="button" class="contentBtn" id="category0" value="all"
				name="btn0" /> <input type="button" class="contentBtn"
				id="category1" value="studio" name="btn1" /> <input type="button"
				class="contentBtn" id="category2" value="rental" name="btn2" /> <input
				type="button" class="contentBtn" id="category3" value="consulting"
				name="btn3" /><br> <select name="searchSelect"
				id="searchSelect" class="checkedContentSelect">
				<option value="none">== 검색조건을 선택해주세요 ==</option>
				<option value="user_name">이름</option>
				<option value="user_id">유저아이디</option>
				<option value="main_content">메인카테고리</option>
				<option value="rsv_date">예약날짜</option>
			</select> <input type="text" id="searchBox" /> <input type="button"
				id="search" name="search" value="검색" class="selectBtn" />
		</div>
	</div>
	<p id="CheckedButton" style="display: none;"></p>
	<br>
	<div style="width: 85%; margin: 0 auto;">
		<table border=1 id="rsvList">
			<thead>
				<tr>
					<td align=center class="toptd">예약번호</td>
					<td align=center class="toptd">카테고리</td>
					<td align=center class="toptd">대분류</td>
					<td align=center class="toptd">소분류</td>
					<td align=center class="toptd">유저타입</td>
					<td align=center class="toptd">아이디</td>
					<td align=center class="toptd">이름</td>
					<td align=center class="toptd">예약날짜</td>
					<td align=center class="toptd">예약시간</td>
					<td align=center class="toptd">수정</td>
					<td align=center class="toptd">삭제</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
</html>