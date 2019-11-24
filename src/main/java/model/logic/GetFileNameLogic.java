package model.logic;

import javax.servlet.http.Part;

public class GetFileNameLogic {
	
	public String getFileName(Part part) {
		String name= null;
		
		try {
			for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
				
				if (dispotion.trim().startsWith("filename")) {//filename=\"pic_278.png\"
					 name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
					 name=name.substring(name.lastIndexOf("\\")+1);
					 break;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return name;
	}

}
