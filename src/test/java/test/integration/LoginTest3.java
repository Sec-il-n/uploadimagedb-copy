package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class LoginTest3 {
	static IDatabaseTester tester;
	static DataSource source;
	static WebDriver driver;
	String xmlfile="src/test/resources/xml/dataset_account.xml";
	String url="http://localhost:5000/";
	RegisterTest4 re4;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDB();
		PrepForTest4.setUpBeforeClassDriver();;

	}
	@BeforeEach
	void setUp() throws Exception {
		re4=new RegisterTest4();
		tester=re4.setUpDbTester(xmlfile);
		source=re4.getDataSource2();
		driver=re4.setUpDriver(url);
	}

	@AfterEach
	void tearDown() throws Exception {
		re4.tearDown(tester);
		re4.tearDownDriver(driver);
	}
	/**
	 * tableに存在するAccountを引数にとりログインに成功する
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int age) throws ClassNotFoundException {
//		String link="簡単Login";
//		Select dropdown=new Select(driver.findElement(By.className("navbar-toggler")));
//		dropdown.selectByVisibleText(link);
		assertEquals(driver.getTitle(),"Welcome to Your Your Picture*s Blog");
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.className("navbar-toggler")).click();
		}
		driver.findElement(By.id("navbarDropdown")).click();
		driver.findElement(By.id("login")).click();
		driver.navigate().forward();
		assertEquals("Login Form",driver.getTitle());
		driver.findElement(By.name("userId")).sendKeys(userId);
		driver.findElement(By.name("pass")).sendKeys(pass);
		driver.findElement(By.name("loginform")).submit();
		//↓ not found
//	x	driver.findElement(By.className("btn btn-secondary btn-sm")).submit();//(ok click)on submit type button
		driver.navigate().forward();
		assertEquals("menu",driver.getTitle());
	}


}
