//	Copyright 2023 Obzen
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//	    http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

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
 * The type Base.
 */
public class Base {

    /**
     * The constant PATTERN_DATE_FORMAT.
     */
    protected static final String PATTERN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    /**
     * The constant DELIMITER_PARAMS.
     */
    protected static final String DELIMITER_PARAMS = "&";
    /**
     * The constant DELIMITER_MAPS.
     */
    protected static final String DELIMITER_MAPS = "=";
    /**
     * The Params.
     */
    protected final HashMap<String, String> params = new LinkedHashMap<>();

    /**
     * Set params.
     *
     * @param key   the key
     * @param value the value
     */
    public void setParams(String key, @Nullable String value){
        if(!isEmpty(value)) params.put(key, value);
    }

    /**
     * Set params.
     *
     * @param queryParam the query param
     * @param value      the value
     */
    public void setParams(QueryParams queryParam, @Nullable  String value){
        if(!isEmpty(value)) params.put(queryParam.getValue(), value);
    }

    /**
     * To serialize string string.
     *
     * @return the string
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

    @NonNull
    private static String urlEncodeUTF8(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8").replaceAll("\\+", "%20");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * To map map.
     *
     * @return the map
     */
    public Map<String, String> toMap(){
        return new LinkedHashMap<>(params);
    }
}
