package model4test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetObjectToDeleteLogic {
//need to get key list from s3
	public static boolean getCreatedFileName(String keyName) {
//		Pattern p=Pattern.compile("^[upload/1569378][0-9]{6}[_1-200×300.jpg]$");
		Pattern p=Pattern.compile("^upload/[0-9]{13}_test_kq98qtnhvh9k8et.jpg$");
//		Pattern p=Pattern.compile("[test_kq98qtnhvh9k8et.jpg]$");
		Matcher m=p.matcher(keyName);
		return m.matches();
	}
//	public static List<String> getKeysList() throws Exception {
//		List<String> Keys=new ArrayList<>();
//		DataSource source=new GetDataSourceLogic().getDataSource();
//		GetAllImageFromDBLogic logic=new GetAllImageFromDBLogic(source);
//		List<ImageBean> bean=logic.execute();
//		for(ImageBean b:bean) {
//			Keys.add(b.getFilename());
//		}
//		return Keys;//null!!!!!!!
//		}
	public static Map<String,String> getObjectKeyName(Map<String,String> s3Keys) throws Exception {
		Map<String,String> toDelete=new HashMap<>();

		for(Map.Entry<String, String> s3keySet : s3Keys.entrySet()) {
			if(getCreatedFileName(s3keySet.getKey())) {
				toDelete.put(s3keySet.getKey(), s3keySet.getValue());
			}

		}
		return toDelete;
	}
//	public static Map<String,String> getObjectKeyName(Map<String,String> s3Keys,List<String> Keys) throws Exception {
//		Map<String,String> toDelete=new HashMap<>();
//
//		for(Map.Entry<String, String> s3keySet : s3Keys.entrySet()) {
//			for(String savedKey: Keys) {
//				if(s3keySet.getKey().equals(savedKey)) {
//				}else {
//					toDelete.put(s3keySet.getKey(), s3keySet.getValue());
//				}
//			}
//		}
//		return toDelete;
//	}

}
