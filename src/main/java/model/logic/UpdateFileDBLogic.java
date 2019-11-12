package model.logic;

import javax.sql.DataSource;

import dao.UploadFileDAO;
import model.beans.ImageBean;
import model.beans.Mutter;;

public class UpdateFileDBLogic{

	DataSource source;
	public UpdateFileDBLogic() {
		super();
	}
	public UpdateFileDBLogic(DataSource source) {
		super();
		this.source=source;
	}
	public String rewriteImage(ImageBean bean){
		UploadFileDAO dao = new UploadFileDAO(source);
		boolean result = dao.rewriteImage(bean);

		String msg;
		if(result) {
			msg="(DB)Image書き換えが完了しました";
		}else {
			msg="(DB)Image書き換えに失敗しました";
		}
		return msg;
	}
	public String rewriteMutter(Mutter bean2){
		UploadFileDAO dao = new UploadFileDAO(source);
		boolean result = dao.rewriteMutter(bean2);

		String msg;
		if(result) {
			msg="(DB)Text書き換えが完了しました";
		}else {
			msg="(DB)Text書き換えに失敗しました";
		}
		return msg;
	}

}

