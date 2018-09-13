package com.cc4j.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/** 文件操作工具类*/
public class FileTool {

	/** 单例设计模式  */
	private static FileTool instance = new FileTool();
	
	private FileTool(){}
	
	public static FileTool getInstance(){
		return instance;
	}
	
	/** 从指定文件、指定行读取指定行数放到List
	 * 
	 * @filePath  文件路径
	 * @startLine	起始行号，从1开始
	 * @lineNumber	要读取的总行数
	 * 
	 * @return	List,内含读取到的文件内容
	 * 
	 * */
	public List<String> readFile(String filePath,long startLine,int lineNumber){
		List<String> fileContentList = null;
		int currentLineNum = 0;			//当前行号
		try{
			File file = new File(filePath);
			fileContentList = new ArrayList<String>();
			if(file.isFile() && file.exists()){				//文件是否合法
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String lineText = null;
                while((lineText = bufferedReader.readLine()) != null){
                	currentLineNum++;
                	if((currentLineNum >= startLine) && (currentLineNum < startLine+lineNumber)){	//要读取的内容
                		fileContentList.add(lineText);
                	}
                }		
			}else{
                System.out.println("找不到指定的文件");
            }
			return fileContentList;

		}catch(Exception e){
			System.out.println("读取文件内容出错");
		}
		return fileContentList;
	}
}
	
	