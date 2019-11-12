package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.logic.IsValidFileLogic;

class IsValidFileLogicTest {
	@Mock
//	@Spy
	Part part;
	@InjectMocks
	IsValidFileLogic logic;
	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	/**
	 * 拡張子が「"jpg", "jpeg", "png", "JPG", "JPEG"」のファイル名でtrueを返す
	 */
	@ParameterizedTest
	@ValueSource(strings = {"test.jpg","143268742_test.png","test2.jpeg","test3.JPG"})
	void test1(String str) {
		assertTrue(logic.execute(str));
	}
	/**
	 * 拡張子が「"jpg", "jpeg", "png", "JPG", "JPEG"」以外のファイル名でfalseを返す
	 */
	@ParameterizedTest
	@ValueSource(strings = {"test.pdf","143268742_test.txt","test2.csv","test3.PNG"})
	void test1_2(String str) {
		assertFalse(logic.execute(str));
	}
	@Test
	void test2() throws Throwable {
		String msg = null;
		try(InputStream in=new FileInputStream("src/test/resources/1563062226948_test.jpg")){
		when(part.getInputStream()).thenReturn(in);
		assertThat(logic.execute(part),is(msg));
		}
	}
	/**
	 * not working...-->worked! add errormsg  ImageIO.read(is);
	 * partが空ファイル/not image fileでerrorメッセージ
	 */
	@Test
	void test2_2() throws Throwable {
		String msg = "ファイルの種類・内容を確認してください。";
		try(InputStream in=new FileInputStream("src/test/resources/csv/mutter_beanslist.csv")){
			when(part.getInputStream()).thenReturn(in);
			assertThat(logic.execute(part),is(msg));
		}
	}

}
