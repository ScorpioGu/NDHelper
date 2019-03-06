package com.cc4j.pojo;

/*
 * 		所有Mac协议的父类,主要包含协议的公共参数
 * */
public class NdpProtocol {				
	private byte protocolType;
	private byte beaconSendMode;
	public byte getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(byte protocolType) {
		this.protocolType = protocolType;
	}
	public byte getBeaconSendMode() {
		return beaconSendMode;
	}
	public void setBeaconSendMode(byte beaconSendMode) {
		this.beaconSendMode = beaconSendMode;
	}
	public NdpProtocol(byte protocolType, byte beaconSendMode) {
		super();
		this.protocolType = protocolType;
		this.beaconSendMode = beaconSendMode;
	}
	
	
	
}
