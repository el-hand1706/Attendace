package update;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tbl.Tbl_PrintTable;
import utility.MyQuery;

/**
 * Servlet implementation class Table
 */
@WebServlet("/update_Table")
public class Table extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション開始
    	HttpSession session = request.getSession();
    	
    	// 変数宣言
    	int iUid = (int)session.getAttribute("iUid");
    	String sSql = "";
    	ArrayList<Tbl_PrintTable> array_printtable = new ArrayList<Tbl_PrintTable>();
//    	HashMap<String,Integer> hmDate = new HashMap<String,Integer>();
    	
    	ResultSet rs;
    	
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
			
			// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select id, "                                                                       );
			sSql = sSql.concat("    cast(month as signed) as month, "                                              );
			sSql = sSql.concat("    cast(day as signed) as day, "                                                  );
			sSql = sSql.concat("	case weekday(concat(year, '/', month, '/', day)) "                             );                                                                          
			sSql = sSql.concat("	when 0 then '月' "                                                             );
			sSql = sSql.concat("	when 1 then '火' "                                                             );
			sSql = sSql.concat("	when 2 then '水' "                                                             );
			sSql = sSql.concat("	when 3 then '木' "                                                             );
			sSql = sSql.concat("	when 4 then '金' "                                                             );
			sSql = sSql.concat("	when 5 then '土' "                                                             );
			sSql = sSql.concat("	when 6 then '日' "                                                             );
			sSql = sSql.concat("	end as weekdays, "                                                             );
			sSql = sSql.concat("	ifnull(date_format(cometime, '%H:%i:%s'),'') as cometimes, "                   );        
			sSql = sSql.concat("	ifnull(date_format(returntime, '%H:%i:%s'),'') as returntimes "                );        
			sSql = sSql.concat("from tbl_attendance "                                                              );
			sSql = sSql.concat("where uid = " + iUid + " "                                                         );
			sSql = sSql.concat("and year = date_format(current_timestamp(), '%Y') "                                );
			sSql = sSql.concat("and month = date_format(current_timestamp(), '%m') "                               );
			sSql = sSql.concat("order by concat(year, '/', month, '/', day) "                                      );
			sSql = sSql.concat("; "                                                                                );
			System.out.println(sSql);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs.next() != false){
				// 
				rs.beforeFirst();
	            while(rs.next()){
	            	Tbl_PrintTable  tbl_printtable = new Tbl_PrintTable();
	            	tbl_printtable.id = rs.getInt("id");
	            	tbl_printtable.months = rs.getInt("month");
	            	tbl_printtable.days = rs.getInt("day");
	            	tbl_printtable.weekdays = rs.getString("weekdays");
	            	tbl_printtable.cometimes = rs.getString("cometimes");
	            	tbl_printtable.returntimes = rs.getString("returntimes");
	            	array_printtable.add(tbl_printtable);
	            }  
			}else{
				// Selectに失敗したときの処理
				throw new Exception();
			}
			
			// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new Exception();
			};
			
			// jspに渡す値をセット
			Calendar cal = Calendar.getInstance();	
//			hmDate.put("year", cal.get(Calendar.YEAR));
//			hmDate.put("month", cal.get(Calendar.MONTH) + 1);
			request.setAttribute("year", cal.get(Calendar.YEAR));
			request.setAttribute("month", cal.get(Calendar.MONTH) + 1);
			request.setAttribute("array_printtable", array_printtable);
			RequestDispatcher dispatch = request.getRequestDispatcher("update/Table.jsp");
			dispatch.forward(request,response);
			
    	}catch(Exception e){
    		System.out.println("認証失敗");
    		// DB切断
    		MyQuery.closeDb();
    		// セッションを破棄
    		session.invalidate();
    		//　エラーメッセージをセットして認証画面に戻る
    		request.setAttribute("sErrMsg","SQL発行に失敗しました。 update/Change.java");
    		request.setAttribute("sAddress","");
    		request.setAttribute("sPassword","");
    		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
			dispatch.forward(request,response);
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doGetで処理
		doGet(request, response);
	}

}
