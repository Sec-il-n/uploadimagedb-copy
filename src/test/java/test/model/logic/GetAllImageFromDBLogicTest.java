package test.model.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import dao.MutterDAO;
import model.beans.ImageBean;
import model.logic.GetAllImageFromDBLogic;
import model4test.CsvFileReadLogic;
import model4test.GetDataSourceLogic;

class GetAllImageFromDBLogicTest {
	DataSource dataSource;
	@Mock
	MutterDAO dao;
	@Spy//テスト対象なのでinject検証
	GetAllImageFromDBLogic logic;
	List<ImageBean>list;
	ImageBean bean ;
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
		dao=new MutterDAO(dataSource);
		logic=new GetAllImageFromDBLogic(dataSource);
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
/**
 * 全リスト取得
 * @throws IOException
 * @throws FileNotFoundException
 * @throws UnsupportedEncodingException
 */
	void test1() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		CsvFileReadLogic csvlogic=new CsvFileReadLogic();
		String file="src/test/resources/csv/mutter_beanslist.csv";
		list=csvlogic.openCsvToBean(file);

		when(dao.findAll()).thenReturn(list);
		doReturn(list).when(logic).execute();
		assertThat(logic.execute(),is(list));

	}
	/**
	 * DAOがNullを返す
	 */
	@Test
	void test1_2() {
		when(dao.findAll()).thenReturn(null);
		doReturn(null).when(logic).execute();
		assertThat(logic.execute(),is(list));
	}
	/**
	 * 全リスト取得(ユーザー)
	 * 複数コメント
	 */
	@ParameterizedTest
	@ValueSource(strings= {"Shima5"})
	void test2(String userId) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		CsvFileReadLogic csvlogic=new CsvFileReadLogic();
		String file="src/test/resources/csv/mutter_beanslist_paged_byuser.csv";
		list=csvlogic.openCsvToBean(file);

		when(dao.findAllByUser(userId)).thenReturn(list);
		doReturn(list).when(logic).listByUser(userId);
		assertThat(logic.listByUser(userId),is(list));
	}
	/**
	 * 全リスト取得(ユーザー)
	 * 該当コメントなし、該当ユーザーなし
	 */
	@ParameterizedTest
	@ValueSource(strings= {"Shima5"})
	void test2_2(String userId) {
		list = new ArrayList<>();
		when(dao.findAllByUser(userId)).thenReturn(list);
		doReturn(list).when(logic).listByUser(userId);
		assertThat(logic.listByUser(userId),is(list));
	}
	/**
	 * 存在するidを引数にとり該当するidのコンテンツのbeanが得られる
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanslist_paged.csv",numLinesToSkip=1)
	void test3(int id,String title,String filename,String text,String userId,String date_time) {
		String Id="1";
		bean = new ImageBean(id,title,filename,text,userId,date_time);
		when(dao.findEach(Id)).thenReturn(bean);
		doReturn(bean).when(logic).execute(Id);
		assertThat(logic.execute(Id),is(bean));

	}
	/**
	 * 存在しないidを引数にとり該当するidのコンテンツのbeanが得られない
	 */
	@ParameterizedTest
	@ValueSource(strings= {"21","0","55"})
	void test3_2(String Id) {
		when(dao.findEach(Id)).thenReturn(null);
		doReturn(bean).when(logic).execute(Id);
		assertThat(logic.execute(Id),is(bean));
	}

}
