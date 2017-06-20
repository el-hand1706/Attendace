package auth;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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



/**
 * Servlet implementation class Auth
 */
@WebServlet("/auth_Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int TOKEN_LENGTH = 16;//16*2=32バイト

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = getCsrfToken(); // セキュリティトークンの取得
			// Input.jsp画面へ遷移
    	request.setAttribute("iFlag", 0);
			request.setAttribute("err_msg", "");
			equest.setAttribute("token", "token"); // hiddenにいれる
			// todo:セッションにいれる
    	RequestDispatcher dispatch = request.getRequestDispatcher("auth/Auth.jsp");
    	dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doGetで処理
		doGet(request, response);
	}


	public static String getCsrfToken() {
    byte token[] = new byte[TOKEN_LENGTH];
    StringBuffer buf = new StringBuffer();
    SecureRandom random = null;

    try {
      random = SecureRandom.getInstance("SHA1PRNG");
      random.nextBytes(token);

      for (int i = 0; i < token.length; i++) {
        buf.append(String.format("%02x", token[i]));
      }

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return buf.toString();
  }

}
