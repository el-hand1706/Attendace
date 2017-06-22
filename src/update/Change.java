package update;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.MyQuery;

/**
 * Servlet implementation class Change
 */
@WebServlet("/update_Change")
public class Change extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Change() {
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
    	int iId =  Integer.parseInt(request.getParameter("id"));
    	HashMap<String,String> sPara = new HashMap<String,String>();
    	ResultSet rs;
    	
    	
    	
    	// 変数宣言
    	String sSql = "";
    	
    	try{
    		// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new Exception();
			};
			
			// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select id, "                                                                      ); 
			sSql = sSql.concat("       ifnull(date_format(cometime, '%h:%i:%s'),'') as cometimes, "               ); 
			sSql = sSql.concat("       ifnull(date_format(returntime, '%h:%i:%s'),'') as returntimes "            );                 
			sSql = sSql.concat("from tbl_attendance "                                                             );
			sSql = sSql.concat("where id = " + iId + " "                                                          );
			sSql = sSql.concat("; "                                                                               );
			System.out.println(sSql);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs != null){
				// 結果を取得
				while(rs.next()){
					sPara.put("id", rs.getString("id"));
					sPara.put("cometime", rs.getString("cometimes"));
					sPara.put("returntime", rs.getString("returntimes"));
				}
			}else{
				// selectに失敗したときの処理
				throw new Exception();
			}
			
			if(MyQuery.closeDb() != 0){
				// DB切断に失敗したときの処理
				throw new Exception();
			}
			
			// 値をセット
			request.setAttribute("id", sPara.get("id"));
			request.setAttribute("cometime", sPara.get("cometime"));
	    	request.setAttribute("returntime", sPara.get("returntime"));
			RequestDispatcher dispatch = request.getRequestDispatcher("update/Change.jsp");
			dispatch.forward(request, response);
    		
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
