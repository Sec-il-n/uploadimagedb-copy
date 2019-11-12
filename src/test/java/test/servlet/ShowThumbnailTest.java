package test.servlet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.logic.CheckDirectoryContentsLogic;
import servlet.GoodComment;

class ShowThumbnailTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Spy
	CheckDirectoryContentsLogic logic;//using actual value get NullPointer in assertion
	Path tmpPath;
	String userId;
	String to;
	String msg;
	int expectedCode;

	@BeforeEach
	void setUp() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	static Stream<Arguments> argumentsProvider1(){
		return Stream.of(
				Arguments.of("src/test/resources/1563062226948_test.jpg","Shima3"),
				Arguments.of("src/test/resources/empty.txt","Shima3"),
				Arguments.of("src/test/resources/xml/dataset_account.xml","Shima2")
		);
	}
	static Stream<Arguments> argumentsProvider2(){
		return Stream.of(
				Arguments.of("","Shima3"),
				Arguments.of("src/_test.jpg","Shima3"),
				Arguments.of("src/test/resources/empty.txt",""),
				Arguments.of("src/dataset.xml","Shima2")
				);
	}
	@ParameterizedTest
	@MethodSource("argumentsProvider1")
	void test(String s,String userId) {
		tmpPath=Paths.get(s);
		to="/WEB-INF/jsp/thumbnail.jsp";
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.addRequestParameter("tmpPath", tmpPath.toString());
		testModule.setSessionAttribute("userId", userId);
		doReturn(true).when(logic).checkDirectry(tmpPath);//cant use Spy when arguments null
		mockReq.setRequestDispatcher(to, null);
		testModule.createServlet(GoodComment.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;

		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());

	}
	@ParameterizedTest
	@MethodSource("argumentsProvider2")
	void test2(String s,String userId) {
		try {
			tmpPath=Paths.get(s);
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		to="/WEB-INF/jsp/thumbnail.jsp";//provider2だとtoが2種
		msg="入力し直して下さい。";
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.addRequestParameter("tmpPath", tmpPath.toString());
		testModule.setSessionAttribute("userId", userId);
		doReturn(false).when(logic).checkDirectry(tmpPath);//nullPointer
		mockReq.setAttribute("msg", msg);
		mockReq.setRequestDispatcher(to, null);
		testModule.createServlet(GoodComment.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;
		assertThat(mockReq.getAttribute("msg"),is("入力し直して下さい。"));//cat't get any just run doGet().need set to mockReq
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());

	}

}
