<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<%
	// Servletのデータ受け取り
	request.setCharacterEncoding("UTF8");
	String strServlet = (String) request.getAttribute("fromServlet");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<title>PAGE</title>
</head>
<body>
	Servletでセットしたデータを表示 ：
	<%=strServlet%>
</body>
</html>
