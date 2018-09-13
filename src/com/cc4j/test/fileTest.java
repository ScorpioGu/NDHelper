package com.cc4j.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.cc4j.util.FileTool;


public class fileTest {

	public static void main(String[] args) throws IOException {
	//	List<String> fileList = FileTool.getInstance().readFile("E:\\javaWorkspace\\MyGui30\\src\\com\\cc4j\\test\\DataRandom2.txt", 5, 1);
		String filePath = System.getProperty("user.dir")+ File.separator+"src"+ File.separator+"com"+
				File.separator+"cc4j"+ File.separator+"setting"+ File.separator+"DataRandom2.txt";
		List<String> fileList = FileTool.getInstance().readFile(filePath, 1, 1);
		int i = 0;
		for(i = 0;i<fileList.size();i++){
			System.out.println(fileList.get(i));
		}
		
		long currentLineNum = 1;

	}
}
