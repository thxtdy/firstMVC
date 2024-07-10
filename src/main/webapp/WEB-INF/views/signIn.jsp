<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
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
	<form action="/mvc/user/signIn" method = "post">
		<label for = "username">사용자 이름 : </label>
		<input type = "text" id = "username" name="username" value="니달리">  
	
		<label for = "password">비밀번호 : </label>
		<input type = "password" id = "password" name="password" value="asd123">  
	
		<button type="submit">로그인</button>  
	
	</form>
</body>
</html>