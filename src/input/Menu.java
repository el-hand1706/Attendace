package input;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import tbl.Tbl_Account;
import tbl.Tbl_Attendance;
import utility.CipherManager;
//import utility.Encryption;
import utility.MyQuery;


/**
 * Servlet implementation class Menu
 */
@WebServlet("/input_Menu")
public class Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Menu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPostで処理
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 文字コードセット
    	request.setCharacterEncoding("UTF8");
    	
    	// セッション開始
    	HttpSession session = request.getSession();

    	// 変数宣言
    	String sAddress = request.getParameter("getAddress");
    	String sPassword = request.getParameter("getPassword");
    	String sSql = "";
    	Tbl_Account tbl_account = new Tbl_Account();
    	Tbl_Attendance tbl_attendance = new Tbl_Attendance();
    	Tbl_Attendance getNewId = new Tbl_Attendance();
    	
    	String sEncryption = "";
    	
    	try {
			sEncryption = CipherManager.encrypt(sPassword);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

    	try{
			// DB接続
			if(MyQuery.connectDb() != 0){
				//DB接続に失敗したときの処理
				throw new SQLException();
			};

			// 入力したアカウントが存在するか確認
			sSql = "";
			sSql = sSql.concat("select * "									);
			sSql = sSql.concat("from tbl_account "							);
			sSql = sSql.concat("where address = \"" + sAddress + "\" "		);
			sSql = sSql.concat("and   password = \"" + sEncryption + "\" "	);
			sSql = sSql.concat("; "											);
			// SQL実行
			tbl_account = MyQuery.selectTbl_Account(sSql);

			if(tbl_account.iGetFlag != 1){
				// UID取得に失敗したときの処理
				throw new SQLException();
			}else{
				
				// 勤怠テーブルから対象の出勤・退勤時間を取得
				sSql = "";
				sSql = sSql.concat("select id, uid, cast(cometime as char) as cometime, cast(returntime as char) as returntime "									);
				sSql = sSql.concat("from tbl_attendance "							);
				sSql = sSql.concat("where cast(created as date) = cast(CURRENT_TIMESTAMP() as date) "		);
				sSql = sSql.concat(" and uid = " + tbl_account.uid 	);
				sSql = sSql.concat("; "											);
				// SQL実行
				tbl_attendance = MyQuery.selectTbl_Attendance(sSql);
				
				// 当日データがない場合は作成する
				if(tbl_attendance.iGetFlag != 1){
					// IDの最大値を取得し今回登録するIDを決定する
					int iId = 0;
					
					sSql = "";
					sSql = sSql.concat("select id, uid, cast(cometime as char) as cometime, cast(returntime as char) as returntime "						); 
					sSql = sSql.concat("from tbl_attendance "				);
					sSql = sSql.concat("where "							);
					sSql = sSql.concat("    id = (select max(id) "	);
					sSql = sSql.concat("          from tbl_attendance) "	);
					sSql = sSql.concat("; "								);
					// SQL実行
					getNewId = MyQuery.selectTbl_Attendance(sSql);
					if(tbl_account.iGetFlag != 1){
						// UID取得に失敗したときの処理
						throw new SQLException();
					}else{
						iId = getNewId.id + 1;
					}
					
					// Tbl_Attendance にデータを挿入
					sSql = "";
					sSql = sSql.concat("insert into "																													); 
					sSql = sSql.concat("    tbl_attendance (id, uid, created, modified) "																);
					sSql = sSql.concat("values "																														);
					sSql = sSql.concat("    (" + iId + ", " + tbl_account.uid + " , current_timestamp(), current_timestamp())"	);
					sSql = sSql.concat("; "																																);
					System.out.println(sSql);
					if(MyQuery.executeSql(sSql) != 0){
						// Insertに失敗したときの処理
						throw new SQLException();
					}
				}
				
				// 現在日付を取得
				Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		        String strDate = sdf.format(cal.getTime());
		        
				// Menu.jsp にページ遷移
				HashMap<String, String> sPara = new HashMap<String,String>();
				sPara.put("iFlag","0");
				sPara.put("sName", tbl_account.name);
				if(tbl_attendance.cometime == null){
					sPara.put("sComeTime", "");
				}else{
					sPara.put("sComeTime", tbl_attendance.cometime);
				}
				if(tbl_attendance.returntime == null){
					sPara.put("sReturnTime", "");
				}else{
					sPara.put("sReturnTime", tbl_attendance.returntime);
				}
				sPara.put("sCurrentDate", strDate);
				request.setAttribute("sPara", sPara);
	    		RequestDispatcher dispatch = request.getRequestDispatcher("input/Menu.jsp");
	    		dispatch.forward(request, response);
			}

			// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new SQLException();
			};
			
			// セッションに値をセット
			session.setAttribute("sChkPass", sEncryption);
			session.setAttribute("iUid", tbl_account.uid);

		}catch(SQLException e){
			System.out.println("認証失敗");
			// Auth.jsp　に戻る
			request.setAttribute("sErrMsg", "SQL発行に失敗しました");
    		request.setAttribute("sAddress", sAddress);
    		request.setAttribute("sPassword", sPassword);
    		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
    		dispatch.forward(request, response);
    		// DB切断
    		MyQuery.closeDb();
		}catch(Exception e){

		}
	}
}
