package auth;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import utility.CipherManager;
import utility.MyQuery;

/**
 * Confirm.jspから値を受け取り、データベースへ書き込む
 * 書き込みが完了したらCompleted.jsp画面に遷移する。
 */

/**
 * Servlet implementation class Completed
 */
@WebServlet("/auth_Completed")
public class Completed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Completed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//doPostで処理
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
		String sSql = "";
		int iUid = 0;
		String sTitle = "";
		String sEncryption = "";
		String sChkToken = (String) session.getAttribute("sToken");
		ResultSet rs;
		
		// 呼び出し元jspから値を受け取る
		String sName = request.getParameter("getName");
		String sAddress = request.getParameter("getAddress");
		String sPassword = request.getParameter("getPassword");
		String sToken = request.getParameter("getToken");
		
		// パスワードを暗号化
		try {
			sEncryption = CipherManager.encrypt(sPassword);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		// セキュリティトークン確認
		if(sToken.equals(sChkToken)){
			// 一致した場合
			try{
				// DB接続
				if(MyQuery.connectDb() != 0){
					//DB接続に失敗したときの処理
					throw new SQLException();
				};
				
				// UIDの最大値を取得し今回登録するUIDを決定する
				sSql = "";
				sSql = sSql.concat("select uid "					    ); 
				sSql = sSql.concat("  from tbl_account "				);
				sSql = sSql.concat(" where "							);
				sSql = sSql.concat("   uid = (select max(uid) "	        );
				sSql = sSql.concat("            from tbl_account) "	    );
				sSql = sSql.concat("; "								    );
				rs = MyQuery.selectSql(sSql);
				if(rs != null){
					// 結果を取得
		            while(rs.next()){
		            	iUid = rs.getInt("uid");
		            }
		            // 次のUidをセット
		            iUid = iUid + 1;
				}else{
					// selectに失敗したときの処理
					throw new Exception();
				}
				
				// Tbl_Account にデータを挿入
				sSql = "";
				sSql = sSql.concat("insert into "																													); 
				sSql = sSql.concat("    tbl_account (uid, name, address, password, created, modified) "																);
				sSql = sSql.concat("values "																														);
				sSql = sSql.concat("    (" + iUid + ", \"" + sName + "\", \"" + sAddress + "\", \"" + sEncryption + "\", current_timestamp(), current_timestamp())"	);
				sSql = sSql.concat("; "																																);
				System.out.println(sSql);
				if(MyQuery.executeSql(sSql) != 0){
					// Insertに失敗したときの処理
					throw new SQLException();
				}
				
				// DB切断
				if(MyQuery.closeDb() != 0){
					//DB切断に失敗したときの処理
					throw new SQLException();
				};
				
				System.out.println("登録成功");
				sTitle = "登録成功";
				
			}catch(SQLException e){
				System.out.println("登録失敗");
				sTitle = "登録失敗";
			}catch(Exception e){
				
			}
			// Completed.jsp にページ遷移
			request.setAttribute("sTitle", sTitle);
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Completed.jsp");
			dispatch.forward(request, response);
		}else{
			// セキュリティートークンが一致しない場合
			request.setAttribute("sErrMsg", "セキュリティートークンが一致しない");
    		request.setAttribute("sAddress", "");
    		request.setAttribute("sPassword", "");	
	    	RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
	    	dispatch.forward(request, response);	
		}
	}
}
