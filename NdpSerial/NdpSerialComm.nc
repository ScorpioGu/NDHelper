#include <TinyError.h>
#include "NdpSerial.h"

interface NdpSerialComm {
	command error_t send(ndp_serial_msg_t* msg, uint8_t len);

	event void sendDone(ndp_serial_msg_t* msg, error_t error);
	
    command uint8_t maxPayloadLength();

    event ndp_serial_msg_t* receive(ndp_serial_msg_t* msg, uint8_t len);

}
