package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
import model.logic.DeleteFileS3Logic;
import model.logic.FileUploadCheckLogic;
import model.logic.FileUploadLogic;
import model.logic.FileUploadS3Logic;
import model.logic.GetFileNameLogic;
import model.logic.IsValidFileLogic;
import model.logic.LoginLogic;
import servlet.RewriteImage;

class RewriteImageTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Spy
	LoginLogic logic;
	FileUploadCheckLogic fulogic;
	@Spy
	GetFileNameLogic gflogic;
	@Spy
	IsValidFileLogic ivlogic;
	@Spy
	FileUploadLogic flogic;
	@Mock
	Base64Logic blogic;
	@Spy
	FileUploadS3Logic s3uplogic;
	@Spy
	DeleteFileS3Logic s3logic;
	@Mock
	Part part;
	InputStream in;
	String msg;
	Path path;
	String b64;
	String to;
	int expectedCode;

	@BeforeEach
	void set() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);
	}
	@BeforeEach
	void init() {
		blogic=new Base64Logic(part, "jpg");
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@BeforeEach
	void setStream() throws Exception {
		in=new FileInputStream("src/test/resources/1563062226948_test.jpg");
	}
	@AfterEach
	void tearDown() throws Exception {
		in.close();
	}

	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void testDoGet(String title,String filename,String text,String userId,String date_time) throws Throwable {
		path=Paths.get("/tmp/test.jpg");
		mockReq=factory.createMockRequest();
		factory.addRequestWrapper(mockReq);
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.addRequestParameter("title", title);
		testModule.addRequestParameter("text", text);
		when(part.getSize()).thenReturn((long)2040);
		when(part.getInputStream()).thenReturn(in);
		doReturn(filename).when(gflogic).getFileName(part);//filename 仮
		doReturn(true).when(ivlogic).execute(filename);
		doReturn(msg).when(ivlogic).execute(part);
		doReturn(true).when(flogic).execute(part,path);
		blogic=new Base64Logic(part, "jpg");
		b64=blogic.execute();
		//本番の状況によるが、blogic(String tmpPath)(<-TmpFileUploadで使用)の方が、テスト,
		//メンテしやすい(?)ただし、tmpfileを削除する問題が出てくるので、再検討。
		//現、Rewrite〜.javaで以下のstub,assertができないのでskip
//		assertThat(blogic.execute(b64),is(not(null)));
//		when(blogic.execute(b64)).thenReturn(blogic.execute64("src/test/resources/1563062226948_test.jpg"));
		testModule.setSessionAttribute("title", title);
		testModule.setSessionAttribute("text", text);
		testModule.setSessionAttribute("base64", b64);
		to="/upload_image_db4/ShowThumbnailEdit";
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(RewriteImage .class);
		try {
			testModule.doPost();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=200;
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());
	}
}
