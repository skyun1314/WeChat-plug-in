package com.oppo.event;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Request */
public class d {
  //  private jh mCacheStragegy;
    private Map<String, String> mHeader;
    private boolean mIsNeedGzip;
    private int mMethod;
    private String mOringinUrl;
    private c mRequestBody;
    private b mRetryHandler;
    private String mStaticTag;
    private String mTag;
    private String mUrl;
    private int mVersionCode;
    private String mVersionName;

    public d(String str) {
        this(0, str);
    }

    public d(int i, String str) {
        this.mIsNeedGzip = false;
     //   this.mCacheStragegy = jh.c;
        this.mVersionCode = -1;
        this.mVersionName = "";
        this.mMethod = i;
        this.mUrl = str;
        this.mOringinUrl = str;
        this.mHeader = new HashMap();
        generateStaticTag();
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public String getOriginUrl() {
        return this.mOringinUrl;
    }

    public void addHeader(String str, String str2) {
        this.mHeader.put(str, str2);
    }



    public String getTag() {
        return this.mTag;
    }

    public void setTag(String str) {
        this.mTag = str;
    }
   /* public void setCacheStragegy(jh jhVar) {
        this.mCacheStragegy = jhVar;
    }
    public jh getCacheControl() {
        return this.mCacheStragegy;
    }*/

    private String generateStaticTag() {
        this.mStaticTag = new StringBuilder(this.mUrl).append("#").append(System.currentTimeMillis()).toString();
        return this.mStaticTag;
    }

    public String getStaticTag() {
        return this.mStaticTag;
    }

    public void setEnableGzip(boolean z) {
        this.mIsNeedGzip = z;
    }

    public boolean isNeedGzip() {
        return this.mIsNeedGzip;
    }

    public c getRequestBody() {
        return this.mRequestBody;
    }

    public void setRequestBody(c cVar) {
        this.mRequestBody = cVar;
    }

    public Map<String, String> getRequestHeader() {
        return this.mHeader;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public void setMethod(int i) {
        this.mMethod = i;
    }

    public b getRetryHandler() {
        return this.mRetryHandler;
    }

    public void setRetryHandler(b bVar) {
        this.mRetryHandler = bVar;
    }

    public void setVersion(int i, String str) {
        this.mVersionCode = i;
        this.mVersionName = str;
    }

    public boolean isCacheable() {
        return this.mVersionCode > 0 && !TextUtils.isEmpty(this.mVersionName);
    }

    public String getCacheKey(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.append("#");
        stringBuilder.append(this.mVersionCode);
        stringBuilder.append("#");
        stringBuilder.append(this.mVersionName);
        return stringBuilder.toString();
    }

    public interface c {
        String a();

        byte[] b();
    }

    public interface b {
        void a(d dVar, BaseDALException baseDALException) throws BaseDALException;
    }

    public class BaseDALException extends Exception {
        private Throwable mThrowable;

        public BaseDALException(Throwable th) {
            super(th);
            this.mThrowable = th;
        }

        public Throwable getCause() {
            if (this.mThrowable != null) {
                return this.mThrowable;
            }
            return super.getCause();
        }

        public String getMessage() {
            if (this.mThrowable != null) {
                return this.mThrowable.getMessage();
            }
            return super.getMessage();
        }
    }
}