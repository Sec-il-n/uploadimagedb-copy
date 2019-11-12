package test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
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

import dao.GoodonCommentDAO;
import model4test.GetDataSourceLogic;

class GoodonCommentDAOTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";
    private static IDatabaseTester tester;
    private static IDataSet dataSet;
    private static DataSource source;
    static String filename;
    GoodonCommentDAO dao;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		filename="src/test/resources/sql/schema_good.sql";
		RunScript.execute(JDBC_URL, USER, PASSWORD, filename, null, false);
		tester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
	}
	@BeforeEach
	void setUp() throws Exception {
		filename="src/test/resources/xml/dataset_good_on_comment.xml";
		dataSet=new FlatXmlDataSetBuilder().build(new File(filename));
		tester.setDataSet(dataSet);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.onSetup();
	}
	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
		dao=new GoodonCommentDAO(source);
	}
	@AfterEach
	void tearDown() throws Exception {
		tester.setTearDownOperation(DatabaseOperation.NONE);
		tester.onTearDown();
	}
	/**
	 * good tableに存在するidとuserIdの組合せを引数にとりtrueが返る
	 * (存在確認)
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider1")
	void test1(String userId,String id) {
		assertTrue(dao.goodCountIfExists(userId, id));
	}
	static Stream<Arguments> argumentsProvider1(){
		return Stream.of(
			Arguments.of("Shima5","1"),
			Arguments.of("Shima5","2"),
			Arguments.of("Shima5","6"),
			Arguments.of("Shima2","4"),
			Arguments.of("Shima1","3")
		);
	}
	/**
	 * good tableに存在しないidとuserIdの組合せを引数にとりfalseが返る
	 * (存在確認)
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider2")
	void test1_2(String userId,String id) {
		assertFalse(dao.goodCountIfExists(userId, id));
	}
	static Stream<Arguments> argumentsProvider2(){
		return Stream.of(
				Arguments.of("Shima6","1"),
				Arguments.of("Shi","13"),
				Arguments.of("Shima1","13"),
				Arguments.of("Shima3","4")
		);
	}
	/**
	 * good tableに存在しないidとuserIdの組合せを引数にとりtrueが返る
	 * (新規)
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider3")
	void test2(String userId,String id) {
		assertTrue(dao.goodCount(userId, id));
	}
	static Stream<Arguments> argumentsProvider3(){
		return Stream.of(
				Arguments.of("Shima5","8"),
				Arguments.of("Shima3","1"),
				Arguments.of("Shima2","3"),
				Arguments.of("Shima1","6")
		);
	}
	/**
	 * good tableに存在するidとuserIdの組合せを引数にとりtrueが返る
	 * (指定された行を消す)
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider1")
	void test3(String userId,String id) {
		assertTrue(dao.resetGoodCount(userId, id));
	}
	@ParameterizedTest
	@MethodSource("argumentsProvider2")
	void test3_2(String userId,String id) {
		assertFalse(dao.resetGoodCount(userId, id));
	}
	/**
	 * good table内でIdの重複する回数を返す。(xmlで2回に設定されたidを呼び出す。)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"1","3","6"})
	void test3_3(String id) {
		assertEquals(dao.goodCountTotal(id),2);
	}
	/**
	 * good table内でIdの重複する回数を返す。(xmlで1回に設定されたidを呼び出す。)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"2","4","8"})
	void test3_4(String id) {
		assertEquals(dao.goodCountTotal(id),1);
	}
}
