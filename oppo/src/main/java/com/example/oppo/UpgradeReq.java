package com.example.oppo;

/**
 * Created by zk on 2018/1/15.
 */

public class UpgradeReq {
    private String md5;
    private String pkgName;
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