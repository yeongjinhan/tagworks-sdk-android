package com.obzen.tagworks;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;
import static com.obzen.tagworks.util.VerificationUtil.checkInitializedTracker;
import static com.obzen.tagworks.util.VerificationUtil.checkValidBaseUrl;
import static com.obzen.tagworks.util.VerificationUtil.checkValidSiteId;
import static com.obzen.tagworks.util.VerificationUtil.checkValidVisitorId;

import android.content.Context;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagTypeParams;
import com.obzen.tagworks.constants.TagWorksParams;
import com.obzen.tagworks.data.EventData;
import com.obzen.tagworks.data.PageViewData;
import com.obzen.tagworks.data.SearchData;
import com.obzen.tagworks.util.PreferencesUtil;

import org.matomo.sdk.Matomo;
import org.matomo.sdk.QueryParams;
import org.matomo.sdk.TrackMe;
import org.matomo.sdk.Tracker;
import org.matomo.sdk.TrackerBuilder;
import org.matomo.sdk.dispatcher.DispatchMode;
import org.matomo.sdk.extra.TrackHelper;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class TagWorks {

    private static final String LOG_TAG = "TagWorks";
    private static final String EVENT_TAG = "obzen";
    private static final String PATTERN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static Context mContext;
    private static String mSiteId;
    private static String mBaseUrl;
    private static Tracker mTracker;
    private static TrackMe mTrackMe;
    private static String mVisitorId;
    private TagWorks() {}
    private static class LazyHolder {
        private static final TagWorks INSTANCE = new TagWorks();
    }
    public static TagWorks getInstance(){
        return LazyHolder.INSTANCE;
    }
    public static void initializeTracker(@NonNull Context context,@NonNull String siteId, @NonNull String baseUrl){
        if(mTracker == null){
            mContext = context;
            mSiteId = checkValidSiteId(siteId);
            mBaseUrl = checkValidBaseUrl(baseUrl);
            mTracker = TrackerBuilder
                    .createDefault(mBaseUrl, 0)
                    .build(Matomo.getInstance(mContext));
            initializeVisitorId();
        }
    }

    public static void initializeTracker(@NonNull Context context,@NonNull TagWorksConfig config){
        if(mTracker == null){
            mContext = context;
            mSiteId = config.getSiteId();
            mBaseUrl = config.getBaseUrl();
            mTracker = TrackerBuilder
                    .createDefault(mBaseUrl, 0)
                    .build(Matomo.getInstance(mContext));
            if(!isEmpty(config.getDispatchInterval())) mTracker.setDispatchInterval(config.getDispatchInterval());
            if(!isEmpty(config.getDispatchTimeout())) mTracker.setDispatchTimeout(config.getDispatchTimeout());
            if(!isEmpty(config.getSessionTimeout())) mTracker.setSessionTimeout(config.getSessionTimeout());
            if(!isEmpty(config.isDispatchGzipped())) mTracker.setDispatchGzipped(config.isDispatchGzipped());
            if(!isEmpty(config.isOptOut())) mTracker.setOptOut(config.isOptOut());
            if(!isEmpty(config.isDispatchWifiOnly()) && config.isDispatchWifiOnly()) mTracker.setDispatchMode(DispatchMode.WIFI_ONLY);
            initializeVisitorId();
        }
    }
    private static void initializeVisitorId(){
        if(PreferencesUtil.getString(mContext, TagWorksParams.VISITOR_ID).equals("")){
            mVisitorId = UUID.randomUUID().toString();
            PreferencesUtil.setString(mContext, TagWorksParams.VISITOR_ID, mVisitorId);
        }else{
            mVisitorId = PreferencesUtil.getString(mContext, TagWorksParams.VISITOR_ID);
        }
    }
    private synchronized static TrackMe getTrackMe(){
        mTrackMe = checkInitializedTracker(mTracker).getDefaultTrackMe();
        mTrackMe.set(QueryParams.SITE_ID, mSiteId);
        mTrackMe.set(TagWorksParams.VISITOR_ID.getValue(), mVisitorId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            mTrackMe.set(QueryParams.HOURS, Integer.toString(zonedDateTime.getHour()));
            mTrackMe.set(QueryParams.MINUTES, Integer.toString(zonedDateTime.getMinute()));
            mTrackMe.set(QueryParams.SECONDS, Integer.toString(zonedDateTime.getSecond()));
            mTrackMe.set(TagWorksParams.CLIENT_DATE.getValue(), zonedDateTime.format(DateTimeFormatter.ofPattern(PATTERN_DATE_FORMAT)));
        }
        return mTrackMe;
    }
    private static void pushEvent(EventData event){
        TrackMe trackMe = getTrackMe();
        event.setCommon(TagWorksParams.VISITOR_ID, mVisitorId);
        event.setCommon(TagWorksParams.CLIENT_DATE, trackMe.get(TagWorksParams.CLIENT_DATE.getValue()));
        TrackHelper
                .track(getTrackMe())
                .event(event.toSerializedData(), EVENT_TAG)
                .with(checkInitializedTracker(mTracker));
    }
    private static void pushPageView(PageViewData pageView){
        TrackMe trackMe = getTrackMe();
        pageView.setCommon(TagWorksParams.VISITOR_ID, mVisitorId);
        pageView.setCommon(TagWorksParams.CLIENT_DATE, trackMe.get(TagWorksParams.CLIENT_DATE.getValue()));
        trackMe.set(QueryParams.EVENT_CATEGORY, pageView.toSerializedData());
        trackMe.set(QueryParams.EVENT_ACTION, EVENT_TAG);
        TrackHelper
                .track(getTrackMe())
                .screen(pageView.getPageView().get(TagWorksParams.PAGE_PATH.getValue()))
                .title(pageView.getPageView().get(TagWorksParams.PAGE_TITLE.getValue()))
                .with(checkInitializedTracker(mTracker));
    }
    private static void pushSearch(SearchData search){
        TrackMe trackMe = getTrackMe();
        search.setCommon(TagWorksParams.VISITOR_ID, mVisitorId);
        search.setCommon(TagWorksParams.CLIENT_DATE, trackMe.get(TagWorksParams.CLIENT_DATE.getValue()));
        TrackHelper
                .track(getTrackMe())
                .event(search.toSerializedData(), EVENT_TAG)
                .with(checkInitializedTracker(mTracker));
    }
    public void setVisitorId(String visitorId){
        mVisitorId = checkValidVisitorId(visitorId);
        PreferencesUtil.setString(mContext, TagWorksParams.VISITOR_ID, mVisitorId);
        mTrackMe.set(TagWorksParams.VISITOR_ID.getValue(), mVisitorId);
    }
    public String getVisitorId(){
        return mVisitorId;
    }
    public void setUserId(String userId){
        checkInitializedTracker(mTracker).setUserId(userId);
    }
    public String getUserId(){
        return checkInitializedTracker(mTracker).getUserId();
    }
    static abstract class BaseTagBuilder {
        private final TagWorks tagWorks;
        BaseTagBuilder(TagWorks tagWorks) {
            this.tagWorks = tagWorks;
        }
        public abstract void push();
    }
    public EventBuilder event(@NonNull TagTypeParams eventKey) {
        return new EventBuilder(this, TagWorksParams.TAG_EVENT_TYPE.getValue(), eventKey.getValue());
    }
    public EventBuilder event(@NonNull TagTypeParams eventKey, String customUserPath) {
        return new EventBuilder(this, TagWorksParams.TAG_EVENT_TYPE.getValue(), eventKey.getValue(), customUserPath);
    }
    public EventBuilder event(@NonNull String eventKey) {
        return new EventBuilder(this, TagWorksParams.TAG_EVENT_TYPE.getValue(), eventKey);
    }
    public EventBuilder event(@NonNull String eventKey, String customUserPath) {
        return new EventBuilder(this, TagWorksParams.TAG_EVENT_TYPE.getValue(), eventKey, customUserPath);
    }
    public static class EventBuilder extends BaseTagBuilder{
        private final String eventKey;
        private final String eventValue;
        private final String customUserPath;
        private final Map<Integer, String> dimensions = new HashMap<>();
        public EventBuilder dimension(int index, @NonNull String value) {
            this.dimensions.put(index, value);
            return this;
        }
        EventBuilder(TagWorks tagWorks, String eventKey, String eventValue) {
            super(tagWorks);
            this.eventKey = eventKey;
            this.eventValue = eventValue;
            this.customUserPath = "";
        }
        EventBuilder(TagWorks tagWorks, String eventKey, String eventValue, String customUserPath) {
            super(tagWorks);
            this.eventKey = eventKey;
            this.eventValue = eventValue;
            this.customUserPath = customUserPath;
        }
        @Override
        public void push() {
            EventData event = new EventData();
            event.setEvent(this.eventKey, this.eventValue);
            for (Map.Entry<Integer, String> entry : dimensions.entrySet()) {
                event.setDimension(entry.getKey(), entry.getValue());
            }
            if(!isEmpty(customUserPath)){
                event.setCustomUserPath(customUserPath);
            }
            pushEvent(event);
        }
    }
    public PageViewBuilder pageView(@NonNull String pagePath, @NonNull String pageTitle) {
        return new PageViewBuilder(this, pagePath, pageTitle);
    }
    public PageViewBuilder pageView(@NonNull String pagePath, @NonNull String pageTitle, String customUserPath) {
        return new PageViewBuilder(this, pagePath, pageTitle, customUserPath);
    }
    public static class PageViewBuilder extends BaseTagBuilder{
        private final String pagePath;
        private final String pageTitle;
        private final String customUserPath;
        private final Map<Integer, String> dimensions = new HashMap<>();
        public PageViewBuilder dimension(int index, @NonNull String value) {
            this.dimensions.put(index, value);
            return this;
        }
        PageViewBuilder(TagWorks tagWorks, String pagePath, String pageTitle) {
            super(tagWorks);
            this.pagePath = pagePath;
            this.pageTitle = pageTitle;
            this.customUserPath = "";
        }
        PageViewBuilder(TagWorks tagWorks, String pagePath, String pageTitle, String customUserPath) {
            super(tagWorks);
            this.pagePath = pagePath;
            this.pageTitle = pageTitle;
            this.customUserPath = customUserPath;
        }
        @Override
        public void push() {
            PageViewData pageView = new PageViewData();
            pageView.setPageView(TagWorksParams.PAGE_PATH.getValue(), pagePath);
            pageView.setPageView(TagWorksParams.PAGE_TITLE.getValue(), pageTitle);
            if(!isEmpty(customUserPath)){
                pageView.setCustomUserPath(customUserPath);
            }
            pushPageView(pageView);
        }
    }
    public SearchBuilder search(@NonNull String searchKeyword) {
        return new SearchBuilder(this, searchKeyword);
    }
    public SearchBuilder search(@NonNull String searchKeyword, String customUserPath) {
        return new SearchBuilder(this, searchKeyword, customUserPath);
    }
    public static class SearchBuilder extends BaseTagBuilder{
        private final String searchKeyword;
        private final String customUserPath;
        private final Map<Integer, String> dimensions = new HashMap<>();
        public SearchBuilder dimension(int index, @NonNull String value) {
            this.dimensions.put(index, value);
            return this;
        }
        SearchBuilder(TagWorks tagWorks, String searchKeyword) {
            super(tagWorks);
            this.searchKeyword = searchKeyword;
            this.customUserPath = "";
        }
        SearchBuilder(TagWorks tagWorks, String searchKeyword, String customUserPath) {
            super(tagWorks);
            this.searchKeyword = searchKeyword;
            this.customUserPath = customUserPath;
        }
        @Override
        public void push() {
            SearchData search = new SearchData();
            search.setSearch(TagWorksParams.SEARCH_KEYWORD.getValue(), searchKeyword);
            if(!isEmpty(customUserPath)){
                search.setCustomUserPath(customUserPath);
            }
            pushSearch(search);
        }
    }
}
