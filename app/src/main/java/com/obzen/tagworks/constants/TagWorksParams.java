package com.obzen.tagworks.constants;

public enum TagWorksParams {

    VISITOR_ID("ozvid"),
    TAG_EVENT_TYPE("obz_trg_type"),
    CLIENT_DATE("obz_client_date"),
    CUSTOM_USER_PATH("obz_user_path"),
    SEARCH_KEYWORD("obz_search_keyword"),
    PAGE_TITLE("epgtl_nm"),
    PAGE_PATH("page_path"),
    EVENT_PARAM("cstm_d");

    private final String value;
    TagWorksParams(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
