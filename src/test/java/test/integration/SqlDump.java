//package test.integration;
//
//import java.io.File;
//import java.net.URISyntaxException;
//import java.security.CodeSource;
//
//
//class SqlDump {
//	//?CodeSource(URL,null)をnew してcatchしたいが(  ,null)できない
//	public SqlDump() throws  URISyntaxException {
//		super();
//	}
//
//	//アクセス権を使って呼び出すことによってProtectionDomainを問題なく取得できることを確認
//	//Getting path to the Jar file being executed
//	CodeSource codeSource=SqlDump.class.getProtectionDomain().getCodeSource();
//	String path=codeSource.getLocation().toURI().getPath();
//	File jarFile=new File(path);
//	String jarDir=jarFile.getParentFile().getPath();
//
//	String dbName="Practice_MVC";
//	String dbUser="root";
//	String dbpass="seri331";
//	//Creating Path Constraints(圧縮?) for folder saving
//	//& folder doesn't exists
//	String folderpath=jarDir + "/backup";
//	File file=new File(folderpath);
//	file.mkdirs();...<?
//
//}
