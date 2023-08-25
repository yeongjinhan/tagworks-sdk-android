//
//  QueryParams
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * TagWorks 쿼리 파라미터 상수 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.23
 */
public enum QueryParams {

    SITE_ID("idsite"),
    URL_PATH("url"),
    REFERRER("urlref"),
    SCREEN_RESOLUTION("res"),
    USER_AGENT("ua"),
    LANGUAGE("lang"),
    USER_ID("uid"),
    EVENT("e_c");

    private final String value;
    QueryParams(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
