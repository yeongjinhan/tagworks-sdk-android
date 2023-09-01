//
//  StandardEventParams
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * The enum Standard event params.
 */
public enum StandardEventParams {

    /**
     * Visitor id standard event params.
     */
    VISITOR_ID("ozvid"),
    /**
     * Tag event type standard event params.
     */
    TAG_EVENT_TYPE("obz_trg_type"),
    /**
     * Client date standard event params.
     */
    CLIENT_DATE("obz_client_date"),
    /**
     * Page title standard event params.
     */
    PAGE_TITLE("epgtl_nm"),
    /**
     * Dimension standard event params.
     */
    DIMENSION("cstm_d"),
    /**
     * Custom user path standard event params.
     */
    CUSTOM_USER_PATH("obz_user_path");

    private final String value;
    StandardEventParams(String value) {
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
