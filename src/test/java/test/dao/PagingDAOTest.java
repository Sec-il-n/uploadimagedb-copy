package test.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import dao.PagingDAO;
import model.beans.ImageBean;
import model4test.GetDataSourceLogic;
//sql not working??＜＝?returnされたListのサイズが空でなても’0’
class PagingDAOTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

    private static IDatabaseTester tester;
    private static IDataSet dataSet;
    private static DataSource source;
    PagingDAO dao;
	GetDataSourceLogic gdlogic;


	static String filename;
	@BeforeAll
	static void setUP() throws Exception {
		filename="src/test/resources/sql/schema.sql";
		RunScript.execute(JDBC_URL, USER, PASSWORD, filename, null, false);
		tester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);

	}

	@BeforeEach
	void set() throws Exception {
		filename="src/test/resources/xml/dataset_mutter_id.xml";
		dataSet=new FlatXmlDataSetBuilder().build(new File(filename));
		tester.setDataSet(dataSet);//順番注意(set)
	    tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);//順番注意(execute)
		tester.onSetup();
	}

	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
		dao=new PagingDAO(source);
	}

	@AfterEach
	void tearDown() throws Exception {
		tester.setTearDownOperation(DatabaseOperation.NONE);
		tester.onTearDown();
	}

/**
 * 存在するページ数を引数にとり、リストが帰ってくる
 * @param n
 */
	@ParameterizedTest
	@ValueSource(ints= {1})
	void test1(int n) {

		List<ImageBean> list=dao.findPage(n);
		assertThat(list,instanceOf(ArrayList.class));
		assertThat(list,hasSize(5));
		assertThat(list.get(0).getText(),is("ほげほげ8に行きました！"));
//	x 	assertNotNull(dao.findPage(n));//emptyでも<[]>
	}

	/**
	 * 存在しないページ数を引数にとり、リストが帰ってこない
	 * @param n
	 */
	@ParameterizedTest
	@ValueSource(ints= {12, 13, 15, 43})
	void test2(int n) {
		assertThat(dao.findPage(n),is(empty()));
		assertThat(dao.findPage(n),is(emptyCollectionOf(ImageBean.class)));

	}

/**
 * Accountに登録されているuserIdと存在するページ数を引数にとり、同一userIdの投稿下リストが返る
 * @param userId
 * @param i
 */
	@ParameterizedTest
	@MethodSource("argumentsProvider1")
	void testDoPost1(String userId,int i) {
		assertThat(dao.findPageByUser(userId,i),instanceOf(ArrayList.class));
		assertThat(dao.findPageByUser(userId,i),hasSize(lessThan(4)));//<4

	}
/**
 * Accountに登録されているuserIdと存在しないページ数を引数にとり、同一userIdの投稿リストが返らない
 * @param userId
 * @param i
 */
	@ParameterizedTest
	@MethodSource("argumentsProvider2")
	void testDoPost2(String userId,int i) {
		assertThat(dao.findPageByUser(userId,i),emptyCollectionOf(ImageBean.class));
	}
/**
 * 降順,SimpleDateFormat <-- time_stamp 確認
 * @param userId
 * @param i
 */
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void testDoPost3(String userId,int i) {
		assertThat(dao.findPageByUser(userId,i),instanceOf(ArrayList.class));
		assertThat(dao.findPageByUser(userId,i),hasSize(3));
		assertThat(dao.findPageByUser(userId,i).get(0).getText(),is("ほげほげ8に行きました！"));
		assertThat(dao.findPageByUser(userId,i).get(2).getDate_time(),is("2019/07/15 06:50"));

	}
	//順番にimportしないと x

	static Stream<Arguments> argumentsProvider1(){
		return Stream.of(
			Arguments.of("Shima5",1),
			Arguments.of("Shima5",2),
			Arguments.of("Shima1",1)
		);
	}
	static Stream<Arguments> argumentsProvider2(){
		return Stream.of(
				Arguments.of("SaKaT5",5),
				Arguments.of("Shalla5",2),
				Arguments.of("Shone800",1)
				);
	}
	//降順確認
	static Stream<Arguments> argumentsProvider(){
		return Stream.of(
				Arguments.of("Shima5",1)
				);
	}

}
