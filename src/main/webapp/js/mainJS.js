const toggleBtn=document.querySelector('.navbar_toogleBtn');
const menu=document.querySelector('.navbar_menu');
const menuChild=menu.childNodes;
const icons=document.querySelector('.navbar_icons');
const faqTest=document.querySelector('#faqTest');
const mainContatiner=document.querySelector('#mainContatiner');

toggleBtn.addEventListener('click',()=>{
    menu.classList.toggle('active');
    icons.classList.toggle('active');
});

$(function() {
	var userLoginDialog;
	var userLoginField = $([]).add("#userId").add("#userPwd");
	
	userLoginDialog = $("#user-login-dialog-form").dialog({
		autoOpen: false,//페이지 로드시 다이얼로그가 자동으로 열리는 것 방지
		height: 250,
		width: 450,
		modal: true,//최상위에 다이알로그 표시
		resizable: false,//창크기 조절할 수 없도록 설정
		buttons: {
			"확인": function() {
				userLoginCheckLength();
			},
			"취소": function() {
				userLoginDialog.dialog("close");
			}
		},
		close: function() {
			userLoginField.removeClass("ui-state-error");//에러 없애기
		}
	});
	
	function userLoginCheckLength() {//로그인 요청시 들어오는 함수
		userLoginField.removeClass("ui-state-error"); //에러 없애기
		if (userLoginField.val().length > 15 || userLoginField.val().length < 5) {
			//false - ID,PWD 재요청
			userLoginField.addClass("ui-state-error");
			alert("아이디와 비밀번호를 다시 확인해 주세요.");
		} else {
			//true - 로그인 유효성 검사
			const userId = btoa($("#userId").val());//base64 인코딩
			const userPwd = btoa($("#userPwd").val());
			var url = `/api/users/login?id=${userId}&pwd=${userPwd}`;// 로그인 요청보내기
			loginFetch(url, userId, userPwd);
		}
	}
	
	function loginFetch(url, id, pwd) {//GET메소드
		fetch(url)
			.then(res => res.text())
			.then(res => {
				if (res == 'true') {
					console.log("로그인 성공 쿠키에 정된 '"+getCookie("userName")+"'이름의 타입 :"+getCookie("userType"));
					
					if(getCookie("userType")==='M'){
						alert("관리자로 로그인하셨습니다 :)");
						document.location.href="/managerMain";
					}else{
						$("#loginZone").children(":eq(1)").text(getCookie("userName"));
						$(".logoutSection").addClass('active');					
					}
					
					userLoginDialog.dialog("close");


				} else alert("아이디와 비밀번호를 다시 확인해 주세요."); 
			});
	}
	
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
	function delCookie(cookie_name) {
		let date = new Date();
		date.setDate(date.getDate() - 100);
		let Cookie = `${cookie_name}=;Expires=Thu, 01 Jan 1970 00:00:00 GMT`
		document.cookie = Cookie;
	}
	function delAllCookie() {
		var x, y;
		var val = document.cookie.split(';');
		for (var item of val) {
			x = item.substr(0, item.indexOf('='));
			y = item.substr(item.indexOf('=') + 1);
			x = x.replace(/^\s+|\s+$/g, '');// 앞과 뒤의 공백 제거하기 
			console.log(x);
			document.cookie = `${x}=;Expires=Thu, 01 Jan 1970 00:00:00 GMT`;
		}
	}
	$("#loginZone").on("click", function() {
		userLoginDialog.dialog("open");
	});
	$("#logoutBtn").on("click", function() {
		alert("로그아웃");
		$(".logoutSection").removeClass('active');
		$("#loginZone").children(":eq(1)").text('Login');
		delAllCookie();
		/*
		delCookie('userId');		
		delCookie('userName');		
		delCookie('userType');*/		
	});
	$("#mypageBtn").on("click", function() {
		alert("마이페이지");
	});
	
});