package auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility.CipherManager;

/**
 * Input.jsp画面に遷移するだけ
 */


/**
 * Servlet implementation class Input
 */
@WebServlet("/auth_Input")
public class Input extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Input() {
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
    			
    	// セキュリティトークン作成
    	String sToken = CipherManager.getCsrfToken(); 	// セキュリティトークンの取得
    	session.setAttribute("token", sToken);			// セッション
    	
    	// Input.jsp画面へ遷移
    	request.setAttribute("sErrMsg", "");
    	request.setAttribute("sToken", sToken);
    	RequestDispatcher dispatch = request.getRequestDispatcher("auth/Input.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// doGetで処理
    	doGet(request,response);
	}

}
