//
//  PacketTransfer
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.transmitter;

import com.obzen.tagworks.data.Event;
import com.obzen.tagworks.data.Packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PacketTransfer implements Transfer{

    private static final int BUFFER_SIZE = 20;
    private final String targetUrl;

    public PacketTransfer(String targetUrl){
        this.targetUrl = targetUrl;
    }

    @Override
    public List<Packet> transferPackets(List<Event> eventBacks) {
        if (eventBacks.isEmpty()) {
            return Collections.emptyList();
        }
        int packetsSize = (int) Math.ceil(eventBacks.size() * 1.0 / BUFFER_SIZE);
        List<Packet> resultPackets = new ArrayList<>(packetsSize);
        for (int i = 0; i < eventBacks.size(); i += BUFFER_SIZE) {
            List<Event> batch = eventBacks.subList(i, Math.min(i + BUFFER_SIZE, eventBacks.size()));
            final Packet packet = serializeJsonObject(batch);
            if (packet != null) {
                resultPackets.add(packet);
            }
        }
        return resultPackets;
    }

    @Override
    public Packet serializeJsonObject(List<Event> eventBacks) {
        try {
            JSONObject params = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (Event eventBack : eventBacks) {
                jsonArray.put(eventBack.toSerializeString());
            }
            params.put("requests", jsonArray);
            return new Packet(targetUrl, params);
        } catch (JSONException e) {

        }
        return null;
    }
}
