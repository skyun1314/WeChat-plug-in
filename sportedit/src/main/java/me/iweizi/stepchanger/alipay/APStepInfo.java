package me.iweizi.stepchanger.alipay;

/**
 * Created by iweiz on 2017/9/5.
 * 支付宝步数数据记录
 */

@SuppressWarnings("unused")
class APStepInfo {
    private String biz;
    private int steps;
    private long time;

    APStepInfo() {
        biz = "alipay";
        steps = 0;
        time = 0;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
