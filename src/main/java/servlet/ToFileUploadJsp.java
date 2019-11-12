package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ToFileUploadJsp")
public class ToFileUploadJsp extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ToFileUploadJsp() {
        super();
    }

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path;
    	HttpSession session = request.getSession();
		String userId=(String) session.getAttribute("userId");
		if(userId==null) {
			String msg="ログインし直して下さい。";
			request.setAttribute("msg", msg);
			path="/WEB-INF/jsp/login.jsp";

		 }else {
			path="/WEB-INF/jsp/fileUpload.jsp";
		 }

		RequestDispatcher dsp=request.getRequestDispatcher(path);
		dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
