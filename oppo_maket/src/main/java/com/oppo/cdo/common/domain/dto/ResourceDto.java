package com.oppo.cdo.common.domain.dto;

import io.protostuff.Tag;
import java.util.List;

public class ResourceDto {
    @Tag(25)
    private String adContent;
    @Tag(23)
    private int adId;
    @Tag(24)
    private String adPos;
    @Tag(20)
    private String adapter;
    @Tag(21)
    private String adapterDesc;
    @Tag(37)
    private String adapterTesterAvatar;
    @Tag(38)
    private String adapterTesterName;
    @Tag(19)
    private int adapterType;
    @Tag(1)
    private long appId;
    @Tag(3)
    private String appName;
    @Tag(29)
    private long auth;
    @Tag(33)
    private String bg;
    @Tag(4)
    private long catLev1;
    @Tag(5)
    private long catLev2;
    @Tag(6)
    private long catLev3;
    @Tag(30)
    private String catName;
    @Tag(13)
    private String checksum;
    @Tag(27)
    private String desc;
    @Tag(15)
    private long dlCount;
    @Tag(16)
    private String dlDesc;
    @Tag(18)
    private float grade;
    @Tag(17)
    private long gradeCount;
    @Tag(35)
    private int iconLabel;
    @Tag(14)
    private String iconUrl;
    @Tag(34)
    private List<Integer> labels;
    @Tag(12)
    private String md5;
    @Tag(7)
    private String pkgName;
    @Tag(28)
    private int point;
    @Tag(32)
    private List<String> screenshots;
    @Tag(26)
    private String shortDesc;
    @Tag(10)
    private long size;
    @Tag(11)
    private String sizeDesc;
    @Tag(36)
    private int special;
    @Tag(39)
    private String srcKey;
    @Tag(31)
    private int type;
    @Tag(22)
    private String url;
    @Tag(9)
    private long verCode;
    @Tag(2)
    private long verId;
    @Tag(8)
    private String verName;

    public long getAppId() {
        return this.appId;
    }

    public void setAppId(long j) {
        this.appId = j;
    }

    public long getVerId() {
        return this.verId;
    }

    public void setVerId(long j) {
        this.verId = j;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public long getCatLev1() {
        return this.catLev1;
    }

    public void setCatLev1(long j) {
        this.catLev1 = j;
    }

    public long getCatLev2() {
        return this.catLev2;
    }

    public void setCatLev2(long j) {
        this.catLev2 = j;
    }

    public long getCatLev3() {
        return this.catLev3;
    }

    public void setCatLev3(long j) {
        this.catLev3 = j;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }

    public String getVerName() {
        return this.verName;
    }

    public void setVerName(String str) {
        this.verName = str;
    }

    public long getVerCode() {
        return this.verCode;
    }

    public void setVerCode(long j) {
        this.verCode = j;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
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

    public String getChecksum() {
        return this.checksum;
    }

    public void setChecksum(String str) {
        this.checksum = str;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    public long getDlCount() {
        return this.dlCount;
    }

    public void setDlCount(long j) {
        this.dlCount = j;
    }

    public String getDlDesc() {
        return this.dlDesc;
    }

    public void setDlDesc(String str) {
        this.dlDesc = str;
    }

    public long getGradeCount() {
        return this.gradeCount;
    }

    public void setGradeCount(long j) {
        this.gradeCount = j;
    }

    public float getGrade() {
        return this.grade;
    }

    public void setGrade(float f) {
        this.grade = f;
    }

    public int getAdapterType() {
        return this.adapterType;
    }

    public void setAdapterType(int i) {
        this.adapterType = i;
    }

    public String getAdapter() {
        return this.adapter;
    }

    public void setAdapter(String str) {
        this.adapter = str;
    }

    public String getAdapterDesc() {
        return this.adapterDesc;
    }

    public void setAdapterDesc(String str) {
        this.adapterDesc = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public int getAdId() {
        return this.adId;
    }

    public void setAdId(int i) {
        this.adId = i;
    }

    public String getAdPos() {
        return this.adPos;
    }

    public void setAdPos(String str) {
        this.adPos = str;
    }

    public String getAdContent() {
        return this.adContent;
    }

    public void setAdContent(String str) {
        this.adContent = str;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String str) {
        this.shortDesc = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int i) {
        this.point = i;
    }

    public long getAuth() {
        return this.auth;
    }

    public void setAuth(long j) {
        this.auth = j;
    }

    public String getCatName() {
        return this.catName;
    }

    public void setCatName(String str) {
        this.catName = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public List<String> getScreenshots() {
        return this.screenshots;
    }

    public void setScreenshots(List<String> list) {
        this.screenshots = list;
    }

    public String getBg() {
        return this.bg;
    }

    public void setBg(String str) {
        this.bg = str;
    }

    public int getIconLabel() {
        return this.iconLabel;
    }

    public void setIconLabel(int i) {
        this.iconLabel = i;
    }

    public List<Integer> getLabels() {
        return this.labels;
    }

    public void setLabels(List<Integer> list) {
        this.labels = list;
    }

    public int getSpecial() {
        return this.special;
    }

    public void setSpecial(int i) {
        this.special = i;
    }

    public String getAdapterTesterAvatar() {
        return this.adapterTesterAvatar;
    }

    public void setAdapterTesterAvatar(String str) {
        this.adapterTesterAvatar = str;
    }

    public String getAdapterTesterName() {
        return this.adapterTesterName;
    }

    public void setAdapterTesterName(String str) {
        this.adapterTesterName = str;
    }

    public String getSrcKey() {
        return this.srcKey;
    }

    public void setSrcKey(String str) {
        this.srcKey = str;
    }

    public String toString() {
        return "ResourceDto{appId=" + this.appId + ", verId=" + this.verId + ", appName='" + this.appName + '\'' + ", catLev1=" + this.catLev1 + ", catLev2=" + this.catLev2 + ", catLev3=" + this.catLev3 + ", pkgName='" + this.pkgName + '\'' + ", verName='" + this.verName + '\'' + ", verCode=" + this.verCode + ", size=" + this.size + ", sizeDesc='" + this.sizeDesc + '\'' + ", md5='" + this.md5 + '\'' + ", checksum='" + this.checksum + '\'' + ", iconUrl='" + this.iconUrl + '\'' + ", dlCount=" + this.dlCount + ", dlDesc='" + this.dlDesc + '\'' + ", gradeCount=" + this.gradeCount + ", grade=" + this.grade + ", adapterType=" + this.adapterType + ", adapter='" + this.adapter + '\'' + ", adapterDesc='" + this.adapterDesc + '\'' + ", url='" + this.url + '\'' + ", adId=" + this.adId + ", adPos='" + this.adPos + '\'' + ", adContent='" + this.adContent + '\'' + ", shortDesc='" + this.shortDesc + '\'' + ", desc='" + this.desc + '\'' + ", point=" + this.point + ", auth=" + this.auth + ", catName='" + this.catName + '\'' + ", type=" + this.type + ", screenshots=" + this.screenshots + ", bg='" + this.bg + '\'' + ", labels=" + this.labels + ", iconLabel=" + this.iconLabel + ", special=" + this.special + ", adapterTesterAvatar='" + this.adapterTesterAvatar + '\'' + ", adapterTesterName='" + this.adapterTesterName + '\'' + ", srcKey='" + this.srcKey + '\'' + '}';
    }
}