package test.servlet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.io.IOException;

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
import model.logic.RegisterCheckLogic;
import model.logic.RegisterLogic;
import servlet.RegisterServlet;

class RegisterServletTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpSession mockSession;
//	HttpServletRequest req;
//	HttpSession session;
//	@Mock
	@Spy
	RegisterCheckLogic clogic;
	@Spy
	RegisterLogic logic;

	Account account;
//@BeforeAllでNestedException これ本来テスト中に書いてあった(?)
	@BeforeEach
	void setUp() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);

	}
	//can't create before configure testModule
	@BeforeEach
	void set() {
//		mockReq=factory.createMockRequest();
//		mockSession=factory.createMockSession();
//	x	req=factory.getWrappedRequest();//nullpointer===>need addRequestWrapper to get
//	x	session=factory.getWrappedRequest().getSession();
	}

	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
/**
 * パラメターの値が”done”で登録事項の最終処理(DB書き込み)と完了の画面移遷をする
 * @param userId
 * @param pass
 * @param name
 * @param age
 * @throws IOException
 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)//old
	void testDoGet1(String userId,String pass,String name,int age) throws IOException {

		account=new Account(userId, pass, name, age);
		testModule.setSessionAttribute("account", account);
		testModule.addRequestParameter("action", "done");
		testModule.createServlet(RegisterServlet.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		boolean result = true;
		mockSession=factory.createMockSession();
		Account ac=(Account)mockSession.getAttribute("account");
		//spy
		doReturn(result).when(logic).execute(ac);
//		when(logic.execute(account)).thenReturn(result);
		assertThat(logic.createMsg(result),is("登録完了しました。ログインして下さい。"));
	}

/**
 * パラメターの値が”null”で登録事項の入力画面移遷をする
 */
	@Test
	void testDoGet2() {
		mockReq=factory.getMockRequest();
		testModule.createServlet(RegisterServlet.class);
		testModule.doGet();

		assertNull(mockReq.getParameter("action"));
//Answer == mock
//		when(x mock => mockReq.getRequestDispatcher(anyString())).thenAnswer(i -> {
//			return i.getArgument(0);
//		});
	}
/**
 * doPost 登録可能な入力値
 * @param userId
 * @param pass
 * @param name
 * @param i
 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)//old
	void testDoPost1(String userId,String pass,String name,int i) {
		String age=String.valueOf(i);
		testModule.addRequestParameter("userId", userId);
		testModule.addRequestParameter("pass", pass);
		testModule.addRequestParameter("name", name);
		testModule.addRequestParameter("age", age);
		testModule.createServlet(RegisterServlet.class);
		testModule.doPost();
//		mockReq=factory.getMockRequest();
//		mockSession=factory.createMockSession();
		int expectedCode=200;
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());


//		when(mockReq).setAttribute(anyString(), anyString()).thenAnswer(i -> {
//
//		});
//x  単なるLogicの挙動確認=>
//		assertEquals(clogic.findBlank(userId),userId);
//		assertEquals(clogic.normalize(age),age);
//		assertNull(clogic.checkPass(pass));
//		doReturn(null).when(clogic).checkName(name);
//	 x 'verify==自動的に'呼び出されなければならない
//		verify(clogic).notHavingErrormsg();

//	x	単なるLogicの挙動確認=> assertEquals("/WEB-INF/jsp/registerResult.jsp",logic.notHavingErrormsg());
//	x	doReturn("/WEB-INF/jsp/registerResult.jsp").when(logic).notHavingErrormsg();
//	x	verify(logic).notHavingErrormsg();//need to be differ method
	}

/**
 * doPost 置換処理で登録可能な入力値
 * @param userId
 * @param pass
 * @param name
 * @param i
 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_before_replaced.csv",numLinesToSkip=1)
	void testDoPost2(String userId,String pass,String name,int i) {
		String age=String.valueOf(i);
		testModule.addRequestParameter("userId", userId);
		testModule.addRequestParameter("pass", pass);
		testModule.addRequestParameter("name", name);
		testModule.addRequestParameter("age", age);
		testModule.createServlet(RegisterServlet.class);
		testModule.doPost();
/*******dont need********/
		mockSession=factory.createMockSession();
		assertNull(clogic.checkName(name));
		//  数値 <==文字列 <== 数値 & 文字列
		assertNull(clogic.checkAge(clogic.checkNumber(clogic.normalize(age))));
//		x 抜粋なのでNullpointer(挙動の確認はlogicで)
// 年齢条件 <== 数値 <== 文字列 <== 数値 & 文字列
//		assertNull(clogic.checkAge(clogic.checkAge(clogic.checkNumber(clogic.normalize(age)))));
/************************/
		int expectedCode=200;
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());

	}


}
