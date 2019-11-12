package model.beans;
import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class ImageBean implements Serializable{
	@CsvBindByName
	private int id;
	@CsvBindByName
	private String title;
	@CsvBindByName
	private String filename;
	@CsvBindByName
	private String userId;
	@CsvBindByName
	private String text;
	@CsvBindByName
	private String date_time;

	public ImageBean() {}

	public ImageBean(int id,String title,String filename, String userId
	, String text, String date_time){
		this.id = id;
		this.title = title;
		this.filename = filename;
		this.userId = userId;
		this.text = text;
		this.date_time = date_time;
	}

	public ImageBean(int id,String title,String filename, String text){
		this.id=id;
		this.title=title;
		this.filename=filename;
		this.text = text;
	}
	public ImageBean(int id,String title,String filename){
		this.id=id;
		this.title=title;
		this.filename=filename;
	}
	public ImageBean(String title,String filename){
		this.title=title;
		this.filename=filename;
	}
	public ImageBean(int id){
		this.id=id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename=filename;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId=userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text=text;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time=date_time;
	}


}