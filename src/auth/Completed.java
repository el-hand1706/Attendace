package auth;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

import utility.CipherManager;
//import utility.Encryption;
import utility.MyQuery;
import auth.Tbl_Account;

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
		
		// 変数宣言
		StringBuilder sNaErrMsg = new StringBuilder("");
		StringBuilder sAdErrMsg = new StringBuilder("");
		StringBuilder sPaErrMsg = new StringBuilder("");
		String sSql = "";
		Tbl_Account tbl_account = new Tbl_Account();
		int iUid = 0;
		String sTitle = "";
		String sEncryption = "";
		
		// 呼び出し元jspから値を受け取る
		String sName = request.getParameter("getName");
		String sAddress = request.getParameter("getAddress");
		String sPassword = request.getParameter("getPassword");
		
		// パスワードを暗号化
//		sEncryption = Encryption.doEncryption(sPassword);
		try {
			sEncryption = CipherManager.encrypt(sPassword);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		// 確認画面の実行ボタンor戻るボタン
		if(request.getParameter("do") != null){
			
			try{
				// DB接続
				if(MyQuery.connectDb() != 0){
					//DB接続に失敗したときの処理
					throw new SQLException();
				};
				
				// UIDの最大値を取得し今回登録するUIDを決定する
				sSql = "";
				sSql = sSql.concat("select * "						); 
				sSql = sSql.concat("from tbl_account "				);
				sSql = sSql.concat("where "							);
				sSql = sSql.concat("    uid = (select max(uid) "	);
				sSql = sSql.concat("          from tbl_account) "	);
				sSql = sSql.concat("; "								);
				// SQL実行
				tbl_account = MyQuery.selectTbl_Account(sSql);
//				if(tbl_account == null){
				if(tbl_account.iGetFlag != 1){
					// UID取得に失敗したときの処理
					throw new SQLException();
				}else{
					iUid = tbl_account.uid + 1;
				}
				
				// Tbl_Account にデータを挿入
				sSql = "";
				sSql = sSql.concat("insert into "																								); 
				sSql = sSql.concat("    tbl_account (uid, name, address, password, created) "													);
				sSql = sSql.concat("values "																									);
				sSql = sSql.concat("    (" + iUid + ", \"" + sName + "\", \"" + sAddress + "\", \"" + sEncryption + "\", current_timestamp())"	);
				sSql = sSql.concat("; "																											);
				if(MyQuery.insertTbl_Account(sSql) != 0){
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
		}else if(request.getParameter("back") != null){
			// 値をセットしてInput.jsp に戻る
			request.setAttribute("iFlag", 1);
			request.setAttribute("sName", sName);
			request.setAttribute("sAddress", sAddress);
			request.setAttribute("sPassword", sPassword);
			request.setAttribute("sNaErrMsg", sNaErrMsg);
			request.setAttribute("sAdErrMsg", sAdErrMsg);
			request.setAttribute("sPaErrMsg", sPaErrMsg);
			
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Input.jsp");
			dispatch.forward(request,response);
		}else{
			// Completed.jsp にページ遷移
						RequestDispatcher dispatch = request.getRequestDispatcher("auth/Completed.jsp");
						dispatch.forward(request, response);
		}
		
	}

}
