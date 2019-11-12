package model.logic;

public class GetTimeFileCreatedLogic {
	public String getTime(String filename) {
		if(filename == null)
			return null;

		return filename.substring(0,13);
	}
}