package com.cc4j.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cc4j.pojo.protocol.Disco;
import com.cc4j.pojo.protocol.Hello;
import com.cc4j.pojo.Mote2;
import com.cc4j.pojo.NdpMsg;
import com.cc4j.pojo.protocol.Searchlight_S;
import com.cc4j.pojo.protocol.UConnect;
import com.cc4j.serial.NdpSerial;
import com.cc4j.serial.SerialInteractor;
import com.cc4j.util.ExcelTool;
import com.cc4j.util.FileTool;


/**
 * 处理消息队列中的消息
 * @author gucc
 *
 */

public class MsgProcessor {
	private static final short NDP_LOCALTIME = 0;       //null
	private static final short NDP_STOP = 1;	   //computer->node 计算机让节点停
	private static final short NDP_START = 2;      //computer->node　计算机让节点开始下一次实验，包里面携带开始实验的节点绝对时间
	private static final short NDP_DEBUG = 3;      //node->computer　节点给计算机发调试信息，计算机打印到文件即可
	private static final short NDP_DISCOVERY = 4;  //node->computer　节点给计算机发相互发现的信息，当两个节点相互发现的时候．计算机给节点发NDP_START
	private static final short NDP_ERROR = 5;
	private static final short NDP_PARAMETERS_1 = 6;
	private static final short NDP_PARAMETERS_2 = 7;
	private static final short NDP_RESERVE_1 = 8;
	private static final short NDP_RESERVE_2 = 9;	
	private static final short NDP_INIT = 10;			//计算机发给节点的消息类型，表示节点初始化
			
	private Map<Integer, Mote2> moteMap;
	
	/**  是否加入过期矫正	*/
	private static final boolean ifAddNdpError = false;	
	
	/**  固定延迟	*/
	private static final int baseDelay = 5500 ;	
	
	/** 是否是第一次收到local类型的数据*/
	private boolean firstReceive = true;
	
	/** 第一个发local消息中的  receiveTime*/
	private long firstReceiveTime = 0;
	
	/** 第一个发local消息中的  sendTime*/
	private long firstSendTime = 0;
	
	/**第一个发local消息mote的id*/
	private int firstLocalMoteID = 0;
	
	/**  节点A、B的运行日志	B节点是后发discory类型消息的节点*/
	private List <String[]> moteLogList = new ArrayList<String[]>();	
	private List <String[]> NeigMoteLogList = new ArrayList<String[]>();

	//以下是各个协议不同占空比下对应的素数对
	/**disco  占空比  -> 素数对*/
    private static final Map<String, String> discoDutyCycleMapPrimes = new HashMap<String, String>() {
    	{
    		put("1", "269,271");
    		put("2", "113,127");
    		put("3", "79,83");
    		put("4", "59,61");
    		put("5", "41,59");
    		put("6", "37,43");
    		put("7", "29,41");
    		put("8", "29,31");
    		put("9", "23,31");
    		put("10", "19,31");
    		//hello_s
    		
/*			put("1", "163,340");
    		put("2", "83,235");
    		put("3", "59,105");
    		put("4", "41,198");
    		put("5", "37,51");
    		put("6", "29,69");
    		put("7", "23,291");
    		put("8", "23,37");
    		put("9", "19,63");
    		put("10", "17,70");*/
    	}
    };
    
    private static final Map<String, String> uconnectDutyCycleMapPrimes = new HashMap<String, String>() {
    	{
    		//u-connect
    		put("1", "173,173");
    		put("2", "89,89");
    		put("3", "59,59");
    		put("4", "43,43");
    		put("5", "37,37");
    		put("6", "29,29");
    		put("7", "29,29");
    		put("8", "23,23");
    		put("9", "19,19");
    		put("10", "17,17");	
    	}
    };
    
