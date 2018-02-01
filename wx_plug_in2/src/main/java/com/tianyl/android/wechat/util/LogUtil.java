package com.tianyl.android.wechat.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtil {

	public static void log(String log){
		String logFile = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date()) + ".log";
		FileUtil.appendStringToFile(log,new File(FileUtil.getBathPath() + logFile));
	}
	
}
