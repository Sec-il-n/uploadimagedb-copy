
package model.logic;

import javax.servlet.http.Part;


public class ErrorCheckLogic{

	public boolean checkNull(Part part){
		if(part==null){
			return false;
		}return true;
	}
	
	public boolean checkEmpty(Part part){
		if(part.getSize()==0){
			return false;
		}return true;
	}

	public boolean checkEmpty(String str){
		if(org.apache.commons.lang3.StringUtils.isEmpty(str)){
			length=0 ※=return NullPointer if put nul
			return false;
		}return true;
	}
	public boolean checkBlank(String str){
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			return false;
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

	public boolean checkAge(int i){
		if(i<=0 || i>110){
			return false;
		}return true;
	}

	public boolean checkZero(String str){
		if(str.equals("0") || str.equals("０")){
			return false;
		}return true;
	}
	
	public boolean checkZero(int n){
		if(n==0){
			return false;
		}
		return true;
	}
}
