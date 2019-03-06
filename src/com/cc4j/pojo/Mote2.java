package com.cc4j.pojo;

public class Mote2 extends Mote {
		
	public Mote2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/** 信道 */
	private int channel;
	
	/** 功率 */
	private int power;	
	
	/** 是否使用cca */
	private boolean cca;
	
	/** 运行何种ndp协议 */	
	private NdpProtocol ndpProtocol;
	
	/** 启动时间 */
	private int bootTime;
	
	/** 时隙时间 */
	private int slotTime;	
	

	/** 轮数 */
	private int roundNum;			
	
	/** 最大轮数 也就是统计次数*/
	private int countNum;			
	
	/** 节点运行日志 */
	private Log log;
	
	/** 发现延迟 */
	private long latencyTime;
	
	
	/** 文件行号  */
	private long fileLineNum;
	



/*****************************************************************************/	
	
	public long getFileLineNum() {
		return fileLineNum;
	}
	public void setFileLineNum(long fileLineNum) {
		this.fileLineNum = fileLineNum;
	}
	
	public long getLatencyTime() {
		return latencyTime;
	}
	public void setLatencyTime(long latencyTime) {
		this.latencyTime = latencyTime;
	}
	
	
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	
	
	public int getCountNum() {
		return countNum;
	}
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	
	public int getSlotTime() {
	return slotTime;

	}
	public void setSlotTime(int slotTime) {
		this.slotTime = slotTime;
	}
	public int getRoundNum() {
		return roundNum;
	}
	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getBootTime() {
		return bootTime;
	}
	public void setBootTime(int bootTime) {
		this.bootTime = bootTime;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public double getpower() {
		return power;
	}
	public void setpower(int power) {
		this.power = power;
	}
	public boolean isCca() {
		return cca;
	}
	public void setCca(boolean cca) {
		this.cca = cca;
	}

	public Mote2(int channel, int power, boolean cca, NdpProtocol macProtocol) {
		super();
		this.channel = channel;
		this.power = power;
		this.cca = cca;
		this.ndpProtocol = macProtocol;
	}
	public NdpProtocol getNdpProtocol() {
		return ndpProtocol;
	}
	public void setNdpProtocol(NdpProtocol ndpProtocol) {
		this.ndpProtocol = ndpProtocol;
	}
	
	
	
}
