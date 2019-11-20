package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import model4test.LoginAutomatied;

class RegisterDeleteTest extends PrepForTest4{
	static WebDriver driver;
	private static String URL="http://localhost:5000/";
	RegisterDeleteTest rdt;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDriver();
		MysqlDump2.setBuckUp();
	}
	@BeforeEach
	void setUp() throws Exception {
		rdt=new RegisterDeleteTest();
		driver=rdt.setUpDriver(URL);
	}

	@AfterEach
	void tearDown() throws Exception {
		MysqlDump2.setRerocate();
		rdt.tearDownDriver(driver);
	}

	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int age) throws Exception {
		//login
		LoginAutomatied.setUpBeforeClass(driver,userId,pass);
		//register delete
		driver.findElement(By.id("Register delete")).isSelected();
		driver.findElement(By.id("Register delete")).click();
//		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		threadSleep();
		driver.navigate().forward();
		assertEquals("delete conf",driver.getTitle());
		//
		driver.findElement(By.id("register_delete_confirm")).isSelected();
		driver.findElement(By.id("register_delete_confirm")).click();
//		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		threadSleep();
		driver.navigate().forward();
		assertEquals("http://localhost:5000",driver.getCurrentUrl());
		assertEquals("登録を取り消しました。",driver.findElement(By.id("msg")).getText());
	}
	public static void threadSleep() {
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
