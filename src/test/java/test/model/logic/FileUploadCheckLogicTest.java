package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.logic.FileUploadCheckLogic;

class FileUploadCheckLogicTest {
	FileUploadCheckLogic logic;
	String checkMsg;
	@Mock
	Part part;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		logic=new FileUploadCheckLogic();//new しないとNullPointerになる
	}
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@AfterEach
	void tearDown() throws Exception {
	}
/**
 * title ,text Check
 * 問題ないのでcheckMsg=null
 * @param title
 */
	@ParameterizedTest
	@ValueSource(strings = {"moge","サンプル","mogemoge1に行きました！"})
	void test1(String str) {
		//title
		assertThat(logic.checkTitle(str),is(checkMsg));
		//text
		assertThat(logic.checkText(str),is(checkMsg));

	}
	/*
	 * title未入力 or 空文字列
	 */
	@ParameterizedTest
	@NullAndEmptySource//utils.String.isEmpty(),isBlank()=>null'or''&'""" "
	void test1_2(String str) {
		assertThat(logic.checkTitle(str),is(anyOf(
				equalTo("titleを入力してください。Emp"),
				equalTo("titleを入力してください。Blank")
			)
		));
	}
	/*
	 * text未入力 or 空文字列
	 */
	@ParameterizedTest
	@NullAndEmptySource//utils.String.isEmpty(),isBlank()=>null'or''&'""" "
	void test2(String str) {
		assertThat(logic.checkText(str),is(anyOf(
				equalTo("textを入力してください。Emp"),
				equalTo("textを入力してください。Bla")
				)
				));
	}
	/**
	 * titleが規定文字数以上でmassageが出る
	 */
	@Test
	void test1_3() {
		checkMsg="title文字数を確認してください。(65文字以内)";
		String str=charProvider(7);
		assertThat(logic.checkTitle(str),is(equalTo(checkMsg)));
	}
	/**
	 * textが規定文字数以上でmassageが出る
	 */
	@Test
	void test2_2() {
		checkMsg="textの文字数を確認してください。(255文字以内)";
		String str=charProvider(30);
		assertThat(logic.checkText(str),is(equalTo(checkMsg)));
	}
	static String charProvider(int n) {
		String str="appleapple";
		StringBuilder br=new StringBuilder(str);
		for(int i=1;i<n;i++) {
			br.append(str);
		}
		return br.toString();

	}
	/**
	 *※
	 */
	@Test
	void test3() throws IOException {
		when(part.getSize()).thenReturn((long) 0);
		when(part.getSubmittedFileName()).thenReturn(null);//!n
		when(part.getInputStream()).thenReturn(null);
		assertThat(logic.checkFile(part),is(equalTo("ファイルを添付してください。")));
	}
	@ParameterizedTest
	@NullSource
	void test3_2(Part part) throws IOException {
		assertThat(logic.checkFile(part),is(equalTo("ファイルを添付してください。n")));
	}




}
