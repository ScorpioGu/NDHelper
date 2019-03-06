package com.cc4j.pojo;

public class Bmp extends NdpProtocol{
	
	private int periodLen;
	private int listenTimeLen;
	public int getPeriodLen() {
		return periodLen;
	}
	public void setPeriodLen(int periodLen) {
		this.periodLen = periodLen;
	}
	public int periodLenMote() {
		return listenTimeLen;
	}
	public int getListenTimeLen() {
		return listenTimeLen;
	}
	public void setListenTimeLen(int listenTimeLen) {
		this.listenTimeLen = listenTimeLen;
	}
	public Bmp(byte protocolType, byte beaconSendMode, int periodLen,
			int listenTimeLen) {
		super(protocolType, beaconSendMode);
		this.periodLen = periodLen;
		this.listenTimeLen = listenTimeLen;
	}
	
	
}
