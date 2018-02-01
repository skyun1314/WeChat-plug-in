package com.oppo;

/* compiled from: ExposureCount */
public class lr {
    public long a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public long g;
    public long h;
    public int i;
    public String j;
    public String k;
    public int l;
    public String m;
    public int n;
    public String o;
    public String p;
    public int q;

    public lr() {
        this.a = -1;
        this.b = null;
        this.c = -1;
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = -1;
        this.j = "";
        this.k = "";
        this.l = 9;
        this.m = "";
        this.n = 0;
        this.o = "";
        this.p = "";
        this.q = -1;
    }

    public lr(String str, int i, int i2, int i3, long j, long j2, int i4, int i5, String str2, String str3, String str4) {
        this(str, i, -1, i2, i3, j, j2, i4, i5, str2, str3, str4);
    }

    public lr(String str, int i, int i2, int i3, int i4, long j, long j2, int i5, int i6, String str2, String str3, String str4) {
        this.a = -1;
        this.b = null;
        this.c = -1;
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = -1;
        this.j = "";
        this.k = "";
        this.l = 9;
        this.m = "";
        this.n = 0;
        this.o = "";
        this.p = "";
        this.q = -1;
        a(str, i, i2, i3, i4, j, j2, i5, i6, str2, str3, str4);
    }

    public lr(lr lrVar) {
        this(lrVar.b, lrVar.c, lrVar.d, lrVar.e, lrVar.f, lrVar.g, lrVar.h, lrVar.i, lrVar.n, lrVar.o, lrVar.p, lrVar.m);
        this.q = lrVar.q;
        a(lrVar.j, lrVar.k, lrVar.l);
    }

    public void a(String str, int i, int i2, int i3, int i4, long j, long j2, int i5, int i6, String str2, String str3, String str4) {
        this.b = str;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.g = j;
        this.h = j2;
        this.i = i5;
        this.n = i6;
        if (str2 != null) {
            this.o = str2;
        }
        if (str3 != null) {
            this.p = str3;
        }
        if (str4 != null) {
            this.m = str4;
        }
        this.a = System.currentTimeMillis();
    }

    public void a(String str, String str2, int i) {
        if (str != null) {
            this.j = str;
        }
        if (str2 != null) {
            this.k = str2;
        }
        this.l = i;
    }

    public boolean a() {
        return this.n > 0 && !"".equals(this.o);
    }

    public boolean equals(Object obj) {
        lr lrVar = (lr) obj;
        if (lrVar == null || 3 != lrVar.q) {
            if (lrVar != null && lrVar.q == this.q && lrVar.g == this.g && lrVar.i == this.i && lrVar.n == this.n && lrVar.o.equals(this.o) && lrVar.p.equals(this.p) && lrVar.m.equals(this.m)) {
                return true;
            }
            return false;
        } else if (lrVar.e == this.e && lrVar.f == this.f && lrVar.g == this.g && lrVar.i == this.i) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return this.b + "-" + this.c + "-" + this.e + "-" + this.f + "-" + this.g + "-" + this.h + "-" + this.i + "-" + this.n + "-" + this.o + "-" + this.p + "-" + this.m;
    }
}