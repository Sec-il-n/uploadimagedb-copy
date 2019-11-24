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

public class PagingDAO{
	
	static final String URL = System.getenv("JDBC_URL");
	static final String USERNAME = System.getenv("DB_USERNAME");
    	static final String PASSWORD = System.getenv("DB_PASSWORD");
	private final DataSource source;

	public PagingDAO(DataSource source){
		this.source=source;
	}

	public List<ImageBean> findPage(int n){
		
		Connection conn=null;
		List<ImageBean> pagedList=new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			String sql="SELECT i.ID, i.TITLE, i.FILENAME, m.USERID, m.TEXT, m.DATE_TIME FROM MUTTER_IMAGE i JOIN MUTTER m ON i.ID=m.ID "
					+ "ORDER BY i.ID DESC LIMIT ?, 5";
			PreparedStatement st=conn.prepareStatement(sql);
			
			st.setInt(1,n*5-5);
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				
				int id=rs.getInt("id");
				String title=rs.getString("title");
				String filename=rs.getString("filename");
				String userId=rs.getString("userId");
				String text=rs.getString("text");
				Timestamp tms=rs.getTimestamp("date_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String date=sdf.format(tms);
				ImageBean imgb=new ImageBean(id,title,filename,userId,text,date);
				pagedList.add(imgb);
			
			}
			st.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		
		}finally{
			
			try {
				if(conn!=null){
					conn.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return pagedList;
	}
	public List<ImageBean> findPageByUser(String userid,int n){
		
		Connection conn=null;
		List<ImageBean> yourPostedList=new ArrayList<>();

		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			String sql="SELECT i.Id, i.title, i.filename, m.userId, m.text, m.date_time FROM mutter_image i JOIN mutter m ON i.Id=m.Id "
					+ "WHERE m.userId=? ORDER BY i.Id DESC LIMIT ?,3";
			PreparedStatement st=conn.prepareStatement(sql);
			
			st.setString(1,userid);
			st.setInt(2,n*3-3);
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
				ImageBean imgb=new ImageBean(id,title,filename,userId,text,date_time);
				yourPostedList.add(imgb);
			
			}
			st.close();
			rs.close();

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
		
		return yourPostedList;
	}
}

