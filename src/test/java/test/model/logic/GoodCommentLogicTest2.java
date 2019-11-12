package test.model.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import dao.GoodonCommentDAO;
import model.logic.GoodCommentLogic;
import model4test.GetDataSourceLogic;

class GoodCommentLogicTest2 {
	@Mock
	GoodonCommentDAO dao;
//	@InjectMocks//x
	@Spy
	GoodCommentLogic logic;//無しx
	DataSource source;
	boolean result;
	@BeforeEach
	void setUp() throws Exception {
		GetDataSourceLogic gdlogic=new GetDataSourceLogic();
		source=gdlogic.getH2DataSource1();
	}
	/**
	 * execute()単体
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void test1(String userId,String by,String id) {
		//caution this 3 lines's order
		logic=new GoodCommentLogic(userId,by,id,source);
		dao=new GoodonCommentDAO(source);
		MockitoAnnotations.initMocks(this);
		//@Mock,@Spy (ok, true,false)
		when(dao.goodCountIfExists(userId, id)).thenReturn(true);
		doReturn(true).when(logic).exists(userId,id,source);
		assertTrue(logic.exists(userId, id, source));
	}
	//ここから
	/**
	 * reset()単体
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void test1_2(String userId,String by,String id) {
		//caution this 3 lines's order
		logic=new GoodCommentLogic(userId,by,id,source);
		dao=new GoodonCommentDAO(source);
		MockitoAnnotations.initMocks(this);
		//@Mock,@Spy (ok, true,false)
		when(dao.resetGoodCount(userId, id)).thenReturn(true);
		doReturn(true).when(logic).reset(userId,id,source);
		assertTrue(logic.reset(userId, id, source));
		when(dao.resetGoodCount(userId, id)).thenReturn(false);
		doReturn(false).when(logic).reset(userId,id,source);
		assertFalse(logic.reset(userId, id, source));
	}
	/**
	 * total()単体
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void test1_3(String userId,String by,String id) {
		//caution this 3 lines's order
		logic=new GoodCommentLogic(userId,by,id,source);
		dao=new GoodonCommentDAO(source);
		MockitoAnnotations.initMocks(this);
		//@Mock,@Spy (ok, true,false)
		when(dao.goodCountTotal(id)).thenReturn(2);
		doReturn(2).when(logic).total(id,source);
		assertEquals(logic.total(id, source),2);
	}

	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void test2(String userId,String by,String id) {
		//caution this 3 lines's order
		logic=new GoodCommentLogic(userId,by,id,source);
		dao=new GoodonCommentDAO(source);
		MockitoAnnotations.initMocks(this);
		//@Mock@Spy existsしないでinsert完了
		when(dao.goodCountIfExists(userId, id)).thenReturn(false);
		when(dao.goodCount(userId, id)).thenReturn(true);
		doReturn(false).when(logic).exists(userId,id,source);
		doReturn(true).when(logic).execute(userId,id,source);
		assertTrue(logic.goodCount());
	}
	/**
	 * good tableに存在しないuserIdとidの組み合わせ、投稿者と評価者が同一でなく、insertが完了する
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_insert.csv",numLinesToSkip=1)
	void test2_2(String userId,String by,String id) {
		//caution this 3 lines's order
		logic=new GoodCommentLogic(userId,by,id,source);
//		dao=new GoodonCommentDAO(source);//なしでもいけてる！？
		MockitoAnnotations.initMocks(this);
		//existsしてreset完了
		when(dao.goodCountIfExists(userId, id)).thenReturn(true);
		when(dao.resetGoodCount(userId, id)).thenReturn(true);
		doReturn(true).when(logic).exists(userId,id,source);
		doReturn(true).when(logic).reset(userId,id,source);
		assertTrue(logic.goodCount());
	}
	/**
	 * good tableに存在しないuserIdとidの組み合わせ、投稿者と評価者が同一で、insertが失敗する
	 */
	@ParameterizedTest
	@CsvFileSource(resources="/csv/good_on_comment_unable.csv",numLinesToSkip=1)
	void test2_3(String userId,String by,String id) {
		logic=new GoodCommentLogic(userId,by,id,source);
		MockitoAnnotations.initMocks(this);
		result=logic.goodCount();
		assertEquals(result,false);
	}
	/**
	 * userId 評価者 id(評価対象)いずれかがnullでfalseを返す
	 */
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void test2_4(String userId,String by,String id) {
		logic=new GoodCommentLogic(userId,by,id,source);
		MockitoAnnotations.initMocks(this);
		result=logic.goodCount();
		assertEquals(result,false);
	}
	static Stream<Arguments> argumentsProvider(){
		return Stream.of(
				Arguments.of("Shima5","Shima3",""),
				Arguments.of("Shima3","","1"),
				Arguments.of("Shima2","",""),
				Arguments.of("","Shima2","6")
		);
	}

}
//InjectMocks-->
		//****falseはok,true x
//		when(dao.goodCountIfExists(userId, id)).thenReturn(true);
//		result=logic.exists(userId, id, source);
//		assertEquals(result,true);
