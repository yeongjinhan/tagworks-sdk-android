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
 * TagWorks SDK 내에서 사용되는 Util 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.14
 */
public class CommonUtil {

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
