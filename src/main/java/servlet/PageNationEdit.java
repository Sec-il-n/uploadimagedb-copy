package servlet;

import java.io.IOException;
import java.util.List;

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
import model.logic.GetDataSourceLogic;
import model.logic.GetPageLogic;
import model.logic.LoginLogic;
import model.logic.PageNationLogic;

@WebServlet("/PageNationEdit")
public class PageNationEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PageNationEdit() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String to = null;

		HttpSession session = request.getSession();
		String userId=(String) session.getAttribute("userId");

		LoginLogic logic=new LoginLogic();
		to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);
		if(to!=null && msg!=null){
			request.setAttribute("msg", msg);
		}else {//forward前まで

			String p = request.getParameter("page");
			String n = request.getParameter("now");
			String action = request.getParameter("action");
			int total=(int) session.getAttribute("total");
			int totalPage=(int) session.getAttribute("totalPage");

	//		if(userId!=null && (p!=null || n!=null)){
			if(p!=null || n!=null){
				int page = 1;//

				PageNationLogic plogic=new PageNationLogic();
				page=plogic.getPageTry(p, n, action, totalPage);
				to = plogic.forwardToEdit(page);

				GetDataSourceLogic gdlogic=new GetDataSourceLogic();
				DataSource source = null;
				try {
					source = gdlogic.getDataSource();
				} catch (NamingException e) {
					e.printStackTrace();
				}

				GetPageLogic gplogic = new GetPageLogic(source);
				List<ImageBean> pagedPostedList=gplogic.findPageByUser(userId, page);
//				List<ImageBean> pagedPostedList=gplogic.findPageByUser(userId, page, source);
				int in = plogic.getEditPageShowing(page, total, totalPage);//

				request.setAttribute("pagedPostedList", pagedPostedList);
				request.setAttribute("page", page);
				request.setAttribute("in", in);

			}
		}
		RequestDispatcher dsp=request.getRequestDispatcher(to);
		dsp.forward(request, response);

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
