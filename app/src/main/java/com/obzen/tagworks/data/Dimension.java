//
//  Dimension.class
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.data;

/**
 * Custom Dimension 데이터 클래스입니다.
 * @author hanyj
 * @version v1.0.0 2023.08.11
 */
public class Dimension {

    private static final String PREFIX = "cstm_d";
    private static final String DELIMITER = "=";
    private int index;
    private String value;

    public Dimension(int index, String value){
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public void setDimension(int index, String value){
        this.index = index;
        this.value = value;
    }

    public String getDimension(){
        return PREFIX + this.index + DELIMITER + this.value;
    }
}
