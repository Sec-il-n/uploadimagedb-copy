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
		
		Context initContext = new InitialContext();//1
		Context context  = (Context)initContext.lookup("java:/comp/env/");
		DataSource source = (DataSource)context.lookup("jdbc/heroku_019203fe6399d5c");

		return source;
	}

	public MysqlDataSource getClearDBDatasource() throws IOException {

		Properties prop=new Properties();
		MysqlDataSource source=new MysqlDataSource();
		
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


}

