package model4test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetObjectToDeleteLogic {
//need to get key list from s3
	public static boolean getCreatedFileName(String keyName) {
		
		Pattern p=Pattern.compile("^upload/[0-9]{13}_test_kq98qtnhvh9k8et.jpg$");
		Matcher m=p.matcher(keyName);
		return m.matches();
	}
	
	public static Map<String,String> getObjectKeyName(Map<String,String> s3Keys) throws Exception {
		
		Map<String,String> toDelete=new HashMap<>();

		for(Map.Entry<String, String> s3keySet : s3Keys.entrySet()) {
			if(getCreatedFileName(s3keySet.getKey())) {
				toDelete.put(s3keySet.getKey(), s3keySet.getValue());
			}

		}
		return toDelete;
	
	}

	
}
