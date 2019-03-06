package com.cc4j.pojo;

public class Hello extends NdpProtocol {

	private int slotLen;
	private int c;
	private int n;
	
	public int getSlotLen() {
		return slotLen;
	}
	public void setSlotLen(int slotLen) {
		this.slotLen = slotLen;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public Hello(byte protocolType, byte beaconSendMode, int slotLen, int c,
			int n) {
		super(protocolType, beaconSendMode);
		this.slotLen = slotLen;
		this.c = c;
		this.n = n;
	}
	
	
}
