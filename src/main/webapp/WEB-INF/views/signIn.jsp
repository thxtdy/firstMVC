<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style type="text/css">
/* styles.css */

/* 전체 배경 */
body {
    background-image: url('/mvc/images/keroroBack.png'); /* 배경 이미지 경로 설정 */
    background-size: cover; /* 화면에 꽉 차도록 배경 이미지 크기 조절 */
    background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
    background-position: center; /* 배경 이미지 중앙 정렬 */
    font-family: 'Fredoka One', sans-serif;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

/* 로그인 컨테이너 */
.login-container {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
    max-width: 400px;
    width: 100%;
}

/* 로그인 헤더 */
.login-header {
    margin-bottom: 20px;
    width: 200px; /* 이미지 너비 설정 */
}

/* 로그인 폼 */
.login-form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

/* 입력 필드 */
.login-form input[type="text"],
.login-form input[type="password"] {
    width: calc(100% - 20px);
    margin: 10px 0;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
}

/* 로그인 버튼 */
.login-form button {
    width: calc(100% - 20px);
    margin: 10px 0;
    padding: 12px;
    background-color: #6ab04c; /* 케로로 초록색 */
    color: #fff; /* 흰색 텍스트 */
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.login-form button:hover {
    background-color: #4b8c3b; /* 버튼 호버 색상 */
}


</style>
<link href="https://fonts.googleapis.com/css2?family=Fredoka+One&display=swap" rel="stylesheet">
</head>
</head>
<body>
	<div class="login-container">
		<h2>로그인</h2>
	<!-- 성공 메시지 출력  -->
	<%
		/* String errorMessage = (String)request.getAttribute("Message"); */
		String success = (String)request.getParameter("message");
		if(success != null ){
			
	%>
		<p style="color:blue"> <%=success%></p>
		
	<% } %>
	<!-- 절대 경로 -->
	<img class="login-header" src="${pageContext.request.contextPath}/images/keroro_logo.png" alt="케로로 로고">
	<form action="/mvc/user/signIn" method = "post" class="login-form">
		<label for = "username">사용자 이름 : </label>
		<input type = "text" id = "username" name="username" value="박태현">  
	
		<label for = "password">비밀번호 : </label>
		<input type = "password" id = "password" name="password" value="asd123">  
	
		<button type="submit">로그인</button>  
	
	</form>
	</div>
</body>
</html>