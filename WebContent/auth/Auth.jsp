<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 変数宣言
	String sAddress = "";
 	String sPassword = "";
	
	// servretのデータ受け取り
	request.setCharacterEncoding("UTF8");
	String sErrMsg = (String) request.getAttribute("sErrMsg");
	
	if(!sErrMsg.equals("")){
		sAddress = (String) request.getAttribute("sAddress");
		sPassword = (String) request.getAttribute("sPassword");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>認証画面</title>
	</head>
	<body>
		<h1>認証画面</h1>
		<a href="/Attendance/auth_Input">新規アカウント作成</a>
		<br/>
		<form action="/Attendance/input_Menu" method="post">
			<p>ADDRESS</p>
			<input type="text" name="getAddress" value="<%=sAddress%>" />
			<br/>
			<p>PASSWORD</p>
			<input type="password" name="getPassword" value="<%=sPassword%>" />
			<br/>
			<p><%=sErrMsg%></p>
			<br/>
			<input type="submit" name="do" value="認証" />
		</form>

	</body>
</html>