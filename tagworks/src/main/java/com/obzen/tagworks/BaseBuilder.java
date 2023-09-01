//	Copyright 2023 Obzen
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//	    http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

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
