package model.beans;
import java.io.Serializable;

public class Mutter implements Serializable{
	private int id;
	private String title;
	private String text;
	private String userId;
	private String date_time;

	public Mutter() {}

	public Mutter(String title,String text,String userId){
		this.title=title;
		this.text=text;
		this.userId=userId;
	}

	public Mutter(int id,String title,String text){
		this.id=id;
		this.title=title;
		this.text=text;
	}

	public Mutter(int id,String title,String text,String userId){
		this.id=id;
		this.title=title;
		this.text=text;
		this.userId=userId;
	}
	public Mutter(int id,String title,String text,String userId,String date_time){
		this.id=id;
		this.title=title;
		this.text=text;
		this.userId=userId;
		this.date_time=date_time;

	}


	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public String getUserId() {
		return userId;
	}
	public String getDate_time() {
		return date_time;
	}

}