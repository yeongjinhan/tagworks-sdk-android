package com.obzen.tagworks;

import static com.obzen.tagworks.util.VerificationUtil.checkValidBaseUrl;
import static com.obzen.tagworks.util.VerificationUtil.checkValidSiteId;

import androidx.annotation.NonNull;

public class TagWorksConfig {

    private String mSiteId;
    private String mBaseUrl;
    private long mDispatchInterval;
    private boolean mDispatchGzipped;
    private int mDispatchTimeout;
    private boolean mDispatchWifiOnly;
    private boolean mOptOut;
    private int mSessionTimeout;

    public String getSiteId() {
        return mSiteId;
    }

    public void setSiteId(String mSiteId) {
        this.mSiteId = mSiteId;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    public long getDispatchInterval() {
        return mDispatchInterval;
    }

    public void setDispatchInterval(long mDispatchInterval) {
        this.mDispatchInterval = mDispatchInterval;
    }

    public boolean isDispatchGzipped() {
        return mDispatchGzipped;
    }

    public void setDispatchGzipped(boolean mDispatchGzipped) {
        this.mDispatchGzipped = mDispatchGzipped;
    }

    public int getDispatchTimeout() {
        return mDispatchTimeout;
    }

    public void setDispatchTimeout(int mDispatchTimeout) {
        this.mDispatchTimeout = mDispatchTimeout;
    }

    public boolean isDispatchWifiOnly() {
        return mDispatchWifiOnly;
    }

    public void setDispatchWifiOnly(boolean mDispatchWifiOnly) {
        this.mDispatchWifiOnly = mDispatchWifiOnly;
    }

    public boolean isOptOut() {
        return mOptOut;
    }

    public void setOptOut(boolean mOptOut) {
        this.mOptOut = mOptOut;
    }

    public int getSessionTimeout() {
        return mSessionTimeout;
    }

    public void setSessionTimeout(int mSessionTimeout) {
        this.mSessionTimeout = mSessionTimeout;
    }

    public static class Builder {

        private String siteId;
        private String baseUrl;
        private long dispatchInterval;
        private boolean dispatchGzipped;
        private int dispatchTimeout;
        private boolean dispatchWifiOnly;
        private boolean optOut;
        private int sessionTimeout;

        public Builder (@NonNull String siteId, @NonNull String baseUrl){
            this.siteId = checkValidSiteId(siteId);
            this.baseUrl = checkValidBaseUrl(baseUrl);
        }

        public Builder dispatchInterval(long dispatchInterval){
            this.dispatchInterval = dispatchInterval;
            return this;
        }

        public Builder dispatchGzipped(boolean dispatchGzipped){
            this.dispatchGzipped = dispatchGzipped;
            return this;
        }

        public Builder dispatchTimeout(int dispatchTimeout){
            this.dispatchTimeout = dispatchTimeout;
            return this;
        }

        public Builder dispatchWifiOnly(boolean dispatchWifiOnly){
            this.dispatchWifiOnly = dispatchWifiOnly;
            return this;
        }

        public Builder optOut(boolean optOut){
            this.optOut = optOut;
            return this;
        }

        public Builder sessionTimeout(int sessionTimeout){
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public TagWorksConfig build(){
            return new TagWorksConfig(this);
        }
    }
    public TagWorksConfig(Builder builder){
        this.mSiteId = builder.siteId;
        this.mBaseUrl = builder.baseUrl;
        this.mDispatchGzipped = builder.dispatchGzipped;
        this.mDispatchInterval = builder.dispatchInterval;
        this.mDispatchTimeout = builder.dispatchTimeout;
        this.mDispatchWifiOnly = builder.dispatchWifiOnly;
        this.mOptOut = builder.optOut;
        this.mSessionTimeout = builder.sessionTimeout;
    }
}
