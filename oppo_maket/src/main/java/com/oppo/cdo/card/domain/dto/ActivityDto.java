package com.oppo.cdo.card.domain.dto;

import io.protostuff.Tag;

/**
 * Created by zk on 2018/2/5.
 */


public class ActivityDto {
    @Tag(11)
    private String apkUrl;
    @Tag(13)
    private String appIcon;
    @Tag(20)
    private long appId;
    @Tag(14)
    private String appName;
    @Tag(18)
    private String checkSum;
    @Tag(6)
    private String detailPic;
    @Tag(21)
    private String detailUrl;
    @Tag(9)
    private long endTime;
    @Tag(8)
    private String guide;
    @Tag(1)
    private int id;
    @Tag(17)
    private String md5;
    @Tag(5)
    private String pic;
    @Tag(12)
    private String pkgName;
    @Tag(3)
    private String receiveNums;
    @Tag(7)
    private String rule;
    @Tag(19)
    private long size;
    @Tag(16)
    private String sizeDesc;
    @Tag(4)
    private String tag;
    @Tag(2)
    private String title;
    @Tag(15)
    private String verName;
    @Tag(10)
    private long versionId;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getReceiveNums() {
        return this.receiveNums;
    }

    public void setReceiveNums(String str) {
        this.receiveNums = str;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String str) {
        this.pic = str;
    }

    public String getDetailPic() {
        return this.detailPic;
    }

    public void setDetailPic(String str) {
        this.detailPic = str;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String str) {
        this.rule = str;
    }

    public String getGuide() {
        return this.guide;
    }

    public void setGuide(String str) {
        this.guide = str;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public long getVersionId() {
        return this.versionId;
    }

    public void setVersionId(long j) {
        this.versionId = j;
    }

    public String getApkUrl() {
        return this.apkUrl;
    }

    public void setApkUrl(String str) {
        this.apkUrl = str;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }

    public String getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(String str) {
        this.appIcon = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getVerName() {
        return this.verName;
    }

    public void setVerName(String str) {
        this.verName = str;
    }

    public String getSizeDesc() {
        return this.sizeDesc;
    }

    public void setSizeDesc(String str) {
        this.sizeDesc = str;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String str) {
        this.md5 = str;
    }

    public String getCheckSum() {
        return this.checkSum;
    }

    public void setCheckSum(String str) {
        this.checkSum = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public long getAppId() {
        return this.appId;
    }

    public void setAppId(long j) {
        this.appId = j;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setDetailUrl(String str) {
        this.detailUrl = str;
    }
}