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
	 * 0.create Dir /image/upload
	 * 1. get 'File' from tmp
	 * 2. write
	 * dist:  pass to upload(out side of the 'webapps')
	 */
	//need createDir()

	/**
	 * ⤴2
	 * move to create file logic
	 */
//	public void execute(Path tmpPath,Path upPath) {
//		Files.copy(tmpPath, upPath, REPLACE_EXISTING);
//
//	}
//
//	}


	/**
	 * パスを、ブラウザから開くことのできる文字列に変換する必要がある場合は、toUriを使用
	 */
	public String showURL() {
		Path p=Paths.get("/");// Pathに対応するファイルが存在している必要はありません。
	 	String path = p.toUri().getPath();
	 	return path;
	}

/**
 * show Image it woudn't be acsessed
 * download =>reloadしないと2回連続でDLできない。(一度stream使うと x)
 */
	HttpServletResponse resp;
	public void acsessFile(Path tmpPath,HttpServletResponse resp) throws IOException{
		File file = new File (tmpPath.toString());
		resp.setHeader("Content-Type", "image/*" );
		//Content-Disposition ?? is downloaded and saved locally.
//		resp.setHeader("Content-Disposition","inline" );//メッセージ本文の一部として表示されるコンテンツ??->DLされた
		resp.setHeader("Content-Disposition","attachment; filename=\"sample.jpg\"" );//<=>別ファイルとして添付
		resp.setContentLength((int)file.length());

		try(BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream out = resp.getOutputStream();){
			int n=0;

			while ((n=in.read()) != -1){
				out.write(n);
			}
		}

//		String to="";
//		resp.sendRedirect(to);

	}


}