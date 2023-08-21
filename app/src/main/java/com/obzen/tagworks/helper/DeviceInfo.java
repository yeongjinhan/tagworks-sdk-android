//
//  DeviceInfo.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.helper;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;

public class DeviceInfo {

    private final Context context;
    private final String httpAgent;
    private final String jvmVersion;
    private final String language;
    private final int[] resolution;
    private final String release;
    private final String model;
    private final String buildId;

    public DeviceInfo(@NonNull Context context){
        this.context = context;
        this.httpAgent = System.getProperty("http.agent");
        this.jvmVersion = System.getProperty("java.vm.version");
        this.language = Locale.getDefault().getLanguage();
        this.resolution = getCurrentDeviceSize();
        this.release = Build.VERSION.RELEASE;
        this.model = Build.MODEL;
        this.buildId = Build.ID;
    }

    @Nullable
    public String getHttpAgent(){
        return httpAgent;
    }

    @Nullable
    public String getJvmVersion(){
        return jvmVersion;
    }

    @Nullable
    public String getLanguage(){
        return language;
    }

    @Nullable
    public int[] getResolution(){
        return resolution;
    }

    @Nullable
    private int[] getCurrentDeviceSize(){
        try{
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display display;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                display = context.getDisplay();
                display.getRealMetrics(displayMetrics);
            } else {
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                display = windowManager.getDefaultDisplay();
                display.getRealMetrics(displayMetrics);
            }
            return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
        }catch (Exception e){
            return null;
        }
    }

    @Nullable
    public String getRelease(){
        return release;
    }

    @Nullable
    public String getModel(){
        return model;
    }

    @Nullable
    public String getBuildId(){
        return buildId;
    }

    @NonNull
    public String getUserAgent(){
        String httpAgent = getHttpAgent();
        if (httpAgent == null || httpAgent.startsWith("Apache-HttpClient/UNAVAILABLE (java")) {
            String dalvik = getJvmVersion();
            if (dalvik == null) dalvik = "0.0.0";
            String android = getRelease();
            String model = getModel();
            String build = getBuildId();
            httpAgent = String.format(Locale.US,
                    "Dalvik/%s (Linux; U; Android %s; %s Build/%s)",
                    dalvik, android, model, build
            );
        }
        return httpAgent;
    }
}
