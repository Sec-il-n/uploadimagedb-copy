package servlet;

import java.io.IOException;
import java.nio.file.Path;

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
import model.beans.Mutter;
import model.logic.CreateFileLogic;
import model.logic.DeleteFileS3Logic;
import model.logic.FileUploadS3Logic;
import model.logic.FindIdFromDBLogic;
import model.logic.GetDataSourceLogic;
import model.logic.LoginLogic;
import model.logic.RewriteFileLogic;
import model.logic.UpdateFileDBLogic;
//現s3と併用しているのでdeleteFilePath(folder uploadの処理不要(?))
@WebServlet("/RewriteImageComplete")
public class RewriteImageComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RewriteImageComplete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Path deleteFilePath = (Path) session.getAttribute("deleteFilePath");
		Path tmpPath = (Path) session.getAttribute("tmpPath");//?
		String title = (String) session.getAttribute("title");
		String text = (String) session.getAttribute("text");
		String filename=(String) session.getAttribute("filename");//from user
		String deleteFileName=(String) session.getAttribute("deleteFileName");
		String postedtime=(String) session.getAttribute("time");
		String base64=(String) session.getAttribute("base64");//new
		String userId = (String) session.getAttribute("userId");

		LoginLogic logic=new LoginLogic();
		String to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);
		if(msg!=null){
			request.setAttribute("msg", msg);
			RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);
		}

		if(userId!=null){
//⤴ブラウザで戻ると︎getAttributeしないので↓かかる(need to code ブラウザ戻る)
			if(deleteFilePath==null|| title==null||text==null||deleteFileName==null||postedtime==null ||base64==null) {
	//		if(deleteFilePath==null|| title==null||text==null||tmpPath==null||filename==null||deleteFileName==null||postedtime==null) {
				session.setAttribute("errormsg", "不正な画面移管が有りました。(更新が完了しているか、内容が取得できていない可能性があります。)");
				response.setHeader("Cache-Control", "no-cache");
				response.sendRedirect("/ToLoginResult");
	//
	//		}else if(deleteFilePath!=null&&title!=null&&text!=null&&tmpPath!=null&&filename!=null&&deleteFileName!=null&&postedtime!=null) {
			}else if(deleteFilePath!=null && title!=null && text!=null && filename!=null && deleteFileName!=null && postedtime!=null) {
				//**
	//			GetPathLogic gplogic=new GetPathLogic();
	//			//1
	//			Path p=gplogic.getDir();
				CreateFileLogic clogic=new CreateFileLogic();
				String newFileName = clogic.getRewritedFileName(postedtime, filename);//
	//			Path createdFile=clogic.createFile(p, newFileName);//
	//			//2
	//			Path path=gplogic.getPath(deleteFilePath);//**
				RewriteFileLogic dlogic=new RewriteFileLogic();
				msg=dlogic.rewrite(base64,deleteFilePath);
	//			String msg=dlogic.execute(path);//

				//DataSource
				GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			    DataSource dataSource = null;
				try {
					dataSource = gdlogic.getDataSource();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				//db
				FindIdFromDBLogic filogic = new FindIdFromDBLogic();
				int id = filogic.execute(deleteFileName,dataSource);

	//			FileUploadLogic flogic=new FileUploadLogic();//
	//			flogic.execute(tmpPath,createdFile);//

				ImageBean bean1 = new ImageBean(id,title,newFileName);
				Mutter bean2 = new Mutter(id,title,text);
				UpdateFileDBLogic uflogic=new UpdateFileDBLogic(dataSource);
				String msg1 = uflogic.rewriteImage(bean1);
				String msg2 = uflogic.rewriteMutter(bean2);
				StringBuilder sb=new StringBuilder(msg);
				sb.append(msg1).append(System.lineSeparator()).append(msg2);
				//s3
				FileUploadS3Logic s3uplogic=new FileUploadS3Logic();
				DeleteFileS3Logic s3logic=new DeleteFileS3Logic ();
				s3uplogic.s3Upload(newFileName, tmpPath.toString());
				s3logic.s3Delete(deleteFileName);

				session.setAttribute("msg",sb.toString());
				session.setAttribute("newFileName",newFileName);//
				response.setHeader("Cache-Control", "no-cache");
				response.sendRedirect("/ToLoginResult");
			}
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
