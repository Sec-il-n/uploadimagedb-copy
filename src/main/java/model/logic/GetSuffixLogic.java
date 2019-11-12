package model.logic;

public class GetSuffixLogic {
	public String getSuffix(String filename) {

		if(filename == null)
			return null;

		int point= filename.lastIndexOf(".");

		if(point != -1) {
			return filename.substring(point + 1);
		}
		return filename;
	}
}