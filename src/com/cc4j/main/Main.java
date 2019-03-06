package com.cc4j.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.cc4j.service.MsgProcessor;
import com.cc4j.pojo.Mote2;
import com.cc4j.serial.SerialInteractor;
import com.cc4j.service.SerialsRecog;
import com.cc4j.pojo.NdpMsg;
import com.cc4j.gui.NeigDiscoUI;

public class Main {

	//key为节点id，value为mote2对象
	private Map<Integer, Mote2> moteMap = new HashMap<Integer, Mote2>();
	//存放消息的阻塞队列
	private BlockingQueue<NdpMsg> msgQueue = new LinkedBlockingQueue<NdpMsg>();
	
	//主函数
	public static void main(String[] args) {
		new Main().start();
	}
	
	/**
	 * 	程序开始
	 * */
	public void start(){
		//生成节点ID号和串口之间的映射表
		Map<Integer, String> idMap = SerialsRecog.idMapInit();
		
		//填充MoteMap，每个mote对象中保存着它自己的interactor
		for(Entry<Integer, String> set: idMap.entrySet()) {
			int moteId = set.getKey();
			String port = set.getValue();
			SerialInteractor interactor = new SerialInteractor(port, moteId, msgQueue);
			interactor.connectSerial();
			Mote2 mote = new Mote2();
			mote.setId(moteId);
			mote.setInteractor(interactor);
			moteMap.put(moteId, mote);
		}

		//设置邻节点
		//相互作为了邻居节点 一个节点和其他的节点都是它的邻节点
		for(int i: moteMap.keySet()) {
			Mote2 mote = moteMap.get(i);
			for(int j: moteMap.keySet()) {
				if(i != j) {
					mote.setNeighMote(moteMap.get(j));
				}
			}
		}
		
		//生成界面
		new NeigDiscoUI(moteMap).FrameInit();

		//处理消息队列中的消息
		new Thread(new Runnable() {
			@Override
			public void run() {
				MsgProcessor processor = new MsgProcessor(moteMap);
				while(true) {
					try {
						processor.process(msgQueue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}		
			}
			
		}).start();		
	}

}
