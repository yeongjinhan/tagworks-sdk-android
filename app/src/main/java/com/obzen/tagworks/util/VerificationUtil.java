package com.obzen.tagworks.util;

import androidx.annotation.NonNull;

import org.matomo.sdk.Tracker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

public final class VerificationUtil {

    private static final String PATTERN_VALID_SITE_ID = "^[0-9]*$";
    private static final String PATTERN_VALID_CONTAINER_ID = "^[0-9a-zA-Z]*$";
    private static final String PATTERN_VALID_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    @NonNull
    public static String checkValidSiteId(@NonNull String value){
        try{
            if(!value.contains(",")) throw new Exception();
            String[] val_arr = value.split(",");
            if(val_arr.length > 2) throw new Exception();
            if(!Pattern.matches(PATTERN_VALID_SITE_ID, val_arr[0])) throw new Exception();
            if(!Pattern.matches(PATTERN_VALID_CONTAINER_ID, val_arr[1])) throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException("invalid SiteId");
        }
        return value;
    }
    @NonNull
    public static String checkValidBaseUrl(@NonNull String value){
        try {
            new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("invalid BaseUrl");
        }
        return value;
    }
    @NonNull
    public static String checkValidVisitorId(@NonNull String value){
        try{
            if(!Pattern.matches(PATTERN_VALID_UUID, value)) throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException("invalid VisitorId");
        }
        return value;
    }
    @NonNull
    public static Tracker checkInitializedTracker(@NonNull Tracker tracker){
        try{
            if(Objects.isNull(tracker)) throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException("not initialized tracker");
        }
        return tracker;
    }
}
