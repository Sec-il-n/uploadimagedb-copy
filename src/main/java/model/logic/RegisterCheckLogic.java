package model.logic;

import java.text.Normalizer;
public class RegisterCheckLogic {

	ErrorCheckLogic c=new ErrorCheckLogic();
	String checkUserId;
	public String checkUserId(String userId){//void->Stringに変更
	    if(!c.checkEmpty(userId)){
	    	checkUserId="UserIdを入力して下さい。Emp";
//	    	throw new NullPointerException("Null入力されました");

	    }
	    if(!c.checkBlank(userId)){
	    	checkUserId="UserIdを入力して下さい。bla";
//	    	throw new NullPointerException("Null,空文字入力されました");

	    }
//	    if(!c.checkLength(userId)){
//	    	checkUserId="UserIdを入力してください。Length";
//	    }

	    if(!c.checkLengthMin(userId,6)){
	    	checkUserId="文字数が正しくありません。(6文字以上10文字以内)min";
//	    	throw new IllegalStateException("文字数が正しくありません。(6文字以上10文字以内)");

	    }
	    if(!c.checkLengthMax(userId,10)){
	    	checkUserId="文字数が正しくありません。(6文字以上10文字以内)max";
//	    	throw new IllegalStateException("文字数が正しくありません。(6文字以上10文字以内)");

	    }
	    return checkUserId;
	}

	public String checkPass;
	public String checkPass(String pass){
		if(!c.checkEmpty(pass)){
			checkPass="パスワードを入力して下さい。emp";
//	    	throw new NullPointerException("Null入力されました。");

	    }
		if(!c.checkBlank(pass)){
			checkPass="パスワードを入力して下さい。bla";
//	    	throw new NullPointerException("Null,空文字入力されました");

	    }

	    if(!c.checkLengthMin(pass,6)){
	    	checkPass="文字数が正しくありません。(6文字以上10文字以内)min";
//	    	throw new IllegalStateException("文字数が正しくありません。(6文字以上10文字以内)");

	    }
	    if(!c.checkLengthMax(pass,10)){
	    	checkPass="文字数が正しくありません。(6文字以上10文字以内)max";
//	    	throw new IllegalStateException("文字数が正しくありません。(6文字以上10文字以内)");

	    }
	    return checkPass;
	}

	String checkName;
	public String checkName(String name){

//		if(!c.checkLength(name)){
//			checkName="名前を入力して下さい。Lng";
//
//		}
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
//		if(!c.checkAgeMax(age)){
//			checkAge="年齢を正しく入力して下さい。max110";
//
//		}
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
	 /**
	  * 入力値の途中にある空文字を除去
	  */
	public String findBlank(String str){
		String replaced = str.replaceAll("[\\s　]+","");
		return replaced;
	}
	public String normalize(String str){
		String normalized;
		normalized = Normalizer.normalize(str,Normalizer.Form.NFKC);
		return normalized;
	}
	//?
	public String checkNumber(String age){
		String replaced;
		replaced = age.replaceAll("([1-9]),(\\d)","$1,$2");
		return replaced;
	}
//added 9/14
	public int returnNumber(String s) {

		int age = 0;
		try {
			age=Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return age;

	}
	//added 9/14
	public String havingErrormsg() {
		return "/WEB-INF/jsp/registerNull.jsp";
	}

	public String notHavingErrormsg() {
		return "/WEB-INF/jsp/registerResult.jsp";
	}
//	public String findZero(String age){
//		if(!c.checkEmpty(age) || !c.checkBlank(age)){
//			String replaced = age.replaceAll("[0]+","　");
//			return replaced;
//		}
//		return age;
//	}
}




