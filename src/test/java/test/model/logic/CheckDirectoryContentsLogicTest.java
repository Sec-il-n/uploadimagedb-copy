package test.model.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import model.logic.CheckDirectoryContentsLogic;

class CheckDirectoryContentsLogicTest {
	CheckDirectoryContentsLogic logic;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		logic=new CheckDirectoryContentsLogic();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	/**
	 * 存在するファイルまでのパスを引数にとりtrueを返す
	 * (存在確認)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"src/test/resources/1563062226948_test.jpg","src/test/resources/empty.txt"})
	void test1(String s) {
		Path path=Paths.get(s);
		assertTrue(logic.checkDirectry(path));
	}
	/**
	 * 存在しないファイルまでのパスを引数にとりfalseを返す
	 * (存在確認)
	 */
	@ParameterizedTest
	@ValueSource(strings = {"/test.jpg","src/test/empty.txt"})
	void test1_2(String s) {
		Path path = Paths.get(s);
		assertFalse(logic.checkDirectry(path));
	}
}
