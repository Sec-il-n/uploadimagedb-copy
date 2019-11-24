package model.logic;

import java.io.File;
import java.nio.file.Path;

import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class DeleteTmpFilesLogic
 */
public class DeleteTmpFileLogic extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
	public void DeleteTmpFile(Path tmpPath) {
		try {
			File file=new File(tmpPath.toString());
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

    }

}
