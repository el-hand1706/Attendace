<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = (String)request.getAttribute("id");
	int iYear = (int)request.getAttribute("year");
	int iMonth = (int)request.getAttribute("month");
	int iDay = (int)request.getAttribute("day");
	String cometime = (String)request.getAttribute("cometime");
 	String returntime = (String)request.getAttribute("returntime");
 	String sErrMsg = (String)request.getAttribute("errmsg");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>勤怠変更画面</title>
	</head>
	<body>
		<h1>勤怠変更画面</h1>
		<form action="/Attendance/update_ChangeTime" method="post">
			<input type="hidden" name="id" value="<%=id%>" />
			<p>出勤時間</p>
			<input type="text" name="cometime" value="<%=cometime%>" />
			<br/>
			<p>退勤時間</p>
			<input type="text" name="returntime" value="<%=returntime%>" />
			<br/>
			<br/>
			<input type="hidden" name="year" value="<%=iYear%>" />
			<input type="hidden" name="month" value="<%=iMonth%>" />
			<input type="hidden" name="day" value="<%=iDay%>" />
			<input type="submit" value="実行" /><button type="button" onclick="history.back()">戻る</button>
		</form>
	</body>
</html>