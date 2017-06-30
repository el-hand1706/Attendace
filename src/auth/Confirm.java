package auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utility.ChkInput;


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
    
    @Override
    public void init() throws ServletException{
    	System.out.println("confirm_init");
    }
    
    @Override
    public void destroy(){
    	System.out.println("confirm_destroy");
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
		int iChkFlag = 0;
		ChkInput cChkInput = new ChkInput();
		final int iMailFormat = 0;
		final int iAlphanumericFormat = 1;
		StringBuilder sNaErrMsg = new StringBuilder("");
		StringBuilder sAdErrMsg = new StringBuilder("");
		StringBuilder sPaErrMsg = new StringBuilder("");
		
		// 呼び出し元Jspからデータ受け取り
		String sName = request.getParameter("getName");
		String sAddress = request.getParameter("getAddress");
		String sPassword = request.getParameter("getPassword");
		String sToken = request.getParameter("getToken");
		
		// 入力チェック		
		iChkFlag += cChkInput.chkFormat(sAddress, iMailFormat, sAdErrMsg);
		iChkFlag += cChkInput.chkFormat(sPassword, iAlphanumericFormat, sPaErrMsg);
		iChkFlag += cChkInput.chkLength(sName, 2, 20, sNaErrMsg);
		iChkFlag += cChkInput.chkLength(sAddress, 2, 64, sAdErrMsg);
		iChkFlag += cChkInput.chkLength(sPassword, 4, 16, sPaErrMsg);
		
		// 入力した値が間違っていたらInput.jsp画面へ戻る
		if(iChkFlag != 0){
			// 呼び出し先Jspに渡すデータセット
			request.setAttribute("sName", sName);
			request.setAttribute("sAddress", sAddress);
			request.setAttribute("sPassword", sPassword);
			request.setAttribute("sErrMsg", "入力項目に間違いがあります。");
			request.setAttribute("sNaErrMsg", sNaErrMsg);
			request.setAttribute("sAdErrMsg", sAdErrMsg);
			request.setAttribute("sPaErrMsg", sPaErrMsg);
						
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Input.jsp");
			dispatch.forward(request, response);
		}else{
			// 呼び出し先Jspに渡すデータセット
			request.setAttribute("sName", sName);
			request.setAttribute("sAddress", sAddress);
			request.setAttribute("sPassword", sPassword);
			request.setAttribute("sToken", sToken);
			
			// Confirm.jsp　画面へ遷移
			RequestDispatcher dispatch = request.getRequestDispatcher("auth/Confirm.jsp");
			dispatch.forward(request, response);
		}
	}
	

	
}
