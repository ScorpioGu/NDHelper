
#ifndef NDP_SERIAL_H
#define NDP_SERIAL_H

enum {
		NDP_LOCALTIME = 0,
		NDP_STOP = 1,
		NDP_START = 2,
		NDP_DEBUG = 3,
		NDP_DISCOVERY = 4,
		NDP_ERROR = 5,
		NDP_PARAMETERS_1 = 6,
		NDP_PARAMETERS_2 = 7,
		NDP_RESERVE_1 = 8,
		NDP_RESERVE_2 = 9,
		NDP_INIT= 10,
	};

typedef struct ndp_serial_msg {
	uint8_t type;
	char msg[100];
} ndp_serial_msg_t;

enum {
  AM_NDP_SERIAL_MSG = 0x77,
};

#endif
