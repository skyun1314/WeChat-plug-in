package com.oppo;

import io.protostuff.Tag;
import java.util.Map;

public class anb {
    @Tag(value=1) String a;
    @Tag(value=4) String b;
    @Tag(value=2) private long c;
    @Tag(value=3) private Map d;

    public anb() {
        super();
    }

    public void a(String arg1) {
        this.a = arg1;
    }

    public void a(Map arg1) {
        this.d = arg1;
    }

    public void a(long arg2) {
        this.c = arg2;
    }

    public String a() {
        return this.a;
    }

    public void b(String arg1) {
        this.b = arg1;
    }

    public long b() {
        return this.c;
    }

    public Map c() {
        return this.d;
    }

    public String d() {
        return this.b;
    }
}

