package model.logic;

import javax.servlet.http.Part;

public class GetFileNameLogic {
	public String getFileName(Part part) {
		String name= null;
//		form-data; name=\"file\"; filename=\"pic_278.png\"
		try {
			for (String dispotion : part.getHeader("Content-Disposition").split(";")) {//
				if (dispotion.trim().startsWith("filename")) {//filename=\"pic_278.png\"
					 name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();//pic_278.png\"
					 name=name.substring(name.lastIndexOf("\\")+1);//UNC パス:\\コンピュータ名(ホスト名)(の後すぐファイル名の場合？)

					 break;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return name;
	}
}