<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="/js/mainJS.js" defer></script>
<%@include file="/WEB-INF/views/header2.jsp"%>

<div id="mainContatiner">
	<div class="home_form">
		<div id="imgContatiner">
			<img id="img" src="/img/main1.png"> <img id="img"
				src="/img/main2.png">
		</div>
		<div id="imgContatiner2"></div>
	</div>

</div>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.12.1/css/all.css"
	integrity="sha384-v8BU367qNbs/aIZIxuivaU55N5GPF89WBerHoGA4QTcbUjYiLQtKdrfXnqAcXyTv"
	crossorigin="anonymous">
<!-- Icon사용 URL -->

<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700'
	rel='stylesheet' type='text/css'>
<!--font-family: 'Roboto' 기본Font사용 URL -->

<link href="/css/main.css" rel="stylesheet" type="text/css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="/js/adminSetting.js"></script>
<script src="/js/adminLogout.js"></script>

<title>Admin Main Page</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.12.1/css/all.css"
	integrity="sha384-v8BU367qNbs/aIZIxuivaU55N5GPF89WBerHoGA4QTcbUjYiLQtKdrfXnqAcXyTv"
	crossorigin="anonymous">
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800'
	rel='stylesheet' type='text/css'>
</head>
<body>
	<!-- --------------------------------------------------------------------------------------------- -->
	<div id="btnSetform" class="dialog">
		<form method="POST" name="setting" action="/studio/admin">
			<fieldset>
				<h1>메뉴 공개 여부 설정</h1>
				<br>
				<div class="btnContainer" style="text-align: center;">
					<div>
						Studio : <label class="switch"> <input type="checkbox"
							value="studio" name="checkTest" id="studiocheck"
							onClick="check(this)" /> <span class="slider round"></span>
						</label>
					</div>

					<div>
						Rental : <label class="switch"> <input type="checkbox"
							value="rental" name="checkTest" id="rentalcheck"
							onClick="check(this)" /> <span class="slider round"></span>
						</label>
					</div>

					<div>
						Consulting : <label class="switch"> <input type="checkbox"
							value="consulting" name="checkTest" id="btn3check"
							onClick="check(this)" /> <span class="slider round"></span>
						</label>
					</div>

					<div>
						FAQ : <label class="switch"> <input type="checkbox"
							value="faq" name="checkTest" id="btn4check" onClick="check(this)" />
							<span class="slider round"></span>
						</label>
					</div>
				</div>

				<br> <br>
				<!-- <input type="submit" name="setting" value="저장"> -->
				<input type="submit" name="setting" tabindex="-1"
					style="position: absolute; top: -1000px" />
				<!-- 돌아가기 버튼구현 <input type="button" value="이전으로 돌아가기" onclick="window.history.go(-1);"/> -->
			</fieldset>
		</form>
	</div>
</body>
</html>