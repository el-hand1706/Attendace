package input;

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
import javax.servlet.http.HttpSession;

import auth.Tbl_Account;
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
				// Menu.jsp にページ遷移
	    		RequestDispatcher dispatch = request.getRequestDispatcher("input/Menu.jsp");
	    		dispatch.forward(request, response);
			}

			// DB切断
			if(MyQuery.closeDb() != 0){
				//DB切断に失敗したときの処理
				throw new SQLException();
			};
			
			// セッションにパスワードをセット
			session.setAttribute("sChkPass", sEncryption);

		}catch(SQLException e){
			System.out.println("認証失敗");
			// Auth.jsp　に戻る
			request.setAttribute("sErrMsg", "SQL発行に失敗しました");
    		request.setAttribute("sAddress", sAddress);
    		request.setAttribute("sPassword", sPassword);
    		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
    		dispatch.forward(request, response);
		}catch(Exception e){

		}
	}
}
