package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.logic.Base64Logic;
import model.logic.CreateFileLogic;
import model.logic.FileUploadCheckLogic;
import model.logic.FileUploadLogic;
import model.logic.GetPathLogic;
import servlet.TmpFileUpload;
import servlet.UploadComplete;

class TmpFileUploadTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	HttpServletRequest req;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Mock
	Part part;
	@Mock
	CreateFileLogic clogic;
//	@InjectMocks//need assert of(flogic)x
	@Spy
	FileUploadLogic flogic;
	@Spy
	FileUploadCheckLogic fulogic;
	InputStream in;
	@Spy
	Base64Logic blogic;
	String b64;
	String to;
	Path tmpDir;
	Path createdFile;
	int expectedCode;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	@BeforeEach
	void set() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);

	}
	@BeforeEach
	void set64() throws Exception {
		blogic=new Base64Logic(part, "jpg");//

	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@BeforeEach
	void setStream() throws Exception {
		in=new FileInputStream("src/test/resources/1563062226948_test.jpg");
	}
	@BeforeEach
	void setDir() throws Exception {
		GetPathLogic gplogic=new GetPathLogic();
		tmpDir=gplogic.getTmpDir();
	}

	@AfterEach
	void tearDown() throws Exception {
		in.close();
	}
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test(String title,String filename,String text,String userId,String date_time) throws IOException {

		mockReq=factory.createMockRequest();
		factory.addRequestWrapper(mockReq);//<= mockReq doesn't implements getPart()--Wrapper classで解決
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.setSessionAttribute("userId", "Shima5");
		testModule.addRequestParameter("title", title);
		testModule.addRequestParameter("text", text);
		when(part.getInputStream()).thenReturn(in);
		when(part.getSize()).thenReturn((long)1024);
		testModule.setSessionAttribute("data", part);//setSession-->(Str key,Object value)<=> req-->(Str,Str)
		when(clogic.createFile(tmpDir, null)).thenReturn(Path.of(tmpDir.toString(), "test.tmp"));
		createdFile = clogic.createFile(tmpDir, null);
//null-->Part=@Mock,それを引数にとるflogic=@Spyで解決
		doReturn(true).when(flogic).execute(part, createdFile);
		b64=blogic.execute64(createdFile.toString());
		when(blogic.execute64(createdFile.toString())).thenReturn(b64);

		testModule.setSessionAttribute("title", title);
		testModule.setSessionAttribute("text", text);
		testModule.setSessionAttribute("base64", b64);
		to="/upload_image_db4/ShowThumbnail";
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(TmpFileUpload.class);
		try {
			testModule.doPost();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());
	}
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void test1_3(String msg,String title,String text,String filename) {
		expectedCode=200;
		mockReq=factory.createMockRequest();
		factory.addRequestWrapper(mockReq);
		req=factory.getWrappedRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();

		testModule.setSessionAttribute("userId", "Shima5");
		testModule.addRequestParameter("title", title);
		testModule.addRequestParameter("text", text);
		doReturn(msg).when(fulogic).checkTitle(title);//not a mock
		doReturn(msg).when(fulogic).checkText(text);
		doReturn(msg).when(fulogic).checkFile(part);
		req.setAttribute("title", title);
		req.setAttribute("text", text);
		req.setAttribute("checkTitle", msg);
		req.setAttribute("checkText", msg);
		req.setAttribute("checkFile", msg);
		mockReq.setRequestDispatcher(to, null);//setReqDsp=not in req
		testModule.createServlet(UploadComplete.class);
		try {
			testModule.doPost();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}

		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
	}
	static Stream<Arguments> argumentsProvider(){
		return Stream.of(
			Arguments.of("titleを入力してください。Blank","","hoge","test1.jpg"),
			Arguments.of("textを入力してください。Blank","hoge","","test1.jpg"),
			Arguments.of("ファイルを添付してください。","hoge","hogehoge","")
		);
	}
}
