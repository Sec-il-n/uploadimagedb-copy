package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Account;

@WebServlet("/ToLoginResult")
public class ToLoginResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ToLoginResult() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String to=null;
		String msg=null;

		HttpSession session=request.getSession();
		Account account=(Account) session.getAttribute("account");
		msg=(String) session.getAttribute("errormsg");
		if(msg==null){
			msg=(String) session.getAttribute("msg");
		}

		if(account==null){
			session.removeAttribute("userId");
			msg="ログインし直して下さい。";
			to="/WEB-INF/jsp/login.jsp";
		}else {
			to="/WEB-INF/jsp/loginResult.jsp";
		}


		request.setAttribute("msg",msg);
		session.removeAttribute("msg");

		//if()でfrom  UploadComp、rewrite //(消すもの分ける)?
		session.removeAttribute("title");
		session.removeAttribute("text");
		session.removeAttribute("filename");
		session.removeAttribute("base64");//
		//
		//showImageから
		session.removeAttribute("mutterList");//
		//EditImageから
		session.removeAttribute("deleteFileName");//
		session.removeAttribute("deleteFilePath");//
		session.removeAttribute("time");//
		//ShowImagesから
		session.removeAttribute("total");//
		session.removeAttribute("totalPage");//
		//Deleteから
		session.removeAttribute("newFileName");//
		session.removeAttribute("errormsg");

		RequestDispatcher dsp=request.getRequestDispatcher(to);
	    dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
