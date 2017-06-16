<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
	String sAddress = (String) request.getAttribute("sAddress");
	String sPassword = (String) request.getAttribute("sPassword");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
		<title>PAGE</title>
	</head>
	<body>
		<form action="/Attendance/auth_Completed" method="post">
			<h1>アカウント登録_確認画面</h1>
			<br />
			<p>ADDRESS</p>
			<%=sAddress%>
			<input type="hidden" name="getId" value="<%=sAddress%>" />
			<br />
			<p>PASSWORD</p>
			<%=sPassword%>
			<input type="hidden" name="getId" value="<%=sPassword%>" />
			<br />
			<br />
			<input type="submit" value="実行" />
		</form>
	</body>
</html>
