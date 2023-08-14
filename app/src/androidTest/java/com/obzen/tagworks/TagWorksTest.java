package com.obzen.tagworks;

import static org.junit.Assert.assertEquals;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TagWorksTest {

    public final static String TAG = "TagWorksTests";

    @Before
    public void initializeTagWorks(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        TagWorks.initializeSdk(appContext, "61,YbIxGr9e", "http://192.168.20.51:81/obzenTagWorks");

        // initialized by config
        TagWorksConfig config = new TagWorksConfig.Builder()
                .setBaseUrl("http://192.168.20.51:81/obzenTagWorks")
                .setSiteId("61,YbIxGr9e")
                .build();

        // call initializeSdk
        TagWorks.initializeSdk(appContext, config);
    }

    @Test
    public void TagWorksPreferencesTest(){
        Log.d(TAG, TagWorks.getInstance().getVisitorId());
        TagWorks.getInstance().setUserId("testUser");
        Log.d(TAG, TagWorks.getInstance().getUserId());
    }

    @Test
    public void TagWorksEventHandleTest(){
        TagWorks.event(TagEvent.CLICK, "testValue", "/test/test2");
    }
}
