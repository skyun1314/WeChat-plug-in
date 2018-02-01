package com.oppo.whoops.domain.dto.req;

import io.protostuff.Tag;
import java.util.List;

public class UpgradeReq {
    @Tag(2)
    private List<Apk> apks;
    @Tag(1)
    private String appType;

    public String getAppType() {
        return this.appType;
    }

    public void setAppType(String str) {
        this.appType = str;
    }

    public List<Apk> getApks() {
        return this.apks;
    }

    public void setApks(List<Apk> list) {
        this.apks = list;
    }

    public String toString() {
        return "UpgradeReq{appType='" + this.appType + '\'' + ", apks=" + this.apks + '}';
    }
}