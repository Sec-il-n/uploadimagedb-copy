package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import dao.LoginDAO;
import model4test.GetDataSourceLogic;

class LoginTest2 {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";
    private static IDatabaseTester dbTester;
    private static DataSource dataSource;
    LoginDAO dao;

    static WebDriver driver;

    @BeforeAll
	static void setUpBeforeClassDB() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/sql/schema_2.sql", null , false);
		dbTester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
	}
    @BeforeAll
	static void setUpBeforeClassDriver() throws Exception {
		System.setProperty("webdriver.chrome.driver","/Applications/selenium-java-3.141.59/chromedriver");
	}
    @BeforeEach
	void setUpDriver() throws Exception {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/upload_image_db4");//toppage
	}
    @BeforeEach
	void setUpDB() throws Exception {
		IDataSet dataSet=new FlatXmlDataSetBuilder().build(new File("src/test/resources/xml/dataset_account.xml"));
		dbTester.setDataSet(dataSet);//順番注意(set)
	    dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);//順番注意(execute)
		dbTester.onSetup();
	}
	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();
		dao=new LoginDAO(dataSource);
	}
	@AfterEach
	void tearDownDriver() {
		driver.quit();
	}
	@AfterEach
	void tearDownDB() throws Exception {
		dbTester.setTearDownOperation(DatabaseOperation.NONE);
		dbTester.onTearDown();
	}
	/**
	 * tableに存在するAccountを引数にとりログインに成功する
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int age) throws ClassNotFoundException {
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.id("login")).click();
			driver.navigate().forward();
			driver.findElement(By.id("userId")).sendKeys(userId);
			driver.findElement(By.id("pass")).sendKeys(pass);
			driver.navigate().forward();
			assertEquals(driver.getTitle(),"menu");

		}
	}
	/**
	 * tableに存在しないAccountを引数にとりログインに失敗する。(表示されるtextをassert)
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void test2(String userId,String pass,String name,int age) throws ClassNotFoundException {
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.id("login")).click();
			driver.navigate().forward();
			driver.findElement(By.id("userId")).sendKeys(userId);
			driver.findElement(By.id("pass")).sendKeys(pass);
			driver.navigate().forward();
			assertEquals("Login Form",driver.getTitle());
			assertTrue(driver.findElement(By.className("alert alert-danger")).isDisplayed());

		}
	}

}
