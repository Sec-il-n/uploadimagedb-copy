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

public class RegisterDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RegisterDelete() {
        	super();
    	}
	
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

			GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			DataSource dataSource = null;
			
			try {
				dataSource = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			boolean resultDelete = false;
			RegisterLogic logic=new RegisterLogic(dataSource);
			
			try {
				resultDelete = logic.delete(account);
			} catch (Exception e) {
				e.printStackTrace();
			}

			msg=logic.deleteMsg(resultDelete);
			to=logic.deleteRgisterPath(resultDelete);

		}else {
			RequestDispatcher dsp=request.getRequestDispatcher(to);
		    	dsp.forward(request, response);
		}
		
		session.setAttribute("deletemsg", msg);
		response.setHeader("Cache-Control", "no-cache");
		response.sendRedirect(to);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
