package test.integration;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

class LoginTest {
	static WebDriver driver;
//	static WebDriverWait waitDriver;
//	static Wait wait;
//	static long DEFAULT_SLEEP_TIMEOUT=30;
	static Select selectMenu;
	String title;
	String expected;
	String userId;
	String msg;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver","/Applications/selenium-java-3.141.59/chromedriver");
	}
	@BeforeEach
	void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/upload_image_db4");//toppage
	}
	@BeforeEach
	void setUpSelect() throws Exception {
	}
	@BeforeEach
	void setWait() throws Exception {
//		waitDriver = new WebDriverWait(driver,DEFAULT_SLEEP_TIMEOUT);//?
//		driver.wait(DEFAULT_SLEEP_TIMEOUT);//IllegalMonitorStateException
//		driver.manage().timeouts().implicitlyWait(DEFAULT_SLEEP_TIMEOUT, TimeUnit.SECONDS);//<-現れていたらすぐ終わる?
	}
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//		driver.quit();
//	}
	@AfterEach
	void tearDown() {
		driver.quit();
	}
	/**
	 * index.jsp(Home)-->login form-->correct user,pass-->get loginresult.jsp
	 */
	@Test
	void test1() {
		title="Welcome to Your Your Picture*s Blog";
		assertEquals(driver.getTitle(),title);
//		WebElement link=driver.findElement(By.id("login"));//-->link.sendKeys("id")
		assertTrue(driver.findElement(By.id("register")).isDisplayed());
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {//選択している必要がある↓click()
			assertTrue(driver.findElement(By.linkText("簡単ログイン")).isDisplayed());
			assertTrue(driver.findElement(By.linkText("Register")).isDisplayed());
//			driver.findElement(By.id("login")).click();//isSelected()-->checkbox,radio button,drop_down
			driver.findElement(By.id("login")).click();
//			assertTrue(driver.findElement(By.name("loginform")).isDisplayed());
			driver.navigate().forward();
			assertTrue(driver.findElement(By.name("loginform")).isEnabled());//有効
			driver.findElement(By.id("userId")).sendKeys("Shima5");
			driver.findElement(By.id("pass")).sendKeys("135790");
			driver.findElement(By.name("submit")).submit();
			driver.navigate().forward();
			assertEquals(driver.getTitle(),"menu");
		}
		//同じコードだがif()にしてここで切るとerror like name="loginform" x
//		if(driver.findElement(By.name("loginform")).isEnabled()) {
//			driver.findElement(By.id("userId")).sendKeys("Shima5");
//			driver.findElement(By.id("pass")).sendKeys("135790");
//			driver.findElement(By.name("submit")).submit();
//			driver.navigate().forward();
//			assertEquals(driver.getTitle(),"menu");
//		}
	}
//	@Test
//	void test2() {
//		msg="ユーザーIdかパスワードが違います。";
//		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
//			driver.findElement(By.id("login")).click();
//			driver.navigate().forward();
//			driver.findElement(By.id("userId")).sendKeys("sh");
//			driver.findElement(By.id("pass")).sendKeys("123");
//			driver.navigate().forward();
//			assertThat(driver.switchTo().alert().getText(),is(msg));
//
//		}
//	}
	@ParameterizedTest
	@CsvFileSource(resources= "/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void test2_2(String userId,String pass,String name,int age) {
		msg="ユーザーIdかパスワードが違います。";
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.id("login")).click();
			driver.navigate().forward();
			driver.findElement(By.id("userId")).sendKeys(userId);
			driver.findElement(By.id("pass")).sendKeys(pass);
			driver.navigate().forward();
			assertThat(driver.switchTo().alert().getText(),is(msg));
		}
	}
	@ParameterizedTest
	@MethodSource("provider")
	void test3(String userId,String pass) {
		msg="ユーザーIdかパスワードが違います。";
		if(driver.findElement(By.className("navbar-toggler")).isSelected()) {
			driver.findElement(By.id("login")).click();
			driver.navigate().forward();
			driver.findElement(By.id("userId")).sendKeys(userId);
			driver.findElement(By.id("pass")).sendKeys(pass);
			driver.navigate().forward();
			assertThat(driver.switchTo().alert().getText(),is(msg));
		}
	}
	static Stream<Arguments> provider(){
		return Stream.of(
			Arguments.of("",""),
			Arguments.of(" ","  ")
		);
	}
}
//	@Test
//	void test3() {
		//exeption x select() was button
//		selectMenu=new Select(driver.findElement(By.className("navbar-toggler" )));//Select==dropdown
//		selectMenu.selectByVisibleText("簡単ログイン");
//		assertTrue(driver.findElement(By.id("login")).isSelected());
//	}

