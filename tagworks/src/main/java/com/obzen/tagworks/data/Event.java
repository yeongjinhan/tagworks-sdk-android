//
//  Event
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.obzen.tagworks.constants.TagWorksParams;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 이벤트 데이터 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.21
 */
public class Event extends Base {

    private final HashMap<String, String> eventParams = new HashMap<>();
    private final HashMap<Integer, String> dimensions = new HashMap<>();
    private final StringBuilder baseStringBuilder = new StringBuilder();

    /**
     * 이벤트를 지정합니다.
     * @param key 이벤트 key
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setEvent(String key){
        eventParams.put(TagWorksParams.TAG_EVENT_TYPE.getValue(), key);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            eventParams.put(TagWorksParams.CLIENT_DATE.getValue(), zonedDateTime.format(DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)));
        }
    }

    /**
     * 방문자 식별자를 지정합니다.
     * @param visitorId 방문자 식별자
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setVisitorId(@Nullable String visitorId){
        if(!isEmpty(visitorId)) eventParams.put(TagWorksParams.VISITOR_ID.getValue(), visitorId);
    }

    /**
     * 사용자 정의 경로를 지정합니다.
     * @param userPath 사용자 정의 경로
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setCustomUserPath(@Nullable String userPath){
        if(!isEmpty(userPath)) eventParams.put(TagWorksParams.CUSTOM_USER_PATH.getValue(), userPath);
    }

    /**
     * 디멘전을 지정합니다.
     * @param dimensions 사용자 정의 디멘전
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setDimensions(Map<Integer, String> dimensions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dimensions.forEach((key, value) -> this.dimensions.merge(key, value, (v1, v2) -> v2));
        }else{
            this.dimensions.putAll(dimensions);
        }
    }

    /**
     * 디멘전을 반환합니다.
     * @return 사용자 정의 디멘전
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public Map<Integer, String> getDimensions(){
        return dimensions;
    }

    /**
     * 사용자 정의 디멘전을 지정합니다.
     * @param index 디멘전 index
     * @param value 디멘전 value
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public void setDimension(int index, String value){
        dimensions.put(index, value);
    }

    /**
     * 사용자 정의 디멘전 value를 반환합니다.
     * @param index 디멘전 index
     * @return 디멘전 value
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public String getDimension(int index){
        return dimensions.get(index);
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
     * 디멘전을 문자열로 반환합니다.
     * @return 디멘전 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    private String serializeDimensions(){
        StringBuilder dimensionStringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : dimensions.entrySet()) {
            dimensionStringBuilder.append(TagWorksParams.DIMENSION.getValue()).append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return dimensionStringBuilder.toString();
    }

    /**
     * 데이터를 문자열로 반환합니다.
     * @return 데이터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    @NonNull
    @Override
    public String toString() {
        return baseStringBuilder
                .append(serializeEventParams())
                .append(serializeDimensions())
                .deleteCharAt(baseStringBuilder.length() - 1).toString();
    }
}
