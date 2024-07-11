<%@page import="com.tenco.model.TodoDTO"%>
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
.edit-container {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
    max-width: 400px;
    width: 100%;
}
.edit-form {
	display: flex;
	flex-direction: column;
	align-items: center;
}
.edit-header {
    margin-bottom: 20px;
    width: 200px; /* 이미지 너비 설정 */
}


textarea {
	resize:none;
	width: calc(100% - 20px);
	height: 200px;
	padding: 10px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
}
.edit-form button {
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
.edit-form button:hover {
	background-color: #4b8c3b; /* 버튼 호버 색상 */
}


</style>
</head>
<body>
	<%
	
		TodoDTO todo = (TodoDTO)request.getAttribute("todo");
		if(todo != null) { %>
		
		<div class = "edit-container">
		<img class="edit-header" src="${pageContext.request.contextPath}/images/keroro_logo.png" alt="케로로 로고">
		<form action="update" method="post" class="edit-form">
			<input type="hidden" name ="id" value="<%=todo.getId()%>">
			<label for ="title">제목 : </label>
			<input type="text" id="title" name="title" value="<%=todo.getTitle()%>">
			<br>

			<label for ="description">설명 : </label>
			<textarea rows="15" cols="50" id="description" name="description">
			</textarea>
			<br>

			<label for ="dueDate">마감일 : </label>
			<input type="Date" id="dueDate" name="dueDate" value="<%=todo.getDueDate()%>">
			<br>

			<label for ="completed">완료 여부 : </label>
			<input type="checkbox" id="completed" name="completed" <%=todo.completedToString()== "true" ? "checked" : "" %> >
			<br>
			<button type="submit">수정</button>
		</form>
		
		<%	
		} else {
			out.print("<p>정보를 불러오는데 실패함</p>");
		}
	%>
	</div>
</body>
</html>