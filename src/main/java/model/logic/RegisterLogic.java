package model.logic;
import java.io.IOException;

import javax.sql.DataSource;

import dao.RegisterDAO;
import model.beans.Account;

public class RegisterLogic{
	DataSource source;//9/13 private static & this.source...無しでservler<-dao nullpointer
//	MysqlDataSource source;

	public RegisterLogic() {
		super();
	}
	public RegisterLogic(DataSource source) {
//	public RegisterLogic(MysqlDataSource source) {
		super();
		this.source=source;
	}

	public boolean execute(Account ac) throws IOException{
//	public boolean execute(Account ac, DataSource source) throws IOException{

		RegisterDAO dao=new RegisterDAO(source);
		boolean result=dao.register(ac);

		if(!result) {
			return false;
		}

		return true;
	}

	public boolean update(Account old, Account ac)throws IOException{

		RegisterDAO dao=new RegisterDAO(source);
        boolean result=dao.registerUpdate(old, ac);

        return result;

    }

	public boolean delete(Account ac)throws IOException{
//	public boolean delete(Account ac, DataSource source)throws IOException{

		RegisterDAO dao=new RegisterDAO(source);
		boolean result=dao.registerDelete(ac);

		return result;
	}

	//added below 2 9/13
	public String createMsg(boolean result) {
		String msg;
		if(result!=true) {
			msg="登録に失敗しました。やり直して下さい。(userId存在が存在します。)";
		}else{
			msg="登録完了しました。ログインして下さい。";
		}
		return msg;

	}


	public String getPath(boolean result) {
		String to;
		if(result!=true) {
			to="/WEB-INF/jsp/registerNull.jsp";
		}else {
			to="/WEB-INF/jsp/login.jsp";
		}
		return to;

	}

	//added 11/20
	public String deleteMsg(boolean result) {
		String msg;
		if(result!=true) {
			msg="書き換えに失敗しました。やり直して下さい。";
		}else{
			msg="登録を取り消しました。";
		}
		return msg;

	}
	public String deleteRgisterPath(boolean result) {
		String to;
		if(result!=true) {
			to="/";
		}else {
			to="/ToLoginResult";
		}
		return to;

	}

}