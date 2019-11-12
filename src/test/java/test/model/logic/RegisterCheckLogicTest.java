package test.model.logic;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import model.logic.RegisterCheckLogic;
//@RunWith(JUnitPlatform.class)
//@SelectClasses(ErrorCheckLogicTest.class)
public class RegisterCheckLogicTest {
	RegisterCheckLogic logic;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		logic=new RegisterCheckLogic ();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@ValueSource(strings= {"sample","sample12"})//"xx","samplename123"
	void testcheckUserId1(String str) {
		assertEquals(logic.checkUserId(str),null);
		assertEquals(logic.checkName(str),null);
		assertEquals(logic.checkAge(str),"数値を入力して下さい。");
	}

	@ParameterizedTest
	@ValueSource(strings= {"str","str2"})
	void testcheckUserId2(String str) {
		assertEquals(logic.checkUserId(str),"文字数が正しくありません。(6文字以上10文字以内)min");
		assertEquals(logic.checkPass(str),"文字数が正しくありません。(6文字以上10文字以内)min");
	}

	@ParameterizedTest
	@ValueSource(strings= {"samplesample12345","samplesample"})
	void testcheckUserId3(String str) {
		assertEquals(logic.checkUserId(str),"文字数が正しくありません。(6文字以上10文字以内)max");
		assertEquals(logic.checkPass(str),"文字数が正しくありません。(6文字以上10文字以内)max");
	}

	@ParameterizedTest
	@ValueSource(ints = {111,1111})
	void testcheckAge1(int n){
		assertEquals(logic.checkAge(n),"年齢を正しく入力して下さい。1-110");
	}

	@ParameterizedTest
	@ValueSource(ints = {0})
	void testcheckAge2(int n){
		assertEquals(logic.checkAge(n),"年齢を正しく入力して下さい。not zero");
	}
/**
 * remove all blanks from String
 * @param str
 */
	@ParameterizedTest
	@ValueSource(strings = {"a bcd   e fg hi j kl","abc de fg      h ijk l"})
	void testfindBlank(String str){
		assertEquals(logic.findBlank(str),"abcdefghijkl");
	}
/**
 * nomalize unicode(数字半角)
 */
	@ParameterizedTest
	@ValueSource(strings = {"２３","2３"})
	void testnormalize(String str){
		assertEquals(logic.normalize(str),"23");
	}
	/**
	 * remove "0" if exists at first
	 */
//	@ParameterizedTest
//	@ValueSource(strings = {"020"})
//	void testcheckNumber(String age){
//		assertEquals(logic.checkNumber(age),"20");
//	}
}
