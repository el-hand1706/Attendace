package update;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

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
 * Servlet implementation class ChangeTime
 */
@WebServlet("/update_ChangeTime")
public class ChangeTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeTime() {
        super();
        // TODO Auto-generated constructor stub
    }

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
    	int id = Integer.parseInt(request.getParameter("id"));
    	String cometime = request.getParameter("cometime");
    	String returntime = request.getParameter("returntime");
    	
    	ResultSet rs;
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
    		
    		sSql = "";
    		sSql = sSql.concat("update tbl_attendance "                                                                   );
    		sSql = sSql.concat("   set cometime = concat(DATE_FORMAT(created, '%y-%m-%d '), \"" + cometime + "\"), "      ); 
    		sSql = sSql.concat("	   returntime = concat(DATE_FORMAT(created, '%y-%m-%d '), \"" + returntime + "\"), "  ); 
    		sSql = sSql.concat("	   modified = current_timestamp() "                                                   ); 
    		sSql = sSql.concat(" where id = " + id + " "                                                                  ); 
    		sSql = sSql.concat("; "                                                                                       );
    		System.out.println(sSql);
    		if(MyQuery.executeSql(sSql) != 0){
    			throw new Exception();
    		}
    		
    		// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select id, "                                                                       );
			sSql = sSql.concat("    concat(month, '/', day) as days, "                                             );
			sSql = sSql.concat("	case weekday(concat(year, '/', month, '/', day)) "                             );                                                                          
			sSql = sSql.concat("	when 0 then '月' "                                                             );
			sSql = sSql.concat("	when 1 then '火' "                                                             );
			sSql = sSql.concat("	when 2 then '水' "                                                             );
			sSql = sSql.concat("	when 3 then '木' "                                                             );
			sSql = sSql.concat("	when 4 then '金' "                                                             );
			sSql = sSql.concat("	when 5 then '土' "                                                             );
			sSql = sSql.concat("	when 6 then '日' "                                                             );
			sSql = sSql.concat("	end as weekdays, "                                                             );
			sSql = sSql.concat("	ifnull(date_format(cometime, '%h:%i:%s'),'') as cometimes, "                   );        
			sSql = sSql.concat("	ifnull(date_format(returntime, '%h:%i:%s'),'') as returntimes "                );        
			sSql = sSql.concat("from tbl_attendance "                                                              );
			sSql = sSql.concat("where uid = " + iUid + " "                                                         );
			sSql = sSql.concat("and year = date_format(current_timestamp(), '%Y') "                                );
			sSql = sSql.concat("and month = date_format(current_timestamp(), '%m') "                               );
			sSql = sSql.concat("order by concat(year, '/', month, '/', day) "                                      );
			sSql = sSql.concat("; "                                                                                );
			System.out.println(sSql);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs != null){
				// 結果を取得
	            while(rs.next()){
	            	Tbl_PrintTable  tbl_printtable = new Tbl_PrintTable();
	            	tbl_printtable.id = rs.getInt("id");
	            	tbl_printtable.days = rs.getString("days");
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
			request.setAttribute("array_printtable", array_printtable);
			RequestDispatcher dispatch = request.getRequestDispatcher("update/Table.jsp");
	    	dispatch.forward(request, response);
	    	
    	}catch(Exception e){
    		System.out.println("認証失敗");
			// DB切断
    		MyQuery.closeDb();
    		// セッションを破棄
    		session.invalidate();
    		//　エラーメッセージをセットして認証画面に戻る
    		request.setAttribute("sErrMsg","SQL発行に失敗しました。　 update/ChangeTime.java");
    		request.setAttribute("sAddress", "");
    		request.setAttribute("sPassword", "");
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
