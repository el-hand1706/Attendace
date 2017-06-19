<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
	int iFlag = (int) request.getAttribute("iFlag");
	
	String sName = "";
	String sAddress = "";
	String sPassword = "";
	StringBuilder sNaErrMsg = new StringBuilder("");
	StringBuilder sAdErrMsg = new StringBuilder("");
	StringBuilder sPaErrMsg = new StringBuilder("");
	
	if(iFlag == 1){
		sName = (String) request.getAttribute("sName");
		sAddress = (String) request.getAttribute("sAddress");
		sPassword = (String) request.getAttribute("sPassword");
		sNaErrMsg = (StringBuilder) request.getAttribute("sNaErrMsg");
		sAdErrMsg = (StringBuilder) request.getAttribute("sAdErrMsg");
		sPaErrMsg = (StringBuilder) request.getAttribute("sPaErrMsg");
	}else{
		;
	}
%>
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
		<p>NAME</p>
		<input type="text" name="getName" value="<%=sName%>" />
		<p><%=sNaErrMsg%></p>
		<br />
		<p>ADDRESS</p>
		<input type="text" name="getAddress" value="<%=sAddress%>" />
		<p><%=sAdErrMsg%></p>
		<br />
		<p>PASSWORD</p>
		<input type="password" name="getPassword" value="<%=sPassword%>" />
		<p><%=sPaErrMsg%></p>
		<br />
		<br />
		<input type="submit" value="実行" />
	</form>
</body>
</html>