package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.beans.ImageBean;
import model.beans.Mutter;
//need to return empty list even if they don't have any comments
//@dao return null --> @Logic return empty Object
//注：テストの検証で上記にそぐわない箇所(findAllByUser)あり
public class MutterDAO{
	static final String URL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_019203fe6399d5c?autoReconnect=true"
			+ "&useUnicode=true&characterEncoding=utf8";
	static final String USERNAME = System.getenv("DB_USERNAME");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
	private final DataSource source;

	public MutterDAO(DataSource source){
		this.source=source;
	}

	public List<ImageBean> findAll(){

		Connection conn=null;
		List<ImageBean> filesList=new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="SELECT i.Id, i.title, i.filename, m.userId, m.text, m.date_time FROM mutter_image i JOIN mutter m ON i.Id=m.Id AND i.title=m.title ORDER BY i.Id DESC";
//			String sql="SELECT i.Id, i.title, i.filename, m.userId, m.text, m.date_time FROM mutter_image i JOIN mutter m ON i.Id=m.Id ORDER BY i.Id DESC";
			PreparedStatement st=conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String title=rs.getString("title");
				String filename=rs.getString("filename");
				String userId=rs.getString("userId");
				String text=rs.getString("text");
				Timestamp tms=rs.getTimestamp("date_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String date_time=sdf.format(tms);
//				String date_time=rs.getString("date_time");
				ImageBean imgb=new ImageBean(id,title,filename,userId, text, date_time);
				filesList.add(imgb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {if(conn!=null){
				conn.close();
			}
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return filesList;
	}

	public List<ImageBean> findAllByUser(String userid){
		Connection conn=null;
		List<ImageBean> yourPostedList=new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();

			String sql="SELECT i.Id, i.title, i.filename, m.userId, m.text, m.date_time FROM mutter_image i JOIN mutter m ON i.Id=m.Id WHERE m.userId=? ORDER BY i.Id DESC";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,userid);
			ResultSet rs=ps.executeQuery();

			while(rs.next()) {
				int id=rs.getInt("id");
				String title=rs.getString("title");
				String filename=rs.getString("filename");
				String userId=rs.getString("userId");
				String text=rs.getString("text");
				Timestamp tms=rs.getTimestamp("date_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String date_time=sdf.format(tms);
				ImageBean imgb=new ImageBean(id,title,filename,userId,text,date_time);
				yourPostedList.add(imgb);
			}
			rs.close();
			ps.close();


		} catch (SQLException e) {
			e.printStackTrace();
			return yourPostedList;
		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return yourPostedList;
			}
		}
		return yourPostedList;
	}
// ✔︎ShowMutterLogic
	public ImageBean findEach(String id){
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="SELECT I.ID, I.TITLE, I.FILENAME, M.USERID, M.TEXT, M.DATE_TIME FROM MUTTER_IMAGE AS I ,MUTTER AS M WHERE I.ID=M.ID AND I.ID=?;";
			PreparedStatement ps=conn.prepareStatement(sql);

			int i=Integer.parseInt(id);
			ps.setInt(1,i);
			ResultSet rs=ps.executeQuery();

			while(rs.next()) {
				int Id=rs.getInt("id");
				String title=rs.getString("title");
				String filename=rs.getString("filename");
				String userId=rs.getString("userId");
				String text=rs.getString("text");
				Timestamp tms=rs.getTimestamp("date_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String date=sdf.format(tms);
				ImageBean imgb=new ImageBean(Id,title,filename,userId,text,date);
				return imgb;
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

		return null;
	}

//upload
//delete??
	public boolean rewrite(Mutter mutter) {
		Connection conn=null;

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			conn=source.getConnection();
			String sql="UPDATE MUTTER SET title=?,text=? WHERE id=?";
			PreparedStatement rs=conn.prepareStatement(sql);
			rs.setString(1,mutter.getTitle());
			rs.setString(2,mutter.getText());
			rs.setInt(3,mutter.getId());
//			rs.setTimestamp(4,new java.sql.Timestamp(new java.util.Date().getTime()));

			int result=rs.executeUpdate();
			if(result!=1) {
				return false;
			}
			rs.close();

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

