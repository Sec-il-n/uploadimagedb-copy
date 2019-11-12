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

class RegisterTest4 extends PrepForTest4{
	static IDatabaseTester tester;
	static DataSource source;
	static WebDriver driver;
	String xmlfile="src/test/resources/xml/dataset_account.xml";//
	String url="http://localhost:8080/upload_image_db4";
	RegisterTest4 re4;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDB();//
		PrepForTest4.setUpBeforeClassDriver();
//		MysqlDump2.setBuckUp();

	}
	@BeforeEach
	void setUp() throws Exception {
		re4=new RegisterTest4();
		tester=re4.setUpDbTester(xmlfile);//
		driver=re4.setUpDriver(url);
	}
//	@AfterAll
//	static void tearDoumDB() {
//		MysqlDump2.setRerocate();
//	}
	@AfterEach
	void tearDown() throws Exception {
		re4.tearDown(tester);//
		re4.tearDownDriver(driver);
	}
	/**
	 * NG表示のassertion->
	 * 〜既存のuserIdを指定して、書き込みが失敗する。
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int n) throws ClassNotFoundException {
		String age=String.valueOf(n);
		String linkText="登録する";
		url="http://localhost:8080/upload_image_db4/RegisterServlet?action=done";
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
//		driver.findElement(By.className("navbar-toggler")).isEnabled();
			driver.findElement(By.className("navbar-toggler")).click();
		}

		driver.findElement(By.id("register")).click();
		driver.navigate().forward();
		assertTrue(driver.findElement(By.name("registerform")).isEnabled());

		driver.findElement(By.id("userId")).sendKeys(userId);
		driver.findElement(By.id("pass")).sendKeys(pass);
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("age")).sendKeys(age);
		driver.findElement(By.name("submitregister")).submit();
		driver.navigate().forward();
		assertEquals(driver.getTitle(),"confirm register");
		driver.findElement(By.linkText(linkText)).isDisplayed();
		driver.findElement(By.linkText(linkText)).click();
		driver.navigate().forward();
		assertEquals(driver.getCurrentUrl(),url);

	}

}
