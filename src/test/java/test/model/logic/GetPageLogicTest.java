package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import dao.PagingDAO;
import model.beans.ImageBean;
import model.logic.GetPageLogic;
import model4test.CsvFileReadLogic;
import model4test.GetDataSourceLogic;
//List<ImageBean> のstubbingが難しい
class GetPageLogicTest {
	@Spy
	GetPageLogic logic;
	@Mock
	PagingDAO dao;
	DataSource source;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();

	}

	@BeforeEach
	void setUp() throws Exception {
	}
	@BeforeEach
	void initMocks() throws Exception {
		dao=new PagingDAO(source);
		logic=new GetPageLogic(source) ;
		MockitoAnnotations.initMocks(this);
	}
/**
 * 存在するページ数を引数にとり、リスト(csv-->端数ページでないのでsize=5)が帰ってくる(全ユーザー)
 * (リストの降順はPagingDAOTestでチェック)
 * @throws IOException
 * @throws FileNotFoundException
 * @throws UnsupportedEncodingException
 */
	@ParameterizedTest
	@ValueSource(ints= {1})//don't mention the number of page ,coz using stub
	void returnCsvBean(int i) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		CsvFileReadLogic csvlogic=new CsvFileReadLogic();
		List<ImageBean> list=csvlogic.openCsvToBean("src/test/resources/csv/mutter_beanslist_paged.csv");

		when(dao.findPage(i)).thenReturn(list);
		doReturn(list).when(logic).findPage(i);
		assertThat(logic.findPage(i),is(hasSize(5)));
		assertThat(list.get(0).getText(),is("ほげほげ4に行きました！"));//woldn't sorted order coz of stub
	}

/**
 * table内に存在するidを引数にとり、リストが帰ってくる(ユーザー)
 */
	@ParameterizedTest
	@MethodSource("argumentsOrganizer")//異なる種類 ->x ValueSource
	void returnCsvBeanByuser(String userId,int i) throws Exception, FileNotFoundException, IOException {
		CsvFileReadLogic csvlogic=new CsvFileReadLogic();
		List<ImageBean> list=csvlogic.openCsvToBean("src/test/resources/csv/mutter_beanslist_paged_byuser.csv");

		when(dao.findPageByUser(userId, i)).thenReturn(list);
		doReturn(list).when(logic).findPageByUser(userId, i);
		assertThat(logic.findPageByUser(userId, i),is(hasSize(3)));//returnに使うList<Bean>のサイズ
	}
	//returnに使うcsv記載のuser(could use only one user)
	static Stream<Arguments> argumentsOrganizer(){
		return Stream.of(
			Arguments.of("Shima5",1)
		);

	}

	/**********************何を返す?? (or 挙動はDAOTestでやってるのでここではfailer無し?
	 */
	/**
	 * 存在しないページ数でリストが返らない
	 */
	@ParameterizedTest
	@ValueSource(ints= {12,32,44})//don't mention the number of page ,coz using stub
	void returnEmpty(int i) throws UnsupportedEncodingException, FileNotFoundException, IOException {
//		CsvFileReadLogic csvlogic=new CsvFileReadLogic();
//		List<ImageBean> list=csvlogic.openCsvToBean("src/test/resources/csv/mutter_beanslist_paged.csv");

//		doReturn(何を返す??emptyCollectionOf(ImageBean.class)).when(logic).findPage(i);//should return List
//		assertThat(logic.findPage(i),hasSize(0));
	}

/**
 * 存在するuserId,page no.でページングされたリストが返る。
 *このやり方だと、Parameterなのでone setづつしか読めない
 */
//	@ParameterizedTest
//	@CsvFileSource(resources="/csv/mutter_beanslist_paged_byuser.csv",numLinesToSkip=1)
//	void test1(int id,String title,String filename,String userId,String text,String date) throws IOException {
//		List<ImageBean> pagedList=new ArrayList<>();
//		ImageBean imgb=new ImageBean(id,title,filename,userId,text,date);
//		pagedList.add(imgb);//
//		//mock
//		when(dao.findPageByUser(userId,1)).thenReturn(pagedList);
//		//mock-->logic@spy
//		doReturn(pagedList).when(logic).findPageByUser(userId,1);
//		assertThat(logic.findPageByUser(userId, 1),instanceOf(ArrayList.class));
////	x	assertThat(logic.findPageByUser(userId, 1),is(hasSize(3)));//1
//
//	}

	/**
	 * table内に存在するidを引数にとり、リストが帰ってくる(全ユーザー)
	 * @param n
	 * @throws IOException
	 */
//		@ParameterizedTest
//		@CsvFileSource(resources="/csv/mutter_beanslist_paged.csv",numLinesToSkip=1)
////		@ValueSource(ints= {8, 7, 6, 5, 4})
//		void test3(int id,String title,String filename,String userId,String text,String date) throws IOException {
//			List<ImageBean> pagedList=new ArrayList<>();
//			ImageBean imgb=new ImageBean(id,title,filename,userId,text,date);
//			pagedList.add(imgb);//
//			//mock??
//			when(dao.findPage(1)).thenReturn(pagedList);
//			//mock-->logic@spy
//			doReturn(pagedList).when(logic).findPage(1);
//			assertThat(logic.findPage(1),instanceOf(ArrayList.class));
//			assertThat(logic.findPage(1),is(hasSize(5)));
//		}
	/**
	 * table内に存在しないidを引数にとり、リストが帰ってこない
	 * @param n
	 * @throws IOException
	 */
//	@ParameterizedTest
//	@ValueSource(ints= {8, 7, 6, 5, 4})
//	void test2(int n) throws IOException {
////IsEmptyCollection cannot be returned by findPage()
////		findPage() should return List
//		doReturn(emptyCollectionOf(ImageBean.class)).when(dao).findPage(n);
//		assertThat(logic.findPage(n),empty());
//	}


}
