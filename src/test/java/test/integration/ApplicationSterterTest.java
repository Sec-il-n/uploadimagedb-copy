package test.integration;

//import net.sourceforge.jwebunit.junit.WebTester;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

//using JWebUnit（成果物外す）
class ApplicationSterterTest {

	static String TESTING_ENGINE_HTMLUNIT="TestingEngineHtmlUnit";//<-key
//	static String TESTING_ENGINE_WEBDRIVER = "TestingEngineWebdriver";
	static String URL="http://localhost:5000/";

	@BeforeEach
	void setUp() throws Exception {
		setBaseUrl(URL);

	}

	@BeforeEach
	void setUpTectingEngine() throws Exception {
		String url = "net.sourceforge.jwebunit.htmlunit.HtmlUnitTestingEngineImpl";
		TestingEngineRegistry.addTestingEngine(TESTING_ENGINE_HTMLUNIT, url);//
//		addTestingEngine(String key, String classpath)
		setTestingEngineKey(TESTING_ENGINE_HTMLUNIT);
		//setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_WEBDRIVER);
	}
	@AfterEach
	void terdown() {
		closeBrowser();
	}

	@Test
	void testEntranceLogin() {
		beginAt("http://google.fr");
//		beginAt("/");
//		x beginAt("/index.html");　○　=DocBase(?)
		assertTitleEquals("Welcome to Your Photo*s blog");
		assertLinkPresent("login");//have to be id
		clickLink("login");//id属性
		assertTitleEquals("Login Form");
		setTextField("userId","Tester12");
        setTextField("pass","121212");//
        submit("submit","Login");//
	}


}
