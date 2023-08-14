//
//  TagWorksConfig.class
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

    public static final class Builder {
        private String siteId;
        private String baseUrl;
        public Builder(){}

        public Builder(@NonNull TagWorksConfig config) {
            this.siteId = config.siteId;
            this.baseUrl = config.baseUrl;
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

        @NonNull
        public TagWorksConfig build(){
            return new TagWorksConfig(siteId, baseUrl);
        }
    }

    private TagWorksConfig(@NonNull String siteId, @NonNull String baseUrl) {
        this.siteId = siteId;
        this.baseUrl = baseUrl;
    }

    @NonNull
    public String getSiteId() {
        return siteId;
    }

    @NonNull
    public String getBaseUrl() {
        return baseUrl;
    }

}
