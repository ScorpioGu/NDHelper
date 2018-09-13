package com.cc4j.pojo.protocol;


/**
 * 		Disco 协议
 * */
public class Disco extends NdpProtocol {

	private int slotlen;		//时隙长度，单位ms
	private int prime1;			//素数1
	private int prime2;			//素数2
	
	
	public int getSlotlen() {
		return slotlen;
	}
	public void setSlotlen(int slotlen) {
		this.slotlen = slotlen;
	}
	public int getPrime1() {
		return prime1;
	}
	public void setPrime1(int prime1) {
		this.prime1 = prime1;
	}
	public int getPrime2() {
		return prime2;
	}
	public void setPrime2(int prime2) {
		this.prime2 = prime2;
	}
	
	public Disco(byte protocolType, byte beaconSendMode, int slotlen,
			int prime1, int prime2) {
		super(protocolType, beaconSendMode);
		this.slotlen = slotlen;
		this.prime1 = prime1;
		this.prime2 = prime2;
	}

	
	

}
