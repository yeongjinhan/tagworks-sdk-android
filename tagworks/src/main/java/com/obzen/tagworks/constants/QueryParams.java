//
//  QueryParams
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * TagWorks 쿼리 파라미터 상수 클래스입니다.
 *
 * @author hanyj
 * @version v1.0.0 2023.08.23
 */
public enum QueryParams {

    /**
     * Site id query params.
     */
    SITE_ID("idsite"),
    /**
     * Url path query params.
     */
    URL_PATH("url"),
    /**
     * Referrer query params.
     */
    REFERRER("urlref"),
    /**
     * Screen resolution query params.
     */
    SCREEN_RESOLUTION("res"),
    /**
     * User agent query params.
     */
    USER_AGENT("ua"),
    /**
     * Language query params.
     */
    LANGUAGE("lang"),
    /**
     * User id query params.
     */
    USER_ID("uid"),
    /**
     * Event query params.
     */
    EVENT("e_c");

    private final String value;
    QueryParams(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
