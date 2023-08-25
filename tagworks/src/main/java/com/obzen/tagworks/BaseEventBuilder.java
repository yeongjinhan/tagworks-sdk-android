//
//  BaseEventBuilder
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseEventBuilder
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public abstract class BaseEventBuilder {
    private final TagWorks tagWorks;
    private final Map<Integer, String> dimensions = new HashMap<>();

    BaseEventBuilder(@NonNull TagWorks tagWorks) {
        this.tagWorks = tagWorks;
    }

    public BaseEventBuilder dimension(@NonNull int index, @NonNull String value){
        dimensions.put(index, value);
        return this;
    }

    public abstract void push();
}
