package test.servlet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import model.beans.ImageBean;
import model.beans.Mutter;
import model.logic.CreateFileLogic;
import model.logic.LoginLogic;
import model.logic.RewriteFileLogic;
import model.logic.UpdateFileDBLogic;
import model4test.FileUploadS3ninjaLogic;
import model4test.GetDataSourceLogic;
import servlet.RewriteImageComplete;

class RewriteImageCompleteTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpServletResponse mockResp;
	MockHttpSession session;
	Path tmpPath;
	Path deleteFilePath;
	String newFileName;
	@Spy
	CreateFileLogic clogic;
	@Spy
	RewriteFileLogic dlogic;
	@Spy
	UpdateFileDBLogic uflogic;
	@Spy
	FileUploadS3ninjaLogic s3;
	@Spy
	LoginLogic logic;
	String msg;
	String b64;
	ImageBean imgbean;
	Mutter mutter;
	@Mock
	JdbcDataSource dataSource;
	String to;
	int expectedCode;


	@BeforeEach
	void set() throws Exception {
		factory=new WebMockObjectFactory();
		testModule=new ServletTestModule(factory);

	}
	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();
	}
	@BeforeEach
	void init() {
		uflogic=new UpdateFileDBLogic(dataSource);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_parameterlist_rewrite.csv",numLinesToSkip=1)
	void test(int id,String title,String filename,String text,String userId,String time) throws IOException {

//		deleteFilePath=Paths.get("//test.tmp");
		tmpPath=Paths.get("/tmp/test.tmp");
		newFileName=time+"_test_"+time;
		msg="書き換えが完了しました。";
		b64="gu2CoIKigqSCpoKogqmCq4Ktgq+CsYKzgrWCt4K5gruCvYK/gsKCxILGgsiCyYLKgsuCzILNgtCC04LWgtmC3ILdgt6C34LggqCCoA==";
		imgbean= new ImageBean(id,title,newFileName);
		mutter= new Mutter(id,title,text);
		to="/upload_image_db4/ToLoginResult";
		mockReq=factory.createMockRequest();
//		factory.addRequestWrapper(mockReq);//<= mockReq doesn't implements getPart()--Wrapper classで解決
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.setSessionAttribute("userId", "Shima5");
		testModule.setSessionAttribute("deleteFilePath", deleteFilePath);
		testModule.setSessionAttribute("title", title);
		testModule.setSessionAttribute("text", text);
		testModule.setSessionAttribute("filename", filename);
		testModule.setSessionAttribute("deleteFileName", "test.tmp");
		testModule.setSessionAttribute("time", time);
		doReturn(newFileName).when(clogic).getRewritedFileName(time, filename);
		doReturn(msg).when(dlogic).rewrite(b64, deleteFilePath);
		msg="(DB)Image書き換えが完了しました";
		doReturn(msg).when(uflogic).rewriteImage(imgbean);
		msg="(DB)Text書き換えが完了しました";
		doReturn(msg).when(uflogic).rewriteMutter(mutter);
		assertTrue(s3.upload(newFileName, null));
		//s3delete 略
		testModule.setSessionAttribute("msg", msg);//仮
		testModule.setSessionAttribute("newFileName", newFileName);//仮
		to="/upload_image_db4/ShowThumbnailEdit";
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(RewriteImageComplete.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=302;//?UploadComplete.doGetの失敗時302
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());
	}

	@Test
	void test2() {
		mockReq=factory.createMockRequest();
		mockResp=factory.createMockResponse();
		session=factory.createMockSession();
		testModule.setSessionAttribute("userId", "Shima5");
		doReturn(msg).when(logic).getMsg(to);//null
		testModule.setSessionAttribute("errormsg", "不正な画面移管が有りました。(更新が完了しているか、内容が取得できていない可能性があります。)");
		to="/upload_image_db4/ToLoginResult";
		mockResp.addHeader("Cache-Control", "no-cache");
		mockResp.encodeRedirectUrl(to);
		testModule.createServlet(RewriteImageComplete.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
		expectedCode=302;//?UploadComplete.doGetの失敗時302
		assertEquals(expectedCode,factory.getMockResponse().getStatusCode());

	}

}

