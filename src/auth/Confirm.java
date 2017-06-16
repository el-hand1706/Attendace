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
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードセット
		request.setCharacterEncoding("UTF8");
		
		// 呼び出し元Jspからデータ受け取り
		String sAddress = request.getParameter("getAddress");
		String sPassword = request.getParameter("getPassword");
		
		// 呼び出し先Jspに渡すデータセット
		request.setAttribute("sAddress", sAddress);
		request.setAttribute("sPassword", sPassword);
		
		// 入力チェック

		// Confirm.jsp　画面へ遷移
		RequestDispatcher dispatch = request.getRequestDispatcher("auth/Confirm.jsp");
		dispatch.forward(request, response);
	}

}
