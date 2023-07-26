package com.obzen.tagworks.data;


import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class EventData extends BaseData{
    private final HashMap<String, String> mEventMap = new HashMap<>();
    public EventData() { }
    public void setEvent(String key, String value){
        mEventMap.put(key, value);
    }
    public HashMap<String, String> getEvent(){
        return mEventMap;
    }
    private String getSerializedEvent(){
        StringBuilder eventStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : mEventMap.entrySet()) {
            eventStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(getDelimiterParams());
        }
        return eventStringBuilder.toString();
    }
    @NonNull
    @Override
    public String toSerializedData(){
        getStringBuilder()
                .append(getSerializedEvent())
                .append(getSerializedCommon())
                .append(getSerializedDimension());
        if(!isEmpty(getCustomUserPath())) {
            getStringBuilder().append(getSerializedCustomUserPath());
        }else{
            getStringBuilder().deleteCharAt(getStringBuilder().length() - 1);
        }
        return getStringBuilder().toString();
    }
}
