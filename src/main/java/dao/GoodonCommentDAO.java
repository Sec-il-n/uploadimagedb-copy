package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class GoodonCommentDAO{

	static final String URL = System.getenv("JDBC_URL");
    static final String USERNAME = System.getenv("DB_USERNAME");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    private final DataSource source;

		public GoodonCommentDAO(DataSource source){
		this.source=source;
	}

	public boolean goodCountIfExists(String userId,String id){

		Connection conn=null;
		int i = 0;
		try {
			i = Integer.parseInt(id);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


			String sql="select exists(select*from good where userId=? and id=?)";

			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,userId);
			st.setInt(2,i);
			ResultSet rs=st.executeQuery();

			int result = 0;
			while(rs.next()) {
				result=rs.getInt(1);
			}
			if(result==0) {
				return false;
			}
			return true;

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
	}
	public boolean goodCount(String userId,String id){

		Connection conn=null;
		int i = 0;
		try {
			i = Integer.parseInt(id);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
	    try {
	    	conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


	    	String sql1="INSERT INTO Good (userId,id) VALUES(?,?);";

			PreparedStatement st=conn.prepareStatement(sql1);
			st.setString(1,userId);
			st.setInt(2,i);
			int result=st.executeUpdate();
			if(result!=1) {
				return false;//
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

		return true;
	}
	public boolean resetGoodCount(String userId,String id){

		Connection conn=null;
		int i = 0;
		try {
			i = Integer.parseInt(id);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


			String sql2="DELETE FROM Good WHERE userId=? AND id=?;";
			PreparedStatement st=conn.prepareStatement(sql2);
			st.setString(1,userId);
			st.setInt(2,i);
			int result=st.executeUpdate();
			if(result!=1) {
				return false;
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
		return true;
	}
	public int goodCountTotal(String id){

		Connection conn=null;
		int i = 0;
		try {
			i = Integer.parseInt(id);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		int count = 0;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			String sql1="SELECT COUNT(*) FROM Good WHERE id=?;";
			PreparedStatement st=conn.prepareStatement(sql1);
			st.setInt(1,i);
			ResultSet rs=st.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}return count;
	}
}
