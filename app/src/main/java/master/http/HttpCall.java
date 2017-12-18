package master.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by master on 2017/11/8.
 */

public class HttpCall {

    Handler mHandler;

    public HttpCall(Context context, final HttpCallbackListener listener) {
        Looper looper = context.getMainLooper();
        mHandler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        listener.onFinish((String) msg.obj);
                        break;
                    case 1:
                        listener.onError((Exception) msg.obj);
                        break;
                    default:

                }
            }
        };
    }

    public void doSuccess(String response) {
        Message message = Message.obtain();
        message.obj = response;
        message.what = 0;
        mHandler.sendMessage(message);
    }

    public void doFail(Exception e) {
        Message message = Message.obtain();
        message.obj = e;
        message.what = 1;
        mHandler.sendMessage(message);
    }
}
