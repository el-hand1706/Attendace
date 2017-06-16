package auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Input.jspで入力した値を取得し、入力チェックを行う
 * 入力値に問題がなければConfirm.jsp画面に表示する
 * 問題があればエラーメッセージを表示しInput.jsp画面に戻る
 */


/**
 * Servlet implementation class Confirm
 */
@WebServlet("/auth_Confirm")
public class Confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirm() {
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
		//変数宣言
		boolean chkFlag = false;
		String mailFormat = 
				("^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+" + 
				"\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+" + 
				"(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$");
		
		// 文字コードセット
		request.setCharacterEncoding("UTF8");
		
		// 呼び出し元Jspからデータ受け取り
		String sAddress = request.getParameter("getAddress");
		String sPassword = request.getParameter("getPassword");
		
		// 入力チェック
		// formatチェック
		chkFlag = chkFormat(sAddress, mailFormat);
		// lengthチェック
		chkFlag = chkLength(sAddress, 0, 64);
		chkFlag = chkLength(sPassword, 4, 16);
		
		
		// 入力した値が間違っていたらInput.jsp画面へ戻る
		if(chkFlag == false){
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Input.jsp");
			dispatch.forward(request, response);
		}else{
			// 呼び出し先Jspに渡すデータセット
			request.setAttribute("sAddress", sAddress);
			request.setAttribute("sPassword", sPassword);
			
			// Confirm.jsp　画面へ遷移
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Confirm.jsp");
			dispatch.forward(request, response);
		}
	}
	
	/**
	 * 文字列がフォーマット通りになっているかチェックする
	 * @param sStr			チェックする文字列
	 * @param chkFormat		フォーマット文字列
	 * @return boolean		問題なければtrueを返す
	 */
	boolean chkFormat(String sStr, String sFormat){
		if( sStr.matches(sFormat) ) {
            return true;
        }
        else {
            return false;
        }
	}
	
	/**
	 * 文字列の長さをチェック
	 * @param sStr  		チェックする文字列
	 * @param iMinLength 	文字列の最小長さ
	 * @param iMaxLength 	文字列の最大長さ
	 * @return boolean		問題なければtrueを返す
	 */
	boolean chkLength(String sStr, int iMinLength, int iMaxLength){
		if(sStr.length() >= iMaxLength && sStr.length() >= iMinLength) {
            return true;
        }
        else {
            return false;
        }
	}
	
}
