package model.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckDirectoryContentsLogic {

//filenameをtaimestamp降順に並べる//確認用?不要?
	public List<String> sortedFileName(List<String> fileName) throws IOException{
		Map<String,String> filesMap=new TreeMap<>(Comparator.reverseOrder());
		for(String f:fileName) {
			filesMap.put(f.substring(0,12),f);
		}
		List<String> sortedFileName=new ArrayList<>();
		for(String s:filesMap.values()) {
			sortedFileName.add(s);
		}
		return sortedFileName;
	}
	public boolean checkDirectry(Path path) {//ファイルまでのパス
		boolean exists = false;
		try {
			exists=Files.exists(path);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
		if(exists) {
			return true;
		}else {
			return false;
		}
	}
//以下不要
//showimages1
	public List<String> filespath(Path path) throws IOException{
		File[] files=new File(path.toString()).listFiles();
		List<String> filesPath=new ArrayList<>();
		if(files!=null){
			for(File f:files) {
				filesPath.add(f.getAbsolutePath());//get()で渡せば抽象にはならない
			}
		}
		return filesPath;
	}

//
//	Set<String> =getResourcePaths(String path);**

	public File[] filespath(String path) throws IOException{
		File[] files=new File(path).listFiles();
		return files;
    }
//戻りbooleanにして、SERVLETで分岐なら使用可
//	public String checkDirectry(Path path) {//ファイルまでのパス
//		String msg=path.toAbsolutePath().toString();
//		if(Files.exists(path)) {
//			msg=msg+"は存在します";
//		}else {
//			msg=msg+"は存在しません";
//		}
//		return msg;
//	}
}


