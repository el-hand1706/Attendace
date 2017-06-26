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
    	int igetId =  Integer.parseInt(request.getParameter("id"));
    	int isetId = 0;
    	int iUid = (int)session.getAttribute("iUid");
    	int iYear = Integer.parseInt(request.getParameter("year"));
		int iMonth = Integer.parseInt(request.getParameter("month"));
		int iDay = Integer.parseInt(request.getParameter("day"));
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
			
			if(igetId == -1){
				// 当日データがない場合は作成する
				// IDの最大値を取得し今回登録するIDを決定する
				
				sSql = "";
				sSql = sSql.concat("select id  "                                    );
				sSql = sSql.concat("  from tbl_attendance "				            );
				sSql = sSql.concat(" where id = (select max(id) "	                );
				sSql = sSql.concat("               from tbl_attendance) "	        );
				sSql = sSql.concat("; "								                );
				// SQL実行
				rs = MyQuery.selectSql(sSql);
				if(rs.next() != false){
					rs.beforeFirst();
					// 結果を取得
		            while(rs.next()){
		            	isetId = rs.getInt("id");
		            }
		            isetId = isetId + 1;
				}
				
				// Tbl_Attendance にデータを挿入
				sSql = "";
				sSql = sSql.concat("insert into "									);
				sSql = sSql.concat("tbl_attendance (id, "                           );
				sSql = sSql.concat("				uid, "                          );
				sSql = sSql.concat("				year, "                         );
				sSql = sSql.concat("				month, "                        );
				sSql = sSql.concat("				day, "                          );
				sSql = sSql.concat("				created, "                      );
				sSql = sSql.concat("				modified, "						);
				sSql = sSql.concat("				delflag ) "						);
				sSql = sSql.concat("values "										);
				sSql = sSql.concat("    (" + isetId + ", "                          );
				sSql = sSql.concat("     " + iUid + " , "                           );
				sSql = sSql.concat("     " + iYear + ", "                           );
				sSql = sSql.concat("     lpad(" + iMonth + ", 2, '0'), "            );
				sSql = sSql.concat("     lpad(" + iDay   + ", 2, '0'), "            );
				sSql = sSql.concat("     current_timestamp(), "                     );
				sSql = sSql.concat("     current_timestamp(), "	                    );
				sSql = sSql.concat("	 0 ) "						);
				sSql = sSql.concat("; "												);
				System.out.println(sSql);
				if(MyQuery.executeSql(sSql) != 0){
					// Insertに失敗したときの処理
					throw new Exception();
				}
			}else{
				isetId = igetId;
			}
			
			// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select id, "                                                                      ); 
			sSql = sSql.concat("       ifnull(date_format(cometime, '%H:%i:%s'),'') as cometimes, "               ); 
			sSql = sSql.concat("       ifnull(date_format(returntime, '%H:%i:%s'),'') as returntimes "            );                 
			sSql = sSql.concat("from tbl_attendance "                                                             );
			sSql = sSql.concat("where id = " + isetId + " "                                                       );
			sSql = sSql.concat("; "                                                                               );
			System.out.println(sSql);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs.next() != false){
				// 
				rs.beforeFirst();
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
			request.setAttribute("year", iYear);
			request.setAttribute("month", iMonth);
			request.setAttribute("day", iDay);
			request.setAttribute("cometime", sPara.get("cometime"));
	    	request.setAttribute("returntime", sPara.get("returntime"));
	    	request.setAttribute("errmsg", " ");
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
