package com.obzen.tagworks.data;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagTypeParams;
import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

public class PageViewData extends BaseData{
    private final HashMap<String, String> mPageViewMap = new HashMap<>();
    public PageViewData() {
        mPageViewMap.put(TagWorksParams.TAG_EVENT_TYPE.getValue(), TagTypeParams.PAGE_VIEW.getValue());
    }
    public void setPageView(String key, String value){
        mPageViewMap.put(key, value);
    }
    public HashMap<String, String> getPageView(){
        return mPageViewMap;
    }
    private String getSerializedPageView(){
        StringBuilder pageViewStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : mPageViewMap.entrySet()) {
            if(!entry.getKey().equals(TagWorksParams.PAGE_PATH.getValue())){
                pageViewStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(getDelimiterParams());
            }
        }
        return pageViewStringBuilder.toString();
    }
    @NonNull
    @Override
    public String toSerializedData(){
        getStringBuilder()
                .append(getSerializedPageView())
                .append(getSerializedCommon())
                .append(getSerializedDimension());
        if(!isEmpty(getCustomUserPath())) {
            getStringBuilder().append(getSerializedCustomUserPath());
        }else{
            getStringBuilder().deleteCharAt(getStringBuilder().length() - 1);
        }
        return getStringBuilder().toString();
    }
}
