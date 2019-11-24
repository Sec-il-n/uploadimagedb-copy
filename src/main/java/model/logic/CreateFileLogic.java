package model.logic;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CreateFileLogic {
	
	public Path createDirectry(Path path) throws IOException {

		Path createdDirectry=null;
		
		try {
			createdDirectry=Files.createDirectories(path);//すでに存在していて作成できなかった場合でも例外はスローされません。<->createDirectory

		} catch (UnsupportedOperationException e1) {
			e1.printStackTrace();
			throw new UnsupportedOperationException("ディレクトリの作成時に原子的に設定できない属性が配列に含まれる");
		
		} catch (FileAlreadyExistsException  e1) {
			e1.printStackTrace();
			throw new FileAlreadyExistsException ("dirが存在するが、ディレクトリではない(オプションの固有例外)");
		
		} catch (IOException  e1) {
			e1.printStackTrace();
			throw new IOException ("入出力エラー");
		}
		
		return createdDirectry;
	}
	
	/**
	 * tmpFile
	 */
	public Path createFile(Path dir,String suffix) throws IOException {

		Path createdfile = null;

		try {
			createdfile=Files.createTempFile(dir, null, suffix);

		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
			throw new IllegalArgumentException   ("新しいファイル名にsuffixを使用できません");

		} catch (UnsupportedOperationException  e) {
			e.printStackTrace();
			throw new UnsupportedOperationException  ("原子的に設定できない属性が配列に含まれます");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return createdfile;
	}
		
	public String getCreatedFileName(String filename) {

		long time=System.currentTimeMillis();
		String nameadd  = new StringBuilder(String.valueOf(time)).append("_").toString();
		return nameadd + filename;
	}

	public Path createUploadFile(Path dir,String filename) throws IOException {
		
		Path createdfile = dir.resolve(filename);//null

		try {
			createdfile=Files.createFile(createdfile);
		
		} catch (UnsupportedOperationException  e) {
			e.printStackTrace();
			throw new UnsupportedOperationException  ("原子的に設定できない属性が配列に含まれます");
		
		} catch (FileAlreadyExistsException e) {
			e.printStackTrace();
			throw new FileAlreadyExistsException ("同名ファイルがすでに存在します");
		
		} catch (NoSuchFileException e) {
			e.printStackTrace();
			throw new NoSuchFileException("ファイルが作成できませんでした");
		}
		
		return createdfile;
	}



	public String getRewritedFileName(String postedtime,String filename) {
		
		long time=System.currentTimeMillis();
		String nameadd  = new StringBuilder(postedtime).append("_").append(String.valueOf(time)).toString();
		String newFileName = nameadd + filename;
		return newFileName;
	}

	/**
	 * test
	 */
	
	public List<String> test(Path path,Path createdFile,Path createdDir){
		
		List<String> test=new ArrayList<>();
		test.add(path.toString());
		test.add(createdFile.toString());
		test.add(createdDir.toString());
		return test;
	}
}
