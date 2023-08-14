//
//  TagWorks.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import static com.obzen.tagworks.constants.TagWorksKey.*;
import static com.obzen.tagworks.util.CommonUtil.*;
import static com.obzen.tagworks.util.VerificationUtil.*;
import android.content.Context;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.obzen.tagworks.util.CommonUtil;
import com.obzen.tagworks.util.PreferencesUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TagWorks SDK 메인 클래스입니다.
 * For example:
 * <pre>
 *     TagWorks.initializeSdk();
 *     TagWorks.getInstance();
 *     TagWorks.event();
 * </pre>
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public class TagWorks {

    /**
     * SDK 설정영역
     */
    @GuardedBy("LOCK")
    private static final Map<String, TagWorks> INSTANCE = new HashMap<>();
    private static final Object LOCK = new Object();
    private static final String INSTANCE_KEY = "TAGWORKS_SDK";
    private final Context context;
    private final TagWorksConfig config;
    private final String siteId;
    private final String baseUrl;

    /**
     * TagWorks 수집 컨테이너 siteId를 반환합니다.
     * @return TagWorks 수집 컨테이너 siteId
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public String getSiteId(){
        return siteId;
    }

    /**
     * TagWorks 수집서버 주소 url을 반환합니다.
     * @return TagWorks 수집서버 주소 url
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public String getBaseUrl(){
        return baseUrl;
    }

    /**
     * 사용자 식별자를 지정합니다.
     * @param userId 방문자 식별자
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public void setUserId(String userId){
        PreferencesUtil.setString(context, PRE_KEY_USER_ID, userId);
    }

    /**
     * 사용자 식별자를 반환합니다.
     * @return 사용자 식별자
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public String getUserId(){
        return PreferencesUtil.getString(context, PRE_KEY_USER_ID);
    }

    /**
     * 방문자 식별자를 지정합니다.
     * @param visitorId 방문자 식별자
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public void setVisitorId(String visitorId){
        if(checkValidVisitorId(visitorId)){
            PreferencesUtil.setString(context, PRE_KEY_VISITOR_ID, visitorId);
        }
    }

    /**
     * 방문자 식별자를 반환합니다.
     * @return 방문자 식별자
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public String getVisitorId(){
        String visitorId = PreferencesUtil.getString(context, PRE_KEY_VISITOR_ID);
        if(isEmpty(visitorId)){
            visitorId = UUID.randomUUID().toString();
            setVisitorId(visitorId);
        }
        return visitorId;
    }

    /**
     * 이벤트 수집 여부를 지정합니다.
     * @param optOut 이벤트 수집 여부
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public void setOptOut(boolean optOut){
        PreferencesUtil.setBoolean(context, PRE_KEY_OPT_OUT, optOut);
    }

    /**
     * 이벤트 수집 여부를 반환합니다.
     * @return 이벤트 수집 여부
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public boolean getOptOut(){
        return PreferencesUtil.getBoolean(context, PRE_KEY_OPT_OUT);
    }

    /**
     * TagWorks 클래스 생성자
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    private TagWorks(Context context, TagWorksConfig config){
        this.context = context;
        this.config = config;
        this.siteId = config.getSiteId();
        this.baseUrl = config.getBaseUrl();
        this.contentBaseUrl = String.format("https://%s/", context.getPackageName());
        this.dimensions = new HashMap<>();
    }

    /**
     * TagWorks Instance 를 반환합니다.
     * @return TagWorks 인스턴스
     * @throws IllegalStateException TagWorks 인스턴스가 초기화되지 않음
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    @NonNull
    public static TagWorks getInstance(){
        TagWorks instance = INSTANCE.get(INSTANCE_KEY);
        if(instance == null){
            throw new IllegalStateException("not initialized TagWorks");
        }
        return instance;
    }

    /**
     * TagWorks SDK를 초기화합니다.
     * @param context Application Context
     * @param siteId TagWorks 수집 컨테이너 siteId
     * @param baseUrl TagWorks 수집서버 주소 url
     * @return TagWorks 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.10
     */
    @NonNull
    public static TagWorks initializeSdk(@NonNull Context context, @NonNull String siteId, @NonNull String baseUrl){
        synchronized(LOCK){
            if(INSTANCE.containsKey(INSTANCE_KEY)){
                return getInstance();
            }
            checkValidSiteId(siteId);
            checkValidBaseUrl(baseUrl);
            TagWorksConfig config = new TagWorksConfig
                    .Builder()
                    .setSiteId(siteId)
                    .setBaseUrl(baseUrl)
                    .build();
            return initializeSdk(context, config);
        }
    }

    /**
     * TagWorks SDK를 초기화합니다.
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     * @return TagWorks 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.10
     */
    @NonNull
    public static TagWorks initializeSdk(@NonNull Context context, @NonNull TagWorksConfig config){
        synchronized(LOCK){
            if(INSTANCE.containsKey(INSTANCE_KEY)){
                return getInstance();
            }
            checkValidSiteId(config.getSiteId());
            checkValidBaseUrl(config.getBaseUrl());
            return initialize(context, config);
        }
    }

    /**
     * TagWorks SDK를 초기화합니다.
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     * @return TagWorks 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    @NonNull
    private static TagWorks initialize(@NonNull Context context, @NonNull TagWorksConfig config){
        final TagWorks instance;
        synchronized(LOCK){
            instance = new TagWorks(context, config);
            INSTANCE.put(INSTANCE_KEY, instance);
            return instance;
        }
    }

    /**
     * 수집 영역
     */

    private final String contentBaseUrl;
    private String contentUrl;
    private final HashMap<Integer, String> dimensions;

    /**
     * 현재 contentUrl을 지정합니다.
     * @param path contentUrl path
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    private void setContentUrl(String path){
        contentUrl = contentBaseUrl + path;
    }

    /**
     * 현재 contentUrl을 반환합니다.
     * @return 현재 contentUrl
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    public String getContentUrl(){
        return contentUrl;
    }

    /**
     * TagWorks 이벤트 수집 빌더 클래스입니다.
     * For example:
     * <pre>
     *     TagWorks.event(TagEvent.CLICK, "testValue", "/test/test2");
     *     TagWorks.event(TagEvent.CLICK, "testValue");
     *     TagWorks.event("CustomEvent", "testValue", "/test/test2");
     * </pre>
     * @author hanyj
     * @version v1.0.0 2023.08.10
     */
    public static class EventBuilder extends BaseEventBuilder{

        private final String eventKey;
        private final String eventValue;
        private final String userPath;

        /**
         * EventBuilder의 기본 생성자입니다.
         * @param eventKey 이벤트 key
         * @param eventValue 이벤트 value
         * @param userPath 사용자 정의 이벤트 path
         * @author hanyj
         * @since  v1.0.0 2023.08.14
         */
        EventBuilder(@NonNull TagWorks tagWorks, @NonNull String eventKey, @Nullable String eventValue, @Nullable String userPath) {
            super(tagWorks);
            this.eventKey = eventKey;
            this.eventValue = eventValue;
            this.userPath = userPath;
        }

        /**
         * EventBuilder의 기본 생성자입니다.
         * @param eventKey 이벤트 key
         * @param eventValue 이벤트 value
         * @author hanyj
         * @since  v1.0.0 2023.08.14
         */
        EventBuilder(@NonNull TagWorks tagWorks, @NonNull String eventKey, @Nullable String eventValue) {
            super(tagWorks);
            this.eventKey = eventKey;
            this.eventValue = eventValue;
            this.userPath = null;
        }

        /**
         * 저장된 이벤트를 발송합니다.
         * @author hanyj
         * @since  v1.0.0 2023.08.14
         */
        @Override
        public void push() {

        }
    }

    /**
     * 사용자 이벤트를 수집합니다.
     * @param eventKey 이벤트 key
     * @param eventValue 이벤트 value
     * @return EventBuilder 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public static EventBuilder event(@NonNull TagEvent eventKey, @NonNull String eventValue) {
        return new EventBuilder(getInstance(), eventKey.getValue(), eventValue);
    }

    /**
     * 사용자 이벤트를 수집합니다.
     * @param eventKey 이벤트 key
     * @param eventValue 이벤트 value
     * @return EventBuilder 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public static EventBuilder event(@NonNull String eventKey, @NonNull String eventValue) {
        return new EventBuilder(getInstance(), eventKey, eventValue);
    }

    /**
     * 사용자 이벤트를 수집합니다.
     * @param eventKey 이벤트 key
     * @param eventValue 이벤트 value
     * @param userPath 사용자 정의 이벤트 path
     * @return EventBuilder 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public static EventBuilder event(@NonNull TagEvent eventKey, @NonNull String eventValue, @Nullable String userPath) {
        return new EventBuilder(getInstance(), eventKey.getValue(), eventValue, userPath);
    }

    /**
     * 사용자 이벤트를 수집합니다.
     * @param eventKey 이벤트 key
     * @param eventValue 이벤트 value
     * @param userPath 사용자 정의 이벤트 path
     * @return EventBuilder 인스턴스
     * @author hanyj
     * @since  v1.0.0 2023.08.14
     */
    public static EventBuilder event(@NonNull String eventKey, @NonNull String eventValue, @Nullable String userPath) {
        return new EventBuilder(getInstance(), eventKey, eventValue, userPath);
    }
}
