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



