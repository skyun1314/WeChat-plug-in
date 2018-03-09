package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;
import java.util.Map;

public class CardDto {
    @Tag(4)
    private String actionParam;
    @Tag(3)
    private String actionType;
    @Tag(1)
    private int code;
    @Tag(5)
    private int cornerLabel;
    @Tag(6)
    private Map<String, Object> ext;
    @Tag(2)
    private int key;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int i) {
        this.key = i;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String str) {
        this.actionType = str;
    }

    public String getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(String str) {
        this.actionParam = str;
    }

    public int getCornerLabel() {
        return this.cornerLabel;
    }

    public void setCornerLabel(int i) {
        this.cornerLabel = i;
    }

    public Map<String, Object> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, Object> map) {
        this.ext = map;
    }

    public String toString() {
        return "CardDto{code=" + this.code + ", key=" + this.key + ", actionType='" + this.actionType + '\'' + ", actionParam='" + this.actionParam + '\'' + ", cornerLabel=" + this.cornerLabel + ", ext=" + this.ext + '}';
    }
}