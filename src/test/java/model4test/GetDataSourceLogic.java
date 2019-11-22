package model4test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.h2.jdbcx.JdbcDataSource;
/**
 *
 * @author secil
 *
 */

public class GetDataSourceLogic{

	Properties props;
	JdbcDataSource dataSource;


public JdbcDataSource getH2DataSource1() throws IOException{

	dataSource = new JdbcDataSource();
	dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;TRACE_LEVEL_FILE=4");
	dataSource.setUser("user");
	dataSource.setPassword("pass");
	return dataSource;

}
/**
 *
 * @return
 * @throws IOException
 */
//1027 assertNotNUll で確認済
public JdbcDataSource getH2DataSource() throws IOException{

	props = new Properties();
	dataSource = new JdbcDataSource();
	
	try(InputStream in=GetDataSourceLogic.class.getResourceAsStream("/jdbc/JdbcDataSource_H2.properties")){
		
		props.load(in);
		dataSource.setURL(props.getProperty("JDBC_URL"));
		dataSource.setUser(props.getProperty("USER"));
		dataSource.setPassword(props.getProperty("PASSWORD"));

	}catch(IOException e){
		e.printStackTrace();
	}
	
	return dataSource;

}
	/**
	 * resource bundle
	 */
	public JdbcDataSource getH2DataSourceResourceBundle() throws IOException{
		
		ResourceBundle bundle=null;
		dataSource = new JdbcDataSource();
		
		try {
			bundle=ResourceBundle.getBundle("JdbcDataSource_H2");
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		
		dataSource.setURL(bundle.getString("JDBC_URL"));
		dataSource.setUser(bundle.getString("USER"));
		dataSource.setPassword(bundle.getString("PASSWORD"));
		return dataSource;

	}



	/**
	 * only 4 LogicTest2
	 */
	public Properties getH2DataSource2() throws IOException{

		props = new Properties();
		
		try{
			FileInputStream in=new FileInputStream("src/test/resources/jdbc/JdbcDataSource_H2.properties");

			props.load(in);

		}catch(IOException e){
			e.printStackTrace();
		}
		
		return props;

	}

}


