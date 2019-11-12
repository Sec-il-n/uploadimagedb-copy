package test.model.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.stream.Stream;

import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.logic.ErrorCheckLogic;

class ErrorCheckLogicTest2 {
	ErrorCheckLogic logic;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	@BeforeEach
	public void setUp() throws Exception {
		logic=new ErrorCheckLogic ();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@NullSource
	public void testchecknull(Part part) {
		assertFalse(logic.checkNull(part));
	}
	//how to tesst?? HttpServletRequest.getPart(String name)-->Part.size()
	@Mock
	Part part;
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void checkEmpty() {
		when(part.getSize()).thenReturn((long)0);
		assertFalse(logic.checkEmpty(part));
	}
	@ParameterizedTest
	@NullAndEmptySource
	public void testcheckEmpty(String str) {//null or empty("" !(" ")) =true
		assertFalse(logic.checkEmpty(str));
	}
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings= {"     ","　",})
	public void testcheckBlank(String str) {//null or blunk (""&&" ") =true
		assertFalse(logic.checkBlank(str));
	}
	@ParameterizedTest
	@MethodSource("provider1")
	void checkLengthMintest(String str,int min) {
		assertFalse(logic.checkLengthMin(str, min));
	}
	@ParameterizedTest
	@MethodSource("provider2")
	void checkLengthMintest_2(String str,int min) {
		assertTrue(logic.checkLengthMin(str, min));
	}
	@ParameterizedTest
	@MethodSource("provider2")
	void checkLengthMaxtest(String str,int min) {
		assertFalse(logic.checkLengthMax(str, min));
	}
	@ParameterizedTest
	@MethodSource("provider1")
	void checkLengthMaxtest_2(String str,int min) {
		assertTrue(logic.checkLengthMax(str, min));
	}

	static Stream<Arguments> provider1(){
		return Stream.of(
			Arguments.of("sampl",6),
			Arguments.of("",6),
			Arguments.of("sample456",10),
			Arguments.of("samp",10)
		);
	}
	static Stream<Arguments> provider2(){
		return Stream.of(
			Arguments.of("sample source",6),
			Arguments.of("   ",0),
			Arguments.of("sample1",6),
			Arguments.of("sample45678!",10)
		);
	}
	@ParameterizedTest
	@ValueSource(strings = {"0","０"})
	public void testcheckZero(String str) {
		assertFalse(logic.checkZero(str));
	}
	@ParameterizedTest
	@ValueSource(ints = {0})
	public void testcheckZero(int n) {
		assertFalse(logic.checkZero(n));
	}
	@ParameterizedTest
	@ValueSource(ints = {0,111,1111})
	public void testcheckAge1(int i) {
		assertFalse(logic.checkAge(i));
	}
	@ParameterizedTest
	@ValueSource(ints = {1,110,25})
	public void testcheckAge2(int i) {
		assertTrue(logic.checkAge(i));
	}
	@ParameterizedTest
	@ValueSource(strings = {"12","111"})
	public void testcheckNumber1(String str){
		assertTrue(logic.checkNumber(str));
	}

	@ParameterizedTest
	@ValueSource(strings = {"1s","ss"})
	public void testcheckNumber2(String str){
		assertFalse(logic.checkNumber(str));
	}
}
