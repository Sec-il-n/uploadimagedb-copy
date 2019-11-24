package model.logic;

import javax.servlet.http.HttpServlet;

public class GetPathNavLogic extends HttpServlet {
	
	public String getNavPath(String action) {
		String path = null;
		
		if(action.equals("start")) {
			path="/WEB-INF/jsp/howtostart.jsp";

		}else if(action.equals("comment")) {
			path="/WEB-INF/jsp/howtocoment.jsp";
		}
		
		return path;

	}

}
