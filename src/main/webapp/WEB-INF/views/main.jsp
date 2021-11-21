<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="/js/mainJS.js" defer></script>
<%@include file ="/WEB-INF/views/header.jsp" %>

	<div id="mainContatiner">
		<div class="home_form">
			<div id="imgContatiner">
				<img id="img" src="/img/main1.png">
				<img id="img" src="/img/main2.png">
			</div>
			<div id="imgContatiner2"></div>
		</div>
	
	</div>
	
 	<!------------------------- login dialog ------------------------->
	<div id="user-login-dialog-form" class="dialog" title="로그인">
		<div class="login_property">
			<div id="loginPropertyLeft" style="display: inline-block">
				<div class="inputText">
					<label for="userId" class="idLabel">&nbsp아이디 :&nbsp </label> 
					<input type="text" name="userId" id="userId"
						placeholder="ID를 입력하세요."
						class="text ui-widget-content ui-corner-all id" required />
				</div>

				<div class="inputText">
					<label for="userPwd" class="passwordLabel">비밀번호 : &nbsp</label> 
					<input type="password" name="userPwd" id="userPwd"
						placeholder="비밀번호를 입력하세요."
						class="text ui-widget-content ui-corner-all password" required />
				</div>
			</div>
		</div>
	</div><!-- login-dialog-form END-->
	
<%@include file ="/WEB-INF/views/footer.jsp" %>