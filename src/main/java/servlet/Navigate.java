package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.logic.GetPathNavLogic;

@WebServlet("/Navigate")
public class Navigate extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public Navigate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		GetPathNavLogic logic=new GetPathNavLogic();
		String path=logic.getNavPath(action);

		RequestDispatcher dsp=request.getRequestDispatcher(path);
	    	dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
