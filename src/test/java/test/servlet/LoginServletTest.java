package test.servlet;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.beans.Account;
import model.logic.LoginLogic;
import servlet.LoginServlet;
//要改善　assertion追加(現 Mockの意味無し)
class LoginServletTest {
	ServletTestModule testModule;//parameter関連
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpSession session;
	String userId;
	String pass;
	Account account;
	Account accountRe;
	String path;
	String file;
	@Spy
	LoginLogic logic ;
//factry. で作成した方のreqしか使えない
	@BeforeEach
	void setUp() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);

	}
	@BeforeEach
	void set() throws Exception {

	}
	@BeforeEach
	void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void testDoGet() {
		mockReq=factory.createMockRequest();
		session=factory.createMockSession();
		path="/WEB-INF/jsp/login.jsp";
		mockReq.setRequestDispatcher(path, null);
		testModule.createServlet(LoginServlet.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		int expectedCode=200;

		testModule.doGet();
		assertEquals(expectedCode,factory.getMockResponse().getErrorCode());//mockResponce==need2 set?
	}
	/**
	 * Accountに存在するuserId,passwordを引数にとり、登録内容を取得できる
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void testPost(String userId,String pass,String name,int age) throws Exception {
		mockReq=factory.createMockRequest();
		session=factory.createMockSession();
		account=new Account(userId, pass);
		accountRe=new Account(userId, pass, name, age);

		testModule.addRequestParameter("userId", userId);
		testModule.addRequestParameter("pass", pass);
//		accoutReturned = logic.execute(account);
		doReturn(accountRe).when(logic).execute(account);
//		session.setAttribute("userId",userId);
		testModule.setSessionAttribute("userId",userId);
//	    session.setAttribute("account",accoutReturned);
	    testModule.setSessionAttribute("account",accountRe);
	    session.setMaxInactiveInterval(900);//
	    path="/WEB-INF/jsp/loginResult.jsp";
	    assertThat(logic.acNotNull(),is(path));
		testModule.createServlet(LoginServlet.class);
		try {
			testModule.doPost();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		//assertion追加(現 Mockの意味無し)
		int expectedCode=200;
		assertThat(mockReq.getRequestDispatcher(path),is(not(nullValue())));
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
//>>=除去、変更
//		mockReq=factory.getMockRequest();
//	>>	req=factory.getWrappedRequest();
//	>>	session=factory.getWrappedRequest().getSession();
	}

	/**
	 * Accountに存在しないuserId,passwordを引数にとり、登録内容を取得できない
	 * @throws Exception
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void testPost2(String userId,String pass,String name,int age) throws Exception {
		mockReq=factory.createMockRequest();
		session=factory.createMockSession();
		account=new Account(userId, pass);
		accountRe=null;

		testModule.addRequestParameter("userId", userId);
		testModule.addRequestParameter("pass", pass);
//		accountReturned = logic.execute(account);
		doReturn(accountRe).when(logic).execute(account);
		String msg="ユーザーIdかパスワードが違います。";
		testModule.addRequestParameter("msg", msg);
//		path=logic.acNull();
		path="/WEB-INF/jsp/login.jsp";
		doReturn(path).when(logic).acNull();
		testModule.createServlet(LoginServlet.class);
		try {
			testModule.doPost();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		//assertion追加(現 Mockの意味無し)
		int expectedCode=200;
		assertThat(mockReq.getRequestDispatcher(path),is(not(nullValue())));
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());


	}

}
