package com.cc4j.serial;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

import com.cc4j.pojo.NdpMsg;
/**
 * 串口通信交互类
 * 每个节点都有自己的串口通信交互对象
 * @author Administrator
 *
 */
public class SerialInteractor implements MessageListener{
    private String port;
    private int moteId;
    private MoteIF moteIF;
    private BlockingQueue<NdpMsg> msgQueue;
	public SerialInteractor(String port, int moteId, BlockingQueue<NdpMsg> msgQueue) {
		super();
		this.port = port;
		this.moteId = moteId;
		this.msgQueue = msgQueue;
	}
	
	/**
	 * 串口接收到消息后添加到消息队列中
	 */
	@Override
	public void messageReceived(int to, Message message) {
	    msgQueue.add(new NdpMsg((NdpSerial)message, moteId));
	}

    /**     	    	
	 * 将节点初始化，进行连接串口注册监听器
	 */
	public void connectSerial() {
		String source = "serial@" + port + ":telosb";
		PhoenixSource phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err); //connect serials
		moteIF = new MoteIF(phoenix);
		moteIF.registerListener(new NdpSerial(), this);
	}
	
	/**
	 * 发包，计算机给节点发消息
	 */
	public void sendPackets(NdpSerial payload) {    
	    try {
			moteIF.send(0, payload);
	    } catch (IOException exception) {
	        System.err.println("Exception thrown when sending packets. Exiting.");
	        System.err.println(exception);
		}
	}
}


