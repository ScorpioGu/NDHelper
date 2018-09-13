package com.cc4j.pojo;


/** 日志*/
public class Log {
	
	/** 当前轮数  */
	private int roundNum;
	
	/** 此轮的发现时间   */
	private long discoveryTime;
	
	/** 此轮对方发送Beacon的时间  */
	private long nebSendTime;
	
	/** 此轮发现延迟  */
	private long latencyTime;
	
	/** 此轮过期时间  没有过期则为0，一个正数， 单位tick   */
	private int passedTime;
	
	/** 此轮启动时间  单位tick*/
	private long bootTime;
	
	/** 此轮时隙时间  单位tick*/
	private int slotTime;

	public int getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public long getDiscoveryTime() {
		return discoveryTime;
	}

	public void setDiscoveryTime(long discoveryTime) {
		this.discoveryTime = discoveryTime;
	}

	public long getNebSendTime() {
		return nebSendTime;
	}

	public void setNebSendTime(long nebSendTime) {
		this.nebSendTime = nebSendTime;
	}

	public long getLatencyTime() {
		return latencyTime;
	}

	public void setLatencyTime(long latencyTime) {
		this.latencyTime = latencyTime;
	}

	public int getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(int passedTime) {
		this.passedTime = passedTime;
	}

	public long getBootTime() {
		return bootTime;
	}

	public void setBootTime(long bootTime) {
		this.bootTime = bootTime;
	}

	public int getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(int slotTime) {
		this.slotTime = slotTime;
	}

	public Log(int roundNum, long discoveryTime, long nebSendTime,
			long latencyTime, int passedTime, long bootTime, int slotTime) {
		super();
		this.roundNum = roundNum;
		this.discoveryTime = discoveryTime;
		this.nebSendTime = nebSendTime;
		this.latencyTime = latencyTime;
		this.passedTime = passedTime;
		this.bootTime = bootTime;
		this.slotTime = slotTime;
	}
	
	
	
	
	
}
