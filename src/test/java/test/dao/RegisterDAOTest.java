package test.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
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
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import dao.RegisterDAO;
import model.beans.Account;
import model4test.GetDataSourceLogic;

class RegisterDAOTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

//	private static String JDBC_DRIVER = org.h2.Driver.class.getName();
//    private static String JDBC_URL = null;
//    private static String USER = null;
//    private static String PASSWORD = null;

    private static IDatabaseTester dbTester;
    private static DataSource dataSource;

	static Properties prop;
	RegisterDAO dao;

//    static void setProperties() throws Exception {
//		GetDataSourceLogic gdlogic = new GetDataSourceLogic();
//		prop = gdlogic.getH2DataSource2();
//		JDBC_URL=prop.getProperty("JDBC_URL");
//		USER=prop.getProperty("USER");
//		PASSWORD=prop.getProperty("PASSWORD");
//	}

    @BeforeAll
	static void setUpBeforeClass() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/sql/schema.sql", null , false);
		dbTester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
	}


	@BeforeEach
	void setUp() throws Exception {
		IDataSet dataSet=new FlatXmlDataSetBuilder().build(new File("src/test/resources/xml/dataset_account.xml"));
		dbTester.setDataSet(dataSet);//順番注意(set)
	    dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);//順番注意(execute)
		dbTester.onSetup();
	}

	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();
		dao=new RegisterDAO(dataSource);
	}

	/**
	 * dataset に存在しない userId を持つ Account をInsertして書き込みが成功する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test(String userId,String pass,String name,int age) {
		Account ac=new Account(userId,pass,name,age);

		assertNotNull(dataSource);
		assertTrue(dao.register(ac));//assert==4

	}


	/**
	 * dataset に存在する値と同じ値を持つ Account を引数にとり書き換えが成功する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Account.csv", numLinesToSkip=1)
	void testUpdate(String userId,String pass,String name,int age) {
		Account accountBefore=new Account("Shima5","135790","島紀香",34);
		Account accountAfter=new Account(userId,pass,name,age);

		assertTrue(dao.registerUpdate(accountBefore, accountAfter));
	}


	/**
	 * dataset に存在しない値を持つ Account を引数にとり書き換えが失敗する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Account.csv", numLinesToSkip=1)
	void testUpdateFile(String userId,String pass,String name,int age) {
		Account accountBefore=new Account("Seri99","see1357904","島奈緒美",44);
		Account accountAfter=new Account(userId,pass,name,age);

		assertFalse(dao.registerUpdate(accountBefore, accountAfter));
	}

	/**
	 * dataset に存在する userId を持つ Account をInsertして書き込みが失敗する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void testFail(String userId,String pass,String name,int age) {
		Account ac=new Account(userId,pass,name,age);

		assertFalse(dao.register(ac));//assert==4
	}

	/**
	 * dataset に存在する userId を持つ Account を引数にして該当箇所の削除が成功する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void testDelete(String userId,String pass,String name,int age) {
		Account ac=new Account(userId,pass);

		assertTrue(dao.registerDelete(ac));
	}

	static Stream<Arguments> argumentsProvider(){
		return Stream.of(
				Arguments.of("Shima1","1111111","島一郎",45),
				Arguments.of("Shima2","222s222","島二郎",75)
				);
	}

	/**
	 * dataset に存在しない値を持つ Account を引数にとり該当箇所の削除が失敗する
	 * @ParameterizedTest
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Account.csv",numLinesToSkip=1)
	void testDeleteFail(String userId,String pass,String name,int age) {
		Account ac=new Account(userId,pass);

		assertFalse(dao.registerDelete(ac));
	}

	@AfterEach
	void tearDown() throws Exception {
		dbTester.setTearDownOperation(DatabaseOperation.NONE);
		dbTester.onTearDown();
	}


}
