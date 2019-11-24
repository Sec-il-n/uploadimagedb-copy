package model.logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GetPathLogic2 {
	
	/**
	 * パスを、ブラウザから開くことのできる文字列に変換する必要がある場合は、toUriを使用
	 */
	public String showURL() {
		Path p=Paths.get("/");
	 	String path = p.toUri().getPath();
	 	return path;
	}

	HttpServletResponse resp;
	public void acsessFile(Path tmpPath,HttpServletResponse resp) throws IOException{
		
		File file = new File (tmpPath.toString());
		resp.setHeader("Content-Type", "image/*" );
		resp.setHeader("Content-Disposition","attachment; filename=\"sample.jpg\"" );
		resp.setContentLength((int)file.length());

		try(BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream out = resp.getOutputStream();){
			int n=0;

			while ((n=in.read()) != -1){
				out.write(n);
			}
		}


	}


}
