//
//  Transmitter
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.transmitter;

import com.obzen.tagworks.data.Event;

public interface Transmitter {

    void setConnectionTimeOut(long connectionTimeOut);
    long getConnectionTimeOut();
    void setTransmitInterval(long transmitInterval);
    long getTransmitInterval();
    void transmit(Event eventBack);
    void flush();
    void clear();
    boolean launch();
}
