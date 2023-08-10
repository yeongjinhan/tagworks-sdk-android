//
//  TagWorks.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * TagWorks SDK 메인 클래스입니다.
 * For example:
 * <pre>
 *     TagWorks.initializeApp();
 *     TagWorks.getInstance();
 * </pre>
 * @author hanyj
 * @version v1.0.0 2023.08.10
 */
public class TagWorks {

    private static class LazyHolder {
        private static final TagWorks INSTANCE = new TagWorks();
    }

    /**
     * TagWorks의 인스턴스를 반환합니다.
     * @return TagWorks Instance
     * @author hanyj
     * @since  v1.0.0 2023.08.10
     */
    public static TagWorks getInstance(){
        return LazyHolder.INSTANCE;
    }

    /**
     * TagWorks SDK를 초기화합니다.
     * @param context Application Context
     * @param siteId TagWorks 수집 컨테이너 siteId
     * @param baseUrl TagWorks 수집서버 주소 url
     *
     * @author hanyj
     * @since  v1.0.0 2023.08.10
     */
    public static void initializeApp(@NonNull Context context, @NonNull String siteId, @NonNull String baseUrl){

    }

    /**
     * TagWorks SDK를 초기화합니다.
     * @param context Application Context
     * @param config TagWorks SDK 설정 객체
     *
     * @author hanyj
     * @since  v1.0.0 2023.08.10
     */
    public static void initializeApp(@NonNull Context context, @NonNull TagWorksConfig config){

    }

}
