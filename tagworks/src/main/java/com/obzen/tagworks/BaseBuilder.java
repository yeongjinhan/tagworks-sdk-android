//
//  BaseBuilder
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import androidx.annotation.NonNull;

import java.util.HashMap;

/**
 * BaseBuilder
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public abstract class BaseBuilder {
    protected final TagWorks tagWorks;
    protected final HashMap<Integer, String> dimensions = new HashMap<>();
    BaseBuilder(@NonNull TagWorks tagWorks) {
        this.tagWorks = tagWorks;
        this.dimensions.putAll(tagWorks.getDimensions());
    }
    abstract BaseBuilder dimension(int index, @NonNull String value);
    abstract void push();

}
