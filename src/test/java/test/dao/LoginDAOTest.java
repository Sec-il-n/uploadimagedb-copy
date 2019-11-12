package test.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
import org.junit.jupiter.params.provider.CsvFileSource;

import dao.LoginDAO;
import model.beans.Account;
import model4test.GetDataSourceLogic;
//全て実際値なので@Mockなし
class LoginDAOTest {

	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

    private static IDatabaseTester dbTester;
    private static DataSource dataSource;
    LoginDAO dao;

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
		dao=new LoginDAO(dataSource);
	}

//	@BeforeEach
//	void initMock() throws Exception {
//		MockitoAnnotations.initMocks(this);
//	}

	@AfterEach
	void tearDown() throws Exception {
		dbTester.setTearDownOperation(DatabaseOperation.NONE);
		dbTester.onTearDown();
	}

	/**
	 * tableに存在するAccountを引数にとりDBから情報取得できる
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test(String userId,String pass,String name,int age) throws ClassNotFoundException {
		Account acIn=new Account(userId,pass);
		Account acReturn=new Account(userId,pass,name,age);

		assertEquals(dao.findId(acIn).getUserId(),acReturn.getUserId());
		assertEquals(dao.findId(acIn).getPass(),acReturn.getPass());
//???ここで使用不可？
//		when(dao.findId(any(Account.class))).thenAnswer(i -> {
//		Account re=i.getArgument(0);
//		return re;
//		});
	}

	/**
	 * tableに存在しないAccountを引数にとりDBから情報取得できない
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void test2(String userId,String pass,String name,int age) throws ClassNotFoundException {
		Account acIn=new Account(userId,pass);

		assertNull(dao.findId(acIn));
	}

}

