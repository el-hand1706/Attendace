<%@ page language="java" 
		 contentType="text/html; charset=UTF-8" 
		 import="java.util.Calendar"
		 import="java.util.ArrayList" 
		 import="tbl.Tbl_PrintTable"
         pageEncoding="UTF-8"%>
<%
	ArrayList<Tbl_PrintTable> array_printtable 
	= (ArrayList<Tbl_PrintTable>)request.getAttribute("array_printtable");
	int iYear = (int)request.getAttribute("year");
	int iMonth = (int)request.getAttribute("month");
	int iSetFlag = 0;
	Calendar calendar = Calendar.getInstance();
	calendar.set(iYear, iMonth, 14);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>一覧画面</title>
	</head>
	<body>
		<h1>一覧画面</h1>
		<p><%=iYear%>/<%=iMonth%></p>
		<br/>
		<table border=1>
		<tr>
 			<th>日付</th>
 			<th>曜日</th>
 			<th>出勤時間</th>
 			<th>退勤時間</th>
 			<th>休憩時間</th>
 			<th>勤務時間</th>
 			<th>変更</th>
 			<th>削除</th>
 		</tr>
<%		for(int i = 1; i < 32; i++){														%>
<%			iSetFlag = 0;																	%>
<%			for(int j = 0; j < array_printtable.size(); j++){								%>
<%				if(array_printtable.get(j).days == i){										%>
		 			<tr>
		 				<td><%=array_printtable.get(j).days %></td>
			 			<td><%=array_printtable.get(j).weekdays %></td>
			 			<td><%=array_printtable.get(j).cometimes %></td>
			 			<td><%=array_printtable.get(j).returntimes %></td>
			 			<td>1</td>
			 			<td><%=array_printtable.get(j).difftime %>
			 			<td><a href="/Attendance/update_Change?id=<%=array_printtable.get(j).id%>&year=<%=iYear%>&month=<%=iMonth%>&day=<%=i%>">変更</a></td>
			 			<td><a href="/Attendance/update_Delete?id=<%=array_printtable.get(j).id%>&year=<%=iYear%>&month=<%=iMonth%>&day=<%=i%>">削除</a></td>
			 		</tr>
<%					iSetFlag = 1;															%>
<%				}																			%>
<%			}																				%>
<%			if(iSetFlag == 0){																%>
<%				calendar.set(iYear, iMonth - 1, i);											%>
<%				String sDayOfWeek = new String();   										%>
<% 				if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {				%>
<%        			sDayOfWeek = "日";		                                       			%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {			%>
<%        			sDayOfWeek =  "月";		                                        		%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {			%>
<%        			sDayOfWeek =  "火";														%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {		%>
<%        			sDayOfWeek =  "水";														%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {	    	%>
<%        			sDayOfWeek =  "木";														%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {	    	%>
<%        			sDayOfWeek =  "金";														%>
<%				}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {	    	%>
<%        			sDayOfWeek =  "土";														%>
<%    			}																			%>
	 			<tr>
	 				<td><%=i%></td>
		 			<td><%=sDayOfWeek%></td>
		 			<td>--:--:--</td>
		 			<td>--:--:--</td>
		 			<td>0</td>
		 			<td>--:--:--</td>
		 			<td><a href="/Attendance/update_Change?id=-1&year=<%=iYear%>&month=<%=iMonth%>&day=<%=i%> ">変更</a></td>
		 			<td></td>
		 		</tr>

<%			}																				%>
<%		}																					%>
		</table>
		<br/>
		<br/>
		<button type="button" onclick="history.back()">戻る</button>
	</body>
</html>