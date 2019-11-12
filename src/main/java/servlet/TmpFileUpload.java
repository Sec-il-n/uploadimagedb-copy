package servlet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.logic.Base64Logic;
import model.logic.CreateFileLogic;
import model.logic.FileUploadCheckLogic;
import model.logic.FileUploadLogic;
import model.logic.GetFileNameLogic;
import model.logic.GetPathLogic;
import model.logic.GetSuffixLogic;
import model.logic.IsValidFileLogic;//
import model.logic.LoginLogic;

@WebServlet("/TmpFileUpload")//
@MultipartConfig(maxFileSize=10485760,location="/tmp/")//

public class TmpFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TmpFileUpload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		Part part = request.getPart("data");
		String to = null;//

		HttpSession session = request.getSession();
		String userId=(String) session.getAttribute("userId");

		LoginLogic logic=new LoginLogic();
		to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);
		if(msg!=null){
			request.setAttribute("msg", msg);
			RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);
		}


		if(userId!=null) {

			FileUploadCheckLogic fulogic=new FileUploadCheckLogic();
			String checkTitle = null;
			String checkText = null;
			String checkFile = null;
			try {
				checkTitle = fulogic.checkTitle(title);
				checkText = fulogic.checkText(text);
				checkFile = fulogic.checkFile(part);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			if(checkTitle!=null || checkFile!=null || checkText!=null) {

				request.setAttribute("title",title);
				request.setAttribute("text",text);
				request.setAttribute("checkTitle",checkTitle);
				request.setAttribute("checkText",checkText);
				request.setAttribute("checkFile",checkFile);

				RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/inputNull.jsp");
				dsp.forward(request, response);

			}else if(checkTitle==null && checkFile==null && checkText==null) {

				GetFileNameLogic glogic=new GetFileNameLogic();
				String filename = glogic.getFileName(part);//null

				IsValidFileLogic ivlogic=new IsValidFileLogic();
				Boolean isValid = ivlogic.execute(filename);
				String isImgMsg = null;
				try {
					isImgMsg = ivlogic.execute(part);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}

				if(!isValid || isImgMsg!=null) {
					request.setAttribute("title",title);
					request.setAttribute("text",text);
					request.setAttribute("isImgMsg",isImgMsg);

					RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/inputNull.jsp");
					dsp.forward(request, response);

				}else if(isValid && isImgMsg==null && checkText==null){
	////****
					CreateFileLogic clogic=new CreateFileLogic();
//	0918			String tmpFileName =clogic.getCreatedFileName(filename);//
					GetPathLogic gplogic=new GetPathLogic();
					Path path=gplogic.getTmpDir();
//
					Path createdFile=null;
					createdFile=clogic.createFile(path, null);
//	0918			createdFile=gplogic.getFilePath(path,tmpFileName);//*

					String suffix=new GetSuffixLogic().getSuffix(filename);
//					Path createdDirectry=null;
//					createdDirectry=clogic.createTmpDirectry(path);//1 !!pathの配下にdirが出来る！(そしてその名前は予測不能。)
	//				createdFile=clogic.createFile(path, suffix);
//					createdFile=clogic.createFile(createdDirectry, null);//2b!=2a
					/**
					 * test
					 */
//					List<String> test=clogic.test(path,createdDirectry,createdFile);//*
					List<String> test=clogic.test(path,path,createdFile);//*

					FileUploadLogic flogic=new FileUploadLogic ();
//					boolean result;
					try {
						flogic.execute(part,createdFile);
//						result=flogic.execute(part,createdFile);
					}catch (IllegalArgumentException  e) {
						e.printStackTrace();
						throw new IllegalArgumentException ("inputstream sizeover");
					}/*catch (NoSuchFileException e) {
//						e.printStackTrace();
//						throw new NoSuchFileException("ファイルが存在しません2");
//					}catch (IOException e) {
//						e.printStackTrace();
//						throw new IOException("ファイル書き込み入出力エラー");
//					}*/
//need if(!result)
	/**
	 * Tomcat > conf >sever.xml reloadable="true"
	 * part->path取得に変更
	 */
					String base64=null;
					String s=createdFile.toString();
					Base64Logic blogic=new Base64Logic(part, suffix);
	//				base64=blogic.execute64();
					base64=blogic.execute64(s);
					part.delete();
	//
					session.setAttribute("title",title);
					session.setAttribute("text",text);
					to="/ShowThumbnail";

					//共通
					session.setAttribute("test",test);//
					session.setAttribute("tmpDir",path);//tmp dir
					session.setAttribute("tmpPath",createdFile);
					session.setAttribute("filename",filename);//?
					session.setAttribute("base64",base64);
					response.setHeader("Cache-Control", "no-cache");
					response.sendRedirect(to);
				}
			}
		}
		doGet(request, response);
	}
}