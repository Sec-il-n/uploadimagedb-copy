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
import model.logic.RegisterCheckLogic;
import model.logic.RegisterLogic;

/**
 * Servlet implementation class RegisterEdit
 */
public class RegisterEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterEdit() {
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
//		if(msg!=null){
		if(to!=null && msg!=null){
			request.setAttribute("msg", msg);
			RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);
		}
//		if(userId!=null && account!=null) {
		 else {
			String action=request.getParameter("action");
			if(action==null){
				to="/WEB-INF/jsp/registerEdit.jsp";

			}else if(action!=null){
				Account newAccount=(Account)session.getAttribute("newAccount");

			    GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			    DataSource dataSource = null;
				try {
					dataSource = gdlogic.getDataSource();
				} catch (NamingException e) {
					e.printStackTrace();
				}

				boolean resultEdit = false;

				if(action.equals("delete")){
					msg="登録を取り消しますか。";
				    to="/WEB-INF/jsp/deleteconf.jsp";

				}else if(action.equals("done")){
					try {
//						resultEdit = logic.update(account,newAccount,dataSource);
						RegisterLogic logic=new RegisterLogic(dataSource);
						resultEdit = logic.update(account,newAccount);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if(resultEdit) {
					    session.removeAttribute("account");
					    session.removeAttribute("newAccount");
					    msg="書き換え完了しました。(再ログインして下さい。)";
					    to="/WEB-INF/jsp/login.jsp";

				    }else{
				    	msg="書き換えに失敗しました。やり直して下さい。";//'d set primary @db
				    	to="/WEB-INF/jsp/registerEditNull.jsp";

				    }
				}
				request.setAttribute("msg", msg);
			}
		}
		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userIdSession=(String) session.getAttribute("userId");

		String to;
		LoginLogic llogic=new LoginLogic();
		to = llogic.sessionCheck(userIdSession);
		String msg = llogic.getMsg(to);
		if(msg!=null){
			request.setAttribute("msg", msg);
		}

		if(userIdSession!=null) {
			String userId=request.getParameter("userId");
			String pass=request.getParameter("pass");
			String name = request.getParameter("name");
			String a=request.getParameter("age");
			int age = 0;

		    RegisterCheckLogic logic=new RegisterCheckLogic();

			userId = logic.findBlank(userId);
	    	pass = logic.findBlank(pass);
	    	name = logic.findBlank(name);
	    	a = logic.findBlank(a);

	    	String normalized = logic.normalize(a);
	    	String replaced = logic.checkNumber(normalized);//開始0除く

	    	String checkUserId = null;
			String checkPass = null;
			String checkName = null;
			String checkAge = null;
			String checkNumber = null;

	    	try {
				checkUserId = logic.checkUserId(userId);
				checkPass = logic.checkPass(pass);
				checkName = logic.checkName(name);
				checkAge = logic.checkAge(replaced);//
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	if(replaced!=null && checkAge==null){

		    	try {
					age = Integer.parseInt(replaced);
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}catch (Exception e1) {
		    	e1.printStackTrace();
				}
	    	}
	    	checkNumber=logic.checkAge(age);//0/0-110

	    	if(checkUserId!=null || checkPass!=null || checkName!=null || checkAge!=null || checkNumber!=null) {

	    		Account newAccount=new Account(userId,pass,name,age);
		    	request.setAttribute("checkZero",checkNumber);
		    	request.setAttribute("newAccount",newAccount);
		    	request.setAttribute("checkUserId",checkUserId);
		    	request.setAttribute("checkPass",checkPass);
		    	request.setAttribute("checkName",checkName);
		    	request.setAttribute("checkAge",checkAge);

		    	to="/WEB-INF/jsp/registerEditNull.jsp";

			}else if(checkUserId==null && checkPass==null && checkName==null && checkAge==null && checkNumber==null) {
//
		    	Account newAccount=new Account(userId,pass,name,age);
				session.setAttribute("newAccount",newAccount);
				to="/WEB-INF/jsp/registerEditResult.jsp";

			}
	    	RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);
		}
	}

}
