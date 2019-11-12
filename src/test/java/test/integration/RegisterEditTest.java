package test.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import model4test.LoginAutomatied;

class RegisterEditTest extends PrepForTest4{
	static WebDriver driver;
	RegisterEditTest ret;
	String url="http://localhost:8080/upload_image_db4";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PrepForTest4.setUpBeforeClassDriver();
		MysqlDump2.setBuckUp();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ret=new RegisterEditTest();
		driver=ret.setUpDriver(url);
	}

	@AfterEach
	void tearDown() throws Exception {
		MysqlDump2.setRerocate();
		ret.tearDownDriver(driver);
	}

	@Test
	void test() throws Exception {
		//login
		LoginAutomatied.setUpBeforeClass(driver);
		//register edit
		driver.findElement(By.id("regisetration")).isSelected();
		driver.findElement(By.id("regisetration")).click();
		threadSleep();
		driver.navigate().forward();
		assertEquals("edit registration",driver.getTitle());
		//configure register & submit
		driver.findElement(By.name("register_edit_form")).isEnabled();
		driver.findElement(By.name("userId")).clear();
		driver.findElement(By.name("userId")).sendKeys("test111");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("145735");
//		driver.findElement(By.name("name")).sendKeys("test");
//		driver.findElement(By.name("age")).sendKeys("45");
		driver.findElement(By.name("register_edit_form")).submit();
		threadSleep();
		driver.navigate().forward();
		//confirmation
		assertEquals("confirm register",driver.getTitle());
		driver.findElement(By.id("confirm_edit_account")).isSelected();
		driver.findElement(By.id("confirm_edit_account")).click();
		threadSleep();
		driver.navigate().forward();
		assertEquals("Login Form",driver.getTitle());
		//find msg
		driver.findElement(By.id("msg")).getText().equals("書き換え完了しました。(再ログインして下さい。)");
	}
	public static void threadSleep() {
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
