package com.obzen.tagworks.data;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagTypeParams;
import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

abstract class BaseData {

    private static final String DELIMITER_PARAMS = "&";
    private final HashMap<String, String> mCommonMap = new HashMap<>();
    private final HashMap<Integer, String> mDimensionMap = new HashMap<>();
    private final StringBuilder mStringBuilder = new StringBuilder();
    private String mCustomUserPath = "";

    public void setCommon(TagWorksParams params, String value){
        mCommonMap.put(params.getValue(), value);
    }
    public HashMap<String, String> getCommon(){
        return mCommonMap;
    }
    @NonNull
    public String getSerializedCommon(){
        StringBuilder commonStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : mCommonMap.entrySet()) {
            commonStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return commonStringBuilder.toString();
    }
    public void setDimension(int key, String value){
        mDimensionMap.put(key, value);
    }
    public HashMap<Integer, String> getDimension(){
        return mDimensionMap;
    }
    public String getSerializedDimension(){
        StringBuilder commonStringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : mDimensionMap.entrySet()) {
            commonStringBuilder.append(TagWorksParams.EVENT_PARAM.getValue()).append(entry.getKey()).append("=").append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return commonStringBuilder.toString();
    }
    public String getCustomUserPath(){
        return mCustomUserPath;
    }
    public void setCustomUserPath(String userPath){
        mCustomUserPath = userPath;
    }
    public String getSerializedCustomUserPath(){
        return TagWorksParams.CUSTOM_USER_PATH.getValue() + "=" + mCustomUserPath;
    }
    @NonNull
    protected StringBuilder getStringBuilder(){
        return mStringBuilder;
    }
    protected String getDelimiterParams(){
        return DELIMITER_PARAMS;
    }
    @NonNull
    protected String toSerializedData(){
        return mStringBuilder.toString();
    }
}
