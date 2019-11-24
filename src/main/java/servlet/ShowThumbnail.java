package servlet;

import java.io.IOException;
import java.nio.file.Path;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.logic.CheckDirectoryContentsLogic;
import model.logic.LoginLogic;

@WebServlet("/ShowThumbnail")
public class ShowThumbnail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ShowThumbnail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String to = null;
		Path tmpPath=(Path) session.getAttribute("tmpPath");
		String userId=(String) session.getAttribute("userId");
		
		LoginLogic llogic=new LoginLogic();
		to = llogic.sessionCheck(userId);
		String msg = llogic.getMsg(to);

		if(to==null && msg==null){
			CheckDirectoryContentsLogic logic=new CheckDirectoryContentsLogic();
			boolean exist=logic.checkDirectry(tmpPath);
			
			if(!exist){
				to="/WEB-INF/jsp/fileUpload.jsp";
				msg="入力し直して下さい。";
				request.setAttribute("msg", msg);
			}else{
				to="/WEB-INF/jsp/thumbnail.jsp";
			}
		}
		
		request.setAttribute("msg", msg);
		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
