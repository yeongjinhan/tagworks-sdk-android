//
//  VerificationUtil
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.util;

import androidx.annotation.NonNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * The type Verification util.
 */
public final class VerificationUtil {

    private static final String PATTERN_VALID_SITE_ID = "^[0-9]*$";
    private static final String PATTERN_VALID_CONTAINER_ID = "^[0-9a-zA-Z]*$";
    private static final String PATTERN_VALID_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    /**
     * Check valid site id.
     *
     * @param value the value
     */
    public static void checkValidSiteId(@NonNull String value){
        try{
            if(!value.contains(",")) throw new Exception();
            String[] val_arr = value.split(",");
            if(val_arr.length > 2) throw new Exception();
            if(!Pattern.matches(PATTERN_VALID_SITE_ID, val_arr[0])) throw new Exception();
            if(!Pattern.matches(PATTERN_VALID_CONTAINER_ID, val_arr[1])) throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException("invalid SiteId");
        }
    }

    /**
     * Check valid base url.
     *
     * @param value the value
     */
    public static void checkValidBaseUrl(@NonNull String value){
        try {
            new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("invalid BaseUrl");
        }
    }

    /**
     * Check valid visitor id boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean checkValidVisitorId(@NonNull String value){
        try{
            if(!Pattern.matches(PATTERN_VALID_UUID, value)) throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException("invalid VisitorId");
        }
        return true;
    }
}
