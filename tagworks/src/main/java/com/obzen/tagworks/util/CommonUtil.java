//
//  CommonUtil
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * The type Common util.
 */
public class CommonUtil {

    /**
     * Is empty boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isEmpty(@Nullable Object obj){
        if (obj == null)
            return true;
        if (obj instanceof String)
            return ("".equals(((String) obj).trim()));
        if (obj instanceof Map)
            return ((Map<?,?>) obj).isEmpty();
        if (obj instanceof List)
            return ((List<?>) obj).isEmpty();
        if (obj instanceof Object[])
            return (((Object[]) obj).length == 0);
        return false;
    }
}
