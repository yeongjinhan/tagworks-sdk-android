//
//  UserAgent.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.helper;

import android.content.Context;

public class UserAgent {

    private final Context context;
    private final DeviceInfo deviceInfo;
    private final ApplicationInfo applicationInfo;

    public UserAgent(Context context){
        this.context = context;
        this.deviceInfo = new DeviceInfo(context);
        this.applicationInfo = new ApplicationInfo(context);
    }

    public String getUserAgent(){
        return "";
    }
}
