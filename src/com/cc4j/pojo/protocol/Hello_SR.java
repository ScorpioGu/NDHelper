package com.cc4j.pojo.protocol;

public class Hello_SR extends NdpProtocol {
	private int slotLen;
	private int slotExtensionLen;
	private int c;
	private int n;
	public int getSlotLen() {
		return slotLen;
	}
	public void setSlotLen(int slotLen) {
		this.slotLen = slotLen;
	}
	public int getSlotExtensionLen() {
		return slotExtensionLen;
	}
	public void setSlotExtensionLen(int slotExtensionLen) {
		this.slotExtensionLen = slotExtensionLen;
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
	public Hello_SR(byte protocolType, byte beaconSendMode, int slotLen,
			int slotExtensionLen, int c, int n) {
		super(protocolType, beaconSendMode);
		this.slotLen = slotLen;
		this.slotExtensionLen = slotExtensionLen;
		this.c = c;
		this.n = n;
	}
	
	
}
