package test.servlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;

import servlet.ToFileUploadJsp;


public class ToFileUploadJspTest {

	private ServletTestModule tester;
	private WebMockObjectFactory factory;

	@BeforeEach
	void setUp() throws Exception {
		factory = new WebMockObjectFactory();
		tester = new ServletTestModule(factory);

	}

	@Test
	void test() throws ServletException, IOException {
		tester.createServlet(ToFileUploadJsp.class);
		tester.doGet();
		int expectedCode=200;

		assertEquals(expectedCode, factory.getMockResponse().getStatusCode());
	}

}
