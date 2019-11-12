
package model.logic;

import javax.servlet.http.Part;


public class ErrorCheckLogic{

	public boolean checkNull(Part part){
		if(part==null){//Part<--処理しないのでNullPointerは発生しない
			return false;
		}return true;
	}
	//空ファイル
	public boolean checkEmpty(Part part){
		if(part.getSize()==0){//Part(処理対象)=nullの場合NullPointer
			return false;
		}return true;
	}

//	public boolean checkLength(String str){//4 Mutter
//	    if(str.isEmpty()){
//	        return false;
//	    }return true;
//	}
	public boolean checkEmpty(String str){//
		if(org.apache.commons.lang3.StringUtils.isEmpty(str)){//null or empty ("" !(" ")) <=> String.Utils isEmpty()==length=0 ※=return NullPointer if put nul
			return false;
		}return true;
	}
	public boolean checkBlank(String str){//
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){//null or blunk (""&&" ")
			return false;//
		}return true;
	}

	public boolean checkLengthMin(String str,int min){
	    if(str.length()<min){
	        return false;
	    }return true;
	}
	public boolean checkLengthMax(String str,int max){
	    if(str.length()>max){
	        return false;
	    }return true;
	}

	public boolean checkNumber(String str){
	    try {
			Integer.parseInt(str);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//->null->
	public boolean checkAge(int i){//
//		if(i>110 && i<=0){ x!!
		if(i<=0 || i>110){
			return false;
		}return true;
	}

	public boolean checkZero(String str){//?
		if(str.equals("0") || str.equals("０")){
//		if(str.equals("[0０]+")){
//		if(str==null || (str.equals("[0０]+"))){
			return false;
		}return true;
	}
	public boolean checkZero(int n){//nullpPointer ?
//		if(n==0||n==00||n==000){
		if(n==0){
			return false;
		}
		return true;
	}
//	public boolean checkAgeMax(String str){
//		if(Integer.parseInt(str)>110 && Integer.parseInt(str)<0){
//			return false;
//		}return true;
//	}
}
