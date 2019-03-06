package com.cc4j.service;

import com.cc4j.pojo.*;
import com.cc4j.serial.NdpSerial;
import com.cc4j.serial.SerialInteractor;
import com.cc4j.util.ExcelTool;
import com.cc4j.util.FileTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 对用户输入的参数信息进行处理发送
 *
 * @author gucc
 */


public class MsgProcessor {
    /**
     * m-c 携带两个节点的本地时间
     */
    private static final short NDP_LOCALTIME = 0;
    /**
     * c-m 停止实验
     */
    private static final short NDP_STOP = 1;
    /**
     * c-m 计算机让节点开始下一次实验，包里面携带开始实验的节点绝对时间
     */
    private static final short NDP_START = 2;
    private static final short NDP_DEBUG = 3;
    /**
     * c-m 节点发现之后，给计算机发送，包里携带本次实验的发现延迟与实验轮数
     */
    private static final short NDP_DISCOVERY = 4;
    private static final short NDP_ERROR = 5;
    private static final short NDP_PARAMETERS_1 = 6;
    private static final short NDP_PARAMETERS_2 = 7;
    private static final short NDP_RESERVE_1 = 8;
    private static final short NDP_RESERVE_2 = 9;
    private static final short NDP_INIT = 10;


    private Map<Integer, Mote2> moteMap;

    /**
     * 是否加入过期矫正
     */
    private static final boolean ifAddNdpError = false;

    /**
     * 固定延迟
     */
    private static final int baseDelay = 1000;

    /**
     * 是否是第一次收到local类型的数据
     */
    private boolean firstReceive = true;

    /**
     * 第一个发local消息mote的id
     */
    private int firstLocalMoteID = 0;

    private static long tableTimeA = 0;
    private static long tableTimeB = 0;

    private static long pretableTimeA = 0;
    private static long pretableTimeB = 0;

    private static String dutyA = "0";
    private static String dutyB = "0";

    private static boolean isSetTitle = true;

    /**
     * 节点A、B的运行日志	B节点是后发discory类型消息的节点
     */
    private List<String[]> moteLogList = new ArrayList<String[]>();
    private List<String[]> NeigMoteLogList = new ArrayList<String[]>();

    /**
     * disco  占空比  -> 素数对
     */
    private static Map<String, String> discoDutyCycleMapPrimes = new HashMap<String, String>() {
        {
            put("1", "239,241");
            put("2", "113,127");
            put("3", "79,83");
            put("4", "59,61");
            put("5", "41,59");
            put("6", "37,43");
            put("7", "29,41");
            put("8", "29,31");
            put("9", "23,31");
            put("10", "19,31");
        }
    };


    private static Map<String, String> uconnectDutyCycleMapPrimes = new HashMap<String, String>() {
        {
            put("1", "173,173");
            put("2", "89,89");
            put("3", "59,59");
            put("4", "43,43");
            put("5", "37,37");
            put("6", "29,29");
            put("7", "29,29");
            put("8", "23,23");
            put("9", "19,19");
            put("10", "17,17");
        }
    };


    private static Map<String, String> searchLightDutyCycleMapPrimes = new HashMap<String, String>() {
        {
            put("1", "101,2500");
            put("2", "53,217");
            put("3", "37,82");
            put("4", "29,44");
            put("5", "5,7");
            put("6", "19,36");
            put("7", "17,21");
            put("8", "13,75");
            put("9", "13,18");
            put("10", "11,30");
        }
    };

    private static Map<String, String> helloDutyCycleMapPrimes = new HashMap<String, String>() {
        {
            put("1", "151,680");
            put("2", "79,206");
            put("3", "53,124");
            put("4", "41,51");
            put("5", "31,49");
            put("6", "23,110");
            put("7", "23,28");
            put("8", "19,26");
            put("9", "17,25");
            put("10", "13,102");
        }

    };

    /**
     * bmp（Circle） 占空比 -> 周期长度
     */
    private static Map<String, String> dutyCycleBmpMapPeriodLen = new HashMap<String, String>() {
        {
            // 为保护隐私，这部分删除
        }
    };

