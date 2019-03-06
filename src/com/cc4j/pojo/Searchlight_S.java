package com.cc4j.pojo;

public class Searchlight_S extends NdpProtocol {

	private int slotLen;			//时隙长度
	private int t;
	
	public int getSlotLen() {
		return slotLen;
	}
	public void setSlotLen(int slotLen) {
		this.slotLen = slotLen;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public Searchlight_S(byte protocolType, byte beaconSendMode, int slotLen,
			int t) {
		super(protocolType, beaconSendMode);
		this.slotLen = slotLen;
		this.t = t;
	}
	
	
}
