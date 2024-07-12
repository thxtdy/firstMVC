<%@page import="java.util.ArrayList"%>
<%@page import="com.tenco.model.TodoDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	TodoDTO dto = (TodoDTO)request.getAttribute("todosList");
	%>
	<div>
		<form action="#">

		</form>
	
	</div>
	
</body>
</html>