package master.http;

/**
 * Created by master on 2017/11/8.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
