//
//  Event.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Event extends Base{

    private final HashMap<String, String> eventParams = new HashMap<>();

    public void setEvent(String key, String value){
        eventParams.put(key, value);
    }

    private String serializeEventParams(){
        StringBuilder eventStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : eventParams.entrySet()) {
            eventStringBuilder.append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return eventStringBuilder.toString();
    }

    @NonNull
    @Override
    public String toSerializedData(){
        baseStringBuilder
                .append(serializeEventParams())
                .append(serializeParams())
                .append(serializeDimension());
        if(!isEmpty(customUserPath)) {
            baseStringBuilder.append(serializeCustomUserPath());
        }else{
            baseStringBuilder.deleteCharAt(baseStringBuilder.length() - 1);
        }
        return baseStringBuilder.toString();
    }
}
