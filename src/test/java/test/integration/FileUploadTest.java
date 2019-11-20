package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import model4test.DeleteObjectsforTest;
import model4test.GetObjectToDeleteLogic;
import model4test.ListKeysVersioningEnabledBucket;
//
class FileUploadTest extends PrepForTest4{
	static WebDriver driver;
	String url="http://localhost:8080/upload_image_db4";
	FileUploadTest fut;
	static List<String> keys;
	static Map<String,String> s3Keys;
	static Map<String,String> toDelete;
	static boolean result;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		PrepForTest4.setUpBeforeClassDriver();
		MysqlDump2.setBuckUp();

	}

	@BeforeAll
	static void manageS3() throws Exception {
//		keys=GetObjectToDeleteLogic.getKeysList();
	}

	@BeforeEach
	void setUp() throws Exception {
		fut=new FileUploadTest();
		driver=fut.setUpDriver(url);
	}
	@AfterAll
	static void tearDoumDB() {
		MysqlDump2.setRerocate();
	}
	@AfterAll
	static void rollBackS3Bucket() throws Exception {
		ListKeysVersioningEnabledBucket bucketKeys=new ListKeysVersioningEnabledBucket();
		s3Keys=bucketKeys.getListvarsionig();
//		toDelete=GetObjectToDeleteLogic.getObjectKeyName(s3Keys, keys);
		toDelete=GetObjectToDeleteLogic.getObjectKeyName(s3Keys);

	}
	@AfterAll
	static void deleteObjects() throws Exception {
//		result=DeleteObjectsforTest.deleteObject(toDelete);
		DeleteObjectsforTest.dialog(result);
	}
	@AfterEach
	void tearDown() throws Exception {
		fut.tearDownDriver(driver);
	}

	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test(String title,String filename,String text,String userId,String date_time) throws IOException {
		String uId="Shima5";
		String pass="135790";
		url="/Users/secil/Google ドライブ/test_kq98qtnhvh9k8et.jpg";
		//login
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.className("navbar-toggler")).click();
		}
			driver.findElement(By.id("navbarDropdown")).click();
			driver.findElement(By.id("login")).click();
			driver.navigate().forward();
			driver.findElement(By.name("userId")).sendKeys(uId);
			driver.findElement(By.name("pass")).sendKeys(pass);
			driver.findElement(By.name("loginform")).submit();
			driver.navigate().forward();
			assertEquals("menu",driver.getTitle());//
			driver.findElement(By.id("post")).isSelected();
			driver.findElement(By.id("post")).click();
			driver.navigate().forward();
			assertTrue(driver.findElement(By.name("uploadform")).isEnabled());
			driver.findElement(By.name("title")).sendKeys(title);
			driver.findElement(By.name("text")).sendKeys(text);
			//select file
			driver.findElement(By.name("data")).sendKeys(url);
			driver.findElement(By.name("uploadform")).submit();
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			driver.navigate().forward();
//	△		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);//doesn't wait
//	x		assertEquals("/upload_image_db4/ShowThumbnail",driver.getCurrentUrl());
			url="http://localhost:5000/ShowThumbnail";
			assertEquals(url,driver.getCurrentUrl());
			assertEquals("thumbnail",driver.getTitle());
			driver.findElement(By.id("post")).isSelected();
			driver.findElement(By.id("post")).click();

			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			driver.navigate().forward();
			url="http://localhost:5000/ToLoginResult";
			assertEquals(url,driver.getCurrentUrl());
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	}
}

