package test.model.logic;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dao.RegisterDAO;
import model.beans.Account;
import model.logic.RegisterLogic;
import model4test.GetDataSourceLogic;
//Mockのみ(宣言してるだけ！)==>  ac1~4変えても成功する
//====> ここでMock --> servletでverify
class RegisterLogicTest3 {
	static DataSource dataSource;
	static GetDataSourceLogic gdlogic;
	@Mock
	RegisterLogic logic;
//	RegisterLogic logic=new RegisterLogic(dataSource);
	@Mock//servlet verify()で使用?
	RegisterDAO dao;
//	RegisterDAO dao=new RegisterDAO(source);
	Account ac;

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


	@BeforeEach
	void setUp() throws Exception {
		gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();

	}

	@BeforeEach
	void init() throws Exception{
		logic=new RegisterLogic(dataSource);
		dao=new RegisterDAO(dataSource);//
	}

	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * dataset に存在しない userId を持つ Account をInsertして書き込みが成功する
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 * @throws IOException
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int age) throws IOException {
		ac=new Account(userId, pass, name, age);
		when(logic.execute(ac)).thenReturn(true);
//		when(dao.register(ac)).thenReturn(true);
//		verify(dao, times(1)).register(ac);
	}


	//
	/**
	 * Account.ac1,2==>
	 * dataset に存在する userId を持つ Account を引数にして該当箇所の書き換えが成功する
	 *
	 * Account.ac2,3==>
	 * dataset に存在しない値を持つ Account を引数にとり書き換えが失敗する
	 * @throws IOException
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)//old
	void test2(String userId,String pass,String name,int age) throws IOException {
		ac=new Account(userId, pass, name, age);
		//exist --> success
		Account ac1=new Account("Sasaki1","135790","畑中陽子",34);
		Account ac2=new Account("Sasaki3","135ouga","横臥まな",14);
		//not exise --> fail
		Account ac3=new Account("Hata333","135790","畑中陽子",34);
		Account ac4=new Account("Ouga777","135ouga","横臥まな",14);

		when(logic.update(ac,ac1)).thenReturn(true);
		when(logic.update(ac,ac2)).thenReturn(true);

		when(logic.update(ac,ac3)).thenReturn(false);
		when(logic.update(ac,ac4)).thenReturn(false);
	}

	/**
	 * Account.ac1,2==>
	 * dataset に存在する userId を持つ Account を引数にして該当箇所の削除が成功する
	 *
	 * Account.ac2,3==>
	 * dataset に存在しない値を持つ Account を引数にとり該当箇所の削除が失敗する
	 * @throws IOException
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_able_to_register.csv",numLinesToSkip=1)//old
	void test3(String userId,String pass,String name,int age) throws IOException {
		ac=new Account(userId, pass, name, age);

		//exist --> success
		Account ac1=new Account("Sasaki55","1s3a5s7a9","佐々木和也",16);
		Account ac2=new Account("Sasaki67","1s3a5s7a9","佐々木彰人",32);
		//not exist --> fail
		Account ac3=new Account("Sasa555","1s3a5s7a9","佐々木和也",16);
		Account ac4=new Account("Sasaki676","1s3a5s7a9","佐々木彰人",32);

		when(logic.delete(ac1)).thenReturn(true);
		when(logic.delete(ac2)).thenReturn(true);

		when(logic.delete(ac3)).thenReturn(false);
		when(logic.delete(ac4)).thenReturn(false);
	}

}
