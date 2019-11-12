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

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import model.beans.ImageBean;
import model.beans.Mutter;
import model.logic.CreateFileLogic;
import model.logic.DeleteTmpFileLogic;
import model.logic.FileUploadLogic;
import model.logic.FileUploadS3Logic;
import model.logic.GetDataSourceLogic;
import model.logic.LoginLogic;

@WebServlet("/UploadComplete")
public class UploadComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UploadComplete() {
        super();
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String title = (String) session.getAttribute("title");
		String text = (String) session.getAttribute("text");
		Path tmpPath = (Path) session.getAttribute("tmpPath");//ファイルまでのパス
//		Path tmpDir = (Path) session.getAttribute("tmpDir");//delete one by one
		String filename=(String) session.getAttribute("filename");
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
	//get path to upload
//			GetPathLogic gplogic=new GetPathLogic();
//			Path dir=gplogic.getDir();
	//upload
			CreateFileLogic clogic=new CreateFileLogic();
			String newFileName =clogic.getCreatedFileName(filename);

	//DataSource
			GetDataSourceLogic gdlogic=new GetDataSourceLogic();
			DataSource source = null;
			try {
				source = gdlogic.getDataSource();
			} catch (NamingException e) {
				e.printStackTrace();
			}
	//		//logic
			ImageBean bean1 = new ImageBean(title,newFileName);
			Mutter bean2 = new Mutter(title,text,userId);

			FileUploadS3Logic s3logic=new FileUploadS3Logic();
			FileUploadLogic flogic=new FileUploadLogic(source);
			boolean result = false;

//			BasicSessionCredentials sessionCredencials;
			try {
//				sessionCredencials=s3logic.MakingRequestsWithIAMTempCredentials();
//				result = s3logic.s3Upload(newFileName,tmpPath.toString(),sessionCredencials);

				result = s3logic.s3Upload(newFileName,tmpPath.toString());
//				flogic.execute(tmpPath, dir, newFileName, context);
			} catch (NullPointerException e) {
				msg="不正な画面移遷がありました。";//一回pop up
				e.printStackTrace();


			 }catch (AmazonServiceException e) {
				 msg="AmazonServiceException ";
				e.printStackTrace();

			 }catch (SdkClientException e) {
				msg="sdkClientException ";
		        e.printStackTrace();

//			} catch (Exception e) {
//				msg="エラーが発生しました。やり直してください。s3";//一回pop up
//				e.printStackTrace();

			} /*catch (FileNotFoundException e) {
				msg="すでに更新しています。";
				e.printStackTrace();
//			}*/
			if(result){
	//->DB need test
				boolean result1=flogic.execute(bean1);
//				boolean result2=flogic.execute(bean2,bean1);
				boolean result2=flogic.execute(bean2);
		//	find error
				msg=flogic.executeFailer(result1, result2, bean1, bean2);
			}else{

				msg="エラーが発生しました。やり直してください。s3 result false";
			}

//			Path path=gplogic.getFilePath(dir, newFileName);//need if delete 'this' file
//delete file one by one(multi thread)
			DeleteTmpFileLogic dtlogic=new DeleteTmpFileLogic();
			dtlogic.DeleteTmpFile(tmpPath);
//			CheckDirectoryContentsLogic cdlogic=new CheckDirectoryContentsLogic();
//			List<String> filesTmpPath=cdlogic.filespath(tmpDir);//
//			dtlogic.DeleteTmpFile(filesTmpPath);

//			session.setAttribute("path",path.toAbsolutePath());

//			session.setAttribute("path",path.toAbsolutePath());//削除
			session.setAttribute("msg",msg);
			session.setAttribute("newFileName",newFileName);
	//
			response.setHeader("Cache-Control", "no-cache");
			response.sendRedirect("/ToLoginResult");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
