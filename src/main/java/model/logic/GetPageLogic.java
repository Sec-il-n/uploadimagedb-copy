package model.logic;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import dao.PagingDAO;
import model.beans.ImageBean;

public class GetPageLogic {

	DataSource source;



	public GetPageLogic() {
		super();
	}
	public GetPageLogic(DataSource source) {
		super();
		this.source=source;
	}

	public List<ImageBean> findPage(int n)throws IOException {
		PagingDAO dao=new PagingDAO(source);
		List<ImageBean> pagedList = dao.findPage(n);
		return pagedList;
	}

	public List<ImageBean> findPageByUser(String userid,int n)throws IOException {
		PagingDAO dao=new PagingDAO(source);
		List<ImageBean> pagedList = dao.findPageByUser(userid,n);
		return pagedList;
	}
	public String toShow() {
		String path="/WEB-INF/jsp/showImages.jsp";
		return path;
	}
	public String toEdit() {
		String path="/WEB-INF/jsp/getEditFile.jsp";
		return path;
	}
	public String toError() {
		String path="/WEB-INF/jsp/errorpage.jsp";
		return path;
	}
}
