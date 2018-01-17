package com.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.accessibility.utils.AccessibilityLog;

/**
 * Created by popfisher on 2017/7/6.
 */

@TargetApi(16)
public class AccessibilitySampleService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 通过代码可以动态配置，但是可配置项少一点
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
//                | AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
//        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
//        accessibilityServiceInfo.notificationTimeout = 0;
//        accessibilityServiceInfo.flags = AccessibilityServiceInfo.DEFAULT;
//        setServiceInfo(accessibilityServiceInfo);
    }







    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
        String pkgName = event.getPackageName().toString();
        int eventType = event.getEventType();
        AccessibilityOperator.getInstance().updateEvent(this, event);
//        try {
//            Thread.sleep(200);
//        } catch (Exception e) {}
        AccessibilityLog.printLog("eventType: " + eventType + " pkgName: " + pkgName);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
