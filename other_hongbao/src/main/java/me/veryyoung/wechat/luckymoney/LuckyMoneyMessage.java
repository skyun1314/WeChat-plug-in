package me.veryyoung.wechat.luckymoney;

/**
 * Created by veryyoung on 2017/3/17.
 */

public class LuckyMoneyMessage {

    private int msgType;

    private int channelId;

    private String sendId;

    private String nativeUrlString;

    private String talker;

    public int getMsgType() {
        return msgType;
    }


    public int getChannelId() {
        return channelId;
    }


    public String getSendId() {
        return sendId;
    }


    public String getNativeUrlString() {
        return nativeUrlString;
    }


    public String getTalker() {
        return talker;
    }


    public LuckyMoneyMessage(int msgType, int channelId, String sendId, String nativeUrlString, String talker) {
        this.msgType = msgType;
        this.channelId = channelId;
        this.sendId = sendId;
        this.nativeUrlString = nativeUrlString;
        this.talker = talker;
    }

}
