package test.model.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import model.logic.CreateFileLogic;

class CreateFileLogicTest {
	CreateFileLogic logic;
	Path path;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void set() throws Exception {
		logic=new CreateFileLogic();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@ValueSource(strings= {"953-hoge.jpg","1-300hoge.png","test.jpeg","test-hoge.JPG"})
	void test1(String filename) {
		String newFileName=logic.getCreatedFileName(filename);
		Pattern p=Pattern.compile("^[0-9]{13}[_.*\\.][jpg|png|jpeg|JPG]");//x ^[1-9]{13}_ --> need []
		Matcher m=p.matcher(newFileName);

		assertTrue(m.find());
	}
	/**
	 * ↓すべてpassしてしまう。(実際作成できていない,Path=null)
	 * ??別環境で／tmpが使えるのか
	 * @throws IOException
	 */
//	@ParameterizedTest
//	@ValueSource(strings= {"jpg","png","jpeg","JPG"})
//	void test2(String suffix) throws IOException {
//		path=Paths.get("/tmp");
////		path=Paths.get("/test");
////		path=Paths.get("src/test/resources");
//		assertNull(logic.createFile(path, suffix));
//		assertThat(logic.createFile(path, suffix),is(instanceOf(Path.class)));
//	}

}
