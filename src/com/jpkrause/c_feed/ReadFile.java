package com.jpkrause.c_feed;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	
	public List<String> OpenFile(InputStream input) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		List<String> dataList = new ArrayList<String>();
		String aLine;
		while((aLine = br.readLine()) != null){
			dataList.add(aLine);
		}
		br.close();
		return dataList;
	}
}
