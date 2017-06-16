package auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// DB　Insert処理
		
		
		// Completed.jsp にページ遷移
		RequestDispatcher dispatch = request.getRequestDispatcher("auth/result.jsp");
		dispatch.forward(request, response);
	}

}
