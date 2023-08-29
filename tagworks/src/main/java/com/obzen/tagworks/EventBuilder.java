//
//  BaseEventBuilder
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import android.os.Build;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseEventBuilder
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public abstract class EventBuilder {
    private final TagWorks tagWorks;

    EventBuilder(@NonNull TagWorks tagWorks) {
        this.tagWorks = tagWorks;
    }

    abstract EventBuilder setCustomUserPath(@NonNull String userPath);
    abstract EventBuilder setDimension(int index, @NonNull String value);
    abstract void push();
}
