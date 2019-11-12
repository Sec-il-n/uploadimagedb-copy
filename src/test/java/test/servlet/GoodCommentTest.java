package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.sql.DataSource;

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

import model.logic.GoodCommentLogic;
import model4test.GetDataSourceLogic;
import servlet.GoodComment;

class GoodCommentTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Mock
	GoodCommentLogic glogic;
	DataSource source;
	boolean result;
	int count;
	String to;
	int expectedCode;
	@BeforeEach
	void set() throws Exception {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
	}
	@BeforeEach
	void setUp() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);
	}
//	@BeforeEach
//	void initMock() throws Exception {
//		MockitoAnnotations.initMocks(this);
//	}
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void testdoGet1(String userId,String by,String id) {
		glogic=new GoodCommentLogic(userId,by,id,source);
		MockitoAnnotations.initMocks(this);
		result=true;
		count=4;
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();

		testModule.addRequestParameter("good", id);
		testModule.addRequestParameter("by", by);
		testModule.setSessionAttribute("userId", userId);
		when(glogic.goodCount()).thenReturn(result);
		when(glogic.total(id, source)).thenReturn(count);
		testModule.setSessionAttribute("count", count);
		to="/upload_image_db4/ShowEachComment";
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(GoodComment.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());

	}

}
