package com.cc4j.pojo.protocol;

import com.cc4j.pojo.protocol.NdpProtocol;

public class Searchlight_Stripe_S extends NdpProtocol {

	private int slotLen;					//时隙长度
	private int slotExtensionLen;			//时隙扩展长度
	private int t;
	
	public Searchlight_Stripe_S(byte protocolType, byte beaconSendMode,
			int slotLen, int slotExtensionLen, int t) {
		super(protocolType, beaconSendMode);
		this.slotLen = slotLen;
		this.slotExtensionLen = slotExtensionLen;
		this.t = t;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
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

	
}
