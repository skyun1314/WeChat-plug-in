package com.oppo.cdo.update.domain.dto;

import io.protostuff.Tag;

public class UpgradeReq {
    @Tag(3)
    private String md5;
    @Tag(1)
    private String pkgName;
    @Tag(2)
    private long verCode;

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }

    public long getVerCode() {
        return this.verCode;
    }

    public void setVerCode(long j) {
        this.verCode = j;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String str) {
        this.md5 = str;
    }

    public String toString() {
        return "UpgradeReq{pkgName='" + this.pkgName + '\'' + ", verCode=" + this.verCode + ", md5='" + this.md5 + '\'' + '}';
    }
}