package com.oppo.whoops.domain.dto.req;

import io.protostuff.Tag;

public class Apk {
    @Tag(1)
    private String catType;
    @Tag(2)
    private String pluginType;
    @Tag(3)
    private int supporter;
    @Tag(4)
    private int verCode;

    public String getCatType() {
        return this.catType;
    }

    public void setCatType(String str) {
        this.catType = str;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String str) {
        this.pluginType = str;
    }

    public int getSupporter() {
        return this.supporter;
    }

    public void setSupporter(int i) {
        this.supporter = i;
    }

    public int getVerCode() {
        return this.verCode;
    }

    public void setVerCode(int i) {
        this.verCode = i;
    }

    public String toString() {
        return "Apk{catType='" + this.catType + '\'' + ", pluginType='" + this.pluginType + '\'' + ", supporter=" + this.supporter + ", verCode=" + this.verCode + '}';
    }
}