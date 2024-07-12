<%@page import="com.tenco.model.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
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
.sign-container {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
    max-width: 400px;
    width: 100%;
}

.sign-header {
    margin-bottom: 20px;
    width: 200px; /* 이미지 너비 설정 */
}

.sign-form {
    display: flex;
    flex-direction: column;
    align-items: center;
}
/* 입력 필드 */
.sign-form input[type="text"],
.sign-form input[type="password"] {
    width: calc(100% - 20px);
    margin: 10px 0;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    box-sizing: border-box;
}

/* 로그인 버튼 */
.sign-form button {
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

.sign-form button:hover {
    background-color: #4b8c3b; /* 버튼 호버 색상 */
}

</style>
</head>
<body>
<div class="sign-container">
	<h2>회원가입</h2>
	<!-- 에러 메시지 출력  -->
	<%
		UserDTO dto = new UserDTO();
		/* String errorMessage = (String)request.getAttribute("Message"); */
		String errorMessage = (String)request.getParameter("message");
		if(errorMessage != null ){
			
	%>
		<p style="color:red"> <%=errorMessage%></p>
		
	<% } %>
	<!-- 절대 경로 -->
	<img class="login-header" src="${pageContext.request.contextPath}/images/keroro_logo.png" alt="케로로 로고">
	<form action="/mvc/user/signUp" method = "post" class="sign-form">
		<label for = "username">사용자 이름 : </label>
		<input type = "text" id = "username" name="username" value="니달리">  
	
		<label for = "password">비밀번호 : </label>
		<input type = "password" id = "password" name="password" value="asd123">  
	
		<label for = "email">이메일 : </label>
		<input type = "text" id = "email" name="email" value="jungle@gmail.com">
		<input type = "hidden" id = "id" name="id" value= "<%= dto.getId() %>">
		<button type="submit">회원가입</button>  
	
	</form>
	</div>
</body>
</html>