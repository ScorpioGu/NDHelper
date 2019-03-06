package com.cc4j.pojo;


import com.cc4j.serial.SerialInteractor;

public class Mote {
	private static int repeatCount;
	private static int maxDelay;
	private int id;
/*************************************************************************************************************************/		//20180402@LW
	//private int localTime;			节点为uint32_t,java应该使用long
	private long localTime;
/*************************************************************************************************************************/
	private int delay;
	private SerialInteractor interactor;
	private Mote neighMote;
	private boolean localTimeValid = false;
	private boolean commandSent = false;
	
	

	public Mote getNeighMote() {
		return neighMote;
	}
	public void setNeighMote(Mote neighMote) {
		this.neighMote = neighMote;
	}

	private boolean restart;
	
	public boolean isRestart() {
		return restart;
	}
	public static int getRepeatCount() {
		return repeatCount;
	}
	public static void setRepeatCount(int repeatCount) {
		Mote.repeatCount = repeatCount;
	}
	public static int getMaxDelay() {
		return maxDelay;
	}
	public static void setMaxDelay(int maxDelay) {
		Mote.maxDelay = maxDelay;
	}
	public void setRestart(boolean restart) {
		this.restart = restart;
	}

	public long getLocalTime() {
		return localTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLocalTime(long localTime) {
		this.localTime = localTime;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public SerialInteractor getInteractor() {
		return interactor;
	}
	public void setInteractor(SerialInteractor interactor) {
		this.interactor = interactor;
	}
	
	public void clear() {
		setLocalTime(0);
	}
	
	public boolean isLocalTimeValid() {
		return localTimeValid;
	}
	
	public void setLocalTimeValid(boolean vflag) {
		this.localTimeValid = vflag;
	}
	
	public boolean isCommandSent() {
		return commandSent;
	}
	
	public void setCommandSent(boolean sflag) {
		this.commandSent = sflag;
	}
	
}
