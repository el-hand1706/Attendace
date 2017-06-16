<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウントの登録</title>
</head>
<body>
	<form action="/Attendance/auth_Confirm" method="post">
		<h1>アカウント登録</h1>
		<br /> 
		<p>ADDRESS</p>
		<input type="text" name="getAddress" value="" />
		<br />
		<p>PASSWORD</p>
		<input type="password" name="getPassword" value="" />
		<br />
		<br />
		<input type="submit" value="実行" />
	</form>
</body>
</html>