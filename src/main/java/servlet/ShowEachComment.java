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
import model.beans.ImageBean;
import model.logic.GetAllImageFromDBLogic;
import model.logic.GetDataSourceLogic;
import model.logic.GoodCommentLogic;
import model.logic.LoginLogic;

@WebServlet("/ShowEachComment")
public class ShowEachComment extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ShowEachComment() {
        	super();
    	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String to = null;
		String id = null;
		String userId=(String) session.getAttribute("userId");
		
		LoginLogic logic=new LoginLogic();
		to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);

		if(to!=null && msg!=null){
			request.setAttribute("msg", msg);
		
		} else {
			id=request.getParameter("id");

			if(id==null) {
				id=(String) session.getAttribute("id");
			}

			GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			DataSource source = null;
			
			try {
				source = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}

			GetAllImageFromDBLogic gilogic=new GetAllImageFromDBLogic(source);
			ImageBean imgb = gilogic.execute(id);

			GoodCommentLogic glogic=new GoodCommentLogic();
			int count=glogic.total(id, source);
			to="/WEB-INF/jsp/showEach.jsp";

			session.setAttribute("id", id);
			session.setAttribute("imgb", imgb);
			session.setAttribute("count", count);
		}

		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);
		
		response.getWriter().append("1 Served at: ").append(request.getContextPath());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);

	}

}
