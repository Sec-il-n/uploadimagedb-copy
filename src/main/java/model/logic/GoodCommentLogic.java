package model.logic;

import javax.sql.DataSource;

import dao.GoodonCommentDAO;


public class GoodCommentLogic{
	String userId;
	String by;
	String id;
	DataSource source;
	public GoodCommentLogic() {
		super();
	}
	public GoodCommentLogic(String userId,String by,String id,DataSource source) {
		this.userId=userId;
		this.by= by;
		this.id= id;
		this.source= source;
	}

	public boolean goodCount() {
		boolean result =true;

		if(!(userId.equals(by)) && userId!=null && id!=null){
				result=exists(userId,id,source);

			if(!result) {
				result=execute(userId, id, source);
				return result;

			} else if(result){
				result=reset(userId, id,source);
				return result;
			}
		} else {
			return false;
		}
		
		return true;
	}

	public boolean exists(String userId,String id,DataSource source){
		GoodonCommentDAO dao=new GoodonCommentDAO(source);
		boolean result=dao.goodCountIfExists(userId,id);

		return result;
	}

    	public boolean execute(String userId,String id,DataSource source){
    		GoodonCommentDAO dao=new GoodonCommentDAO(source);
       		boolean result=dao.goodCount(userId,id);
      		return result;
    	}

    	public boolean reset(String userId,String id,DataSource source){
    		GoodonCommentDAO dao=new GoodonCommentDAO(source);
    		boolean result=dao.resetGoodCount(userId,id);//assertion error?
    		return result;
	}

	public int total(String id,DataSource source){
		GoodonCommentDAO dao=new GoodonCommentDAO(source);
		int count=dao.goodCountTotal(id);
		return count;
	}


}
