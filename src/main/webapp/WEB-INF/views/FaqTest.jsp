<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="/js/mainJS.js" defer></script>
<%@include file="/WEB-INF/views/header.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="/js/TweenMax.js"></script>
<script>
var qnaNum = -1;
var url = "/api/faq";

var index = 1; // 현재 페이지
var next_index = index; // 다음 페이지
var previous_index = 0; // 이전 페이지
var cnt; // json 갯수 확인

$(window).load(function() {
	$("#adminbtn").hide();
	let logintype = '<%=(String)session.getAttribute("userType")%>';
	
	if(logintype == 'M' && logintype != null){
		$("#adminbtn").show();
	}
	
	$('#previous_btn').click(function(){
		if (index == 1) alert("처음 페이지 입니다!");
		else {
			index--;
			$('#listWrap').text('');
			faqFetch(url);
		}
	})
	
	$('#next_btn').click(function(){
		if ((index * 4) >= cnt) alert("마지막 페이지 입니다!");
		else {
			index++;
			$('#listWrap').text('');
			faqFetch(url);
		}
	})
	
	async function faqFetch(url) {
		const response = await fetch(url);
		const json = await response.json();
		
		if (json != null) {
			cnt = 0;
			for (var value of json) {
				cnt++;
				
				if (cnt >= (index * 4) - 3 && cnt <= (index * 4)) {
					var str = "<li class='qa_li'><div class='question'><p class='tit'>"+value.question+"</p>"
					+ "<p class='iconDiv'><img src='https://happyjung.diskn.com/data/lecture/icon_jquery_faq2_icon_arrow.png'></p></div>"
					+ "<div class='answer'>"+value.answer+"</div></li>";
					$("#listWrap").append(str);
				}		
			}
		}
	}
    faqFetch(url);
    
    $(document).on("click",".qa_li .question",function(){ 
    	q = $(".qa_li .question").index(this);
        if(q!=qnaNum){
            $('.qa_li .answer').stop(true, true).slideUp(400);
            $('.qa_li').removeClass('open');
            TweenMax.to($('.qa_li .question').eq(qnaNum).find('.iconDiv'), 0.4, {rotation:0});
            qnaNum = q;
            $('.qa_li').eq(qnaNum).addClass('open');
            $('.qa_li .answer').eq(qnaNum).stop(true, true).slideDown(400);
            TweenMax.to($('.qa_li .question').eq(qnaNum).find('.iconDiv'), 0.4, {rotation:0});
        }else{
            $('.qa_li .answer').eq(qnaNum).stop(true, true).slideUp(400);
            $('.qa_li').eq(qnaNum).removeClass('open');
            TweenMax.to($('.qa_li').eq(qnaNum).find('.question p'), 0.4, {rotation:0});
            qnaNum = -1;
        }
    });
    
    var faqInsertDialog;
	
    faqInsertDialog = $("#faq-insert-dialog-form").dialog({
		autoOpen: false,//페이지 로드시 다이얼로그가 자동으로 열리는 것 방지
		height: 250,
		width: 450,
		modal: true,//최상위에 다이알로그 표시
		resizable: false,//창크기 조절할 수 없도록 설정
		buttons: {
			"확인": function() {
				let question_dialog = $("#questiontext").val();
				let answer_dialog = $("#answertext").val();
				
				var url_dialog = '/api/faq/0?question='+question_dialog+'&answer='+answer_dialog;
				faqFetch(url_dialog);
				
				document.location.href="/faq";
			},
			"취소": function() {
				faqInsertDialog.dialog("close");
			}
		}
	});
    
    $("#insert_btn").on("click", function() {
    	faqInsertDialog.dialog("open");
	});
}); 
</script>
<style>
@import url('http://fonts.googleapis.com/earlyaccess/notosanskr.css');

ul, li, p {
	list-style: none;
	padding: 0;
	margin: 0;
}

.listWrap {
	font-family: 'Noto Sans KR', sans-serif;
	margin-bottom: 20px;
}

.listWrap .qa_li {
	position: relative;
	display: block;
	padding: 0;
	border-bottom: 1px solid #ededed;
	cursor: pointer;
}

.listWrap .qa_li:first-child {
	border-top: 1px solid #a6a6a6;
}

.listWrap .qa_li .ca_name {
	margin-bottom: 14px;
	font-weight: 400;
	color: #999;
	font-size: 18px;
}

.listWrap .qa_li .tit {
	color: #222;
	font-size: 24px;
	transition: color 0.3s ease-out;
}

.listWrap .qa_li:hover .tit {
	color: #0a7ac8;
}

.qa_li .question {
	position: relative;
	display: block;
	padding: 25px 100px 25px 120px;
	background:
		url('https://happyjung.diskn.com/data/lecture/icon_jquery_faq2_icon_q.png')
		40px center no-repeat;
}

.qa_li .question .iconDiv {
	position: absolute;
	right: 40px;
	top: 50%;
	-webkit-transform: translateY(-50%);
	-moz-transform: translateY(-50%);
	-o-transform: translateY(-50%);
	-ms-transform: translateY(-50%);
	transform: translateY(-50%);
}

.qa_li .answer {
	position: relative;
	display: none;
	padding: 40px 120px;
	font-size: 16px;
	color: #222;
	line-height: 28px;
	background: #f6f6f6
		url('https://happyjung.diskn.com/data/lecture/icon_jquery_faq2_icon_a.png')
		40px 40px no-repeat;
	border-top: 1px solid #e4e4e4;
}

.modA {
	position: absolute;
	right: 30px;
	bottom: 30px;
	color: #e82b2b;
}
</style>
</head>

<body>
	<ul class="listWrap" id="listWrap"></ul>
	<div class="indexlist">
		<input type="button" class="contentBtn" id="previous_btn" value="previous" name="previous" />
		<input type="button" class="contentBtn" id="next_btn" value="next" name="next" />
		<div id="adminbtn">
			<input type="button" class="contentBtn" id="insert_btn" value="글쓰기" name="insert" />
			<input type="button" class="contentBtn" id="delete_btn" value="글삭제" name="delete" />
		</div>
	</div>
	
	<!------------------------- Faq Insert dialog ------------------------->
	<div id="faq-insert-dialog-form" class="dialog" title="FAQ 추가">
		<div class="login_property">
			<div id="loginPropertyLeft" style="display: inline-block">
				<div class="inputText">
					<label for="questionlabel" class="idLabel">질문 : </label> 
					<input type="text" name="questiontext" id="questiontext"
						placeholder="질문을 입력하세요."
						class="text ui-widget-content ui-corner-all id" required />
				</div>

				<div class="inputText">
					<label for="answerlabel" class="passwordLabel">답변 : </label> 
					<input type="text" name="answertext" id="answertext"
						placeholder="답변을 입력하세요."
						class="text ui-widget-content ui-corner-all id" required />
				</div>
			</div>
		</div>
	</div><!-- Faq-Insert-dialog-form END-->
</body>
</html>