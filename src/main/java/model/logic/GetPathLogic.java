package model.logic;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class GetPathLogic {
	private String s;
	private static Path path;
//	private static Path path;

	public static Path getPath(String s) {

		path = null;
		try {
			path = Paths.get(s);
		} catch (InvalidPathException  e1) {
			e1.printStackTrace();
		}
		return path;
	}

	public Path getDir(){//or argument (s)
//		String s="image/upload";
		String s="upload";
		Path path=getPath(s);
		return path;
	}

	public static Path getPath(ServletContext context,String filename) throws MalformedURLException, URISyntaxException {
		String s= context.getResource(filename).toString();
		path = Paths.get(s);
		return path;
}
	//↑と組み合わせて使用
	public static String getPathString(Path path)  {
		String s=path.toString();
		return s;
	}
//	//toStringがOKなら⇩
	public String getPathString(ServletContext context,String filename) throws MalformedURLException  {
		s= context.getResource(filename).toString();
		return s;
	}
	//appない(src/main/resources)
	public File getFile(String filename) throws MalformedURLException  {
		URI uri = null;
		File file = null;
		try {
			uri=Thread.currentThread().getContextClassLoader().getResource(filename).toURI();
			//getResource:vs class...
			file=new File(uri);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (IllegalArgumentException  e) {
			e.printStackTrace();

		}
		return file;
	}
	public URI uri(String filename) throws MalformedURLException  {
		URI uri = null;
		try {
			uri=Thread.currentThread().getContextClassLoader().getResource(filename).toURI();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (IllegalArgumentException  e) {
			e.printStackTrace();

		}
		return uri;
	}

	public List<String> sortedFileURL(List<String> sortedFileName, ServletContext context) throws MalformedURLException{
		List<String> sortedURL=new ArrayList<>();
		for(String filename:sortedFileName){
			String uri=context.getResource(filename).toString();
			sortedURL.add(uri);
		}
		return sortedURL;
	}

	public Path getFilePath(Path dir,String filename) {//dir=アンカー
			path=dir.resolve(filename);
		return path;
	}
	/*　
	 * @createTmpDirectry()に使用
	 * ー＞need coding to dele this directory
	 *   /tmp配下に名前未定のフォルダが作られる
	 */
public Path getTmpDir() {
	s="/tmp";//"/"ルートディレクトリからのファイルパス
	path=getPath(s);
	return path;
}

public String getSTmpPath(ServletContext context) {
	String StringtmpPath=context.getRealPath("/src/main/resources/tmp");
	return StringtmpPath;
}

//test only
	public Path getPathProperties() {
		s="/Applications/Eclipse_2019-03.app/Contents/upload_image_db4/src/test/resources/jdbc/JdbcDataSource_H2.properties";
		path=getPath(s);
		return path;
	}

}