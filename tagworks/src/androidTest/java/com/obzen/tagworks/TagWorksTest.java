package com.obzen.tagworks;

import static org.junit.Assert.assertEquals;
import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.obzen.tagworks.constants.StandardEvent;

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
        //TagWorks.event(TagEvent.CLICK, "testValue");
    }

    @Test
    public void TagWorksUserAgentTest(){
        Log.d(TAG, TagWorks.getInstance().getUserAgent());
    }

    @Test
    public void TagWorksDimensionTest(){
        TagWorks.getInstance().setDimension(0, "test0");
        Log.d(TAG, TagWorks.getInstance().getDimension(0));
        TagWorks.getInstance().setDimension(1, "test1");
        Log.d(TAG, TagWorks.getInstance().getDimension(1));
        TagWorks.getInstance().setDimension(0, "test999");
        Log.d(TAG, TagWorks.getInstance().getDimension(0));
    }

    @Test
    public void TagWorksEventTest(){
        TagWorks.getInstance().setDimension(0, "test0");
        TagWorks.getInstance().setDimension(1, "test1");
        TagWorks.EventPushBuilder.event(StandardEvent.CLICK,  null).dimension(1, "testzzzz").push();
        sleep(3000);
        TagWorks.EventPushBuilder.pageView("Main/Home", "메인화면", null).push();
        sleep(3000);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
