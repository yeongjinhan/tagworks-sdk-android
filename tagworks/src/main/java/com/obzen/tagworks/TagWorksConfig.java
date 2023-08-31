//
//  TagWorksConfig
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import androidx.annotation.NonNull;


/**
 * TagWorks SDK 설정 클래스입니다.
 * For example:
 * <pre>
 *     TagWorksConfig config = new TagWorksConfig.Builder()
 *                 .setBaseUrl("https://obzen.com/obzenTagWorks")
 *                 .setSiteId("61,YbIxGr9e")
 *                 .build();
 * </pre>
 *
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public final class TagWorksConfig {

    private final String siteId;
    private final String baseUrl;
    private final long sessionTimeOut;
    private final long dispatchInterval;
    private final int dispatchRetryCount;
    private final boolean manualDispatch;

    public static final class Builder {
        private String siteId;
        private String baseUrl;
        private long sessionTimeOut = 30;
        private long dispatchInterval = 5 * 1000;
        private int dispatchRetryCount = 3;
        private boolean manualDispatch = false;
        public Builder(){}

        public Builder(@NonNull TagWorksConfig config) {
            this.siteId = config.siteId;
            this.baseUrl = config.baseUrl;
            this.sessionTimeOut = config.sessionTimeOut;
            this.dispatchInterval = config.dispatchInterval;
            this.dispatchRetryCount = config.dispatchRetryCount;
            this.manualDispatch = config.manualDispatch;
        }

        @NonNull
        public Builder setSiteId(String siteId){
            this.siteId = siteId;
            return this;
        }

        @NonNull
        public Builder setBaseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setSessionTimeOut(long sessionTimeOut){
            this.sessionTimeOut = sessionTimeOut;
            return this;
        }

        public Builder setDispatchInterval(long dispatchInterval) {
            this.dispatchInterval = dispatchInterval;
            return this;
        }

        public Builder setDispatchRetryCount(int dispatchRetryCount){
            this.dispatchRetryCount = dispatchRetryCount;
            return this;
        }

        public Builder setManualDispatch(boolean manualDispatch){
            this.manualDispatch = manualDispatch;
            return this;
        }

        @NonNull
        public TagWorksConfig build(){
            return new TagWorksConfig(siteId, baseUrl, sessionTimeOut, dispatchInterval, dispatchRetryCount, manualDispatch);
        }
    }

    private TagWorksConfig(@NonNull String siteId, @NonNull String baseUrl, long sessionTimeOut, long dispatchInterval, int dispatchRetryCount, boolean manualDispatch) {
        this.siteId = siteId;
        this.baseUrl = baseUrl;
        this.sessionTimeOut = sessionTimeOut;
        this.dispatchInterval = dispatchInterval;
        this.dispatchRetryCount = dispatchRetryCount;
        this.manualDispatch = manualDispatch;
    }

    @NonNull
    public String getSiteId() {
        return siteId;
    }

    @NonNull
    public String getBaseUrl() {
        return baseUrl;
    }

    public long getSessionTimeOut(){
        return sessionTimeOut;
    }

    public long getDispatchInterval(){
        return dispatchInterval;
    }

    public int getDispatchRetryCount(){
        return dispatchRetryCount;
    }

    public boolean isManualDispatch(){
        return manualDispatch;
    }
}
