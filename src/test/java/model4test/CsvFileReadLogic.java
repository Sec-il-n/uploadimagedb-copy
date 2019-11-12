package model4test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import model.beans.ImageBean;


public class CsvFileReadLogic {

	public List<ImageBean> openCsvToBean(String file) throws UnsupportedEncodingException, FileNotFoundException, IOException{

		List<ImageBean> list=null;
		try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"))){
			CsvToBeanBuilder<ImageBean> builder=new CsvToBeanBuilder<ImageBean>(reader);
			builder.withType(ImageBean.class);
			list=builder.build().parse();

		}
		return list;

	}



//xx Stringのみ?  1行目飛ばし?
//	public List<String> readCsvFile(String filepath) throws Exception {
//		try(FileReader fr=new FileReader(filepath);
//			BufferedReader br=new BufferedReader(fr);){
//
//			String line = null;
//			while((line=br.readLine())!= null) {
//				StringTokenizer tokenizer=new StringTokenizer(line, ",");
//				while(tokenizer.hasMoreTokens()) {
//					tokenizer.nextToken().toString();
//	........
//				}
//			}
//		}
//		return null;
//	}
//	public String[] readCsvFile(String filepath) throws Exception {
//		try(FileReader fr=new FileReader(filepath);
//				BufferedReader br=new BufferedReader(fr);){
//
//			String line;
//			String [] words = null;
//			//		StringTokenizer token;
//			while((line=br.readLine()) != null) {
//				byte[] b=line.getBytes();
//				line=new String(b);
//				words=line.split(",", -1);
//			}
//			return words;
//		}
//	}
}
