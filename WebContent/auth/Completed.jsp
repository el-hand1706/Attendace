<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sTitle = (String) request.getAttribute("sTitle");    
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
		<title>アカウント登録_完了画面</title>
	</head>
	<body>
		<form action="/Attendance/auth_Auth" method="post">
			<h1>アカウント登録_完了画面</h1>
			<br />
			<p><%=sTitle%></p>
			<br />
			<input type="submit" value="戻る" />
		</form>
	</body>
</html>
