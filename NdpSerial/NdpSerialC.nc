// $Id: TestSerialC.nc,v 1.7 2010-06-29 22:07:25 scipio Exp $

/*									tab:4
 * Copyright (c) 2000-2005 The Regents of the University  of California.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the
 *   distribution.
 * - Neither the name of the University of California nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (c) 2002-2003 Intel Corporation
 * All rights reserved.
 *
 * This file is distributed under the terms in the attached INTEL-LICENSE     
 * file. If you do not find these files, copies can be found by writing to
 * Intel Research Berkeley, 2150 Shattuck Avenue, Suite 1300, Berkeley, CA, 
 * 94704.  Attention:  Intel License Inquiry.
 */

/**
 * Application to test that the TinyOS java toolchain can communicate
 * with motes over the serial port. 
 *
 *  @author Gilman Tolle
 *  @author Philip Levis
 *  
 *  @date   Aug 12 2005
 *
 **/

#include "stdio.h"
#include "string.h"
#include "NdpSerial.h"
#include "printf.h"

module NdpSerialC {
    uses {
        interface SplitControl as Control;
        interface Boot;
        interface Receive;
        interface AMSend;
        interface Packet;
		interface LocalTime<T32khz> as LocalTime; 
    }
    provides interface NdpSerialComm;
}

implementation {
	#define QUEUE_SIZE 5
    message_t packet;
    bool locked = FALSE;
	ndp_serial_msg_t Queue[QUEUE_SIZE];
	uint8_t front, rear, pendingMsg;

	//void serialSend();
	
	task void serialSend() {
	    error_t err;
        ndp_serial_msg_t* payload;
		
		payload = (ndp_serial_msg_t*)call Packet.getPayload(&packet, sizeof(ndp_serial_msg_t));
		front = (front+1) % QUEUE_SIZE;
		memcpy(payload,&Queue[front],sizeof(ndp_serial_msg_t));
		err = call AMSend.send(AM_BROADCAST_ADDR, &packet, sizeof(ndp_serial_msg_t)); 
		//printf("Dequeue: front = %u rear = %u pendingMsg = %u err =%u\r\n", front, rear, pendingMsg, err);
		//printfflush();
		
	}
	
    event void Boot.booted() {
        call Control.start();
		front = rear = 0;
		pendingMsg = 0;
    }

	event message_t* Receive.receive(message_t* bufPtr, void* payload, uint8_t len) {
		ndp_serial_msg_t* pmsg;  
                
//        if (len != sizeof(ndp_serial_msg_t)) {
//			return bufPtr;
//		} else {
//            pmsg = (ndp_serial_msg_t*)payload;
//       }

	    //printf("Received %u\r\n", pmsg->type);
		//printfflush();
		pmsg = (ndp_serial_msg_t*)payload;
        signal NdpSerialComm.receive(pmsg, len);

	    return bufPtr;
    }

	command error_t NdpSerialComm.send(ndp_serial_msg_t* msg, uint8_t len) {
		//error_t err;
		//ndp_serial_msg_t* payload;
		
		//printf("MaxPayload = %u\r\n", call Packet.maxPayloadLength());
		//call Packet
		//printf("locked = %u SIZE = %u\r\n", locked, sizeof(ndp_serial_msg_t));
		//payload = (ndp_serial_msg_t*)call Packet.getPayload(&packet, sizeof(ndp_serial_msg_t));
		
		if (call Packet.maxPayloadLength() < sizeof(ndp_serial_msg_t)) {
			return ESIZE;
        }

		if((rear+1)%QUEUE_SIZE != front) {
			rear = (rear+1)%QUEUE_SIZE;
			memcpy(&(Queue[rear]),msg,sizeof(ndp_serial_msg_t));
			pendingMsg++;
			//printf("Enqueue: front = %u rear = %u pendingMsg = %u\r\n", front, rear, pendingMsg);
			//printfflush();
		}else 
			return FAIL;

		if(pendingMsg-1 == 0)
			post serialSend();
		return SUCCESS;
	}
	
	
	command uint8_t NdpSerialComm.maxPayloadLength() {
		return call Packet.maxPayloadLength();
	}

	event void AMSend.sendDone(message_t* bufPtr, error_t error) {
        ndp_serial_msg_t* payload;
		
		payload = (ndp_serial_msg_t*)call Packet.getPayload(bufPtr, sizeof(ndp_serial_msg_t));
        signal NdpSerialComm.sendDone(payload, error); //0223
        
		pendingMsg--;
		if(pendingMsg)
			post serialSend();

	    //printf("sendDone: front = %u rear = %u pendingMsg = %u\r\n", front, rear, pendingMsg);
		//printfflush();
//		printf("sendDone with time %lu\r\n", call LocalTime.get()/32);
//		printfflush();
		
//		pmsg = (ndp_serial_msg_t*)call Packet.getPayload(bufPtr, sizeof(ndp_serial_msg_t));
//		if (&packet == bufPtr) {
//			locked = FALSE;
//			signal NdpSerialComm.sendDone(pmsg, error);
//		}

    }

	event void Control.startDone(error_t err) {
		
    }

	event void Control.stopDone(error_t err) {

	}
}



