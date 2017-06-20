<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
	String sName = (String) request.getAttribute("sName");
	String sAddress = (String) request.getAttribute("sAddress");
	String sPassword = (String) request.getAttribute("sPassword");
	String sToken = (String) request.getAttribute("sToken");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
		<title>アカウント登録_確認画面</title>
	</head>
	<body>
		<form action="/Attendance/auth_Completed" method="post">
			<h1>アカウント登録_確認画面</h1>
			<br />
			<p>NAME</p>
			<%=sName%>
			<input type="hidden" name="getName" value="<%=sName%>" />
			<br />
			<p>ADDRESS</p>
			<%=sAddress%>
			<input type="hidden" name="getAddress" value="<%=sAddress%>" />
			<br />
			<p>PASSWORD</p>
			<%=sPassword%>
			<input type="hidden" name="getPassword" value="<%=sPassword%>" />
			<br />
			<br />
			<input type="hidden" value="<%=sToken%>" /> 
			<input type="submit" name="do" value="実行" /><button type="button" onclick="history.back()">戻る</button>
		</form>
	</body>
</html>
