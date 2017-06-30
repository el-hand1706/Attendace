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
import utility.GetData;
import utility.MyQuery;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/update_Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを開始
    	HttpSession session = request.getSession();
    	
    	// 値を取得
    	int igetId =  Integer.parseInt(request.getParameter("id"));
    	int iUid = (int)session.getAttribute("iUid");
    	int iYear = Integer.parseInt(request.getParameter("year"));
		int iMonth = Integer.parseInt(request.getParameter("month"));
    	ArrayList<Tbl_PrintTable> array_printtable = new ArrayList<Tbl_PrintTable>();
    	
    	// 変数宣言
    	String sSql = "";
    	
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
				
			// Tbl_Attendance にデータを挿入
			sSql = "";
			sSql = sSql.concat("update tbl_attendance "							);
			sSql = sSql.concat("   set delflag = 1 "                            );
			sSql = sSql.concat(" where id = " + igetId                          );
			System.out.println(sSql);
			if(MyQuery.executeSql(sSql) != 0){
				// Insertに失敗したときの処理
				throw new Exception();
			}
			
			// table表示するデータを取得
			array_printtable = GetData.getTbl_PrintTable(iUid);
			
			// DB切断
			if(MyQuery.closeDb() != 0){
				// DB切断に失敗したときの処理
				throw new Exception();
			}
			
			// jspに渡す値をセット
//			hmDate.put("year", cal.get(Calendar.YEAR));
//			hmDate.put("month", cal.get(Calendar.MONTH) + 1);
			request.setAttribute("year", iYear);
			request.setAttribute("month", iMonth);
			request.setAttribute("array_printtable", array_printtable);
			RequestDispatcher dispatch = request.getRequestDispatcher("update/Table.jsp");
			dispatch.forward(request,response);
			
			System.out.println("aaa");
			
    	}catch(Exception e){
    		System.out.println("認証失敗");
    		// DB切断
    		MyQuery.closeDb();
    		// セッションを破棄
    		session.invalidate();
    		//　エラーメッセージをセットして認証画面に戻る
    		request.setAttribute("sErrMsg","SQL発行に失敗しました。 update/Delete.java");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
