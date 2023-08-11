//
//  TagWorks.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import static com.obzen.tagworks.util.VerificationUtil.checkValidBaseUrl;
import static com.obzen.tagworks.util.VerificationUtil.checkValidSiteId;

import android.content.Context;
import android.util.ArrayMap;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * TagWorks SDK 메인 클래스입니다.
 * For example:
 * <pre>
 *     TagWorks.initializeSdk();
 *     TagWorks.getInstance();
 * </pre>
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public class TagWorks {

    @GuardedBy("LOCK")
    private static final Map<String, TagWorks> INSTANCE = new HashMap<>();
    private static final Object LOCK = new Object();
    private static final String INSTANCE_KEY = "TAGWORKS_SDK";
    private final Context context;
    private final TagWorksConfig config;
    private final String siteId;
    private final String baseUrl;

    /**
     * TagWorks 클래스 생성자
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    protected TagWorks(Context context, TagWorksConfig config){
        this.context = context;
        this.config = config;
        this.siteId = this.config.getSiteId();
        this.baseUrl = this.config.getBaseUrl();
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

    public static class EventBuilder extends BaseEventBuilder{

        private final String eventKey;
        private final String eventValue;
        private final String userPath;

        EventBuilder(@NonNull TagWorks tagWorks, @NonNull String eventKey, @Nullable String eventValue, @Nullable String userPath) {
            super(tagWorks);
            this.eventKey = eventKey;
            this.eventValue = eventValue;
            this.userPath = userPath;
        }

        @Override
        public void push() {

        }
    }
    public static EventBuilder event(@NonNull TagEvent eventKey, @Nullable String eventValue, @Nullable String userPath) {
        return new EventBuilder(getInstance(), eventKey.getValue(), eventValue, userPath);
    }
}
