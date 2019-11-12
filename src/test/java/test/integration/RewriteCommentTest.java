package test.integration;

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

import model4test.LoginAutomatied;

//class FileEditTest extends PrepForTest4{
class RewriteCommentTest extends PrepForTest4{
	static WebDriver driver;
	RewriteCommentTest rct;
	String url="http://localhost:8080/upload_image_db4";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDriver();
		MysqlDump2.setBuckUp();
	}
	@BeforeEach
	void setUp() throws Exception {
		rct=new RewriteCommentTest();
		driver=rct.setUpDriver(url);
	}
	@AfterAll
	static void tearDoumDB() {

	}
	@AfterEach
	void tearDown() throws Exception {
		MysqlDump2.setRerocate();
		rct.tearDownDriver(driver);
	}
//replace dialog or control

	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_to_rewrite.csv",numLinesToSkip=1)
	void test(String title,String filename,String text,String userId,String date_time) throws Exception {
		url="/Users/secil/Google ドライブ/test_fjq4u85roeseo9y0.jpg";
		LoginAutomatied.setUpBeforeClass(driver);
		driver.findElement(By.id("edit")).isSelected();
		driver.findElement(By.id("edit")).click();
		driver.navigate().forward();
		assertEquals("get file to delete",driver.getTitle());
		driver.findElement(By.linkText("sample6")).isEnabled();
		driver.findElement(By.linkText("sample6")).click();
//		driver.findElement(By.id("select title")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		assertTrue(driver.findElement(By.name("editform")).isEnabled());
		driver.findElement(By.name("title")).sendKeys(title);
		driver.findElement(By.name("text")).sendKeys(text);
		driver.findElement(By.name("data")).sendKeys(url);
		driver.findElement(By.name("editform")).submit();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		assertEquals("Thumbnail edited",driver.getTitle());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.id("rewriteImage_complete")).isSelected();
		driver.findElement(By.id("rewriteImage_complete")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		assertEquals("http://localhost:8080/upload_image_db4/ToLoginResult",driver.getCurrentUrl());
	}

}
