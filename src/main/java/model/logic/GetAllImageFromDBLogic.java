package model.logic;

import java.util.List;

import javax.sql.DataSource;

import dao.MutterDAO;
import model.beans.ImageBean;;

public class GetAllImageFromDBLogic{

	DataSource source;

	public GetAllImageFromDBLogic() {
		super();
	}
	public GetAllImageFromDBLogic(DataSource source) {
		super();
		this.source=source;
	}

	public List<ImageBean> execute(){
		MutterDAO dao = new MutterDAO(source);
		List<ImageBean> filesList = dao.findAll();
		return filesList;
	}

	public List<ImageBean> listByUser(String userId){
		MutterDAO dao = new MutterDAO(source);
		List<ImageBean> yourPostedList = dao.findAllByUser(userId);
		return yourPostedList;
	}

	public ImageBean execute(String id){
		MutterDAO dao = new MutterDAO(source);
		ImageBean imgb=dao.findEach(id);
		return imgb;
	}

}

