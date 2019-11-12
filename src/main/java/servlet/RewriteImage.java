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
import javax.servlet.http.Part;

import model.logic.Base64Logic;
import model.logic.CreateFileLogic;
import model.logic.FileUploadCheckLogic;
import model.logic.FileUploadLogic;
import model.logic.GetFileNameLogic;
import model.logic.GetPathLogic;
import model.logic.GetSuffixLogic;
import model.logic.IsValidFileLogic;
import model.logic.LoginLogic;

@WebServlet("/RewriteImage")
@MultipartConfig(maxFileSize=10485760,location="/tmp/")
public class RewriteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RewriteImage() {
        super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		Part part = request.getPart("data");
		FileUploadCheckLogic fulogic=new FileUploadCheckLogic();
		String checkTitle = null;
		String checkText = null;
		String checkFile = null;

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		LoginLogic logic=new LoginLogic();
		String to = logic.sessionCheck(userId);
		String msg = logic.getMsg(to);
		if(msg!=null){
			request.setAttribute("msg", msg);
			RequestDispatcher dsp=request.getRequestDispatcher(to);
			dsp.forward(request, response);
		}
		if(userId!=null) {

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

				RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/rewriteImage.jsp");
				dsp.forward(request, response);

			}else if(checkTitle==null && checkFile==null && checkText==null) {
	//Completeまで不要？
	//			String deleteFileName=(String) session.getAttribute("deleteFileName");//
	//			String postedtime=(String) session.getAttribute("time");//
				GetFileNameLogic gflogic = new GetFileNameLogic();
				String filename = gflogic.getFileName(part);
				//
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

					RequestDispatcher dsp=request.getRequestDispatcher("/WEB-INF/jsp/rewriteImage.jsp");
					dsp.forward(request, response);

				}else if(isValid && isImgMsg==null && checkText==null){
	//****
					GetPathLogic gplogic=new GetPathLogic();
					Path path=gplogic.getTmpDir();
	//
					CreateFileLogic clogic=new CreateFileLogic();
					Path createdFile = clogic.createFile(path, null);
					FileUploadLogic flogic=new FileUploadLogic ();
					//0918 add return boolean
//					boolean result=false;
					try {
//						result=flogic.execute(part,createdFile);
						flogic.execute(part,createdFile);

					}catch (IllegalArgumentException  e) {
						e.printStackTrace();
						throw new IllegalArgumentException ("inputstream sizeover");
					}/*catch (NoSuchFileException e) {
						e.printStackTrace();
						throw new NoSuchFileException("ファイルが存在しません2");
					}catch (IOException e) {
						e.printStackTrace();
						throw new IOException("ファイル書き込み入出力エラー");
					}*/
	////****ここからthumbnailの表示は、base64のみにしておいて、６４scope->copy
	//				try {
	////					execute(Path tmpPath,Path upDir,String newFileName,ServletContext context)
	//			        flogic.execute(part,createdFile);//null
	//			      }catch (IOException e) {
	//			        e.printStackTrace();
	//			        throw new IOException("ファイル書き込み入出力エラー");
	//			      }//***
					//tmpここから
					String suffix=new GetSuffixLogic().getSuffix(filename);
					Base64Logic bslogic=new Base64Logic(part, suffix);
					String base64=bslogic.execute();//tmp なしで64で書き込む場合
					String base64display=bslogic.execute(base64);
					part.delete();

					//属性title,textだと✖︎か
					session.setAttribute("title",title);
					session.setAttribute("text",text);
					session.setAttribute("tmpPath",createdFile);
					session.setAttribute("filename",filename);
					session.setAttribute("base64",base64);//
					session.setAttribute("base64edit",base64display);

					response.setHeader("Cache-Control", "no-cache");
					response.sendRedirect("/ShowThumbnailEdit");
				}
			}
		}
		doGet(request, response);
	}

}
