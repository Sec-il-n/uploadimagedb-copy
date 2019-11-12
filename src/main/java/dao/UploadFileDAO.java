package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.beans.ImageBean;
import model.beans.Mutter;


public class UploadFileDAO{

	static final String URL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_019203fe6399d5c?autoReconnect=true"
			+ "&useUnicode=true&characterEncoding=utf8";
	static final String USERNAME = System.getenv("DB_USERNAME");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

	private final DataSource source;

	public UploadFileDAO(DataSource source){

		this.source=source;
	}

	public boolean create(ImageBean bean) {
		Connection conn=null;

		try {
//			conn=source.getConnection();
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql="INSERT INTO mutter_image(TITLE,FILENAME) VALUES(?,?)";//?userId??
			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,bean.getTitle());
			rs.setString(2,bean.getFilename());

			int result=rs.executeUpdate();
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

//modified
	public boolean create(Mutter mutter,String id) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="INSERT INTO mutter(ID,TITLE,TEXT,USERID,DATE_TIME) VALUES(?,?,?,?,?)";
			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,id);
			rs.setString(2,mutter.getTitle());
			rs.setString(3,mutter.getText());
			rs.setString(4,mutter.getUserId());
			rs.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));

			int result=rs.executeUpdate();
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
	public boolean create(Mutter mutter) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql="INSERT INTO mutter(TITLE,TEXT,USERID,DATE_TIME) VALUES(?,?,?,?)";
			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,mutter.getTitle());
			rs.setString(2,mutter.getText());
			rs.setString(3,mutter.getUserId());
			rs.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

			int result=rs.executeUpdate();
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

	//Edit?
	public String findId(String filename){
		Connection conn=null;
		String id = null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="select Id from mutter_image where filename=?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,filename);

			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
					int i=rs.getInt("id");
					try {
						id = Integer.toString(i);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					return id;
			}
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
		return id;
	}


	public boolean rewriteImage(ImageBean bean) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="UPDATE mutter_image SET title=?,filename=? WHERE id=?";//where filename=? でも良い

			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,bean.getTitle());
			rs.setString(2,bean.getFilename());
			rs.setInt(3,bean.getId());

			int result=rs.executeUpdate();
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
//=\=MutterDAO rewrite(Mutter bean)
	public boolean rewriteMutter(Mutter bean2) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql="UPDATE mutter SET title=?,text=? WHERE id=?";//where filename=? でも良い

			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,bean2.getTitle());
			rs.setString(2,bean2.getText());
			rs.setInt(3,bean2.getId());

			int result=rs.executeUpdate();
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
}


