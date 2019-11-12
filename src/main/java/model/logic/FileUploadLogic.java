package model.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import dao.DeleteDAO;
import dao.UploadFileDAO;
import model.beans.ImageBean;
import model.beans.Mutter;

public class FileUploadLogic {
	private DataSource source=null;
	public FileUploadLogic() {
		super();
	}
	public FileUploadLogic(DataSource source) {
		super();
		this.source=source;
	}
//changed re boolean & test 0919
//	public void execute(Part part,Path path) {
	public boolean execute(Part part,Path path) {
		try {
			try(BufferedInputStream br=new BufferedInputStream(part.getInputStream(),(int)part.getSize());
				BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(path.toString()))){
				int count=0;
				byte[] buf=new byte[(int)part.getSize()];
					while((count=br.read(buf)) != -1) {//読み込まれるバイト数-->終了-1
						bw.write(buf, 0, count);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
/**
 * Edit Image
 */
public boolean execute(ImageBean bean) {
		UploadFileDAO dao=new UploadFileDAO(source);
		boolean result =dao.create(bean);
		return result;
	}
//public boolean execute(Mutter mutter,ImageBean bean) {
//	String filename=bean.getFilename();
//		UploadFileDAO dao=new UploadFileDAO(source);
//		String id=dao.findId(filename);
//		boolean result=dao.create(mutter,id);
//		return result;
//	}
public boolean execute(Mutter mutter) {
	UploadFileDAO dao=new UploadFileDAO(source);
	boolean result=dao.create(mutter);
	return result;
}

//made test 09/18 but haven't used @servlet
//just4practice...?
public String executeFailer(boolean result1,boolean result2,ImageBean bean1,Mutter bean2){
		String msg = null;
		if(result1!=true || result2!=true){
			DeleteDAO dao=new DeleteDAO(source);
	 		dao.deleteImage(bean1);
			dao.deleteMutter(bean2);
			msg="書込みに失敗しました";
		}else if(result1==true && result2==true){
			msg="書込み完了しました";
		}
		return msg;
	}
/**
 * 未使用
 * ???先にcreate fileしなくてもこれで作成できるのか。
 */
//public void execute(Path tmpPath/*Context path?*/,Path pathup,ServletContext context) throws IOException {
	public void execute(Path tmpPath,Path upDir,String newFileName,ServletContext context) throws IOException {
//	InputStream is=context.getResourceAsStream(tmpPath.toString());//x null  ?/tmp System root <=>Web アプリケーションのコンテキストルート?
	File upload=new File(upDir.toString(),newFileName);
	                //ontext.getRealPath=>null ->madeDir worked? ->/app/..でexceptionは免れたがloadできない!!(no file or path no exist)?。
	                //or coz this path from deploed & extended war ,did't 変更反映されていない。?
	try(BufferedInputStream br=new BufferedInputStream(new FileInputStream(tmpPath.toString()));
		BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(upload))){
		int count=0;
		File file=tmpPath.toFile();
		byte[] buff=new byte[(int)file.length()];
		while((count=br.read(buff)) != -1) {//
			bw.write(buff, 0, count);
		}
	}
}
/**
 * 未使用
 * @param tmpPath
 * @param upPath
 * @throws IOException
 */
//public void execute(Path tmpPath,Path upPath) throws IOException {
//	File in=new File(tmpPath.toString());
//	File out=new File(upPath.toString());
//	try(BufferedInputStream br=new BufferedInputStream(new FileInputStream(in));//IOException
////	try(BufferedInputStream br=new BufferedInputStream(new FileInputStream(tmpPath.toString()));//==⤴︎？
//		BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(out))){
//
//		int count=0;
//		File file=tmpPath.toFile();
//		byte[] buff=new byte[(int)file.length()];
//		while((count=br.read(buff)) != -1) {//
//			bw.write(buff, 0, count);
//		}
//	}
//}
}
