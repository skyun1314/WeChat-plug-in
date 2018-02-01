package com.oppo.cdo.card.domain.dto;

import com.oppo.cdo.common.domain.dto.ResourceDto;

import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2018/2/1.
 */
    /* compiled from: ExposureInfo */
public  class c {
    public Map<String, String> a;
    public int b;
    public int c;
    public int d;
    public List<b> e;
    public List<a> f;

    /* compiled from: ExposureInfo */
    public static class a {
        public ResourceDto a;
        public int b;

        public a(ResourceDto resourceDto, int i) {
            this.a = resourceDto;
            this.b = i;
        }
    }

    /* compiled from: ExposureInfo */
    public static class b {
        public BannerDto a;
        public int b;


        public b(BannerDto bannerDto, int i) {
            this.a = bannerDto;
            this.b = i;
        }
    }

    public c(){}

    public c(Map<String, String> map, int i, int i2, int i3) {
        this.a = map;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }
}