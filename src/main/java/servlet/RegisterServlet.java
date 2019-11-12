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
import model.logic.RegisterCheckLogic;
import model.logic.RegisterLogic;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to =null;
		String msg=null;

		HttpSession session = request.getSession();

		String action=request.getParameter("action");

		if(action==null){
			to="/WEB-INF/jsp/register.jsp";

		}else if(action!=null && action.equals("done")){

			Account account=(Account)session.getAttribute("account");

		    GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		    DataSource dataSource = null;
//		    MysqlDataSource dataSource = null;
//		    try {
//				dataSource = gdlogic.getClearDBDatasource();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			try {
				dataSource = gdlogic.getDataSource();

			} catch (NamingException e) {
				e.printStackTrace();
			}

			RegisterLogic logic=new RegisterLogic(dataSource);
		    boolean result = logic.execute(account);

		    try {
				msg=logic.createMsg(result);
				to=logic.getPath(result);

		    } catch (Exception e) {
				e.printStackTrace();
			}
//
		}

		request.setAttribute("msg", msg);
		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = null;

		HttpSession session = request.getSession();

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
		String checkZero = null;

    	try {
			checkUserId = logic.checkUserId(userId);
			checkPass = logic.checkPass(pass);
			checkName = logic.checkName(name);
			checkAge = logic.checkAge(replaced);//
		} catch (Exception e) {
			e.printStackTrace();
		}
//    	if(replaced!=null){
    	if(replaced!=null && checkAge==null){
    		age=logic.returnNumber(replaced);

    	}
    	checkZero=logic.checkAge(age);

    	if(checkUserId!=null || checkPass!=null || checkName!=null || checkAge!=null || checkZero!=null) {
//	    if(checkUserId!=null || checkPass!=null || checkName!=null || checkAge!=null) {
//	    	if(replaced!=null && checkAge==null){
//		    	try {
//					age = Integer.parseInt(replaced);
//				} catch (NumberFormatException e1) {
//					e1.printStackTrace();
//				}catch (Exception e1) {
//		    	e1.printStackTrace();
//				}
//	    	}
//	    	checkZero=logic.checkAge(age);
	    	Account account=new Account(userId,pass,name,age);
	    	request.setAttribute("checkZero",checkZero);
	    	request.setAttribute("account",account);
	    	request.setAttribute("checkUserId",checkUserId);
	    	request.setAttribute("checkPass",checkPass);
	    	request.setAttribute("checkName",checkName);
	    	request.setAttribute("checkAge",checkAge);
//	    	to="/WEB-INF/jsp/registerNull.jsp";
	    	to=logic.havingErrormsg();

		}else if(checkUserId==null && checkPass==null && checkName==null && checkAge==null && checkZero==null) {
//	    }else if(checkUserId==null && checkPass==null && checkName==null && checkAge==null) {
//	    	userId = logic.findBlank(userId);
//	    	pass = logic.findBlank(pass);
//	    	name = logic.findBlank(name);
//	    	a = logic.findBlank(a);
//	    	String normalized = logic.normalize(a);
//	    	String replaced = logic.checkNumber(normalized);
//	    	try {
//				age = Integer.parseInt(replaced);
//			} catch (NumberFormatException e1) {
//				e1.printStackTrace();
//			}

//			if(replaced!=null && checkAge==null){
//		    	try {
//	    			age = Integer.parseInt(replaced);
//		    		checkZero=logic.checkAge(age);//NumberFormat
//
//				} catch (NumberFormatException e1) {
//					e1.printStackTrace();
//				}
//	    	}

	    	Account account=new Account(userId,pass,name,age);
			session.setAttribute("account",account);
//			to="/WEB-INF/jsp/registerResult.jsp";
			to=logic.notHavingErrormsg();
	    }

		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);
		doGet(request, response);

	}
}



