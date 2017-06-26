package input;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
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

import tbl.Tbl_Attendance;
import utility.CipherManager;

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
    	Tbl_Attendance tbl_attendance = new Tbl_Attendance();
    	ResultSet rs;
    	int iUid = 0;
    	String sName = new String();
    	
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
				throw new Exception();
			};

			// 入力したアカウントが存在するか確認
			sSql = "";
			sSql = sSql.concat("select uid, "						        );
			sSql = sSql.concat("       name "				    		    );
			sSql = sSql.concat("  from tbl_account "					    );
			sSql = sSql.concat(" where address = \"" + sAddress + "\" "		);
			sSql = sSql.concat("   and password = \"" + sEncryption + "\" "	);
			sSql = sSql.concat("; "											);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs.next() != false){
				rs.beforeFirst();
				// 結果を取得
	            while(rs.next()){
	            	iUid = rs.getInt("uid");
	            	sName = rs.getString("name");
	            }

				// 勤怠テーブルから対象の出勤・退勤時間を取得
	            sSql = "";                                                                               
				sSql = sSql.concat("select id, "                                                         );
				sSql = sSql.concat("       uid, "                                                        );
				sSql = sSql.concat("       cast(cometime as char) as cometime, "                         );
				sSql = sSql.concat("       cast(returntime as char) as returntime "						 );
				sSql = sSql.concat("  from tbl_attendance "							                     );
				sSql = sSql.concat(" where year  = date_format(current_timestamp(), '%Y') "	             );
				sSql = sSql.concat("   and month = date_format(current_timestamp(), '%m') "              );
				sSql = sSql.concat("   and day   = date_format(current_timestamp(), '%d') "              );
				sSql = sSql.concat("   and uid = " + iUid 	                                             );
				sSql = sSql.concat("   and delflag = 0 "	                                             );
				sSql = sSql.concat("; "											                         );
				// SQL実行
				rs = MyQuery.selectSql(sSql);
				
				if(rs.next() != false){
					// 
					rs.beforeFirst();
		            while(rs.next()){
		            	tbl_attendance.id = rs.getInt("id");
		            	tbl_attendance.uid = rs.getInt("uid");
		            	tbl_attendance.cometime = rs.getString("cometime");
		            	tbl_attendance.returntime = rs.getString("returntime");
		            }

				}else{
					// 当日データがない場合は作成する
					// IDの最大値を取得し今回登録するIDを決定する
					int iId = 0;
					
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
			            	iId = rs.getInt("id");
			            }
			            iId = iId + 1;
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
					sSql = sSql.concat("				modified, "		    			);
					sSql = sSql.concat("				delflag) "						);
					sSql = sSql.concat("values "										);
					sSql = sSql.concat("    (" + iId + ", "                             );
					sSql = sSql.concat("     " + iUid + " , "                );
					sSql = sSql.concat("     date_format(current_timestamp(), '%Y'), "  );
					sSql = sSql.concat("     date_format(current_timestamp(), '%m'), "  );
					sSql = sSql.concat("     date_format(current_timestamp(), '%d'), "  );
					sSql = sSql.concat("     current_timestamp(), "                     );
					sSql = sSql.concat("     current_timestamp(), "	                    );
					sSql = sSql.concat("	 0 ) "						                );
					sSql = sSql.concat("; "												);
					System.out.println(sSql);
					if(MyQuery.executeSql(sSql) != 0){
						// Insertに失敗したときの処理
						throw new Exception();
					}
				}
				
				// 現在日付を取得
				Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		        String strDate = sdf.format(cal.getTime());
		        
				// Menu.jsp にページ遷移
				HashMap<String, String> sPara = new HashMap<String,String>();
				sPara.put("iFlag","0");
				sPara.put("sName", sName);
				if(tbl_attendance.cometime == null){
					sPara.put("sComeTime", "----/--/-- --:--:--");
				}else{
					sPara.put("sComeTime", tbl_attendance.cometime);
				}
				if(tbl_attendance.returntime == null){
					sPara.put("sReturnTime", "----/--/-- --:--:--");
				}else{
					sPara.put("sReturnTime", tbl_attendance.returntime);
				}
				sPara.put("sCurrentDate", strDate);
				request.setAttribute("sPara", sPara);
	    		RequestDispatcher dispatch = request.getRequestDispatcher("input/Menu.jsp");
	    		dispatch.forward(request, response);
			}else{
				// selectに失敗したときの処理
				throw new Exception();
			}

			// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new Exception();
			};
			
			// セッションに値をセット
			session.setAttribute("sChkPass", sEncryption);
			session.setAttribute("iUid", iUid);

		}catch(Exception e){
			System.out.println("認証失敗");
			// DB切断
    		MyQuery.closeDb();
    		// セッションを破棄
    		session.invalidate();
    		//　エラーメッセージをセットして認証画面に戻る
    		request.setAttribute("sErrMsg","SQL発行に失敗しました。　 input/Menu.java");
    		request.setAttribute("sAddress",sAddress);
    		request.setAttribute("sPassword",sPassword);
    		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
			dispatch.forward(request,response);
		}
	}
}
