//	Copyright 2023 Obzen
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//	    http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

package com.obzen.tagworks;

import androidx.annotation.NonNull;


/**
 * The type Tag works config.
 */
public final class TagWorksConfig {

    private final String siteId;
    private final String baseUrl;
    private final long sessionTimeOut;
    private final long dispatchInterval;
    private final int dispatchRetryCount;
    private final boolean manualDispatch;

    /**
     * The type Builder.
     */
    public static final class Builder {
        private String siteId;
        private String baseUrl;
        private long sessionTimeOut = 30;
        private long dispatchInterval = 5 * 1000;
        private int dispatchRetryCount = 3;
        private boolean manualDispatch = false;

        /**
         * Instantiates a new Builder.
         */
        public Builder(){}

        /**
         * Instantiates a new Builder.
         *
         * @param config the config
         */
        public Builder(@NonNull TagWorksConfig config) {
            this.siteId = config.siteId;
            this.baseUrl = config.baseUrl;
            this.sessionTimeOut = config.sessionTimeOut;
            this.dispatchInterval = config.dispatchInterval;
            this.dispatchRetryCount = config.dispatchRetryCount;
            this.manualDispatch = config.manualDispatch;
        }

        /**
         * Set site id builder.
         *
         * @param siteId the site id
         * @return the builder
         */
        @NonNull
        public Builder setSiteId(String siteId){
            this.siteId = siteId;
            return this;
        }

        /**
         * Set base url builder.
         *
         * @param baseUrl the base url
         * @return the builder
         */
        @NonNull
        public Builder setBaseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Set session time out builder.
         *
         * @param sessionTimeOut the session time out
         * @return the builder
         */
        public Builder setSessionTimeOut(long sessionTimeOut){
            this.sessionTimeOut = sessionTimeOut;
            return this;
        }

        /**
         * Sets dispatch interval.
         *
         * @param dispatchInterval the dispatch interval
         * @return the dispatch interval
         */
        public Builder setDispatchInterval(long dispatchInterval) {
            this.dispatchInterval = dispatchInterval;
            return this;
        }

        /**
         * Set dispatch retry count builder.
         *
         * @param dispatchRetryCount the dispatch retry count
         * @return the builder
         */
        public Builder setDispatchRetryCount(int dispatchRetryCount){
            this.dispatchRetryCount = dispatchRetryCount;
            return this;
        }

        /**
         * Set manual dispatch builder.
         *
         * @param manualDispatch the manual dispatch
         * @return the builder
         */
        public Builder setManualDispatch(boolean manualDispatch){
            this.manualDispatch = manualDispatch;
            return this;
        }

        /**
         * Build tag works config.
         *
         * @return the tag works config
         */
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

    /**
     * Gets site id.
     *
     * @return the site id
     */
    @NonNull
    public String getSiteId() {
        return siteId;
    }

    /**
     * Gets base url.
     *
     * @return the base url
     */
    @NonNull
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Get session time out long.
     *
     * @return the long
     */
    public long getSessionTimeOut(){
        return sessionTimeOut;
    }

    /**
     * Get dispatch interval long.
     *
     * @return the long
     */
    public long getDispatchInterval(){
        return dispatchInterval;
    }

    /**
     * Get dispatch retry count int.
     *
     * @return the int
     */
    public int getDispatchRetryCount(){
        return dispatchRetryCount;
    }

    /**
     * Is manual dispatch boolean.
     *
     * @return the boolean
     */
    public boolean isManualDispatch(){
        return manualDispatch;
    }
}
