<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.HashMap"%>
<%
	HashMap sPara = (HashMap) request.getAttribute("sPara");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>メニュー画面</title>
	</head>
	<body>
		<h1>メニュー画面</h1>
		<p>名前 : <%=sPara.get("sName") %></p>
		<p>日付 : <%=sPara.get("sCurrentDate") %></p>
		<br />
<% 		if(sPara.get("sComeTime").equals("")){		%>
			<a href="/Attendance/input_SetTime?&sFlag=1">出勤</a>  
<%		}else{ 		%> 
			出勤
<%		} 			%> 	
		<br />
<% 		if(sPara.get("sReturnTime").equals("")){	%>
			<a href="/Attendance/input_SetTime?sFlag=2">退勤</a>
<%		}else{ 		%> 
			退勤
<%		} 			%> 	
		<br />
		<br />
		<a href="/Attendance/update_Table">一覧画面</a>
		<br />
		<br />
		<p>出勤時間 : <%=sPara.get("sComeTime") %></p>
		<p>退勤時間 : <%=sPara.get("sReturnTime") %></p>
		<button type="button" onclick="history.back()">戻る</button>
	</body>
</html>