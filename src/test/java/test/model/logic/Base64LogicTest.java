package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

import org.junit.jupiter.api.Test;

import model.logic.Base64Logic;
//still not sure how to test Part
class Base64LogicTest {
//	@Mock//(can't mock Interface)
	Part part;
//	@InjectMocks
	Base64Logic b64logic;

	String suffix="jpg";
	String testTmpPath="src/test/resources/1563062226948_test.jpg";

//	@BeforeEach
//	void initMocks() throws Exception {
//		MockitoAnnotations.initMocks(this);
//	}
//dont't need that test
//4 practice Pattern(passed 9/12)
	@Test
	void test2() throws IOException {
		b64logic=new Base64Logic();
		String sb = b64logic.execute64(testTmpPath);
		Pattern p= Pattern.compile("^[data:image/jpg;base64,].+");
		Matcher macher=p.matcher(sb);
//		verify(b64logic,times(1)).(sb.toString());
		assertThat(sb,notNullValue());
		assertThat(macher.find(),is(true));
	}

}
