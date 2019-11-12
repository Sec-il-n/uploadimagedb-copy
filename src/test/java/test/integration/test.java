package test.integration;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model4test.DeleteObjectsforTest;

public class test {
	static List<String> toDelete;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		toDelete=new ArrayList<>();
		toDelete.add("upload/1569476659559_test_kq98qtnhvh9k8et.jpg");
		toDelete.add("upload/1569476654753_test_kq98qtnhvh9k8et.jpg");

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void test21() {
		DeleteObjectsforTest.deleteObject(toDelete);
	}

}
