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
			//if delete each file
		    try {
		    	File file=new File(tmpPath.toString());
				file.delete();//やり直した分は消えない
//				Files.delete(tmpPath);//やり直した分は消えない

		    } catch (Exception e) {
		    	e.printStackTrace();
//		    } catch (NoSuchFileException e) {
//				e.printStackTrace();
//
//		    } catch (IOException e) {
//				e.printStackTrace();
			}

    }
//    public void DeleteTmpFile(List<String> filesTmpPath) {
//    	for(String f:filesTmpPath) {
//    		try {
//    			Files.delete(Paths.get(f));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}


//    	}
//    }
}
