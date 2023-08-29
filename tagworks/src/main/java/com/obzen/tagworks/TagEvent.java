//
//  TagEvent
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

/**
 * TagWorks Event 파라미터 상수입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public enum TagEvent {

    PAGE_VIEW("PageView"),
    CLICK("Click"),
    SEARCH("Search");

    private final String value;
    TagEvent(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
