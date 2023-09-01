//
//  StandardEvent
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * TagWorks Event 파라미터 상수입니다.
 *
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public enum StandardEvent {

    /**
     * Page view standard event.
     */
    PAGE_VIEW("PageView"),
    /**
     * Click standard event.
     */
    CLICK("Click"),
    /**
     * Scroll standard event.
     */
    SCROLL("Scroll"),
    /**
     * Download standard event.
     */
    DOWNLOAD("Download"),
    /**
     * Out link standard event.
     */
    OUT_LINK("OutLink"),
    /**
     * Search standard event.
     */
    SEARCH("Search");

    private final String value;
    StandardEvent(String value) {
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
