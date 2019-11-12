package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import dao.LoginDAO;
import model.beans.Account;
import model.logic.LoginLogic;
import model4test.GetDataSourceLogic;

class LoginLogicTest {
	GetDataSourceLogic gdlogic;
	DataSource dataSource;
	HttpServletRequest req;
	@Spy
	LoginLogic logic;
	@Mock
	LoginDAO dao;
	Account ac;

	@BeforeEach
	void setUp() throws Exception {
		gdlogic=new GetDataSourceLogic();
		dataSource=gdlogic.getH2DataSource1();
	}

	@BeforeEach
	void initLogic() throws Exception {
		logic=new LoginLogic(dataSource);
	}

	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * LoginDAOがAccountを返す場合のLogivnLogicスタブ化
	 * @throws Exception
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test1(String userId,String pass,String name,int age) throws Exception {
		ac=new Account(userId, pass);
		Account reAccount=new Account(userId, pass, name, age);
//mock <-- dao 接続してないので実際ではない
		when(dao.findId(ac)).thenReturn(reAccount);
//Spy <--　logic==daoのスタブ化からの実値
		doReturn(reAccount).when(logic).execute(ac);
//added 9/13
		assertThat(logic.execute(ac),is(reAccount));

	}
	/**
	 * LoginDAOがAccountを返さない場合のLogivnLogicスタブ化
	 * @param userId
	 * @param pass
	 * @param name
	 * @param age
	 * @throws Exception
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test2(String userId,String pass,String name,int age) throws Exception {
		ac=new Account(userId, pass);
		when(dao.findId(ac)).thenReturn(null);
		doReturn(null).when(logic).execute(ac);
		assertNull(logic.execute(ac));
	}
/**
 * session がタイムアウトの場合、引数にuserIdが取れないと、ログインページを返す。
 * ログインページが返されると、メッセージも返される。
 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/Accounts_exist_in_table.csv",numLinesToSkip=1)
	void test3_1(String userId,String pass,String name,int age) {

		assertNull(logic.sessionCheck(userId));
		assertNull(logic.getMsg(logic.sessionCheck(userId)));
	}
	/**
	 * session がタイムアウトの場合、引数にuserIdが取れると、nullを返す。
	 * nullが返されると、メッセージも返されない。
	 */
	@ParameterizedTest
	@NullSource
	void test3_2(String userId) {
		assertThat(logic.sessionCheck(userId),is("/WEB-INF/jsp/login.jsp"));
		assertThat(logic.getMsg(logic.sessionCheck(userId)),is("ログインし直して下さい。"));
	}

}
