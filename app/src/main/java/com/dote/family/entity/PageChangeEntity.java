package com.dote.family.entity;

/**
 *
 * Created by weihui on 2017/8/8.
 */

public class PageChangeEntity {

    private int key;

    private String value;

    public PageChangeEntity(int key,String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
