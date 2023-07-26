package com.obzen.tagworks.util;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class CommonUtil {
    @NonNull
    public static boolean isEmpty(Object obj){
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
