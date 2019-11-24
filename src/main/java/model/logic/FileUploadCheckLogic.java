package model.logic;

import javax.servlet.http.Part;

public class FileUploadCheckLogic {
	String checkTitle = null;
	ErrorCheckLogic c=new ErrorCheckLogic();

	public String checkTitle(String title){
	    
		if(!c.checkEmpty(title)){
			checkTitle="titleを入力してください。Emp";
		}
		if(!c.checkBlank(title)){
			checkTitle="titleを入力してください。Blank";
		}
	    	
		try {
			if(!c.checkLengthMax(title,65)){
				checkTitle="title文字数を確認してください。(65文字以内)";

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	    
		return checkTitle;
	}

	
	String checkText = null;
	
	public String checkText(String text){
		
		if(!c.checkEmpty(text)){
			checkText="textを入力してください。Emp";
		}
		if(!c.checkBlank(text)){
			checkText="textを入力してください。Bla";
		}
		
		try {
			if(!c.checkLengthMax(text,255)){
				checkText="textの文字数を確認してください。(255文字以内)";

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return checkText;
	}

	String checkFile = null;
	public String checkFile(Part part){
    	
		if(!c.checkNull(part)){
    			checkFile ="ファイルを添付してください。n";
    		}
    		try {
			if(!c.checkEmpty(part)){
				checkFile ="ファイルを添付してください。";
			}
		
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
    	
		return checkFile;
	}
}