    private static final Map<String, String> searchLightDutyCycleMapPrimes = new HashMap<String, String>() {
    	{
    		//searchlight
/*    		put("1", "200,100");
    		put("2", "100,50");
    		put("4", "50,25");
    		put("8", "25,12");*/

    		//hello_S
    		put("1", "163,340");
    		put("2", "83,235");
    		put("3", "59,105");
    		put("4", "41,198");
    		put("5", "37,51");
    		put("6", "29,69");
    		put("7", "23,291");
    		put("8", "23,37");
    		put("9", "19,63");
    		put("10", "17,55");
    	}
    };
    
    private static final Map<String, String> helloDutyCycleMapPrimes = new HashMap<String, String>() {
    	{
    		put("1", "151,680");
    		put("2", "79,206");
    		put("3", "53,124");
    		put("4", "41,51");
    		put("5", "31,49");
    		put("6", "23,110");
    		put("7", "23,28");
    		put("8", "19,26");
    		put("9", "17,25");
    		put("10", "13,102");
    	}
    };
    

    /********************************************************************************************************************************/		//20180419@LW
    
    /** 文件数据缓存*/
    private static List<String> dataList = new ArrayList<String>();
    
    /** 下一次开始读取的文件行号*/
    private static long currentLineNumber = 0;
    
    /**文件数据缓存索引*/
    private static int currentDataListIndex = 0;
    
    /** 文件数据缓存大小*/
    private static final int dataListSize = 500;
    
    /**是否是第一次处理数据 */
    private static boolean firstProcess = true;
    
    /** 占空比以及时隙时间所在文件路径 */
    private static String filePath = System.getProperty("user.dir")+java.io.File.separator+"src"+java.io.File.separator+"com"+
			java.io.File.separator+"cc4j"+java.io.File.separator+"setting"+java.io.File.separator+"input.txt";//DataRandom1  input
    /********************************************************************************************************************************/
    
    /**上一次接收信息的轮数*/
    private long lastRoundNum = -1;
    /** 时间过期标志*/
    private boolean motePassedFlag = false;
    private boolean neigMotePassedFlag = false;
    
    /** 第一次发送discovery类型数据的节点Id号*/
    private int firstDiscoveryId = 0;
    
	public MsgProcessor(Map<Integer, Mote2> moteMap2) {
		super();
		this.moteMap = moteMap2;
	}

