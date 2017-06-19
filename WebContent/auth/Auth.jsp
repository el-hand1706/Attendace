<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 変数宣言
	String sAddress = "";
 	String sPassword = "";
 	String sErrMsg = "";
	
	// servretのデータ受け取り
	request.setCharacterEncoding("UTF8");
	int iFlag = (int) request.getAttribute("iFlag");
	
	if(iFlag == 1){
		sAddress = (String) request.getAttribute("sAddress");
		sPassword = (String) request.getAttribute("sPassword");
		sErrMsg = "存在しません";
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
		<form action="/Attendance/auth_Input" method="post">
			<input type="submit" name="new" value="新規アカウント作成" />
		</form>
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