package com.tianyl.android.wechat.util;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Random;

import de.robv.android.xposed.XposedHelpers;

/**
 * Created by tianyl on 17/2/16.
 */

public class WechatDatabase {

    private Object dbObj;

    public WechatDatabase(Object dbObj){
        this.dbObj = dbObj;
    }

    public Cursor rawQuery(String sql,String... args) {
        Object obj = XposedHelpers.callMethod(dbObj, "rawQuery", sql, args);
        return (Cursor)obj;
    }

    public Cursor findMessageByMsgsvrid(String msgsvrid){
        return rawQuery("select * from message where msgsvrid = ? ",msgsvrid);
    }

    public void insertSysMessage(String talker,int talkerId,String msg,long createTime){
        Long msgSvrId = createTime + (new Random().nextInt());
        Long msgId = getNextMsgId();
        ContentValues v = new ContentValues();
        v.put("msgid", msgId);
        v.put("msgSvrid", msgSvrId);
        v.put("type", 10000);
        v.put("status", 3);
        v.put("createTime", createTime);
        v.put("talker", talker);
        v.put("content", msg);
        if (talkerId != -1) {
            v.put("talkerid", talkerId);
        }
        XposedHelpers.callMethod(dbObj, "insert", "message", "", v);
    }

    public Long getNextMsgId(){
        Cursor cur = rawQuery("SELECT max(msgId) FROM message");
        if (cur == null || !cur.moveToFirst()) {
            return 1L;
        }
        Long msgId = cur.getLong(0) + 1;
        cur.close();
        return msgId;
    }
}
