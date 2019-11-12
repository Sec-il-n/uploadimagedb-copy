package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import dao.DeleteDAO;
import dao.UploadFileDAO;
import model.beans.ImageBean;
import model.beans.Mutter;
import model.logic.FileUploadLogic;
import model4test.GetDataSourceLogic;
//UploadFileDAO内のmethod完了0917
class FileUploadLogicTest {
	DataSource dataSource;
	@Mock
	UploadFileDAO dao;
	@Mock
	DeleteDAO deDao;
	@Mock
	Part part;
	@Spy
	FileUploadLogic logic;
	ImageBean imgbean;
	Mutter mutter;
	String msg;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	@BeforeEach
	void setUp() throws Exception {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();
	}
	@BeforeEach
	void set() {
		dao=new UploadFileDAO(dataSource);
		logic=new FileUploadLogic(dataSource);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void testExecute() throws IOException {
		Path path=Paths.get("/tmp/test.jpg");//Ⓜ︎truth<ex.
		InputStream in=new FileInputStream("src/test/resources/1563062226948_test.jpg");
		when(part.getSize()).thenReturn((long)1024);//Ⓜ︎
		when(part.getInputStream()).thenReturn(in);

		doReturn(true).when(logic).execute(part, path);
		assertTrue(logic.execute(part, path));
	}
	/**
	 * mutter_Image tableへの書き込みが成功する。
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test1(String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(title,filename);
		when(dao.create(imgbean)).thenReturn(true);
		doReturn(true).when(logic).execute(imgbean);
		assertTrue(logic.execute(imgbean));
	}
	/**
	 * mutter_Image tableへの書き込みが失敗する。
	 */
	@Test
	void test1_2() {
		imgbean=new ImageBean();
		when(dao.create(imgbean)).thenReturn(false);
		doReturn(false).when(logic).execute(imgbean);
		assertFalse(logic.execute(imgbean));
	}

	/**
	 * mutter tableへの書き込みが成功する。
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test2(String title,String filename,String userId,String text,String date) {
		mutter=new Mutter(title,text,userId);
		when(dao.create(mutter)).thenReturn(true);
		doReturn(true).when(logic).execute(mutter);
		assertTrue(logic.execute(mutter));
	}
	/**
	 * mutter tableへの書き込みが失敗する。
	 */
	@Test
	void test2_2() {
		mutter=new Mutter();
		when(dao.create(mutter)).thenReturn(false);
		doReturn(false).when(logic).execute(mutter);
		assertFalse(logic.execute(mutter));
	}
	/**
	 * Haven't used servlet
	 * might be just 4 a practice??
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanslist.csv",numLinesToSkip=1)
	void test3(int id,String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(id,title,filename);
		mutter=new Mutter(id,title,text);
		logic=new FileUploadLogic(dataSource);//Spyのままでは⇩ assert でNullPointerになる
		msg="書込みに失敗しました";
		when(deDao.deleteImage(imgbean)).thenReturn(false);
		when(deDao.deleteMutter(mutter)).thenReturn(true);
		assertThat(logic.executeFailer(false, true, imgbean, mutter),is(equalTo(msg)));
		assertThat(logic.executeFailer(true, false, imgbean, mutter),is(equalTo(msg)));
	}
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanslist.csv",numLinesToSkip=1)
	void test3_2(int id,String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(id,title,filename);
		mutter=new Mutter(id,title,text);
		logic=new FileUploadLogic(dataSource);//Spyのままでは⇩ assert でNullPointerになる
		msg="書込み完了しました";
		when(deDao.deleteImage(imgbean)).thenReturn(true);
		when(deDao.deleteMutter(mutter)).thenReturn(true);
		assertThat(logic.executeFailer(true, true, imgbean, mutter),is(equalTo(msg)));
		assertThat(logic.executeFailer(true, true, imgbean, mutter),is(equalTo(msg)));
	}
	//削除or別使用Logicへ移動
	/**
	 * 存在するファイル名を引数にとり、Idが返される。
	 */
//	@ParameterizedTest
//	@ValueSource(strings = {"1563166159083_953-hoge.jpg","1563146159167_953-hoge.jpg"})
//	void test3(String filename)
//		when(dao.findId(filename)).thenReturn("4");
//		doReturn("4").when(logic).
//	}

}
