//
//  TagWorksParams
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

public enum TagWorksParams {

    VISITOR_ID("ozvid"),
    TAG_EVENT_TYPE("obz_trg_type"),
    CLIENT_DATE("obz_client_date"),
    PAGE_TITLE("epgtl_nm"),
    DIMENSION("cstm_d"),
    CUSTOM_USER_PATH("obz_user_path");

    private final String value;
    TagWorksParams(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
