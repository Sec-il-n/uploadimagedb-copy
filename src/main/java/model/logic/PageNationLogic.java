package model.logic;

public class PageNationLogic {
	public int getPageTry(String p,String n,String action,int totalPage){
		int page = 0;
		int now = 0;

		if(n!=null && (action.equals("before") || action.equals("after"))){
			try {
				now = Integer.parseInt(n);
			} catch (NumberFormatException e) {//nullPointer?
				e.printStackTrace();
			}
			if(action.equals("before")) {
				if(now==1){//最大ページ数。適宜変更
					page=1;
				}else {
					page=now-1;
				}
			}else if(action.equals("after")) {
				if(now==totalPage){
					page=totalPage;
				}else {
					page=now+1;
				}
			}
			return page;

		}else if(p!=null && action.equals("middle")){
			try {
				page = Integer.parseInt(p);
			} catch (NumberFormatException e) {//nullPointer?
				e.printStackTrace();
			}
			if(page==1){
				page=1;
			}else if(page==totalPage){
				page=totalPage;
			}
		}
		return page;
	}

	public int getPage(String s,String action,int totalPage){
		int page = 0;
		int now = 0;
		try {
			now = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
//		if(0<now && now<1000){
			if(action.equals("before")) {
				if(now==1){//最大ページ数。適宜変更
					page=1;
				}else {
					page=now-1;
				}

			}else if(action.equals("after")) {
				if(now==totalPage){
					page=totalPage;
				}else {
					page=now+1;
				}

			}
//	}
			if(page==1){
				page=1;
			}else if(page==totalPage){
				page=totalPage;
			}
//			return page;
//		}
		return page;
	}
	public int getPageMiddle(String s,String action,int totalPage){
		int page = 0;
		try {
			page = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if(action.equals("middle")){//
//				int page = Integer.parseInt(s);
//			if(/*0<page && */page<1000){
				if(page==1){
					page=1;
				}else if(page==totalPage){
					page=totalPage;
				}
				return page;
		}
		return page;
	}

	public int getPageShowing(int page,int total,int totalPage){
		int in = 0;
//		if(0<page && 0<=totalPage && total<1000){
			if(page<totalPage) {
				in=page*5;
			}else if(page==totalPage) {
				in=total;
			}
//			return in;
//		}
		return in;
	}
	public int getEditPageShowing(int page,int total,int totalPage){
		int in = 0;
		if(page<totalPage) {
			in=page*3;
		}else if(page==totalPage) {
			in=total;
		}
		return in;
	}
	public String forwardToEdit(int page){
		String to = null;
		if(page==1){
			to="/WEB-INF/jsp/getEditFile.jsp";
		}else {
			to="/WEB-INF/jsp/getEditFile2.jsp";
		}
		return to;
	}
	public String forwardTo(int page){
		String to = null;
		if(page==1){
			to="/WEB-INF/jsp/showImages.jsp";
		}else {
			to="/WEB-INF/jsp/showPage.jsp";
		}
		return to;
	}

	public int getTotalPageCount(int total){

		int totalPage;
		if(total%5==0) {
			totalPage=total/5;
		}else {
			totalPage=total/5+1;
		}
		return totalPage;
	}

	public int getTotalPageCountEdit(int total){

		int totalPage;
		if(total%3==0) {
			totalPage=total/3;
		}else {
			totalPage=total/3+1;
		}
		return totalPage;
	}
}

