package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import model.beans.Account;

public class RegisterDAO{
	static final String URL = System.getenv("JDBC_URL");
	static final String USERNAME = System.getenv("DB_USERNAME");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

//    private final MysqlDataSource source;
    private final DataSource source;


    public RegisterDAO(MysqlDataSource source){
    	this.source=source;
    }
	public RegisterDAO(DataSource source){
	    this.source=source;
	}

//	public void properties() throws IOException {
//		GetDataSourceLogic logic=new GetDataSourceLogic();
//		Properties props=logic.getProperties();
//		URL=props.getProperty("JDBC_URL");
//		USERNAME=props.getProperty("DB_USERNAME");
//		PASSWORD=props.getProperty("DB_PASSWORD");
//
//	}

	public Boolean register(Account ac){

		Connection conn=null;

		String userId=ac.getUserId();
	    String pass=ac.getPass();
	    String name=ac.getName();
	    int age=ac.getAge();

	    try {
//			conn=source.getConnection();
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


			String sql="INSERT INTO ACCOUNT (userId,pass,name,age) VALUES(?,?,?,?);";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,userId);
			st.setString(2,pass);
			st.setString(3,name);
			st.setInt(4,age);
			int result=st.executeUpdate();

			if(result==1){
				return true;
//				return account;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
//			return null;
		}finally{
		    try {
				if(conn!=null){
				    conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    return false;
//		return account;
	}


	public boolean registerUpdate(Account old, Account ac){

		Connection conn=null;
		String userId=old.getUserId();
		String pass=old.getPass();

		String newUserId=ac.getUserId();
		String newPass=ac.getPass();
		String newName=ac.getName();
	    int newAge=ac.getAge();

		try {
//			conn=source.getConnection();
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			String sql="UPDATE ACCOUNT SET userId=?, pass=?, name=?, age=? WHERE userId=? AND pass=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,newUserId);
			st.setString(2,newPass);
			st.setString(3,newName);
			st.setInt(4,newAge);
			st.setString(5,userId);
			st.setString(6,pass);
			int result=st.executeUpdate();

			if(result==1){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean registerDelete(Account ac){

		Connection conn=null;

		String userId=ac.getUserId();
		String pass=ac.getPass();

		try {
//			conn=source.getConnection();
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			String sql="DELETE FROM ACCOUNT WHERE userId=? AND pass=?";
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,userId);
			st.setString(2,pass);
			int result=st.executeUpdate();

			if(result==1){
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}