package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import model.beans.Account;

public class LoginDAO{
	
	static final String URL = System.getenv("JDBC_URL");
    	static final String USERNAME = System.getenv("DB_USERNAME");
   	static final String PASSWORD = System.getenv("DB_PASSWORD");
	private final DataSource source;

	public LoginDAO(DataSource source){
		this.source=source;
	}
	
	public Account findId(Account ac) throws ClassNotFoundException {
	
		Connection conn=null;
		String userId;
		String pass;
		String name;
		int age;
		Account account=null;

	    try {

			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql="SELECT * FROM ACCOUNT WHERE USERID=? AND PASS=?";
			PreparedStatement st=conn.prepareStatement(sql);

			st.setString(1,ac.getUserId());
			st.setString(2,ac.getPass());
			ResultSet rs=st.executeQuery();
			
		    	if(rs.next()){
				userId=rs.getString("USERID");
				pass=rs.getString("PASS");
				name=rs.getString("NAME");
				age=rs.getInt("AGE");
				account=new Account(userId,pass,name,age);
			}
			st.close();
			rs.close();

		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NullPointerException("account(引数・戻り)いずれかがNULL");
		
	   	} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
		    
		    try {
				if(conn!=null){
				    	conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return account;
	}

}




