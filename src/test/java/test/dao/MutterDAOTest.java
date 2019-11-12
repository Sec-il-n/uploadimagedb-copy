package test.dao;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import dao.MutterDAO;
import model.beans.ImageBean;
import model.beans.Mutter;
import model4test.GetDataSourceLogic;

class MutterDAOTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

    private static IDatabaseTester tester;
    private static IDataSet dataSet;
    private static DataSource source;
    MutterDAO dao;
//	GetDataSourceLogic gdlogic;
    static String filename;
    List<ImageBean>list;
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
		tester.setDataSet(dataSet);
	    tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.onSetup();
	}
	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
		dao=new MutterDAO(source);
	}
	@AfterEach
	void tearDown() throws Exception {
		tester.setTearDownOperation(DatabaseOperation.NONE);
		tester.onTearDown();
	}


/**
 * 全リスト取得
 */
	@Test
	void test1() {
		list=dao.findAll();
		assertThat(list,instanceOf(ArrayList.class));//<--only OK ⤵追加すると一瞬曖昧でNG
		assertThat(list,hasSize(8));
//		降順、DateFormat確認
		assertThat(list.get(7).getFilename(),is("1563166159183_953-hoge.jpg"));
		assertThat(list.get(3).getDate_time(),is("2019/07/14 12:21"));
	}
	/**
	 * 全リスト取得(ユーザー)
	 * 複数コメント
	 */
	@ParameterizedTest
	@ValueSource(strings= {"Shima5"})
	void test2(String userId) {
		list=dao.findAllByUser(userId);
		assertThat(list,is(hasSize(5)));
		assertThat(list,is(instanceOf(ArrayList.class)));
		//0->降順 + date_format
		assertThat(list.get(0).getDate_time(),is("2019/07/11 06:50"));

//		x is(not(null))
	}
	/*
	 * 全リスト取得(ユーザー)
	 * 1comment
	 */
	@ParameterizedTest
	@ValueSource(strings= {"Shima3","Shima2","Shima1"})
	void test2_2(String userId) {
		list=dao.findAllByUser(userId);
		assertThat(list,is(hasSize(1)));
		assertThat(list,is(instanceOf(ArrayList.class)));
		assertThat(list.get(0).getUserId(),is(userId));
	}
	/**
	 * 引数ユーザーの投稿したコメントが無く空のリストを返す
	 */
	@ParameterizedTest
	@ValueSource(strings= {"Sh","Shone31","Shiailla5"})
	void test2_3(String userId) {
		List<ImageBean>list = new ArrayList<>();
		assertThat(dao.findAllByUser(userId),is(list));

	}
	/**
	 * 存在するidを引数にとり該当するidのコンテンツのbeanが得られる
	 * @param id
	 */
	@ParameterizedTest
	@ValueSource(strings= {"1","2","3","4","5","6","7","8"})
	void test3(String id) {
		ImageBean bean=dao.findEach(id);
		assertThat(bean,hasProperty("id"));
		assertThat(bean,hasProperty("title"));
		assertThat(bean,hasProperty("filename"));
		assertThat(bean,hasProperty("userId"));
		assertThat(bean,hasProperty("text"));
		assertThat(bean,hasProperty("date_time"));
	}
	/*
	 * 存在しないidを引数にとり該当するidのコンテンツのbeanが得られない
	 */
	@ParameterizedTest
	@ValueSource(strings= {"11","32","53","84","9","0"})
	void test3_2(String id) {
		ImageBean bean=dao.findEach(id);
		assertThat(bean,nullValue());
	}
	/**
	 * 存在するidの書き換え情報を持つbeanを引数にとり書き換えが成功する
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_to_rewrite.csv",numLinesToSkip=1)
	void test5(int id,String title,String filename,String text,String userId,String date_time) {
		Mutter mutter=new Mutter(id, title, text);
		boolean result=dao.rewrite(mutter);
		assertTrue(result);
	}
	/**
	 * 引数Mutter Beanが空の場合書き換えが失敗する
	 */
	@Test
	void test5_2() {
		Mutter mutter=new Mutter();
		assertFalse(dao.rewrite(mutter));
	}

}
