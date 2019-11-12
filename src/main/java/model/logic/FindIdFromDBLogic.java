package model.logic;

import javax.sql.DataSource;

import dao.UploadFileDAO;;

public class FindIdFromDBLogic{

	public int execute(String filename,DataSource source){
		UploadFileDAO dao = new UploadFileDAO(source);
		String s = dao.findId(filename);
		int id = Integer.parseInt(s);
		return id;
	}
}

