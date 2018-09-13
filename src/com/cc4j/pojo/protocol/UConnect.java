package com.cc4j.pojo.protocol;

import com.cc4j.pojo.protocol.NdpProtocol;

public class UConnect extends NdpProtocol {
	
	private int slotLen;			//时隙长度
	private int prime;				//素数
	
	public int getSlotLen() {
		return slotLen;
	}
	public void setSlotLen(int slotLen) {
		this.slotLen = slotLen;
	}
	public int getPrime() {
		return prime;
	}
	public void setPrime(int prime) {
		this.prime = prime;
	}
	public UConnect(byte protocolType, byte beaconSendMode, int slotLen,
			int prime) {
		super(protocolType, beaconSendMode);
		this.slotLen = slotLen;
		this.prime = prime;
	}
	
	
}
