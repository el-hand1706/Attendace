package input;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tbl.Tbl_PrintMenu;
import utility.MyQuery;

/**
 * Servlet implementation class SetTime
 */
@WebServlet("/input_SetTime")
public class SetTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetTime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPostで処理
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション開始
    	HttpSession session = request.getSession();
    	int iUid = (int) session.getAttribute("iUid");
    	
    	// 変数宣言
    	Tbl_PrintMenu tbl_printmenu = new Tbl_PrintMenu();		// DB取得用
    	String sSql = "";										// SQL文
    	String sSelect = new String();							// 出勤時間 or 退勤時間
    	ResultSet rs;
    	
    	try{
	    	// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
	    	
	    	// 出勤 or 退勤
	    	String sFlag = request.getParameter("sFlag");
	    	System.out.println(sFlag + iUid);
    		
    		if(sFlag.equals("1")){
    			sSelect = "cometime"; // 出勤時間をupdate
    		}else{
    			sSelect = "returntime";	// 退勤時間をupdate    			
    		}
    		
    		// 出勤時間 or 退勤時間をUPDATE
			sSql = "";
			sSql = sSql.concat("update tbl_attendance "																	);
			sSql = sSql.concat("   set    " + sSelect + " = current_timestamp(),  modified = current_timestamp() "		);
			sSql = sSql.concat(" where cast(created as date) = cast(CURRENT_TIMESTAMP() as date) "						);
			sSql = sSql.concat("   and uid = " + iUid 																	);
			sSql = sSql.concat("; "																						);
			// SQL実行
			if(MyQuery.executeSql(sSql) != 0){
				// Insertに失敗したときの処理
				throw new Exception();
			}
			
			// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select tbl_account.name as name, "                                                  );
			sSql = sSql.concat("       cast(tbl_attendance.cometime as char) as cometime, "                         );
			sSql = sSql.concat("       cast(tbl_attendance.returntime as char) as returntime "						);
			sSql = sSql.concat("  from tbl_account, tbl_attendance "		                                        );
			sSql = sSql.concat(" where tbl_account.uid = tbl_attendance.uid "						                );
			sSql = sSql.concat("   and tbl_account.uid = " + iUid + " "												);
			sSql = sSql.concat("   and cast(tbl_attendance.created as date) = cast(current_timestamp() as date) "	);
			sSql = sSql.concat("; "																				    );
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs.next() != false){
				// 
				rs.beforeFirst();
	            while(rs.next()){
	            	tbl_printmenu.name = rs.getString("name");
	            	tbl_printmenu.cometime = rs.getString("cometime");
	            	tbl_printmenu.returntime = rs.getString("returntime");
	            }
			}
			
			// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new Exception();
			};
    	}catch(Exception e){
    		// Auth.jsp　に戻る
    		System.out.println("認証失敗");
			request.setAttribute("sErrMsg", "err input_SetTime.java");
			request.setAttribute("sAddress", "");
			request.setAttribute("sPassword", "");
    		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
    		dispatch.forward(request, response);
    		// DB切断
			MyQuery.closeDb();
		}
    	// 現在日付を取得
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = sdf.format(cal.getTime());
        
    	// Menu.jsp にページ遷移
		HashMap<String, String> sPara = new HashMap<String,String>();
		sPara.put("iFlag","0");
		sPara.put("sName", tbl_printmenu.name);
		if(tbl_printmenu.cometime == null){
			sPara.put("sComeTime", "");
		}else{
			sPara.put("sComeTime", tbl_printmenu.cometime);
		}
		if(tbl_printmenu.returntime == null){
			sPara.put("sReturnTime", "");
		}else{
			sPara.put("sReturnTime", tbl_printmenu.returntime);
		}
		sPara.put("sCurrentDate", strDate);
		request.setAttribute("sPara", sPara);
		RequestDispatcher  dispatch = request.getRequestDispatcher("input/Menu.jsp");
		dispatch.forward(request, response);
	}

}
