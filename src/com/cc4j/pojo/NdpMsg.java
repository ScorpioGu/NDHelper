package com.cc4j.pojo;


import com.cc4j.serial.NdpSerial;

public class NdpMsg {
	private NdpSerial ndpSerial;//？？是一些和通信有关的数据包信息吗 还是啥
	private int moteId;
	
	public NdpMsg(NdpSerial ndpSerial, int moteId) {
		super();
		this.ndpSerial = ndpSerial;
		this.moteId = moteId;
	}
	public NdpSerial getNdpSerial() {
		return ndpSerial;
	}
	public void setNdpSerial(NdpSerial ndpSerial) {
		this.ndpSerial = ndpSerial;
	}
	public int getMoteId() {
		return moteId;
	}
	public void setMoteId(int moteId) {
		this.moteId = moteId;
	}
	
}
