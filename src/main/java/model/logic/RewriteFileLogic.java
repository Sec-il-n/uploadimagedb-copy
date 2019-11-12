package model.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class RewriteFileLogic {
//	public String execute(Path delete) throws IOException {
//		boolean result;
//		String msg=null;
//
//		try {
//			result = Files.deleteIfExists(delete);
//		} catch (DirectoryNotEmptyException e) {
//			e.printStackTrace();
//			throw new DirectoryNotEmptyException("ファイルがディレクトリで、ディレクトリが空でないために削除できなかった");
//		} catch (IOException  e) {
//		e.printStackTrace();
//		throw new IOException("入出力エラー");
//		}
//		if(result) {
//			msg="書き換えが完了しました。";
//		}else {
//			msg="書き換え(ファイル削除)に失敗しました。";
//		}
//		return msg;
//	}

	public  String rewrite(String base64 ,Path deleteFilePath)  {
//	public void rewrite(Path path,Path deleteFilePath) throws IOException {
		byte[] buff=Base64.getDecoder().decode(base64);//ネストクラス(Base64.Decoder=非static参照)
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
