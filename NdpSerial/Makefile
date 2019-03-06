COMPONENT=NdpSerialAppC
NdpSerial.java:
	mig java -target=null $(CFLAGS) -java-classname=NdpSerial NdpSerial.h ndp_serial_msg -o $@

TestSerial.class: $(wildcard *.java) NdpSerial.java
	javac -target 1.4 -source 1.4 *.java

BUILD_EXTRA_DEPS += TestSerial.class
CLEAN_EXTRA = *.class NdpSerial.java
PFLAGS += -I$(TOSDIR)/lib/printf

CFLAGS += -I$(TOSDIR)/lib/T2Hack




include $(MAKERULES)

