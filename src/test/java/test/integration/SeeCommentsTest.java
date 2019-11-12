package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import model4test.LoginAutomatied;

class SeeCommentsTest extends PrepForTest4{
	static WebDriver driver;
	SeeCommentsTest sct;
	String url="http://localhost:8080/upload_image_db4";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDriver();
//		MysqlDump2.setBuckUp();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		sct=new SeeCommentsTest();
		driver=sct.setUpDriver(url);
	}

	@AfterEach
	void tearDown() throws Exception {
//		MysqlDump2.setRerocate();
		sct.tearDownDriver(driver);
	}

	@Test
	void test() throws Exception {
		//login
		LoginAutomatied.setUpBeforeClass(driver);
		//show comments
		driver.findElement(By.id("see")).isSelected();
		driver.findElement(By.id("see")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		assertEquals("Upload Comments",driver.getTitle());
		//configure paging(after)
		driver.findElement(By.id("after")).isSelected();
		driver.findElement(By.id("after")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		//select particular title
		driver.findElement(By.linkText("sample1->11")).isSelected();
		driver.findElement(By.linkText("sample1->11")).click();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.navigate().forward();
		//is good button enabled
		driver.findElement(By.id("good")).isSelected();//x enabled-->form ,button
		assertEquals("http://localhost:8080/upload_image_db4/ShowEachComment?id=1",driver.getCurrentUrl());

	}

}
