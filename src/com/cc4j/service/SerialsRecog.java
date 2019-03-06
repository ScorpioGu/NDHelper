package com.cc4j.service;

import com.cc4j.util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SerialsRecog {
    
    /**
     * 已知唯一标识和节点号之间的映射(读取配置文件)，唯一标识和USB口之间的映射（通过motelist命令然后提取）
     * 可以生成节点号和USB口之间的映射，保存在idMap中
     */
	public static Map<Integer, String> idMapInit() {
		Map<Integer, String> idMap = new HashMap<Integer, String>();
		InputStream in = null;  
		String cmd = "motelist | grep 'dev' | awk '{print $1\",\"$2}'" ;
		String tmp;
        try {  
            Process pro = Runtime.getRuntime().exec(new String[]{"bash",  
                    "-c", cmd});  
            pro.waitFor();  
            in = pro.getInputStream();  
            BufferedReader read = new BufferedReader(new InputStreamReader(in));  
            while((tmp = read.readLine()) != null) {
            	String[] res = tmp.split(",");
				int moteId = Integer.parseInt(PropertiesUtil.getProperty(res[0]));
            	String port = res[1];
            	idMap.put(moteId, port);
            }
            in.close();
        } catch (Exception e) {  
        	System.out.println("未插入节点！");
            e.printStackTrace();  
        }
        return idMap;
	}
}

