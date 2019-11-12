package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.beans.Account;
import model.logic.RegisterLogic;
import servlet.RegisterServlet;

class RegisterDeleteTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Mock
	RegisterLogic logic;
	@Mock
	JdbcDataSource dataSource;
	String userId;
	Account account;
	String msg;
	String to;
	int expectedCode;

	@BeforeEach
	void set() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)//old
	void testDoGet1(String userId,String pass,String name,int age) throws IOException {
		account=new Account(userId,pass);
		msg="登録を取り消しました。";
	    to="/upload_image_db4";
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		when(logic.delete(account)).thenReturn(true);
		testModule.setSessionAttribute("account", account);
		testModule.setSessionAttribute("userId", userId);
		testModule.setSessionAttribute("msg", msg);
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(RegisterServlet.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());

	}

}
