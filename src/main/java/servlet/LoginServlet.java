package servlet;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import model.beans.Account;
import model.logic.GetDataSourceLogic;
import model.logic.LoginLogic;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path="/WEB-INF/jsp/login.jsp";
		RequestDispatcher dsp=request.getRequestDispatcher(path);
	    	dsp.forward(request, response);

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=null;
		String userId;
		String pass;
		String msg=null;

		userId=request.getParameter("userId");
		pass=request.getParameter("pass");
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
	    	DataSource dataSource = null;
		
		try {
			dataSource = gdlogic.getDataSource();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		Account account =new Account(userId,pass);
		LoginLogic logic =new LoginLogic(dataSource);
		Account accountReturned = null;
		
		try {
			accountReturned = logic.execute(account);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(accountReturned!=null){
			HttpSession session=request.getSession();
			session.setAttribute("userId",userId);
			session.setAttribute("account",accountReturned);
			session.setMaxInactiveInterval(900);
			path=logic.acNotNull();

		}else if(accountReturned==null){
			msg="ユーザーIdかパスワードが違います。";
			request.setAttribute("msg", msg);
			path=logic.acNull();
		}

		RequestDispatcher dsp=request.getRequestDispatcher(path);
	    	dsp.forward(request, response);
		
		doGet(request, response);
	}

}
