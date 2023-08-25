//
//  Sender
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.transmitter;

import com.obzen.tagworks.data.Packet;

public interface Sender {

    boolean send(Packet packet);
}
