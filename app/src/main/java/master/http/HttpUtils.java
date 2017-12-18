package master.http;

import android.accounts.NetworkErrorException;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by master on 2017/11/8.
 */

public class HttpUtils {

    static ExecutorService threadpool = Executors.newCachedThreadPool();
    static final int CONNECT_TIME_OUT = 5000;
    static Map<String, String> httpHeadMap = new HashMap<>();


    public static void addHttpHead(String key, String value) {
        if (key != null && value != null) {
            httpHeadMap.put(key, value);
        }
    }


    /**
     * GET请求
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void executeGet(final Context context, final String url, final HttpCallbackListener listener) {
        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                URL mURL;
                HttpURLConnection mHttpURLConnection = null;
                try {
                    mURL = new URL(url);
                    mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                    mHttpURLConnection.setRequestMethod("GET");
                    mHttpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
                    int mResponseCode = mHttpURLConnection.getResponseCode();
                    if (mResponseCode == 200) {
                        // 访问成功
                        InputStream mInputStream = mHttpURLConnection.getInputStream();
                        BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream, "UTF-8"));
                        StringBuffer mStringBuffer = new StringBuffer();
                        String line = "";
                        while ((line = mBufferedReader.readLine()) != null) {
                            mStringBuffer.append(line);
                        }
                        mInputStream.close();
                        mBufferedReader.close();
                        new HttpCall(context, listener).doSuccess(mStringBuffer.toString());
                    } else {
                        new HttpCall(context, listener).doFail(new NetworkErrorException("ResponseCode = " + mResponseCode));
                    }
                } catch (Exception e) {
                    new HttpCall(context, listener).doFail(e);
                } finally {
                    if (mHttpURLConnection != null) {
                        mHttpURLConnection.disconnect();
                    }
                }
            }
        });
    }


    /**
     * POST请求
     *
     * @param context
     * @param url
     * @param params
     * @param listener
     */
    public static void executePost(final Context context, final String url, final Map<String, Object> params, final HttpCallbackListener listener) {

        final StringBuffer mStringBuffer = new StringBuffer();
        for (String key : params.keySet()) {
            if (mStringBuffer.length() != 0) {
                mStringBuffer.append("&");
            }
            mStringBuffer.append(key).append("=").append(params.get(key));
        }
        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                URL mURL;
                HttpURLConnection mHttpURLConnection = null;
                try {
                    mURL = new URL(url);
                    mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                    mHttpURLConnection.setRequestMethod("POST");
                    mHttpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
                    mHttpURLConnection.setDoInput(true);
                    mHttpURLConnection.setDoOutput(true);
                    mHttpURLConnection.setUseCaches(false);
                    // 设置消息头
                    for (String key : httpHeadMap.keySet()) {
                        mHttpURLConnection.addRequestProperty(key, httpHeadMap.get(key));
                    }
                    httpHeadMap.clear();

                    PrintWriter mPrintWriter = new PrintWriter(mHttpURLConnection.getOutputStream());
                    mPrintWriter.write(mStringBuffer.toString());
                    mPrintWriter.flush();
                    mPrintWriter.close();
                    int mResponseCode = mHttpURLConnection.getResponseCode();
                    if (mResponseCode == 200) {
                        InputStream mInputStream = mHttpURLConnection.getInputStream();
                        BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream, "UTF-8"));
                        StringBuffer mStringBuffer = new StringBuffer();
                        String line = "";
                        while ((line = mBufferedReader.readLine()) != null) {
                            mStringBuffer.append(line);
                        }
                        mInputStream.close();
                        mBufferedReader.close();
                        new HttpCall(context, listener).doSuccess(mStringBuffer.toString());
                    } else {
                        new HttpCall(context, listener).doFail(new NetworkErrorException("ResponseCode = " + mResponseCode));
                    }
                } catch (Exception e) {
                    new HttpCall(context, listener).doFail(e);
                } finally {
                    if (mHttpURLConnection != null) {
                        mHttpURLConnection.disconnect();
                    }
                }
            }
        });
    }


}
