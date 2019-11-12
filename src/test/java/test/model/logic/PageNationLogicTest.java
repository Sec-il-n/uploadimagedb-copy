package test.model.logic;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import model.logic.PageNationLogic;

public class PageNationLogicTest {
//	@Mock
	@Spy
	PageNationLogic plogic;

	@BeforeEach
	void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	/**
	 * コメント数136,ページ数28,現在7ページ目で「前へ」が押された場合
	 */
	@Test
	void test() {
		String s="7";
		String action="before";
		int totalPage=28;
		int total=136;
		int page=6;

		doReturn(6).when(plogic).getPage(s, action, totalPage);
		doReturn(30).when(plogic).getPageShowing(page, total, totalPage);

	}
	/**
	 * コメント数136,ページ数28,現在7ページ目で「前へ」が押された場合
	 */
	@Test
	void testMiddle() {
		String p="9";
		String action="middle";
		int totalPage=28;
		int total=136;
		int page=plogic.getPage(p, action, totalPage);
		doReturn(9).when(plogic).getPage(p, action, totalPage);
		doReturn(5).when(plogic).getPageShowing(page, total, totalPage);

	}
	@Test
	void getTotalPageTest(){
		int total=256;
		doReturn(52).when(plogic).getTotalPageCount(total);
	}
}
