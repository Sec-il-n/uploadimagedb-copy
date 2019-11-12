package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.beans.ImageBean;
import model.beans.Mutter;

public class DeleteDAO  {
	static final String URL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_019203fe6399d5c?autoReconnect=true"
			+ "&useUnicode=true&characterEncoding=utf8";
	static final String USERNAME = System.getenv("DB_USERNAME");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    private final DataSource source;

    public DeleteDAO(DataSource source) {
    	this.source=source;
    }

    public boolean deleteImage(ImageBean bean) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="DELETE FROM mutter_image WHERE title=? AND filename=?";//where filename=? でも良い

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

    public boolean deleteMutter(Mutter bean2) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="delete from mutter where title=? and text=?;";//where filename=? でも良い

			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,bean2.getTitle());
			rs.setString(2,bean2.getText());

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
