package model.logic;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Logic {
	private Part part;
	private String suffix;
//overrideする側で書く？
	public Base64Logic() {}

	public Base64Logic(Part part,String suffix){
		this.part=part;
		this.suffix=suffix;
	}

//	public String execute64(Part part,String suffix) throws IOException {
	public String execute64(String path) throws IOException {
		File f=new File(path);
		StringBuffer sb = null;
		try(BufferedInputStream br=new BufferedInputStream(new FileInputStream(f));//IOException
				ByteArrayOutputStream bo=new ByteArrayOutputStream()){

			int count=0;
			byte[] buf=new byte[(int)f.length()];

			while((count=br.read(buf)) != -1) {
				bo.write(buf,0,count);
			}
			if(bo.size()>0) {//added 4 test easy
//			if(bo!=null) {//if(bo==null){}=>デッドコード(絶対実行されない)
				String base64=new String(Base64.encodeBase64(bo.toByteArray()),"ASCII");
				sb=new StringBuffer();
				sb.append("data:image/").append(suffix).append(";base64,").append(base64);
				return sb.toString();
			}
			return null;
		}
	}
	/*
	 * try without tmpfile @Edit
	 * worked but cant use to write to s3 with b64
	 */
	public String execute() throws IOException {
//		File f=new File(path);
		try(BufferedInputStream br=new BufferedInputStream(part.getInputStream(),(int)part.getSize());//IOException
				ByteArrayOutputStream bo=new ByteArrayOutputStream()){

			int count=0;
			byte[] buf=new byte[(int)part.getSize()];

			while((count=br.read(buf)) != -1) {
				bo.write(buf,0,count);
			}
			if(bo.size()>0) {
				String base64=new String(Base64.encodeBase64(bo.toByteArray()),"ASCII");
	//			StringBuffer sb=new StringBuffer();
	//			sb.append("data:image/").append(suffix).append(";base64,").append(base64);
				return base64;
			}
			return null;
		}
	}
	public String execute(String base64) throws IOException {
		StringBuffer sb=new StringBuffer();
		sb.append("data:image/").append(suffix).append(";base64,").append(base64);
		return sb.toString();
	}
}