    /**
     * Nihao 占空比 -> 周期长度
     */
    private static Map<String, String> dutyCycleNihaoMapPeriodLen = new HashMap<String, String>() {
        {

            put("1", "299");
            put("2", "75");
            put("3", "43");
            put("4", "30");
            put("5", "23");
            put("6", "19");
            put("7", "16");
            put("8", "14");
            put("9", "12");
            put("10", "11");
        }
    };

    /**
     * 文件数据缓存
     */
    private static List<String> dataList = new ArrayList<String>();

    /**
     * 下一次开始读取的文件行号
     */
    private static long currentLineNumber = 0;

    /**
     * 文件数据缓存索引
     */
    private static int currentDataListIndex = 0;

    /**
     * 文件数据缓存大小
     */
    private static final int dataListSize = 2;

    /**
     * 是否是第一次处理数据
     */
    private static boolean firstProcess = true;

    /**
     * 占空比以及时隙时间所在文件路径
     */
    private static String filePath = System.getProperty("user.dir") + java.io.File.separator + "src" + java.io.File.separator + "com" +
            java.io.File.separator + "lw" + java.io.File.separator + "setting" + java.io.File.separator + "input.txt";

    /**
     * 上一次接收信息的轮数
     */
    private long lastRoundNum = -1;

    /**
     * 时间过期标志
     */
    private boolean motePassedFlag = false;
    private boolean neigMotePassedFlag = false;

    /**
     * 第一次发送discovery类型数据的节点Id号
     */
    private int firstDiscoveryId = 0;

    public MsgProcessor(Map<Integer, Mote2> moteMap2) {
        super();
        this.moteMap = moteMap2;
    }

