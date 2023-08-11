//
//  TagWorksKey.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.constants;

/**
 * TagWorks SDK 내에서 사용되는 공용 상수입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public enum TagWorksKey {

    PRE_KEY_VISITOR_ID("tagworks.visitorid");

    private final String value;
    TagWorksKey(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
