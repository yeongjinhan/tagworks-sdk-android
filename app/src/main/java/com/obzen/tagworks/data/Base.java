//
//  Base.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

abstract class Base {

    protected static final String DELIMITER_PARAMS = "&";
    protected static final String DELIMITER_MAPS = "=";
    private final HashMap<String, String> params = new HashMap<>();
    private final HashMap<Integer, String> dimensions = new HashMap<>();
    protected final StringBuilder baseStringBuilder = new StringBuilder();
    protected String customUserPath = "";

    public void setParams(String key, String value){
        params.put(key, value);
    }

    public void setDimension(int index, String value){
        dimensions.put(index, value);
    }

    public void setCustomUserPath(String userPath){
        customUserPath = userPath;
    }

    public String serializeParams(){
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStringBuilder.append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return paramsStringBuilder.toString();
    }

    public String serializeDimension(){
        StringBuilder dimensionStringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : dimensions.entrySet()) {
            dimensionStringBuilder.append(TagWorksParams.DIMENSION.getValue()).append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return dimensionStringBuilder.toString();
    }

    public String serializeCustomUserPath(){
        return TagWorksParams.CUSTOM_USER_PATH.getValue() + DELIMITER_MAPS + customUserPath;
    }

    @NonNull
    protected String toSerializedData(){
        return baseStringBuilder.toString();
    }
}
