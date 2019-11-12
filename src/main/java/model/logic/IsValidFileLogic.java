package model.logic;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

//
public class IsValidFileLogic {
	public boolean execute(String name){
	    if (name != null) {
	       String[] perms = { "jpg", "jpeg", "png", "JPG", "JPEG"};//大文字に揃えるやり方、heic対策
	       String[] names = name.split("\\.");
	       for (String perm: perms) {
	    	   if (perm.equals(names[names.length - 1])) {
		           return true;
		       }
		   }
	    }
		   return false;
	}

	public String execute(Part part) throws Throwable{
		InputStream is = part.getInputStream();//IOException
		BufferedImage bi = null;
		String isImgMsg = null;
		try {
			bi = ImageIO.read(is);//戻り(BufferedImage)がnullでもExceptionにならない
			if(bi==null) {
				isImgMsg = "ファイルの種類・内容を確認してください。";
			}
		} catch (IllegalArgumentException e) {
			isImgMsg = "ファイルの種類・内容を確認してください。";
			e.printStackTrace();
		}//IllegalArgumentException - streamがnullの場合。->not image or 0byteのファイル識別

		return isImgMsg;
	}
}


