package model.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class RewriteFileLogic {
	public  String rewrite(String base64 ,Path deleteFilePath)  {
		byte[] buff=Base64.getDecoder().decode(base64);
		ByteArrayInputStream in=new ByteArrayInputStream(buff);
		String msg;
		try {
			Files.copy(in, deleteFilePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			msg="書き換えに失敗しました。";
			e.printStackTrace();
		}
		msg="書き換えが完了しました。";
		return msg;

	}

}
