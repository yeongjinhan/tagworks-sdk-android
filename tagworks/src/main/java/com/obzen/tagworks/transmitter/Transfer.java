//
//  Transfer
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.transmitter;

import com.obzen.tagworks.data.Event;
import com.obzen.tagworks.data.Packet;

import java.util.List;

public interface Transfer {

    List<Packet> transferPackets(List<Event> eventBacks);
    Packet serializeJsonObject(List<Event> eventBacks);

}