    public void process(NdpMsg msg) throws IOException {


        int id = msg.getMoteId();
        Mote2 mote = moteMap.get(id);
        Mote2 neighborMote = (Mote2) mote.getNeighMote();
        SerialInteractor interactor = mote.getInteractor();
        SerialInteractor neighborInteractor = neighborMote.getInteractor();

        NdpSerial ndpMsg = msg.getNdpSerial();
        short type = ndpMsg.get_type();

        if (firstProcess) {
            firstProcess = false;
            currentLineNumber = mote.getFileLineNum();
        }
        if ((currentDataListIndex >= dataList.size()) || (currentDataListIndex < 0) || (dataList.size() == 0)) {
            if (currentDataListIndex > dataList.size())
                currentDataListIndex = 0;
            if (currentDataListIndex < 0) {
                if (currentLineNumber != mote.getFileLineNum()) {
                    currentLineNumber = currentLineNumber - dataList.size() - 1;
                }
            }
            dataList = FileTool.getInstance().readFile(filePath, currentLineNumber, dataListSize);
            currentDataListIndex = 0;
            currentLineNumber += dataList.size();
        }


        switch (type) {
            case NDP_LOCALTIME:
                String[] localTimeInfo = ndpMsg.getString_msg().split(",");
                mote.setRestart(true);

                if (neighborMote != null && (!neighborMote.isRestart())) {
                    mote.setLocalTime(Long.parseLong(localTimeInfo[0].trim()));
                    mote.getNeighMote().setLocalTime(Long.parseLong(localTimeInfo[1].trim()));
                } else {

                }
                if (neighborMote != null && neighborMote.isRestart() && mote.isRestart()) {
                    if (mote.getRoundNum() < mote.getCountNum()) {
                        //给先发localTime类型数据的节点的消息,也就是给neighborMote
                        String msgA = "";
                        //给后发localTime类型数据的节点的消息
                        String msgB = "";

                        //给先发localTime类型数据的节点的nextStartTime
                        String nextStartTimeA = Long.parseLong(localTimeInfo[1].trim()) + baseDelay * 32 + "";
                        //给后发localTime类型数据的节点的nextStartTime
                        String nextStartTimeB = Long.parseLong(localTimeInfo[0].trim()) + baseDelay * 32 + "";

                        pretableTimeA = tableTimeA;
                        pretableTimeB = tableTimeB;
                        tableTimeA = 0;
                        tableTimeB = 0;

                        long beforeSlotTimeA = 0;
                        long beforeSlotTimeB = 0;

                        long slotTimeA = 0;
                        long slotTimeB = 0;

                        int slotLenMote = 0;
                        int slotLenNeigMote = 0;

                        String primesA = "";
                        String primesB = "";

                        String data = dataList.get(currentDataListIndex);
                        System.out.println("currentLine=" + (currentLineNumber + currentDataListIndex - dataList.size()) + "," + "data = " + data);
                        //dataArray[0] dutyCycleA  dataArray[1] dutyCycleB   dataArray[2] slotTimeA   dataArray[2] slotTimeB
                        String[] dataArray = data.split(",");
                        byte ndpType = mote.getNdpProtocol().getProtocolType();
                        switch (ndpType) {
                            case 0:
                                Disco discoMote = (Disco) mote.getNdpProtocol();
                                Disco discoNeigMote = (Disco) neighborMote.getNdpProtocol();

                                slotLenMote = discoMote.getSlotlen();
                                slotLenNeigMote = discoNeigMote.getSlotlen();


                                primesA = discoDutyCycleMapPrimes.get(dataArray[0]);
                                primesB = discoDutyCycleMapPrimes.get(dataArray[1]);
                            case 1:
                                if (ndpType == 1) {
                                    UConnect uConnetMote = (UConnect) mote.getNdpProtocol();
                                    UConnect uConnectNeigMote = (UConnect) neighborMote.getNdpProtocol();

                                    slotLenMote = uConnetMote.getSlotLen();
                                    slotLenNeigMote = uConnectNeigMote.getSlotLen();

                                    primesA = uconnectDutyCycleMapPrimes.get(dataArray[0]);
                                    primesB = uconnectDutyCycleMapPrimes.get(dataArray[1]);
                                }

                            case 2:
                                if (ndpType == 2) {
                                    Searchlight_S searchLightMote = (Searchlight_S) mote.getNdpProtocol();
                                    Searchlight_S searchLightNeigMote = (Searchlight_S) neighborMote.getNdpProtocol();
                                    slotLenMote = searchLightMote.getSlotLen();
                                    slotLenNeigMote = searchLightNeigMote.getSlotLen();
                                    primesA = searchLightDutyCycleMapPrimes.get(dataArray[0]);
                                    primesB = searchLightDutyCycleMapPrimes.get(dataArray[1]);
                                }

                            case 4:
                                if (ndpType == 4) {
                                    Hello helloMote = (Hello) mote.getNdpProtocol();
                                    Hello helloNeigMote = (Hello) neighborMote.getNdpProtocol();
                                    slotLenMote = helloMote.getSlotLen();
                                    slotLenNeigMote = helloNeigMote.getSlotLen();

                                    /** 实际上是 c n*/
                                    primesA = helloDutyCycleMapPrimes.get(dataArray[0]);
                                    primesB = helloDutyCycleMapPrimes.get(dataArray[1]);
                                }
                                String[] primesArray_A = primesA.split(",");
                                String[] primesArray_B = primesB.split(",");

                                int prime1A = Integer.parseInt(primesArray_A[0].trim());
                                int prime2A = Integer.parseInt(primesArray_A[1].trim());

                                int prime1B = Integer.parseInt(primesArray_B[0].trim());
                                int prime2B = Integer.parseInt(primesArray_B[1].trim());

                                beforeSlotTimeA = Long.parseLong(dataArray[2].trim());
                                beforeSlotTimeB = Long.parseLong(dataArray[3].trim());
                                tableTimeA = beforeSlotTimeA;
                                tableTimeB = beforeSlotTimeB;

                                slotTimeA = (beforeSlotTimeA * 32) % (prime1A * prime2A * slotLenMote);
                                slotTimeB = (beforeSlotTimeB * 32) % (prime1B * prime2B * slotLenNeigMote);

                                msgA = prime1A + "," + prime2A + "," + nextStartTimeA + "," + slotTimeA;
                                msgB = prime1B + "," + prime2B + "," + nextStartTimeB + "," + slotTimeB;
                                break;
                            case 3:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                Bmp bmpMote = (Bmp) mote.getNdpProtocol();
                                Bmp bmpNeigMote = (Bmp) neighborMote.getNdpProtocol();
                                int periodLenA = Integer.parseInt(dutyCycleBmpMapPeriodLen.get(dataArray[0]));
                                int periodLenB = Integer.parseInt(dutyCycleBmpMapPeriodLen.get(dataArray[1]));

                                int listenLenMote = bmpMote.getListenTimeLen();
                                int listenLenNeigMote = bmpNeigMote.getListenTimeLen();

                                tableTimeA = Long.parseLong(dataArray[2].trim());
                                tableTimeB = Long.parseLong(dataArray[3].trim());


                                long periodTimeA = ((tableTimeA) % (periodLenA / listenLenMote * periodLenA)) * 32;
                                long periodTimeB = ((tableTimeB) % (periodLenB / listenLenMote * periodLenB)) * 32;

                                msgA = periodLenA + "," + "2" + "," + nextStartTimeA + "," + periodTimeA;
                                msgB = periodLenB + "," + "2" + "," + nextStartTimeB + "," + periodTimeB;

                                break;
                            case 8:
                                NiHao NihaoMote = (NiHao) mote.getNdpProtocol();
                                NiHao NihaoNeigMote = (NiHao) neighborMote.getNdpProtocol();

                                int NihaoperiodLenA = Integer.parseInt(dutyCycleNihaoMapPeriodLen.get(dataArray[0]));
                                int NihaoperiodLenB = Integer.parseInt(dutyCycleNihaoMapPeriodLen.get(dataArray[1]));

                                int NihaolistenLenMote = NihaoMote.getListenTimeLen();
                                int NihaolistenLenNeigMote = NihaoNeigMote.getListenTimeLen();

                                tableTimeA = Long.parseLong(dataArray[2].trim());
                                tableTimeB = Long.parseLong(dataArray[3].trim());


                                long NihaoperiodTimeA = ((tableTimeA) % (NihaolistenLenMote * NihaoperiodLenA * 10)) * 32;
                                long NihaoperiodTimeB = ((tableTimeB) % (NihaolistenLenMote * NihaoperiodLenB * 10)) * 32;

                                msgA = NihaoperiodLenA + "," + "2" + "," + nextStartTimeA + "," + NihaoperiodTimeA;
                                msgB = NihaoperiodLenB + "," + "2" + "," + nextStartTimeB + "," + NihaoperiodTimeB;
                                break;
                        }

                        NdpSerial payload = new NdpSerial();
                        payload.set_type(NDP_START);
                        //给先发discory类型消息节点的信息
                        payload.setString_msg(msgA);
                        neighborInteractor.sendPackets(payload);
                        neighborMote.setRestart(false);

                        NdpSerial payload2 = new NdpSerial();
                        payload2.set_type(NDP_START);
                        //给后发discory类型消息节点的信息
                        payload2.setString_msg(msgB);

                        interactor.sendPackets(payload2);
                        mote.setRestart(false);

                        System.out.println("msgA:" + msgA);
                        System.out.println("msgB:" + msgB);

                        /**先发NDP_LOCALTIME类型消息节点日志*/
                        /**轮数 ,节点号，发现时间，邻节点发送时间，发现延迟，过期标志，表中时间，时隙时间  */
                        String[] neigMoteStr = {neighborMote.getRoundNum() + "", neighborMote.getId() + "", neighborMote.getLocalTime() + "", mote.getLocalTime() + "",
                                neighborMote.getLatencyTime() + "", (neigMotePassedFlag ? 1 : 0) + "", nextStartTimeA + "", tableTimeA + "", +beforeSlotTimeA + ""};

                        /**后发NDP_LOCALTIME类型消息节点日志*/
                        String[] moteStr = {mote.getRoundNum() + "", mote.getId() + "", Long.parseLong(localTimeInfo[0].trim()) + "", Long.parseLong(localTimeInfo[1].trim()) + "",
                                mote.getLatencyTime() + "", (motePassedFlag ? 1 : 0) + "", nextStartTimeB + "", tableTimeB + "", +beforeSlotTimeB + ""};

                        /**local和discory顺序不一致*/
                        if (firstDiscoveryId == mote.getId()) {
                            neigMoteStr[4] = mote.getLatencyTime() + "";
                            moteStr[4] = neighborMote.getLatencyTime() + "";
                        }

                        long neigMoteDelay = Integer.parseInt(neigMoteStr[4]) / 32;
                        long moteDelay = Integer.parseInt(moteStr[4]) / 32;
                        long maxDelayTime = Math.max(neigMoteDelay, moteDelay);
                        if (isSetTitle) {
                            isSetTitle = false;
                            String[] title = {"行号 ", "占空比１", "占空比２", "时间１", "时间２", "有效标志 ", "节点１", "节点２", "节点１发现延迟(ms) ", "节点２发现延迟(ms) ", "发现延迟(ms) "};
                            moteLogList.add(title);
                        }

                        long currentLine = currentLineNumber + currentDataListIndex - dataList.size();
                        String[] RecordInfo = {currentLine - 1 + "", dutyA, dutyB, pretableTimeA + "", pretableTimeB + "", (motePassedFlag ? 0 : 1) + ""
                                , neighborMote.getId() + "", mote.getId() + "", neigMoteDelay + "", moteDelay + "", maxDelayTime + ""};
                        moteLogList.add(RecordInfo);
                        ExcelTool.getInstance().appendDataList("/opt/tinyos-2.1.2/wustl/upma/apps/tests/data.xlsx", moteLogList);
                        moteLogList.clear();
                        dutyA = data.split(",")[0];
                        dutyB = data.split(",")[1];

                        System.out.println(neigMoteStr[1] + "节点,发现延迟:" + Integer.parseInt(neigMoteStr[4]) / 32 + "ms" +
                                "(RecTime:" + neigMoteStr[2] + "	NbrSendTime:" + neigMoteStr[3] + ")");
                        System.out.println(moteStr[1] + "节点,发现延迟:" + Integer.parseInt(moteStr[4]) / 32 + "ms" +
                                "(RecTime:" + moteStr[2] + "	NbrSendTime:" + moteStr[3] + ")");
                        System.out.println("-----------------------------------------------------------------");
                        this.firstReceive = true;
                        this.firstLocalMoteID = 0;
                        currentDataListIndex++;

                        motePassedFlag = false;
                        neigMotePassedFlag = false;


                    } else {
                        //stop nodes
                        NdpSerial stopPayload = new NdpSerial();
                        String stopTime1 = mote.getLocalTime() + "";
                        stopPayload.set_type(NDP_STOP);
                        stopPayload.setString_msg(stopTime1);
                        interactor.sendPackets(stopPayload);
                        System.out.println(mote.getId() + " stop");

                        NdpSerial stopPayload2 = new NdpSerial();
                        String stopTime2 = neighborMote.getLocalTime() + "";
                        stopPayload2.set_type(NDP_STOP);
                        stopPayload2.setString_msg(stopTime2);
                        neighborInteractor.sendPackets(stopPayload2);
                        System.out.println(neighborMote.getId() + " stop");
                    }

                }

                break;
            case NDP_STOP:
                break;
            case NDP_START:
                break;
            case NDP_DEBUG:
                break;
            case NDP_DISCOVERY:
                String[] discoveryInfo = ndpMsg.getString_msg().split(",");
                mote.setRoundNum(Integer.parseInt(discoveryInfo[0].trim()));
                mote.setLatencyTime(Long.parseLong(discoveryInfo[1].trim()));
                //需要特别注意的是loacl和discory到来的顺序有可能不一致
                if (this.firstReceive) {
                    this.firstLocalMoteID = id;
                    this.firstReceive = false;
                }
                break;
            case NDP_ERROR:
                String[] errorInfo = ndpMsg.getString_msg().split(",");
                long currentRoundNum = Long.parseLong(errorInfo[0].trim());
                if (currentRoundNum != lastRoundNum) {
                    currentDataListIndex--;
                    System.out.println("currentDataListIndex=" + currentDataListIndex);
                } else {
                    neigMotePassedFlag = true;
                }
                motePassedFlag = true;
                lastRoundNum = currentRoundNum;
                break;
            case NDP_INIT:
                System.out.println(id + ":" + ndpMsg.getString_msg());
                break;
        }
    }
}

