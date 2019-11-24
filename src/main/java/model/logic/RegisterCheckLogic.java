package model.logic;

import java.text.Normalizer;
public class RegisterCheckLogic {

	ErrorCheckLogic c=new ErrorCheckLogic();
	String checkUserId;
	public String checkUserId(String userId){
		if(!c.checkEmpty(userId)){
			checkUserId="UserIdを入力して下さい。Emp";

		}
		if(!c.checkBlank(userId)){
			checkUserId="UserIdを入力して下さい。bla";

		}

		if(!c.checkLengthMin(userId,6)){
			checkUserId="文字数が正しくありません。(6文字以上10文字以内)min";

		}
		if(!c.checkLengthMax(userId,10)){
			checkUserId="文字数が正しくありません。(6文字以上10文字以内)max";

		}
		
		return checkUserId;
	
	}

	public String checkPass;
	public String checkPass(String pass){
		
		if(!c.checkEmpty(pass)){
			checkPass="パスワードを入力して下さい。emp";

	    	}
		if(!c.checkBlank(pass)){
			checkPass="パスワードを入力して下さい。bla";

	    	}

		if(!c.checkLengthMin(pass,6)){
			checkPass="文字数が正しくありません。(6文字以上10文字以内)min";

		}
		if(!c.checkLengthMax(pass,10)){
			checkPass="文字数が正しくありません。(6文字以上10文字以内)max";

		}
	    	
		return checkPass;
	}

	String checkName;
	public String checkName(String name){

		if(!c.checkEmpty(name)){
			checkName="名前を入力して下さい。emp";

	    	}
		if(!c.checkBlank(name)){
			checkName="名前を入力して下さい。bla";

	    	}
		if(!c.checkLengthMax(name,20)){
	    		checkName="文字数が正しくありません。(20文字以内)max";

	    	}
			
		return checkName;
	}
	
	String checkAge;
	public String checkAge(String age){

		if(!c.checkEmpty(age)){
			checkAge="年齢を入力して下さい。emp";

	    	}
		if(!c.checkBlank(age)){
			checkAge="年齢を入力して下さい。bla";

	    	}
		if(!c.checkNumber(age)){
	    	checkAge="数値を入力して下さい。";

	    	}
		if(!c.checkZero(age)){
			checkAge="年齢を正しく入力して下さい。0";

		}
	    	
		return checkAge;
    	}

	String checkNumber;
	public String checkAge(int n){
		if(!c.checkAge(n)){
			checkNumber="年齢を正しく入力して下さい。1-110";

		}
		if(!c.checkZero(n)){
			checkNumber="年齢を正しく入力して下さい。not zero";

		}
		return checkNumber;
	}

	public String findBlank(String str){
		String replaced = str.replaceAll("[\\s　]+","");
		return replaced;
	}
	public String normalize(String str){
		String normalized;
		normalized = Normalizer.normalize(str,Normalizer.Form.NFKC);
		return normalized;
	}
	public String checkNumber(String age){
		String replaced;
		replaced = age.replaceAll("([1-9]),(\\d)","$1,$2");
		return replaced;
	}
	public int returnNumber(String s) {

		int age = 0;
		try {
			age=Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return age;

	}
	public String havingErrormsg() {
		return "/WEB-INF/jsp/registerNull.jsp";
	}

	public String notHavingErrormsg() {
		return "/WEB-INF/jsp/registerResult.jsp";
	}

}




