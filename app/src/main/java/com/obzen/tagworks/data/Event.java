//
//  Event.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;
import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

/**
 * 이벤트 데이터 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.21
 */
public class Event extends Base{

    private final HashMap<String, String> eventParams = new HashMap<>();

    /**
     * 이벤트를 지정합니다.
     * @param key 이벤트 key
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setEvent(String key){
        eventParams.put(TagWorksParams.TAG_EVENT_TYPE.getValue(), key);
    }

    /**
     * 이벤트를 문자열로 반환합니다.
     * @return 이벤트 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    private String serializeEventParams(){
        StringBuilder eventStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : eventParams.entrySet()) {
            eventStringBuilder.append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return eventStringBuilder.toString();
    }

    /**
     * 최종 데이터를 문자열로 반환합니다.
     * @return 데이터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
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
