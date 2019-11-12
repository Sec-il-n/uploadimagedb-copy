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
import model.logic.GetAllImageFromDBLogic;
import model.logic.GetDataSourceLogic;
import model.logic.GetPageLogic;
import model.logic.LoginLogic;
import model.logic.PageNationLogic;

@WebServlet("/ShowImages")
public class ShowImages extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ShowImages() {
        super();
    }
//parameter=無し,"show","edit"の三分岐
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action=request.getParameter("action");
		HttpSession session = request.getSession();
		String userId=(String) session.getAttribute("userId");
		String path=null;
		String msg =null;

		LoginLogic logic=new LoginLogic();
		path = logic.sessionCheck(userId);
		msg = logic.getMsg(path);
		if(msg!=null){
			request.setAttribute("msg", msg);
		}

		if(userId!=null) {
		    GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		    DataSource dataSource = null;
			try {
				dataSource = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			GetAllImageFromDBLogic gilogic = new GetAllImageFromDBLogic(dataSource);
			GetPageLogic gplogic = new GetPageLogic(dataSource);
			PageNationLogic pnlogic =new PageNationLogic();
			int total = 0;
			int totalPage;
			int in ;

			List<ImageBean> filesList = gilogic.execute();
			try {
				total = filesList.size();
			} catch (Exception e) {
//				path="/WEB-INF/jsp/errorpage.jsp";
				path=gplogic.toError();
				RequestDispatcher dsp=request.getRequestDispatcher(path);
				dsp.forward(request, response);
				e.printStackTrace();
			}
			session.setAttribute("total", total);
//			GetPageLogic gplogic = new GetPageLogic(dataSource);
//			int totalPage;
//			int in ;
			if(action.equals("show")) {
				//1ページ目
				int page=1;
				List<ImageBean> pagedList=gplogic.findPage(page);
				//pageNation 表示部分
				totalPage = pnlogic.getTotalPageCount(total);
				in = pnlogic.getPageShowing(page, total, totalPage);

//				session.setAttribute("total", total);//
				session.setAttribute("totalPage", totalPage);//
				request.setAttribute("page", page);
				request.setAttribute("pagedList", pagedList);
				request.setAttribute("in", in);//

				//				path="/WEB-INF/jsp/showImages.jsp";
				path=gplogic.toShow();

			}else if(action.equals("edit")) {///*
				int page=1;
				List<ImageBean> yourPostedList = gilogic.listByUser(userId);//all
				List<ImageBean> pagedPostedList=gplogic.findPageByUser(userId, page);
				try {
					total = yourPostedList.size();
				} catch (Exception e) {
//					path="/WEB-INF/jsp/errorpage.jsp";
					path=gplogic.toError();
					RequestDispatcher dsp=request.getRequestDispatcher(path);
					dsp.forward(request, response);
					e.printStackTrace();
				}

				totalPage = pnlogic.getTotalPageCountEdit(total);
				in=pnlogic.getEditPageShowing(page, total, totalPage);

				session.setAttribute("total", total);//rewrite total count of all posts
				session.setAttribute("totalPage", totalPage);
				request.setAttribute("page", page);
				request.setAttribute("in", in);//
				request.setAttribute("pagedPostedList", pagedPostedList);

//				path="/WEB-INF/jsp/getEditFile.jsp";
				path=gplogic.toEdit();
			}
		}
		RequestDispatcher dsp=request.getRequestDispatcher(path);
		dsp.forward(request, response);


		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}