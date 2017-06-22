<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.ArrayList" import="tbl.Tbl_PrintTable"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Tbl_PrintTable> array_printtable 
	= (ArrayList<Tbl_PrintTable>)request.getAttribute("array_printtable");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>一覧画面</title>
	</head>
	<body>
		<h1>一覧画面</h1>
		<table border=1>
		<tr>
 			<th>日付</th>
 			<th>曜日</th>
 			<th>出勤時間</th>
 			<th>退勤時間</th>
 			<th>変更</th>
 		</tr>
<%		for(int i = 0; i < array_printtable.size(); i++){			%>
	 		<tr>
	 			<td><%=array_printtable.get(i).days %></td>
	 			<td><%=array_printtable.get(i).weekdays %></td>
	 			<td><%=array_printtable.get(i).cometimes %></td>
	 			<td><%=array_printtable.get(i).returntimes %></td>
	 			<td><a href="/Attendance/update_Change?id=<%=array_printtable.get(i).id%>">変更</a></td>
	 		</tr>
<%		}															%>
		</table>
		<br/>
		<a href="/Attendance/update_Insert">追加</a>
		<br/>
		<button type="button" onclick="history.back()">戻る</button>
	</body>
</html>