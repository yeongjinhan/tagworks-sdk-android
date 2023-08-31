//
//  TagWorks
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import static com.obzen.tagworks.util.CommonUtil.*;
import static com.obzen.tagworks.util.VerificationUtil.*;
import android.content.Context;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.obzen.tagworks.constants.QueryParams;
import com.obzen.tagworks.constants.StandardEvent;
import com.obzen.tagworks.constants.StandardEventParams;
import com.obzen.tagworks.data.Event;
import com.obzen.tagworks.dispatcher.EventDispatcher;
import com.obzen.tagworks.dispatcher.PacketSender;
import com.obzen.tagworks.util.PreferencesUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TagWorks SDK 메인 클래스입니다.
 * <pre>
 *     TagWorks.initializeSdk();
 *     TagWorks.getInstance();
 * </pre>
 * @author hanyj
 * @version v1.0. 02023.08.10
 */
public class TagWorks {

    /**
     * SDK 설정영역
     */

    private static final String PRE_KEY_VISITOR_ID = "tagworks.visitorid";
    private static final String PRE_KEY_USER_ID = "tagworks.userid";
    private static final String PRE_KEY_OPT_OUT = "tagworks.optout";
    @GuardedBy("LOCK")
    private static final Map<String, TagWorks> INSTANCE = new HashMap<>();
    private static final Object INSTANCE_LOCK = new Object();
    private static final Object PUSH_LOCK = new Object();
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
     * TagWorks 클래스 생성자 입니다.
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    private TagWorks(@NonNull Context context, @NonNull TagWorksConfig config){
        this.context = context;
        this.config = config;
        this.siteId = config.getSiteId();
        this.baseUrl = config.getBaseUrl();
        this.contentBaseUrl = String.format("https://%s/", context.getPackageName());
        this.deviceInfo = new DeviceInfo(context);
        this.dimensions = new HashMap<>();
        this.eventDispatcher = new EventDispatcher(
                new PacketSender(baseUrl, config.getSessionTimeOut()),
                config.getDispatchInterval(),
                config.getDispatchRetryCount(),
                config.isManualDispatch()
        );
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

        Log.d("TagWorks", "getInstance Call -->" + instance.hashCode());
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
        synchronized(INSTANCE_LOCK){
            if(INSTANCE.containsKey(INSTANCE_KEY)){
                return getInstance();
            }
            checkValidSiteId(siteId);
            checkValidBaseUrl(baseUrl);
            TagWorksConfig config = new TagWorksConfig.Builder()
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
        synchronized(INSTANCE_LOCK){
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
        synchronized(INSTANCE_LOCK){
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
    private String contentReferrerUrl;
    private final HashMap<Integer, String> dimensions;
    private final DeviceInfo deviceInfo;

    /**
     * 현재 contentUrl을 지정합니다.
     * @param path contentUrl path
     * @author hanyj
     * @since  v1.0.0 2023.08.11
     */
    public void setContentUrl(String path){
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
     * 기기의 UserAgnet를 반환합니다.
     * @return UserAgnet
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public String getUserAgent(){
        return deviceInfo.getUserAgent();
    }

    /**
     * 공용 사용자 정의 디멘전을 지정합니다.
     * @param index 디멘전 index
     * @param value 디멘전 value
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public void setDimension(int index, String value){
        dimensions.put(index, value);
    }

    /**
     * 공용 사용자 정의 디멘전 value를 반환합니다.
     * @param index 디멘전 index
     * @return 디멘전 value
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public String getDimension(int index){
        return dimensions.get(index);
    }

    /**
     * 공용 사용자 정의 디멘전을 반환합니다.
     * @return 디멘전 Map
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    public HashMap<Integer, String> getDimensions(){
        return dimensions;
    }

    /**
     * Query 파라미터를 주입합니다.
     * @param event 이벤트 객체
     * @author hanyj
     * @since  v1.0.0 2023.08.21
     */
    private void injectParams(@NonNull Event event){
        event.setVisitorId(getVisitorId());
        event.setParams(QueryParams.SITE_ID, siteId);
        event.setParams(QueryParams.URL_PATH, contentUrl);
        event.setParams(QueryParams.REFERRER, contentReferrerUrl);
        String resolution = "unknown";
        int[] res = deviceInfo.getResolution();
        if (res != null) resolution = String.format("%sx%s", res[0], res[1]);
        event.setParams(QueryParams.SCREEN_RESOLUTION, resolution);
        event.setParams(QueryParams.USER_AGENT, deviceInfo.getUserAgent());
        event.setParams(QueryParams.LANGUAGE, deviceInfo.getLanguage());
        event.setParams(QueryParams.USER_ID, getUserId());
        event.setParams(QueryParams.EVENT, event.toString());
    }

    /**
     * TagWorks 이벤트 수집 Builder 클래스입니다.
     * <pre>
     *     TagWorks.EventPushBuilder.event();
     *     TagWorks.EventPushBuilder.pageView();
     * </pre>
     * @author hanyj
     * @version v1.0. 02023.08.30
     */
    public static class EventPushBuilder{

        /**
         * 수집할 이벤트를 지정합니다.
         * @param eventKey 이벤트 key
         * @param customUserPath 사용자 정의 경로
         * @author hanyj
         * @since  v1.0.0 2023.08.30
         */
        @NonNull
        public static EventBuilder event(@NonNull StandardEvent eventKey, @Nullable String customUserPath){
            return new EventBuilder(getInstance(), eventKey.getValue(), customUserPath);
        }

        /**
         * 수집할 이벤트를 지정합니다.
         * @param eventKey 이벤트 key
         * @param customUserPath 사용자 정의 경로
         * @author hanyj
         * @since  v1.0.0 2023.08.30
         */
        @NonNull
        public static EventBuilder event(@NonNull String eventKey, @Nullable String customUserPath){
            return new EventBuilder(getInstance(), eventKey, customUserPath);
        }

        /**
         * 수집할 화면 경로 및 타이틀을 지정합니다.
         * @param pagePath 페이지 경로
         * @param pageTitle 페이지 제목
         * @param customUserPath 사용자 정의 경로
         * @author hanyj
         * @since  v1.0.0 2023.08.30
         */
        @NonNull
        public static PageViewBuilder pageView(@NonNull String pagePath, @Nullable String pageTitle, @Nullable String customUserPath){
            return new PageViewBuilder(getInstance(), pagePath, pageTitle, customUserPath);
        }

        /**
         * EventPushBuilder PageView Builder 클래스입니다.
         * <pre>
         *     // 단순 화면 경로 수집
         *     TagWorks.EventPushBuilder
         *          .pageView("MainActivity/HomeFragment", "HomePage")
         *          .push();
         *     // 사용자 정의 디멘전을 포함한 경로 수집
         *     TagWorks.EventPushBuilder
         *          .pageView("MainActivity/HomeFragment", "HomePage")
         *          .dimension(1, "customDimension1")
         *          .dimension(99, "customDimension99")
         *          .push();
         * </pre>
         * @author hanyj
         * @version v1.0. 02023.08.30
         */
        protected static class PageViewBuilder extends BaseBuilder{

            private final String pagePath;
            private final String pageTitle;
            private final String customUserPath;

            /**
             * PageViewBuilder 클래스 생성자 입니다.
             * @param tagWorks TagWorks 인스턴스
             * @param pagePath Page 경로
             * @param pageTitle Page 타이틀
             * @param customUserPath 사용자 정의 경로
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            public PageViewBuilder(@NonNull TagWorks tagWorks, @NonNull String pagePath, @Nullable String pageTitle, @Nullable String customUserPath) {
                super(tagWorks);
                this.pagePath = pagePath;
                this.pageTitle = pageTitle;
                this.customUserPath = customUserPath;
            }

            /**
             * PageView 사용자 정의 디멘전을 지정합니다.
             * @param index 디멘전 index
             * @param value 디멘전 value
             * @return PageViewBuilder 인스턴스
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            @Override
            public PageViewBuilder dimension(int index, @NonNull String value) {
                dimensions.put(index, value);
                return this;
            }

            /**
             * 이벤트 객체를 이벤트 큐에 적재합니다.
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            @Override
            public void push() {
                Event event = new Event(dimensions);
                event.setEvent(StandardEvent.PAGE_VIEW);
                event.setEventParams(StandardEventParams.PAGE_TITLE, pageTitle);
                event.setCustomUserPath(customUserPath);
                tagWorks.setContentUrl(pagePath);
                tagWorks.eventPush(event);
            }
        }

        /**
         * EventPushBuilder Event Builder 클래스입니다.
         * <pre>
         *     // Standard 이벤트 수집
         *     TagWorks.EventPushBuilder
         *          .event(TagStandardEvent.Click, "Main/HomePage")
         *          .push();
         *     // 사용자 정의 이벤트 수집
         *     TagWorks.EventPushBuilder
         *          .event("PopupViewExit")
         *          .dimension(3, "팝업화면 종료")
         *          .push();
         * </pre>
         * @author hanyj
         * @version v1.0. 02023.08.30
         */
        protected static class EventBuilder extends BaseBuilder{

            private final String eventKey;
            private final String customUserPath;
            private final HashMap<Integer, String> dimensions = new HashMap<>();

            /**
             * EventBuilder 클래스 생성자 입니다.
             * @param tagWorks TagWorks 인스턴스
             * @param eventKey 이벤트 key
             * @param customUserPath 사용자 정의 경로
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            public EventBuilder(@NonNull TagWorks tagWorks, @NonNull String eventKey, @Nullable String customUserPath) {
                super(tagWorks);
                this.eventKey = eventKey;
                this.customUserPath = customUserPath;
            }

            /**
             * PageView 사용자 정의 디멘전을 지정합니다.
             * @param index 디멘전 index
             * @param value 디멘전 value
             * @return EventBuilder 인스턴스
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            @Override
            public EventBuilder dimension(int index, @NonNull String value) {
                dimensions.put(index, value);
                return this;
            }

            /**
             * 이벤트 객체를 이벤트 큐에 적재합니다.
             * @author hanyj
             * @since  v1.0.0 2023.08.30
             */
            @Override
            public void push() {
                Event event = new Event(dimensions);
                event.setEvent(eventKey);
                event.setCustomUserPath(customUserPath);
                tagWorks.eventPush(event);
            }
        }
    }

    /**
     * 발송 영역
     */

    private final EventDispatcher eventDispatcher;

    /**
     * 이벤트를 발송합니다.
     * @param event 이벤트 객체
     * @author hanyj
     * @since  v1.0.0 2023.08.24
     */
    private void eventPush(Event event){
        synchronized (PUSH_LOCK){
            injectParams(event);
            if(!getOptOut()){
                // add Queue
                eventDispatcher.enqueue(event);
            }else{
                // drop
            }
        }
    }
}
