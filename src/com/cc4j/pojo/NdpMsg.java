package com.cc4j.pojo;

import com.cc4j.serial.NdpSerial;

/*
 * 给串口数据包添加节点Id字段
 **/
public class NdpMsg {
	private NdpSerial ndpSerial;
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
