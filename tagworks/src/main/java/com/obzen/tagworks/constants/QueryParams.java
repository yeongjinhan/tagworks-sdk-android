//
//  QueryParams
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * The enum Query params.
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
