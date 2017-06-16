package pHelloWorld;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class TestP
 */
@WebServlet("/TestP")
public class TestP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestP() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 呼び出し元Jspからデータ受け取り
		request.setCharacterEncoding("UTF8");
		String jsp = request.getParameter("fromJsp");

		// 呼び出し先Jspに渡すデータセット
		request.setAttribute("fromServlet", jsp + " + サーブレットで追加");

		// result.jsp にページ遷移
		RequestDispatcher dispatch = request.getRequestDispatcher("result.jsp");
		dispatch.forward(request, response);
	}

}
