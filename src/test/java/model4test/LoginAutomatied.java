package model4test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginAutomatied {
/*
 * login with typical value
 */
	public static void setUpBeforeClass(WebDriver driver) throws Exception {
		String uId="Shima5";
		String pass="135790";
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
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			assertEquals("menu",driver.getTitle());//
	}
	/*
	 * login with parameter value
	 */
	public static void setUpBeforeClass(WebDriver driver,String uId,String pass) throws Exception {
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
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		assertEquals("menu",driver.getTitle());//
	}
}
