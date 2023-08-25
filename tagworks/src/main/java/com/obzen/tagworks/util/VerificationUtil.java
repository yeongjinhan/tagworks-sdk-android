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
 * TagWorks 유효성 검증 유틸 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public final class VerificationUtil {

    private static final String PATTERN_VALID_SITE_ID = "^[0-9]*$";
    private static final String PATTERN_VALID_CONTAINER_ID = "^[0-9a-zA-Z]*$";
    private static final String PATTERN_VALID_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    /**
     * siteId 값의 유효성을 검증합니다.
     *
     * @param value TagWorks 수집 컨테이너 siteId 값
     * @throws RuntimeException 유효하지 않은 SiteId
     * @author hanyj
     * @since v1.0.0 2023.08.10
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
     * 수집서버 주소 값의 유효성을 검증합니다.
     *
     * @param value TagWorks 수집서버 baseUrl 값
     * @throws RuntimeException 유효하지 않은 baseUrl
     * @author hanyj
     * @since v1.0.0 2023.08.10
     */
    public static void checkValidBaseUrl(@NonNull String value){
        try {
            new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException("invalid BaseUrl");
        }
    }

    /**
     * VisitorId의 유효성을 검증합니다.
     * @param value TagWorks VisitorId 값
     * @return 유효성 여부
     * @throws RuntimeException 유효하지 않은 VisitorId
     * @author hanyj
     * @since  v1.0.0 2023.08.10
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
