package update;

import java.io.IOException;
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
import utility.GetData;
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
    	ArrayList<Tbl_PrintTable> array_printtable = new ArrayList<Tbl_PrintTable>();
//    	HashMap<String,Integer> hmDate = new HashMap<String,Integer>();
    	
    	
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
			
			// table表示するデータを取得
			array_printtable = GetData.getTbl_PrintTable(iUid);
			
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
