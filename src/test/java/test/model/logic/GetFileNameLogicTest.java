package test.model.logic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.logic.GetFileNameLogic;

class GetFileNameLogicTest {
	@InjectMocks
	GetFileNameLogic logic;
	@Mock
	Part part;

	@BeforeEach
	void initMock() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
//part=mockのみ... x NullPointer(part?)=> injectMocks... ok
	@Test
	void test1() {
		String formdata="form-data; name=\"file\"; filename=\"pic_278.png\"";
//		when(part.getHeader("Content-Disposition")).thenReturn(formdata);
		doReturn(formdata).when(part).getHeader("Content-Disposition");
		assertThat(logic.getFileName(part),is(equalTo("pic_278.png")));
	}
}
