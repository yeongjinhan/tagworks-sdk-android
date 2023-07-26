package com.obzen.tagworks.constants;

public enum TagTypeParams {

    PAGE_VIEW("PageView"),
    CLICK("Click"),
    SEARCH("Search");

    private final String value;
    TagTypeParams(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
