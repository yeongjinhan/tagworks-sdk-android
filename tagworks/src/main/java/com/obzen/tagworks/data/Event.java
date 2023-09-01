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

import com.obzen.tagworks.constants.StandardEvent;
import com.obzen.tagworks.constants.StandardEventParams;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Event.
 */
public class Event extends Base {

    private final static String DEVICE_TYPE = "app";
    private final HashMap<String, String> eventParams = new LinkedHashMap<>();
    private final Map<Integer, String> dimensions;
    private final StringBuilder baseStringBuilder = new StringBuilder();

    /**
     * Instantiates a new Event.
     *
     * @param dimensions the dimensions
     */
    public Event(Map<Integer, String> dimensions){
        this.dimensions = dimensions;
    }

    /**
     * Set event params.
     *
     * @param key   the key
     * @param value the value
     */
    public void setEventParams(@NonNull StandardEventParams key, @Nullable String value){
        if(!isEmpty(value)) eventParams.put(key.getValue(), value);
    }

    /**
     * Set event params.
     *
     * @param key   the key
     * @param value the value
     */
    public void setEventParams(@NonNull String key, @Nullable String value){
        if(!isEmpty(value)) eventParams.put(key, value);
    }

    /**
     * Set event.
     *
     * @param key the key
     */
    public void setEvent(@NonNull StandardEvent key){
        eventParams.put(StandardEventParams.TAG_EVENT_TYPE.getValue(), key.getValue());
        eventParams.put(StandardEventParams.DEVICE_TYPE.getValue(), DEVICE_TYPE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            eventParams.put(StandardEventParams.CLIENT_DATE.getValue(), zonedDateTime.format(DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)));
        }
    }

    /**
     * Set event.
     *
     * @param key the key
     */
    public void setEvent(@NonNull String key){
        eventParams.put(StandardEventParams.TAG_EVENT_TYPE.getValue(), key);
        eventParams.put(StandardEventParams.DEVICE_TYPE.getValue(), DEVICE_TYPE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            eventParams.put(StandardEventParams.CLIENT_DATE.getValue(), zonedDateTime.format(DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)));
        }
    }

    /**
     * Set visitor id.
     *
     * @param visitorId the visitor id
     */
    public void setVisitorId(@Nullable String visitorId){
        if(!isEmpty(visitorId)) eventParams.put(StandardEventParams.VISITOR_ID.getValue(), visitorId);
    }

    /**
     * Set custom user path.
     *
     * @param userPath the user path
     */
    public void setCustomUserPath(@Nullable String userPath){
        if(!isEmpty(userPath)) eventParams.put(StandardEventParams.CUSTOM_USER_PATH.getValue(), userPath);
    }

    /**
     * Set dimensions.
     *
     * @param dimensions the dimensions
     */
    public void setDimensions(Map<Integer, String> dimensions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dimensions.forEach((key, value) -> this.dimensions.merge(key, value, (v1, v2) -> v2));
        }else{
            this.dimensions.putAll(dimensions);
        }
    }

    /**
     * Get dimensions map.
     *
     * @return the map
     */
    public Map<Integer, String> getDimensions(){
        return dimensions;
    }

    /**
     * Set dimension.
     *
     * @param index the index
     * @param value the value
     */
    public void setDimension(int index, String value){
        dimensions.put(index, value);
    }

    /**
     * Get dimension string.
     *
     * @param index the index
     * @return the string
     */
    public String getDimension(int index){
        return dimensions.get(index);
    }

    @NonNull
    private String serializeEventParams(){
        StringBuilder eventStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : eventParams.entrySet()) {
            eventStringBuilder.append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return eventStringBuilder.toString();
    }

    @NonNull
    private String serializeDimensions(){
        StringBuilder dimensionStringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : dimensions.entrySet()) {
            dimensionStringBuilder.append(StandardEventParams.DIMENSION.getValue()).append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return dimensionStringBuilder.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return baseStringBuilder
                .append(serializeEventParams())
                .append(serializeDimensions())
                .deleteCharAt(baseStringBuilder.length() - 1).toString();
    }
}
