<%@page import="java.sql.ResultSet"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="com.tenco.model.TodoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODO List</title>
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

.list {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 800px;
    width: 100%;
    text-align: center;
}

h2 {
    font-size: 28px;
    color: #4CAF50; /* 케로로 초록색 */
    margin-bottom: 20px;
}

a {
    color: #4CAF50; /* 케로로 초록색 */
    text-decoration: none;
    font-weight: bold;
}

a:hover {
    text-decoration: underline;
}

table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed; /* 테이블 너비 고정 */
    margin-top: 20px;
}

th, td {
    border: 1px solid #dddddd;
    padding: 10px;
    text-align: center;
}

th:nth-child(1),
td:nth-child(1) {
    width: 20%; /* 제목 열 너비 고정 */
}

th:nth-child(2),
td:nth-child(2) {
    width: 30%; /* 설명 열 너비 고정 */
}

th:nth-child(3),
td:nth-child(3) {
    width: 15%; /* 마감일 열 너비 고정 */
}

th:nth-child(4),
td:nth-child(4) {
    width: 10%; /* 완료 여부 열 너비 고정 */
}

th:nth-child(5),
td:nth-child(5) {
    width: 25%; /* 액션 열 너비 고정 */
}

button {
    padding: 8px 12px;
    background-color: #FF9800; /* 케로로 주황색 */
    color: #FFFFFF;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #F57C00; /* 버튼 호버 색상 */
}
</style>


</head>
<body>
	<%
		
		List<TodoDTO> list =  (List<TodoDTO>)request.getAttribute("todoList");
	
		    if(list != null && !list.isEmpty()) {
	%>
	<div class ="list">
	<h2>할 일 목록</h2>
	<a href="todoForm"> 새 할일 추가</a>
	
	<table border="1">
		<tr>
			<th>제목</th>
			<th>설명</th>
			<th>마감일</th>
			<th>완료 여부</th>
			<th>(액션-버튼)</th>
		</tr>
		<%
			for(TodoDTO todo : list) {
		%>
		<tr>
			<td><%= todo.getTitle() %></td>
			<td><%= todo.getDescription() %></td>
			<td><%= todo.getDueDate() %></td>
			<td><%= todo.completedToString() == "true" ? "완료" : "미완료"%> </td>
			<td>
				<a href="detail?id=<%=todo.getId()%>">상세보기</a>
				<form action="delete"method = "GET">
					<input type="hidden" name="id" value="<%=todo.getId() %>">
					<button type="submit">삭제</button>
				</form>
			</td>
		</tr>
		<%} %>
	</table>
	
	<% } else {  %>

	<hr>
	<p>등록된 할 일이 없습니다.</p>	

	<% } %>	
	</div>
</body>
</html>