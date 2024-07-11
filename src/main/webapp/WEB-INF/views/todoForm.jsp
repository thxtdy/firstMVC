<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>할 일 추가가가가ㅏ가가가가가각가가가가가가가ㅏ각가</title>
<style type="text/css">
body {
	background-image: url('/mvc/images/keroroBack.png'); /* 배경 이미지 경로 설정 */
    background-size: cover; /* 화면에 꽉 차도록 배경 이미지 크기 조절 */
    background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
    background-position: center; /* 배경 이미지 중앙 정렬 */
	font-family : 'Fredoka One', sans-serif;
	margin: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}
.form-container{
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
    max-width: 400px;
    width: 100%;
}
.form-header {
    margin-bottom: 20px;
    width: 200px; /* 이미지 너비 설정 */
}

.todo-form {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.todo-form input[type="text"], .todo-form input[type="date"] {
	width: calc(100% - 20px);
	margin: 10px 0;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
	box-sizing: border-box;
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

.todo-form button {
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

.todo-form button:hover {
	background-color: #4b8c3b; /* 버튼 호버 색상 */
}
a{
    text-decoration-line: none;
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
</style>
</head>
<body>
	<div class="form-container">
		<img class="form-header" src="${pageContext.request.contextPath}/images/keroro_logo.png" alt="케로로 로고">
		<%-- 상대 경로: http://localhost:8080/mvc/todo/add --%>
		<form action="add" method="post" class="todo-form">
			<label for="title">제목 : </label> <input type="text" id="title" name="title" value="코난 극장판 도장깨기"> <br>
			<br> <label for="description">설명 : </label>
			<textarea rows="15" cols="50" id="description" name="description">
			재밌어요...
		</textarea>

			<label for="dueDate">마감기한 : </label> <input type="date" id="dueDate" name="dueDate" value="2024-07-10"> <label for="completed">완료 여부 : </label> <input type="checkbox"
				id="completed" name="completed"><br>

			<button type="submit">추가</button>


		</form>
		<br>
		<br> <a href="list">목록으로 돌아가기</a>
	</div>
</body>
</html>