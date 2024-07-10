<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "styleSheet" type = "text/css" href="../css/styles.css">
</head>
<body>
	<h2>회원가입</h2>
	<!-- 에러 메시지 출력  -->
	<%
		/* String errorMessage = (String)request.getAttribute("Message"); */
		String errorMessage = (String)request.getParameter("message");
		if(errorMessage != null ){
			
	%>
		<p style="color:red"> <%=errorMessage%></p>
		
	<% } %>
	<!-- 절대 경로 -->
	<form action="/mvc/user/signUp" method = "post">
		<label for = "username">사용자 이름 : </label>
		<input type = "text" id = "username" name="username" value="니달리">  
	
		<label for = "password">비밀번호 : </label>
		<input type = "password" id = "password" name="password" value="asd123">  
	
		<label for = "email">이메일 : </label>
		<input type = "text" id = "email" name="email" value="jungle@gmail.com">
		<button type="submit">회원가입</button>  
	
	</form>
	
</body>
</html>