	public void process(NdpMsg msg) throws IOException {

		int id = msg.getMoteId();
		Mote2 mote = moteMap.get(id); //moteMap<Integer,Mote2>  get就是通过key获取
		Mote2 neighborMote = (Mote2)mote.getNeighMote();//这里的mote是Mote2类型。所以可以强转
		//int passedTime = 0;	//如果加入过期矫正，记录过期时间		
		SerialInteractor interactor = mote.getInteractor();//这个类型包含了一些发送数据包函数。是用于通信的
		SerialInteractor neighborInteractor = neighborMote.getInteractor();
		
		NdpSerial ndpMsg = msg.getNdpSerial();
		short type = ndpMsg.get_type();
		System.out.println("Process 　" + mote.getId() + " " + "Type "+ type + " : " + ndpMsg.getString_msg() + " : " + System.currentTimeMillis());
		
		if(firstProcess){					//说明是第一次处理消息,指定起始读取的文件行号
			firstProcess=false;
			currentLineNumber = mote.getFileLineNum();//从界面获取起始行号
		}

		if((currentDataListIndex>= dataList.size()) || (currentDataListIndex < 0)|| (dataList.size() == 0)){			//需要更新dataList中的数据了后者当currentDataListIndex=0时，接收到NDP_ERROR类型的消息
			if(currentDataListIndex>dataList.size())
				currentDataListIndex = 0;
			if(currentDataListIndex < 0){
				if(currentLineNumber!= mote.getFileLineNum()){
					currentLineNumber=currentLineNumber-dataList.size()-1;	
				}
			}
			dataList = FileTool.getInstance().readFile(filePath, currentLineNumber, dataListSize);
			currentDataListIndex = 0;
			currentLineNumber+=dataList.size();
		}
		
		
		
		switch(type) {
			case NDP_LOCALTIME:
				//现在是ｌｏｃａｌＴｉｍｅＩｎｆｏ既有自己的本地时间也有对方的本地时间咯？那在发这个信息之前，它们需要有一个发包收包的过程咯
				String[] localTimeInfo = ndpMsg.getString_msg().split(",");
				mote.setRestart(true);		//用于判断是否接收过节点的NDP_LOCALTIME类型数据。
				
				if(neighborMote != null && (!neighborMote.isRestart())){				//neighborMote没有发送过NDP_LOCALTIME类型数据，可以更新receiveTime和
					mote.setLocalTime(Long.parseLong(localTimeInfo[0].trim()));						
					mote.getNeighMote().setLocalTime(Long.parseLong(localTimeInfo[1].trim()));	
				}else{
					
				}
				if(neighborMote != null && neighborMote.isRestart() && mote.isRestart()) {	//已经收到了两个节点的NDP_LOCALTIME类型的信息，可以给节点发数据了	
					if(mote.getRoundNum() < mote.getCountNum()){
						String msgA = "";			//给先发localTime类型数据的节点的消息,也就是给neighborMote
						String msgB = "";			//给后发localTime类型数据的节点的消息
						
						String nextStartTimeA =	Long.parseLong(localTimeInfo[1].trim())+baseDelay*32 +"";		//给先发localTime类型数据的节点的nextStartTime
						String nextStartTimeB =	Long.parseLong(localTimeInfo[0].trim())+baseDelay*32 +"";		//给后发localTime类型数据的节点的nextStartTime

						long slotTimeA = 0;			//时隙时间
						long slotTimeB = 0;
						
						long beforeSlotTimeA = 0;
						long beforeSlotTimeB = 0;
						
						int slotLenMote = 0;
						int slotLenNeigMote = 0;
						
						String primesA="";
						String primesB="";
						
						String data = dataList.get(currentDataListIndex);					//文件中的数据
						System.out.println("currentLine="+(currentLineNumber+currentDataListIndex-dataList.size())+","+"data = " + data);
						
						String[] dataArray = data.split(",");			//dataArray[0] dutyCycleA  dataArray[1] dutyCycleB   dataArray[2] slotTimeA   dataArray[2] slotTimeB

						byte ndpType = mote.getNdpProtocol().getProtocolType();				//获取节点运行协议类型,默认两个节点运行的ndp协议一直
						//注意下面几处没有ｂｒｅａｋ，可以继续往下执行
						switch(ndpType){
							case 0:
								Disco discoMote = (Disco) mote.getNdpProtocol();					//mote 运行的Disco
								Disco discoNeigMote = (Disco) neighborMote.getNdpProtocol();		//neigMote 运行的Disco
								
								slotLenMote = discoMote.getSlotlen();					//两个节点设定的时隙长度
								slotLenNeigMote = discoNeigMote.getSlotlen();
								
								primesA = discoDutyCycleMapPrimes.get(dataArray[0]);		//A节点素数对
								primesB = discoDutyCycleMapPrimes.get(dataArray[1]);		//B节点素数对
								
							case 1:	
								if(ndpType == 1){
									UConnect uConnetMote = (UConnect) mote.getNdpProtocol();					//mote 运行的Disco
									UConnect uConnectNeigMote = (UConnect) neighborMote.getNdpProtocol();		//neigMote 运行的Disco
									
									slotLenMote = uConnetMote.getSlotLen();					//两个节点设定的时隙长度
									slotLenNeigMote = uConnectNeigMote.getSlotLen();
									
									primesA = uconnectDutyCycleMapPrimes.get(dataArray[0]);		//A节点素数对
									primesB = uconnectDutyCycleMapPrimes.get(dataArray[1]);		//B节点素数对
								}
								
							case 2:
								if(ndpType == 2){
									Searchlight_S searchLightMote = (Searchlight_S) mote.getNdpProtocol();					//mote 运行的Disco
									Searchlight_S searchLightNeigMote = (Searchlight_S) neighborMote.getNdpProtocol();		//neigMote 运行的Disco
									
									slotLenMote = searchLightMote.getSlotLen();					//两个节点设定的时隙长度
									slotLenNeigMote = searchLightNeigMote.getSlotLen();
									
									/**实际上是tsA，tsB*/
									primesA = searchLightDutyCycleMapPrimes.get(dataArray[0]);		//A节点素数对
									primesB = searchLightDutyCycleMapPrimes.get(dataArray[1]);		//B节点素数对
								}

							case 4:
								if(ndpType == 4){
									Hello helloMote = (Hello) mote.getNdpProtocol();					//mote 运行的Disco
									Hello helloNeigMote = (Hello) neighborMote.getNdpProtocol();		//neigMote 运行的Disco
									
									slotLenMote = helloMote.getSlotLen();					//两个节点设定的时隙长度
									slotLenNeigMote = helloNeigMote.getSlotLen();
									
									/** 实际上是 c n*/
									primesA = helloDutyCycleMapPrimes.get(dataArray[0]);		//A节点素数对
									primesB = helloDutyCycleMapPrimes.get(dataArray[1]);		//B节点素数对
								}

								String[] primesArray_A = primesA.split(",");				//A节点两个素数
								String[] primesArray_B = primesB.split(",");				//B节点两个素数
								
								int prime1A = Integer.parseInt(primesArray_A[0].trim());	//A节点第一个素数
								int prime2A = Integer.parseInt(primesArray_A[1].trim());	//A节点第二个素数
								
								int prime1B = Integer.parseInt(primesArray_B[0].trim());	//B节点第一个素数
								int prime2B = Integer.parseInt(primesArray_B[1].trim());	//B节点第二个素数

								beforeSlotTimeA = Long.parseLong(dataArray[2].trim());
								beforeSlotTimeB = Long.parseLong(dataArray[3].trim());
								//算出处于哪个ｓｌｏｔ
								slotTimeA = (beforeSlotTimeA*32)%(prime1A*prime2A*slotLenMote);
								slotTimeB = (beforeSlotTimeB*32)%(prime1B*prime2B*slotLenNeigMote);
								System.out.println("prime1A="+prime1A+","+"prime2A="+prime2A+","+"slotLenMote="+slotLenMote+","+"slotTimeA="+slotTimeA);
								System.out.println("prime1B="+prime1B+","+"prime2B="+prime2B+","+"slotLenNeigMote="+slotLenNeigMote+","+"slotTimeB="+slotTimeB);
								msgA = prime1A+","+prime2A+","+nextStartTimeA+","+slotTimeA;
								msgB = prime1B+","+prime2B+","+nextStartTimeB+","+slotTimeB;													
								break;
							case 3:
								break;

							case 5:
								break;
							case 6:
								break;
							case 7:
								break;
						}
						
						//System.out.println("msgA="+msgA+",msgB="+msgB);      
						NdpSerial payload = new NdpSerial();
						payload.set_type(NDP_START);
						payload.setString_msg(msgA);				//给先发discory类型消息节点的信息
						neighborInteractor.sendPackets(payload);
						neighborMote.setRestart(false);
						
						//邻居节点发开始命令
						NdpSerial payload2 = new NdpSerial();
						payload2.set_type(NDP_START);
						payload2.setString_msg(msgB);				//给后发discory类型消息节点的信息
						
						interactor.sendPackets(payload2);
						mote.setRestart(false);
						
						System.out.println("msgA"+msgA);
						System.out.println("msgB"+msgB);
						
						/**先发NDP_LOCALTIME类型消息节点日志*/
						/**轮数 ,节点号，发现时间，邻节点发送时间，发现延迟，过期标志，启动时间，时隙时间  */
						String [] neigMoteStr = {neighborMote.getRoundNum()+"",neighborMote.getId()+"",neighborMote.getLocalTime()+"",mote.getLocalTime()+"",
									neighborMote.getLatencyTime()+"",(neigMotePassedFlag?1:0)+"",nextStartTimeA+"",slotTimeA+"",+beforeSlotTimeA+""};
							
						/**后发NDP_LOCALTIME类型消息节点日志*/					
						String [] moteStr = {mote.getRoundNum()+"",mote.getId()+"",Long.parseLong(localTimeInfo[0].trim())+"",Long.parseLong(localTimeInfo[1].trim())+"",
									mote.getLatencyTime()+"",(motePassedFlag?1:0)+"",nextStartTimeB+"",slotTimeB+"",+beforeSlotTimeB+""};
						
						/**local和discory顺序不一致*/ /**/
						if(firstDiscoveryId == mote.getId()){
							neigMoteStr[4] =mote.getLatencyTime() + "";
							moteStr[4] = neighborMote.getLatencyTime()+"";	
						}
						
						//将这条日志添加到mote的日志列表	两个节点打印到一个文件
						moteLogList.add(neigMoteStr);
						moteLogList.add(moteStr);
						if(moteLogList.size()>=2){
							ExcelTool.getInstance().appendDataList("/opt/tinyos-2.1.2/wustl/upma/apps/tests/data.xlsx", moteLogList);
							moteLogList.clear();
						}
						System.out.println(neigMoteStr[0]+","+neigMoteStr[1]+","+neigMoteStr[2]+","+neigMoteStr[3]+","+neigMoteStr[4]+","+neigMoteStr[5]+","+neigMoteStr[6]+","+neigMoteStr[7]);	
						System.out.println(moteStr[0]+","+moteStr[1]+","+moteStr[2]+","+moteStr[3]+","+moteStr[4]+","+moteStr[5]+","+moteStr[6]+","+moteStr[7]);
						
						this.firstReceive = true;
						this.firstLocalMoteID = 0;
						this.currentDataListIndex++;
 						
						motePassedFlag= false;
						neigMotePassedFlag= false;

					}else {//stop nodes

						NdpSerial stopPayload = new NdpSerial();
						String stopTime1 = mote.getLocalTime()+ "";  
						stopPayload .set_type(NDP_STOP);
						stopPayload .setString_msg(stopTime1);
						interactor.sendPackets(stopPayload);
						System.out.println(mote.getId() + " stop");
						

						NdpSerial stopPayload2 = new NdpSerial();
						String stopTime2 = neighborMote.getLocalTime()+ "";
						stopPayload2.set_type(NDP_STOP);
						stopPayload2.setString_msg(stopTime2);
						neighborInteractor.sendPackets(stopPayload2);
						System.out.println(neighborMote.getId() + " stop");
					}
					
				}
				break;
			case NDP_STOP:     //unused
				break;
			case NDP_START:		//unused	
				break;
			case NDP_DEBUG:
				break;
			case NDP_DISCOVERY:		
				String[] discoveryInfo = ndpMsg.getString_msg().split(",");
				mote.setRoundNum(Integer.parseInt(discoveryInfo[0].trim()));	   //解析节点轮数
				mote.setLatencyTime(Long.parseLong(discoveryInfo[1].trim()));		//解析节点发现延迟
				
				if(this.firstReceive){				//需要特别注意的是loacl和discory到来的顺序有可能不一致
					this.firstLocalMoteID = id;
					this.firstReceive = false;		
				}
				break;
			case NDP_ERROR:
				String[] errorInfo = ndpMsg.getString_msg().split(",");
				long currentRoundNum = Long.parseLong(errorInfo[0].trim());
				if(currentRoundNum != lastRoundNum){
					currentDataListIndex--;	
					System.out.println("currentDataListIndex="+currentDataListIndex);
				}else{
					neigMotePassedFlag = true;
				}
				motePassedFlag = true;
				lastRoundNum = currentRoundNum;
				break;	
			case NDP_INIT:     //unused
				System.out.println(id + ":"+ndpMsg.getString_msg());
				break;	
	}
  }
}

