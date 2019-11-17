package model.logic;

import javax.sql.DataSource;

import dao.LoginDAO;
import model.beans.Account;

public class LoginLogic{

	DataSource source;

	public LoginLogic() {
		super();
	}
	public LoginLogic(DataSource source) {
		super();
		this.source=source;//need if not can't work!!
	}


	public Account execute(Account account) throws Exception {
//	public Account execute(Account account,DataSource source) throws Exception {
		LoginDAO dao=new LoginDAO(source);
		Account ac=dao.findId(account);
		return ac;
	}

//	public boolean execute(Account account) throws Exception {
//		if(account!=null){
//			return true;
//		}
//		return false;
//	}

//	public boolean execute(Account account,DataSource source) throws Exception {
//        LoginDAO dao=new LoginDAO(source);//
//        Account ac=dao.findId(account);//
//        if(ac!=null){
//        	return true;
//        }
//        return false;
//    }

	//ネストクラスにする?

	public String sessionCheck(String userId){
		String path = null;
		if(userId==null) {
			path="/WEB-INF/jsp/login.jsp";
		}
		return path;
	}

	public String getMsg(String path){
		String msg = null;
		if(path!=null){
			msg="ログインし直して下さい。";
		}
		return msg;
	}
/**
 * 結論:req,resp関連はLogicにしない
 */
	//added 4 testing smooth
//	public String acNotNull(String userId,Account ac) {//can't use inservlet =session==null
//	public String acNotNull(HttpSession session,String userId,Account ac) {//can't mock session
	public String acNotNull() {//can't mock session

		String path="/WEB-INF/jsp/loginResult.jsp";
			return path;

	}

	public String acNull() {
		String path="/WEB-INF/jsp/login.jsp";
		return path;

	}
//	public String getPathToLoginResult(boolean result){
//		String to = null;
//		if(result=true){
//			to="/WEB-INF/jsp/loginResult.jsp";
//	//
//		}else {
////			to="/WEB-INF/jsp/login.jsp";
//			to="/index.jsp";//???
//		}
//		return to;
//	}

}
