//
//  Base
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;
import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.obzen.tagworks.constants.QueryParams;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 수집 데이터의 추상 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.21
 */
public class Base {

    protected static final String PATTERN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    protected static final String DELIMITER_PARAMS = "&";
    protected static final String DELIMITER_MAPS = "=";
    protected final HashMap<String, String> params = new LinkedHashMap<>();

    /**
     * 파라미터를 지정합니다.
     * @param key 파라미터 key
     * @param value 파라미터 value
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setParams(String key, @Nullable String value){
        if(!isEmpty(value)) params.put(key, value);
    }

    /**
     * 파라미터를 지정합니다.
     * @param queryParam 파라미터 key
     * @param value 파라미터 value
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    public void setParams(QueryParams queryParam, @Nullable  String value){
        if(!isEmpty(value)) params.put(queryParam.getValue(), value);
    }

    /**
     * 데이터를 문자열로 반환합니다.
     * @return 데이터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.23
     */
    @NonNull
    public String toSerializeString() {
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStringBuilder.append(urlEncodeUTF8(entry.getKey())).append(DELIMITER_MAPS).append(urlEncodeUTF8(entry.getValue())).append(DELIMITER_PARAMS);
        }
        return paramsStringBuilder
                .deleteCharAt(paramsStringBuilder.length() - 1)
                .toString();
    }

    private static String urlEncodeUTF8(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8").replaceAll("\\+", "%20");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 데이터를 Map으로 반환합니다.
     * @return 데이터 문자열
     * @author hanyj
     * @since  v1.0.0 2023.08.24
     */
    public Map<String, String> toMap(){
        return new LinkedHashMap<>(params);
    }
}
