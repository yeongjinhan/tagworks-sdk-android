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
 * The type Tag works.
 */
public class TagWorks {

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
     * Get site id string.
     *
     * @return the string
     */
    public String getSiteId(){
        return siteId;
    }

    /**
     * Get base url string.
     *
     * @return the string
     */
    public String getBaseUrl(){
        return baseUrl;
    }

    /**
     * Set user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId){
        PreferencesUtil.setString(context, PRE_KEY_USER_ID, userId);
    }

    /**
     * Get user id string.
     *
     * @return the string
     */
    public String getUserId(){
        return PreferencesUtil.getString(context, PRE_KEY_USER_ID);
    }

    /**
     * Set visitor id.
     *
     * @param visitorId the visitor id
     */
    public void setVisitorId(String visitorId){
        if(checkValidVisitorId(visitorId)){
            PreferencesUtil.setString(context, PRE_KEY_VISITOR_ID, visitorId);
        }
    }

    /**
     * Get visitor id string.
     *
     * @return the string
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
     * Set opt out.
     *
     * @param optOut the opt out
     */
    public void setOptOut(boolean optOut){
        PreferencesUtil.setBoolean(context, PRE_KEY_OPT_OUT, optOut);
    }

    /**
     * Get opt out boolean.
     *
     * @return the boolean
     */
    public boolean getOptOut(){
        return PreferencesUtil.getBoolean(context, PRE_KEY_OPT_OUT);
    }

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
     * Get instance tag works.
     *
     * @return the tag works
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
     * Initialize sdk tag works.
     *
     * @param context the context
     * @param siteId  the site id
     * @param baseUrl the base url
     * @return the tag works
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
     * Initialize sdk tag works.
     *
     * @param context the context
     * @param config  the config
     * @return the tag works
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

    @NonNull
    private static TagWorks initialize(@NonNull Context context, @NonNull TagWorksConfig config){
        final TagWorks instance;
        synchronized(INSTANCE_LOCK){
            instance = new TagWorks(context, config);
            INSTANCE.put(INSTANCE_KEY, instance);
            return instance;
        }
    }

    private final String contentBaseUrl;
    private String contentUrl;
    private String contentReferrerUrl;
    private final HashMap<Integer, String> dimensions;
    private final DeviceInfo deviceInfo;

    /**
     * Set content url.
     *
     * @param path the path
     */
    public void setContentUrl(String path){
        contentUrl = contentBaseUrl + path;
    }

    /**
     * Get content url string.
     *
     * @return the string
     */
    public String getContentUrl(){
        return contentUrl;
    }

    /**
     * Get user agent string.
     *
     * @return the string
     */
    public String getUserAgent(){
        return deviceInfo.getUserAgent();
    }

    /**
     * Set dimension.
     *
     * @param index the index
     * @param value the value
     */
    public void setDimension(int index, String value){
        dimensions.put(index, value);
    }

    /**
     * Get dimension string.
     *
     * @param index the index
     * @return the string
     */
    public String getDimension(int index){
        return dimensions.get(index);
    }

    /**
     * Get dimensions hash map.
     *
     * @return the hash map
     */
    public HashMap<Integer, String> getDimensions(){
        return dimensions;
    }

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
     * The type Event push builder.
     */
    public static class EventPushBuilder{

        /**
         * Event event builder.
         *
         * @param eventKey       the event key
         * @param customUserPath the custom user path
         * @return the event builder
         */
        @NonNull
        public static EventBuilder event(@NonNull StandardEvent eventKey, @Nullable String customUserPath){
            return new EventBuilder(getInstance(), eventKey.getValue(), customUserPath);
        }

        /**
         * Event event builder.
         *
         * @param eventKey       the event key
         * @param customUserPath the custom user path
         * @return the event builder
         */
        @NonNull
        public static EventBuilder event(@NonNull String eventKey, @Nullable String customUserPath){
            return new EventBuilder(getInstance(), eventKey, customUserPath);
        }

        /**
         * Page view page view builder.
         *
         * @param pagePath       the page path
         * @param pageTitle      the page title
         * @param customUserPath the custom user path
         * @return the page view builder
         */
        @NonNull
        public static PageViewBuilder pageView(@NonNull String pagePath, @Nullable String pageTitle, @Nullable String customUserPath){
            return new PageViewBuilder(getInstance(), pagePath, pageTitle, customUserPath);
        }

        /**
         * The type Page view builder.
         */
        protected static class PageViewBuilder extends BaseBuilder{

            private final String pagePath;
            private final String pageTitle;
            private final String customUserPath;

            /**
             * Instantiates a new Page view builder.
             *
             * @param tagWorks       the tag works
             * @param pagePath       the page path
             * @param pageTitle      the page title
             * @param customUserPath the custom user path
             */
            public PageViewBuilder(@NonNull TagWorks tagWorks, @NonNull String pagePath, @Nullable String pageTitle, @Nullable String customUserPath) {
                super(tagWorks);
                this.pagePath = pagePath;
                this.pageTitle = pageTitle;
                this.customUserPath = customUserPath;
            }

            @Override
            public PageViewBuilder dimension(int index, @NonNull String value) {
                dimensions.put(index, value);
                return this;
            }

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
         * The type Event builder.
         */
        protected static class EventBuilder extends BaseBuilder{

            private final String eventKey;
            private final String customUserPath;
            private final HashMap<Integer, String> dimensions = new HashMap<>();

            /**
             * Instantiates a new Event builder.
             *
             * @param tagWorks       the tag works
             * @param eventKey       the event key
             * @param customUserPath the custom user path
             */
            public EventBuilder(@NonNull TagWorks tagWorks, @NonNull String eventKey, @Nullable String customUserPath) {
                super(tagWorks);
                this.eventKey = eventKey;
                this.customUserPath = customUserPath;
            }

            @Override
            public EventBuilder dimension(int index, @NonNull String value) {
                dimensions.put(index, value);
                return this;
            }

            @Override
            public void push() {
                Event event = new Event(dimensions);
                event.setEvent(eventKey);
                event.setCustomUserPath(customUserPath);
                tagWorks.eventPush(event);
            }
        }
    }

    private final EventDispatcher eventDispatcher;

    private void eventPush(Event event){
        synchronized (PUSH_LOCK){
            injectParams(event);
            if(!getOptOut()){
                eventDispatcher.enqueue(event);
            }
        }
    }
}
