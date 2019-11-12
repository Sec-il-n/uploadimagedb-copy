package test.integration;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//import model.logic.GetDataSourceLogic;
import model4test.GetDataSourceLogic;
//for unit testing(dbunit)
//interface PrepForTest4 {
public class PrepForTest4 {
	public static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	public static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
	public static final String USER = "user";
	public static final String PASSWORD = "pass";

	//PrepForTest4.setUpBeforeClassDB()で呼び出し
	public static void setUpBeforeClassDB() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/sql/schema_2.sql", null , false);
	}
	public static void setUpBeforeClassDriver() throws Exception {
		System.setProperty("webdriver.chrome.driver","/Applications/selenium-java-3.141.59/chromedriver");
	}
//	public default IDatabaseTester setUpDbTester(String xmlfile) throws Exception {
	public IDatabaseTester setUpDbTester(String xmlfile) throws Exception {
		IDatabaseTester dbTester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
		IDataSet dataSet=new FlatXmlDataSetBuilder().build(new File(xmlfile));
		dbTester.setDataSet(dataSet);//順番注意(set)
	    dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);//順番注意(execute)
		dbTester.onSetup();
		return dbTester;
	}
//	public default DataSource getDataSource() throws IOException {
	public DataSource getDataSource2() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		DataSource dataSource=gdlogic.getH2DataSource1();
		return dataSource;

	}

//	public default WebDriver setUpDriver(String url) throws Exception {
	public WebDriver setUpDriver(String url) throws Exception {
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(url);
		return webDriver;
	}
	public void tearDown(IDatabaseTester dbTester) throws Exception {
		dbTester.setTearDownOperation(DatabaseOperation.NONE);
		dbTester.onTearDown();
	}
	public void tearDownDriver(WebDriver webDriver) {
		webDriver.quit();
	}
}
