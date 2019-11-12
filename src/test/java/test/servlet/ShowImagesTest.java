package test.servlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mockrunner.base.NestedApplicationException;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import model.beans.Account;
import model.beans.ImageBean;
import model.logic.GetAllImageFromDBLogic;
import model.logic.GetPageLogic;
import model.logic.PageNationLogic;
import model4test.CsvFileReadLogic;
import servlet.ShowImages;

class ShowImagesTest {
	ServletTestModule testModule;
	WebMockObjectFactory factory;
	MockHttpServletRequest mockReq;
	MockHttpSession session;
	@Spy
	GetAllImageFromDBLogic galllogic;
	@Spy
	PageNationLogic pnlogic;
	@Spy
	GetPageLogic gplogic;
	//servletのInjectMockは-->doGet()なので x (?)
//	ShowImages servlet;
	Account account;
	CsvFileReadLogic csvlogic;
	String file;
	List<ImageBean>listAll;
	List<ImageBean>listAllByUser;
	List<ImageBean>listPaged;
	List<ImageBean>listPagedByUser;
//	GetPageLogic gplogic = new GetPageLogic(**dataSource);
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
	void set() throws Exception {
		account=new Account("Shima5", "135790", "島風香", 34);

		csvlogic=new CsvFileReadLogic();
		file="src/test/resources/csv/mutter_beanslist.csv";
		listAll=csvlogic.openCsvToBean(file);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	/**
	 * for test1,2
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@AfterEach
	void tearDown() throws Exception {
	}
/**
 * Session有効、パラメタaction="show"
 * @throws IOException
 * @throws FileNotFoundException
 * @throws UnsupportedEncodingException
 */
	@Test
	void test1() throws UnsupportedEncodingException, FileNotFoundException, IOException {
//can't create before configure testModule
		mockReq=factory.createMockRequest();
		session=factory.createMockSession();

		file="src/test/resources/csv/mutter_beanslist_paged.csv";
		listPaged=csvlogic.openCsvToBean(file);
//can't apart from test berow. Otherwise 'set'==null
		testModule.setSessionAttribute("account", account);
		int total=listAll.size();
		testModule.setSessionAttribute("total", total);
		testModule.addRequestParameter("action", "show");
		doReturn(listAll).when(galllogic).execute();
//		List<ImageBean> pagedList=gplogic.findPage(page);
		doReturn(listPaged).when(gplogic).findPage(1);
//		//pageNation 表示部分
//		totalPage = pnlogic.getTotalPageCount(total);
		doReturn(2).when(pnlogic).getTotalPageCount(total);
//		in = pnlogic.getPageShowing(page, total, totalPage);
		doReturn(5).when(pnlogic).getPageShowing(1, total, 2);
		testModule.setSessionAttribute("totalPage", 2);//
		testModule.setSessionAttribute("page", 1);
		testModule.setSessionAttribute("pagedList", listPaged);
		testModule.setSessionAttribute("in", 5);
		testModule.createServlet(ShowImages.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
//	x NumberformatEx  int total=Integer.parseInt((String)session.getAttribute("total"));
////	in = pnlogic.getPageShowing(page, total, totalPage);
		int expectedCode=200;
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
//servlet->>injectMockでもnull
//		assertThat(session.getAttribute("total"),is(8));//null<---Mockにすべき
	}

	/**
	 * Session有効、パラメタaction="edit"
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	void test2() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		mockReq=factory.createMockRequest();
		session=factory.createMockSession();
		//all posts by userId 'Shima5'
		file="src/test/resources/csv/mutter_beanslist_byuser.csv";
		listAllByUser=csvlogic.openCsvToBean(file);
		file="src/test/resources/csv/mutter_beanslist_paged_byuser.csv";
		listPagedByUser=csvlogic.openCsvToBean(file);
		int total=listAllByUser.size();
//can't apart from test berow. Otherwise 'set'==null
		testModule.setSessionAttribute("account", account);
		testModule.setSessionAttribute("total", total);
		testModule.addRequestParameter("action", "edit");
//		totalPage = pnlogic.getTotalPageCountEdit(total);
		doReturn(2).when(pnlogic).getTotalPageCountEdit(total);//?remove
//		in=pnlogic.getEditPageShowing(page, total, totalPage);
		doReturn(3).when(pnlogic).getEditPageShowing(1, total, 2);//?
//		session.setAttribute("totalPage", totalPage);//
		testModule.setSessionAttribute("totalPage", 2);
//		request.setAttribute("page", page);
		testModule.setSessionAttribute("page", 1);
//		request.setAttribute("in", in);//
		testModule.setSessionAttribute("in", 3);//
		testModule.setSessionAttribute("pagedPostedList", 3);
		String path=gplogic.toEdit();
		mockReq.setRequestDispatcher(path, null);

		testModule.createServlet(ShowImages.class);
		try {
			testModule.doGet();
		} catch (NestedApplicationException e) {
			e.printStackTrace();
		}
//can't create before configure testModule (before or after doGet()?)
//		mockReq=factory.createMockRequest();
//		session=factory.createMockSession();
		int expectedCode=200;
		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());

	}

}
