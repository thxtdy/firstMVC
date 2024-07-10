<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>할 일 추가가가가ㅏ가가가가가각가가가가가가가ㅏ각가</title>
</head>
<body>
	<h1>TODO Page</h1>
	<%-- 상대 경로: http://localhost:8080/mvc/todo/add --%>
	<form action="add" method="post">
		<label for="title">제목 : </label>
		<input type="text" id="title" name="title" value="코난 극장판 도장깨기">	
		<br><br>
		<label for="description">설명 : </label>
		<textarea rows="15" cols="50" id="description" name="description">
			재밌어요...
		</textarea>
		
		<label for="dueDate">마감기한 : </label>
		<input type="date" id="dueDate" name="dueDate" value="2024-07-10">
		
		<label for="completed">완료 여부 : </label>
		<input type="checkbox" id="completed" name="completed"><br>
		
		<button type="submit">추가</button>	
					
			
	</form>
	<br><br>
	<a href="list">목록으로 돌아가기</a>
	
</body>
</html>