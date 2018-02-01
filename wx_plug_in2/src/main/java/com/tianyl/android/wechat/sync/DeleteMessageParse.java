package com.tianyl.android.wechat.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianyl.android.wechat.util.FileUtil;

public class DeleteMessageParse {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\user\\Desktop\\delete_message.sql");
		JSONArray result = parse(file);
		System.out.println(result);
	}

	public static JSONArray parse(File file) {
		JSONArray result = new JSONArray();
		List<String> sqls = FileUtil.readLines(file);
		for (String sql : sqls) {
			if (sql.contains(" and") && sql.contains("talker=")) {
				String talker = sql.substring(sql.indexOf("talker=") + 7, sql.indexOf(" and"));
				talker = talker.trim().replaceAll("'", "");
				if (sql.contains("createTime <=")) {
					String createTime = sql.substring(sql.indexOf("createTime <=") + 13).trim();
					// System.out.println(talker + ":" + createTime);
					JSONObject json = new JSONObject();
					json.put("talker", talker);
					json.put("createTime", createTime);
					result.add(json);
				}
			}
		}
		return result;
	}

}
