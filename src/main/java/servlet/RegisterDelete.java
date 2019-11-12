package servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.beans.Account;
import model.logic.GetDataSourceLogic;
import model.logic.LoginLogic;
import model.logic.RegisterLogic;

/**
 * Servlet implementation class RegisterDelete
 */
public class RegisterDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterDelete() {
        super();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId=(String) session.getAttribute("userId");
		Account account=(Account) session.getAttribute("account");

		LoginLogic llogic=new LoginLogic();
		String to = llogic.sessionCheck(userId);
		String msg = llogic.getMsg(to);
		if(msg!=null){
			request.setAttribute("msg", msg);
		}

		if(userId!=null && account!=null) {

//			RegisterLogic logic=new RegisterLogic(dataSource);

		    GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		    DataSource dataSource = null;
			try {
				dataSource = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			boolean resultDelete = false;
			try {
//				resultDelete = logic.delete(account,dataSource);
				RegisterLogic logic=new RegisterLogic(dataSource);
				resultDelete = logic.delete(account);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(resultDelete) {
//				session.invalidate();
				session.removeAttribute("userId");
				session.removeAttribute("account");
				msg="登録を取り消しました。";
			    to="/upload_image_db4";

			}else {
				msg="書き換えに失敗しました。やり直して下さい。";//'d set primary @db
		    	to="/upload_image_db4/ToLoginResult";
			}

		}else {
			RequestDispatcher dsp=request.getRequestDispatcher(to);
		    dsp.forward(request, response);
		}
		session.setAttribute("msg", msg);
		response.setHeader("Cache-Control", "no-cache");
		response.sendRedirect(to);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
