package com.obzen.tagworks.data;

import static com.obzen.tagworks.util.CommonUtil.isEmpty;

import androidx.annotation.NonNull;

import com.obzen.tagworks.constants.TagTypeParams;
import com.obzen.tagworks.constants.TagWorksParams;

import java.util.HashMap;
import java.util.Map;

public class SearchData extends BaseData{

    private final HashMap<String, String> mSearchMap = new HashMap<>();

    public SearchData() {
        mSearchMap.put(TagWorksParams.TAG_EVENT_TYPE.getValue(), TagTypeParams.SEARCH.getValue());
    }

    public void setSearch(String key, String value){
        mSearchMap.put(key, value);
    }
    public HashMap<String, String> getSearch(){
        return mSearchMap;
    }
    private String getSerializedSearch(){
        StringBuilder searchStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : mSearchMap.entrySet()) {
            searchStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(getDelimiterParams());
        }
        return searchStringBuilder.toString();
    }

    @NonNull
    @Override
    public String toSerializedData(){
        getStringBuilder()
                .append(getSerializedSearch())
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
