//
//  Packet
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import org.json.JSONObject;

public class Packet {

    private final String targetUrl;
    private final JSONObject body;

    public Packet(String targetUrl, JSONObject body){
        this.targetUrl = targetUrl;
        this.body = body;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public JSONObject getBody() {
        return body;
    }
}
