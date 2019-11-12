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

import model.logic.GetDataSourceLogic;
import model.logic.GoodCommentLogic;
import model.logic.LoginLogic;

@WebServlet("/GoodComment")
public class GoodComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GoodComment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to;

		String id=request.getParameter("good");
		String by=request.getParameter("by");
		HttpSession session=request.getSession();
		String userId=(String) session.getAttribute("userId");
		LoginLogic logic=new LoginLogic();
		to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);
//↑確認済み
		if(to!=null && msg!=null){
//		if(userId==null){
			request.setAttribute("msg", msg);
			RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);

		}else {
			GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			DataSource source = null;
			try {
				source = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			GoodCommentLogic glogic=new GoodCommentLogic(userId,by,id,source);
//		    GoodCommentLogic glogic=new GoodCommentLogic();
//			boolean result=glogic.goodCount(userId, by, userId, source);
		    boolean result=glogic.goodCount();
		    int count=glogic.total(id, source);
			if(result==true){

				session.setAttribute("count", count);
			    session.setAttribute("id", id);
//				response.setHeader("Cache-Control", "no-cache");
				response.sendRedirect("/ShowEachComment");
			}
			else {//counted on my oun
				response.sendRedirect("/ShowEachComment");
				response.getWriter().append("2 Served at: ").append(request.getContextPath());
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
