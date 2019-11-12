package model.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;
/**
 *
 * @author secil
 *JNDI and JDBC
 *http://www.java2s.com/Code/Java/Database-SQL-JDBC/JNDIandJDBC.htm
 */
public class GetDataSourceLogic{
	String url;
	String userId;
	String pass;

	public DataSource getDataSource() throws NamingException {
//		ssl 使用の場合は以下を追加
//		System.setProperty("javax.net.ssl.trustStore","/home/project/truststore");
//		System.setProperty("javax.net.ssl.trustStorePassword","somepass");
		Context initContext = new InitialContext();//1
		Context context  = (Context)initContext.lookup("java:/comp/env/");//にバインドされたconyext
		DataSource source = (DataSource)context.lookup("jdbc/heroku_019203fe6399d5c");

		return source;
	}

	public MysqlDataSource getClearDBDatasource() throws IOException {

		Properties prop=new Properties();
		MysqlDataSource source=new MysqlDataSource();//DataSourceへのキャストはできない。setUrl,setUser..が定義されていないとなる。?
		try(InputStream in=GetDataSourceLogic.class.getResourceAsStream("/jdbc/jdbcDataSource_Mysql.properties")){
			prop.load(in);
			url=prop.getProperty("JDBC_URL");
			userId=prop.getProperty("DB_USERNAME");
			pass=prop.getProperty("DB_PASSWORD");

			source.setUrl(url);
			source.setUser(userId);
			source.setPassword(pass);

			return source;
		}
	}

	public Properties getProperties() throws IOException {
		Properties prop=new Properties();
		try(InputStream in=GetDataSourceLogic.class.getResourceAsStream("/jdbc/jdbcDataSource_Mysql.properties")){
			prop.load(in);
		return prop;
		}
	}




//	private static MysqlDataSource mysqlDS = null;

//	public DataSource getMysqlDataSource() throws IOException, NamingException, SQLException{
////Oracleパフォーマンス拡張機能のプロパティの設定?
//    	Properties props = new Properties();
//		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
//
//
//		Context context = null;
//    	try {
//			context=new InitialContext();//1
//		} catch (NameNotFoundException e) {
//			System.err.println(e.getMessage());
//			e.printStackTrace();
//		}
//    	mysqlDS.setURL("jdbc:mysql:thin:root/@//localhost:3306/Practice_MVC");
//		mysqlDS.setUser("root");
//		mysqlDS.setPassword("");
////    	データソース・インスタンスをJNDIに登録
//    	context.bind("jdbc/upload_image_db4", mysqlDS);
//    	mysqlDS = (MysqlDataSource)context.lookup("jdbc/upload_image_db4");
//    	//->へ続くConnection conn = mysqlDS.getConnection();
//
//       return mysqlDS;
//    }


//	public DataSource getMysqlDataSource() throws IOException, NamingException, SQLException{
////Oracleパフォーマンス拡張機能のプロパティの設定
////		GetPathLogic gplogic =new GetPathLogic();
////		String s=gplogic.getPropertiesMysql().toString();
//		Properties props = new Properties();
////    	FileInputStream fis = new FileInputStream(s);
////		props.load(fis);//    	非props.put();
////    	mysqlDS.setProperties(props);
////		mysqlDS.x   setConnectionProperties(props);
//		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
////    	props.setProperty(Context.PROVIDER_URL, "file:/src/main/resources/jdbc");
////
////    	Properties props = new Properties();
////    	mysqlDS.setURL(props.getProperty("jdbc:mysql:thin:root/@//localhost:3306/Practice_MVC"));
//		mysqlDS.setURL("jdbc:mysql:thin:root/@//localhost:3306/Practice_MVC");
////		mysqlDS.setServerName("localhost");
//		mysqlDS.setUser("root");
//		mysqlDS.setPassword("");
////		mysqlDS.setDatabaseName("Practice_MVC");
//
//		Context context = null;
//		try {
//			context=new InitialContext(props);
//		} catch (NamingException e) {
//			System.err.println(e.getMessage());
//			e.printStackTrace();
//		}
////    	データソース・インスタンスをJNDIに登録
//		context.bind("jdbc/upload_image_db4", mysqlDS);
//		mysqlDS = (MysqlDataSource)context.lookup("jdbc/upload_image_db4");
//		//->へ続くConnection conn = mysqlDS.getConnection();
////		mysqlDS.setURL(props.getProperty("MYSQL_JDBC_URL"));
////		mysqlDS.setUser(props.getProperty("MYSQL_DB_USER"));
////		mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASS"));
////		mysqlDS.setDatabaseName(props.getProperty("MYSQL_DB_NAME"));
//
//		return mysqlDS;
//	}


}

