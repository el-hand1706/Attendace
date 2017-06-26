package update;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tbl.Tbl_PrintTable;
import utility.ChkInput;
import utility.GetData;
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
    	int iYear = Integer.parseInt(request.getParameter("year"));
    	int iMonth = Integer.parseInt(request.getParameter("month"));
    	int iDay = Integer.parseInt(request.getParameter("day"));
    	String cometime = request.getParameter("cometime");
    	String returntime = request.getParameter("returntime");
    	
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
    		
			if(ChkInput.isTimeType(cometime) == true || ChkInput.isTimeType(returntime) == true){
				// 入力チェックで問題なければ
				// update
	    		sSql = "";
	    		sSql = sSql.concat("update tbl_attendance "                                                                   );
	    		sSql = sSql.concat("   set cometime = concat(" + iYear + ", '-', " + iMonth + ", '-', " + iDay + ", ' ', '" + cometime + "'), "      ); 
	    		sSql = sSql.concat("	   returntime = concat(" + iYear + ", '-', " + iMonth + ", '-', " + iDay + ", ' ', '" + returntime + "'), "      ); 
	    		sSql = sSql.concat("	   modified = current_timestamp() "                                                   ); 
	    		sSql = sSql.concat(" where id = " + id + " "                                                                  ); 
	    		sSql = sSql.concat("; "                                                                                       );
	    		System.out.println(sSql);
	    		if(MyQuery.executeSql(sSql) != 0){
	    			throw new Exception();
	    		}
				
				// table表示するデータを取得
				array_printtable = GetData.getTbl_PrintTable(iUid);
				
				// jspに渡す値をセット
				request.setAttribute("year", iYear);
				request.setAttribute("month", iMonth);
				request.setAttribute("array_printtable", array_printtable);
				RequestDispatcher dispatch = request.getRequestDispatcher("update/Table.jsp");
		    	dispatch.forward(request, response);
			}else{
				// jspに渡す値をセット
				request.setAttribute("id", String.valueOf(id));
				request.setAttribute("year", iYear);
				request.setAttribute("month", iMonth);
				request.setAttribute("day", iDay);
				request.setAttribute("cometime", cometime);
				request.setAttribute("returntime", returntime);
				request.setAttribute("errmsg", "時間形式で入力してください(HH:MM)");
		    	RequestDispatcher dispatch = request.getRequestDispatcher("update/Change.jsp");
				dispatch.forward(request, response);
			}
    		// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new Exception();
			};
			
			
	    	
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
