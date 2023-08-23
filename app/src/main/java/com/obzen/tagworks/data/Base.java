//
//  Base.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

import android.os.Build;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

/**
 * 수집 데이터의 추상 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.21
 */
abstract class Base {

    protected static final String DELIMITER_PARAMS = "&";
    protected static final String DELIMITER_MAPS = "=";
    private final HashMap<String, String> params = new HashMap<>();
    private final HashMap<Integer, String> dimensions = new HashMap<>();
    protected final StringBuilder baseStringBuilder = new StringBuilder();
    protected String customUserPath = "";

    /**
     * 파라미터를 지정합니다.
     * @param key 파라미터 key
     * @param value 파라미터 value
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setParams(String key, String value){
        params.put(key, value);
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
     * 사용자 정의 경로를 지정합니다.
     * @param userPath 사용자 정의 경로
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setCustomUserPath(String userPath){
        customUserPath = userPath;
    }

    /**
     * 파라미터를 문자열로 반환합니다.
     * @return 파라미터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public String serializeParams(){
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStringBuilder.append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return paramsStringBuilder.toString();
    }

    /**
     * 디멘전을 문자열로 반환합니다.
     * @return 디멘전 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public String serializeDimension(){
        StringBuilder dimensionStringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : dimensions.entrySet()) {
            dimensionStringBuilder.append(TagWorksParams.DIMENSION.getValue()).append(entry.getKey()).append(DELIMITER_MAPS).append(entry.getValue()).append(DELIMITER_PARAMS);
        }
        return dimensionStringBuilder.toString();
    }

    /**
     * 사용자 정의 경로를 문자열로 반환합니다.
     * @return 사용자 정의 경로 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public String serializeCustomUserPath(){
        return TagWorksParams.CUSTOM_USER_PATH.getValue() + DELIMITER_MAPS + customUserPath;
    }

    /**
     * 최종 데이터를 문자열로 반환합니다.
     * @return 데이터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    @NonNull
    protected String toSerializedData(){
        return baseStringBuilder.toString();
    }
}
