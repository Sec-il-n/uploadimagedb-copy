package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.beans.ImageBean;
import model.beans.Mutter;
import model.logic.CreateFileLogic;
import model.logic.FileUploadLogic;
import model4test.FileUploadS3ninjaLogic;
import servlet.UploadComplete;
class UploadCompleteTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	@Spy
	CreateFileLogic clogic;
	@Spy
	FileUploadS3ninjaLogic s3logic;
	@Spy
	FileUploadLogic flogic;
	String title;
	String newFileName;
	String filename;
	String tmpPath;
	String msg;
	String to;
	ImageBean imgBean;
	Mutter mutter;
	int expectedCode;//全成功200,処理が飛ばされてリダイレクト302??
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	/**
	 * Session有効、DB,s3書き込み成功
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test(String title,String filename,String text,String userId,String date_time) {
		newFileName="156313854021_moge3_test.jpg";
		tmpPath="/tmp/234567.tmp";
		msg="書込み完了しました";
		to="/upload_image_db4/ToLoginResult";
		expectedCode=200;//success the request
		imgBean=new ImageBean(title,newFileName);
		mutter=new Mutter(title,text,userId);

		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.setSessionAttribute("title", title);
		testModule.setSessionAttribute("text", text);
		testModule.setSessionAttribute("tmpPath", tmpPath);
		testModule.setSessionAttribute("filename", filename);
		testModule.setSessionAttribute("userId", userId);
//		Path tmpPath = (Path) session.getAttribute("tmpPath");//ファイルまでのパス
		doReturn(newFileName).when(clogic).getCreatedFileName(filename);
//		FileUploadS3Logic s3logic=new FileUploadS3Logic();
		doReturn(true).when(s3logic).upload(newFileName,tmpPath);
//		FileUploadLogic flogic=new FileUploadLogic(source);
		doReturn(true).when(flogic).execute(imgBean);
//		msg=flogic.executeFailer(result1, result2, bean1, bean2, source);
		doReturn(msg).when(flogic).executeFailer(true, true, imgBean, mutter);
		testModule.setSessionAttribute("msg",msg);
		testModule.setSessionAttribute("newFileName",newFileName);
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(UploadComplete.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}

		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
	}
	/**
	 * Session有効、DB,s3書き込み失敗（パラメタ一部取得不可により）
	 */
	@Test
	void test2() {
		msg="不正な画面移遷がありました。";
		to="/upload_image_db4/ToLoginResult";
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.setSessionAttribute("userId", "Shone78");
		//変数=null
//		tring userId = (String) session.getAttribute("userId");
		doReturn(filename).when(clogic).getCreatedFileName(filename);
//		result = s3logic.s3Upload(newFileName,tmpPath.toString());
		doReturn(false).when(s3logic).upload(newFileName,tmpPath);
		testModule.setSessionAttribute("msg",msg);
//		testModule.setSessionAttribute("newFileName",newFileName);
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(UploadComplete.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=302;//moved temporary
		//?rewriteImageComplete.doGetの成功時302
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
	}
}
