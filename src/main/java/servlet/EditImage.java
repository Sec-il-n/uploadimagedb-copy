package servlet;

import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.logic.GetPathLogic;
import model.logic.GetTimeFileCreatedLogic;

@WebServlet("/EditImage")
@MultipartConfig(maxFileSize=10485760,location="/tmp/")
public class EditImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditImage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String i = request.getParameter("id");//null
		int id = Integer.parseInt(i);
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String deleteFileName=request.getParameter("deleteFileName");

		GetTimeFileCreatedLogic gtlogic=new GetTimeFileCreatedLogic ();
		String time=gtlogic.getTime(deleteFileName);
//get path to delete
		GetPathLogic gplogic=new GetPathLogic();
		Path path=gplogic.getDir();
		Path deleteFilePath=gplogic.getFilePath(path, deleteFileName);
//		String deleteFilePath=path.toString() + "/"+ deleteFileName;

//		ImageBean imgb=new ImageBean(id,title,text);
//		request.setAttribute("imgb",imgb);

		HttpSession session = request.getSession();//いご処理で必要
		session.setAttribute("deleteFileName", deleteFileName);
		session.setAttribute("time", time);
		session.setAttribute("deleteFilePath", deleteFilePath);

		request.setAttribute("idrewrite", id);
		request.setAttribute("title", title);
		request.setAttribute("text", text);
		RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/rewriteImage.jsp");
		dsp.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String deleteFileName=request.getParameter("deleteFileName");
//
//		GetPathLogic gplogic=new GetPathLogic();
//		Path path=gplogic.getPath();
//
//		GetTimeFileCreatedLogic gtlogic=new GetTimeFileCreatedLogic ();
//		String time=gtlogic.getTime(deleteFileName);
//		String deleteFilePath=path.toString() + "/"+ deleteFileName;
//
//		HttpSession session = request.getSession();
//		session.setAttribute("deleteFileName", deleteFileName);
//		session.setAttribute("time", time);
//		session.setAttribute("deleteFilePath", deleteFilePath);
//
//		RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/rewriteImage.jsp");
//		dsp.forward(request, response);

		doGet(request, response);
	}

}


