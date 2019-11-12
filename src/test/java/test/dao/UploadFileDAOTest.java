package test.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import dao.UploadFileDAO;
import model.beans.ImageBean;
import model.beans.Mutter;
import model4test.GetDataSourceLogic;

class UploadFileDAOTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";
    private static IDatabaseTester tester;
    private static IDataSet dataSet;
    private static DataSource source;
    UploadFileDAO dao;
    String filename;
    ImageBean imgbean;
    Mutter mutter;

	@BeforeEach//changed from All~ (to deal id,auto_increment)
	void setUpBeforeClass() throws Exception {
		filename="src/test/resources/sql/schema_2.sql";
		RunScript.execute(JDBC_URL, USER, PASSWORD, filename, null, false);
		tester=new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
	}
	@BeforeEach
	void setUp() throws Exception {
		filename="src/test/resources/xml/dataset_mutter.xml";
		dataSet=new FlatXmlDataSetBuilder().build(new File(filename));
		tester.setDataSet(dataSet);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.onSetup();
	}
	@BeforeEach
	void getDataSource() throws IOException {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
		dao=new UploadFileDAO(source);
	}
	@AfterEach
	void tearDown() throws Exception {
		tester.setTearDownOperation(DatabaseOperation.NONE);
		tester.onTearDown();
	}
	/**
	 * mutter_Image tableへの書き込みが成功する。
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test1(String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(title, filename);
		assertTrue(dao.create(imgbean));

	}
	/**
	 * 引数が空のBeans Objectで処理が失敗する。
	 */
//	@ParameterizedTest
//	@MethodSource("argumentsProvider")
	@Test
	void test1_2() {
		imgbean=new ImageBean();
		assertFalse(dao.create(imgbean));
	}
	/**
	 * 空文字を含むBeansを引数にとっても、処理は実行される。
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void test1_3(String title,String filename) {
		imgbean=new ImageBean(title,filename);
		assertTrue(dao.create(imgbean));
	}
	//⇩ ではsqlのNotNull制約でもから文字挿入される
	static Stream<Arguments> argumentsProvider(){
		return Stream.of(
			Arguments.of("","test1.jpg"),
			Arguments.of("hoge",""),
			Arguments.of("",""),
			Arguments.of(" "," ")
		);
	}
	/**
	 * mutter tableへの書き込みが成功する。
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_insert.csv",numLinesToSkip=1)
	void test2(String title,String filename,String userId,String text,String date) {
		mutter=new Mutter(title,text,userId);
		assertTrue(dao.create(mutter));

	}
	/**
	 * 存在するファイル名を引数にとり、Idが返される。
	 * 注意：id=String
	 * (戻りが一貫していないのでParameterTestは使用不可)
	 */
	@Test
	void test3() {
		assertThat(dao.findId("1563166159183_953-hoge.jpg"),is("1"));
		assertThat(dao.findId("1563167159173_953-hoge.jpg"),is("3"));
		assertThat(dao.findId("1563146159167_953-hoge.jpg"),is("7"));
	}
	/**
	 * 存在しないファイル名を引数にとり、Idが返されない。
	 */
	@Test
	void test3_2() {
		String id=null;
		assertThat(dao.findId("15683_953-hoge.jpg"),is(id));
		assertThat(dao.findId("156316773_953-hoge.jpg"),is(id));
		assertThat(dao.findId(" "),is(id));
	}
	/*
	 * 存在するIdを含むbeans引数にとり、書き換えが成功する。(table:Mutter_image)
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_to_rewrite.csv",numLinesToSkip=1)
	void test4(int id,String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(id,title,filename);
		assertTrue(dao.rewriteImage(imgbean));
	}
	/*
	 * 存在しないIdを含むbeans引数にとり、書き換えが成功しない。(table:Mutter_image)
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_un_rewritable.csv",numLinesToSkip=1)
	void test4_2(int id,String title,String filename,String userId,String text,String date) {
		imgbean=new ImageBean(id,title,filename);
		assertFalse(dao.rewriteImage(imgbean));
	}
	/*
	 * 存在するIdを含むbeans引数にとり、書き換えが成功する。(table:Mutter)
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_to_rewrite.csv",numLinesToSkip=1)
	void test5(int id,String title,String filename,String userId,String text,String date) {
		mutter=new Mutter(id,title,text);
		assertTrue(dao.rewriteMutter(mutter));
	}
	/*
	 * 存在しないIdを含むbeans引数にとり、書き換えが成功しない。(table:Mutter)
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/mutter_beanlist_un_rewritable.csv",numLinesToSkip=1)
	void test5_2(int id,String title,String filename,String userId,String text,String date) {
		mutter=new Mutter(id,title,text);
		assertFalse(dao.rewriteMutter(mutter));
	}

}
