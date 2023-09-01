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
 * The type Base builder.
 */
public abstract class BaseBuilder {
    /**
     * The Tag works.
     */
    protected final TagWorks tagWorks;
    /**
     * The Dimensions.
     */
    protected final HashMap<Integer, String> dimensions = new HashMap<>();

    /**
     * Instantiates a new Base builder.
     *
     * @param tagWorks the tag works
     */
    BaseBuilder(@NonNull TagWorks tagWorks) {
        this.tagWorks = tagWorks;
        this.dimensions.putAll(tagWorks.getDimensions());
    }

    /**
     * Dimension base builder.
     *
     * @param index the index
     * @param value the value
     * @return the base builder
     */
    abstract BaseBuilder dimension(int index, @NonNull String value);

    /**
     * Push.
     */
    abstract void push();

}
