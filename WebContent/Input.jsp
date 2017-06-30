
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ServletのPost</title>
</head>
<body>
	<form action="/Attendance/Confirm.java" method="post">
		Postで送信
		<br /> 
		<input type="text" name="fromJsp" value="Jspでセット" />
		<br />
		<input type="submit" value="実行" />
	</form>
</body>
</html>