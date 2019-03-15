## NDHelper：Neighbour Discovery Helper
用于控制，记录多种邻节点发现协议的专用工具软件。使用tinyos提供的tinyos.jar建立telosb节点与计算机的串口通信。
该软件设置节点的工作参数，记录节点运行协议期间的信息，控制节点实验结束之后重新设置下一次实验节点的运行时间，统计实验结果记录到文件。

gui包包含设置节点协议以及工作参数的界面类。

main包包含启动类。读取配置文件、执行shell初始化USB端口与端口上所插入节点的id的映射表。建立节点与计算机的串口连接。
启动参数设置界面。开始工作消息处理线程

pojo包包含程序操作的普通java对象，Log类记录节点运行期间每次实验的各种信息。Mote与Mote2代表节点。NdpMsg是要处理的数据包，它包含了从节点发送过
来的数据包NdpSerial与发送该数据包的节点的id。

serial包中NdpSerial是串口通信的数据包，为了维持节点断与计算机端数据包格式的一致。我们在节点端定义串口数据包的struct,NdpSerial
是由tinyos提供的自动化工具自动生成的，其格式与节点端的struct相同。SerialInteractor负责建立连接，发送数据，接受数据。该类实现了
监听器接口，当收到消息时会调用messageReceived方法，将收到的数据包封装后放入消息队列。每个端口都对应了一个SerialInteractor对象。

service包提供了消息处理器。对不同的协议进行处理。SerialRecog通过读取配置文件、执行shell初始化USB端口与端口上所插入节点的id的映射表。

setting存放随机数，用于实际实验。

util提供了一些工具类，用于文件处理，Properties读取。

## TIPS:
    该工具需配合telosb节点使用
    节点端串口通信的模块在NdpSerial文件夹中
    


