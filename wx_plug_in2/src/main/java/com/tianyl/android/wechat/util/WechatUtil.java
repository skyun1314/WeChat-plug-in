package com.tianyl.android.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WechatUtil {

	public static String getTime(){
		Date date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(date);
	}
	
	public static String getType(Object obj){
		if(obj == null){
			return "NULL";
		}
		return obj.getClass().getName();
	}
	
	public static String getStr(Object obj){
		if(obj == null){
			return "NULL";
		}

		if(obj.getClass().equals(new byte[]{}.getClass())){
			return encodeHexStr((byte[])obj);
		}
		
		return obj.toString();
	}
	
	public static String encodeHexStr(byte[] data) {
		char[] toDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; 
		int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];  
            out[j++] = toDigits[0x0F & data[i]];  
        }
        return new String(out);  
    }
	
}
