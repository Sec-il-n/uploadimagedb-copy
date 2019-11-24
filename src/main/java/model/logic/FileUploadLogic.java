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
	
	public boolean execute(Part part,Path path) {
		try {
			try(BufferedInputStream br=new BufferedInputStream(part.getInputStream(),(int)part.getSize());
			BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(path.toString()))){
				
				int count=0;
				byte[] buf=new byte[(int)part.getSize()];
				
				while((count=br.read(buf)) != -1) {
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

	public boolean execute(Mutter mutter) {
		
		UploadFileDAO dao=new UploadFileDAO(source);
		boolean result=dao.create(mutter);
		return result;
	}

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

	public void execute(Path tmpPath,Path upDir,String newFileName,ServletContext context) throws IOException {
		File upload=new File(upDir.toString(),newFileName);
		try(BufferedInputStream br=new BufferedInputStream(new FileInputStream(tmpPath.toString()));
		
		    BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(upload))){
			int count=0;
			File file=tmpPath.toFile();
			byte[] buff=new byte[(int)file.length()];
			
			while((count=br.read(buff)) != -1) {
				bw.write(buff, 0, count);
			}
	
		}

	}

}
