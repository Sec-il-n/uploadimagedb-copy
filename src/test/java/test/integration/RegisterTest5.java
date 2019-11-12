package test.integration;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class RegisterTest5 extends PrepForTest4{
	static WebDriver driver;
	String url="http://localhost:8080/upload_image_db4";
	RegisterTest5 re5;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDriver();
		MysqlDump2.setBuckUp();
	}
	@BeforeEach
	void setUp() throws Exception {
		re5=new RegisterTest5();
		driver=re5.setUpDriver(url);
	}
	@AfterAll
	static void tearDoumDB() {
		MysqlDump2.setRerocate();
	}
	@AfterEach
	void tearDown() throws Exception {
		re5.tearDownDriver(driver);
	}
	/**
	 * dbにないuserIdを指定してaccount への書き込みが成功する。
	 * @throws InterruptedException
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void test2(String userId,String pass,String name,int n) throws ClassNotFoundException, InterruptedException{
		String age=String.valueOf(n);
		String linkText="登録する";
		String msg="登録完了しました。ログインして下さい。";
		url="http://localhost:8080/upload_image_db4/RegisterServlet?action=done";
		//4buckup dialog
//		driver.switchTo().alert().accept();
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
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
			assertTrue(driver.findElement(By.linkText(linkText)).isDisplayed());
			driver.findElement(By.linkText(linkText)).isSelected();
			driver.findElement(By.linkText(linkText)).click();
			driver.navigate().forward();
			assertEquals(driver.getTitle(),"Login Form");
//	x		driver.wait(10);//could use only case to be synchronized & locked
//	x		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));//cant use (driver,long)
//	??		but ⤴︎ need wait.until(Function<?super wabdriver ,v>)
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			assertThat(driver.findElement(By.id("msg")).getText(),is(msg));
//	x		assertThat(driver.findElement(By.className("alert alert-danger")).getText(),is(msg));
//	x		assertThat(driver.switchTo().alert().getText(),is(msg));
	}

}
