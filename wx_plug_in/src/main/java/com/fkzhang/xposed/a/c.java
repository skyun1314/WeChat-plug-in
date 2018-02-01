package com.fkzhang.xposed.a;

import android.content.ContentValues;
import java.util.HashMap;

public class c {
    private ContentValues ʻ;
    private HashMap<String, Boolean> ʼ;

    public c() {
        this(new ContentValues());
    }

    public c(ContentValues contentValues) {
        this.ʻ = contentValues;
        this.ʼ = new HashMap();
    }

    public ContentValues ʻ() {
        return this.ʻ;
    }

    public void ʻ(String str) {
        this.ʻ.put("type", str);
    }
}
