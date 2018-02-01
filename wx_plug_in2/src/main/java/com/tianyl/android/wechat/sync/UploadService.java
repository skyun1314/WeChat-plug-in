package com.tianyl.android.wechat.sync;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tianyl.android.wechat.util.FileUtil;
import com.tianyl.android.wechat.util.LogUtil;
import com.tianyl.android.wechat.util.NetUtil;
import com.tianyl.android.wechat.util.StringUtil;

public class UploadService {

	public static void main(String[] args) {
		update();
	}

	private static final Set<String> IGNORE_PUBLISHER_USERNAMES = new HashSet<>();

    static {
        IGNORE_PUBLISHER_USERNAMES.addAll(Arrays.asList("gh_4e3ae6589322",
                "cmbchina-95555","gh_98a49c9dbbc0","gh_ccb9eb7900f6","gh_302f6e40c75f","gh_a19f4d5e89e5",
                "wxzhifu","mphelper"));
    }

	public static void update() {
		List<File> jsonFiles = findJsonFiles();
		for (File file : jsonFiles) {
			String jsonStr = FileUtil.read(file);
			List<AppMsg> msgs = null;
			try {
				JSONObject json = JSONObject.parseObject(jsonStr);
				msgs = buildMsg(json);
			} catch (JSONException e) {
				e.printStackTrace();
				// 记录日志，删除文件
				LogUtil.log("json格式错误：" + e.getMessage());
				deleteFile(file);
				continue;
			}
			boolean sendFlag = sendToServer(msgs);
			if (sendFlag) {
				deleteFile(file);
			}
		}
	}

	private static boolean sendToServer(List<AppMsg> msgs) {
        if (msgs == null || msgs.size() == 0){
            return true;
        }
		String url = "https://pisp.x1y2z3.com/api/wx/article/save";
		String result = NetUtil.post(url, JSONArray.toJSONString(msgs));
		try {
			JSONObject json = JSONObject.parseObject(result);
			return json.getBooleanValue("result");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void deleteFile(File file) {
        if(file.exists()){
            file.delete();
        }
	}

	private static List<AppMsg> buildMsg(JSONObject json) throws JSONException {
		List<AppMsg> result = new ArrayList<>();
		AppMsg msg = new AppMsg();
		msg.setAppName(json.getString(".msg.appinfo.appname"));
		msg.setTitle(json.getString(".msg.appmsg.title"));
		msg.setDigest(json.getString(".msg.appmsg.des"));
		msg.setPublisherUsername(json.getString(".msg.appmsg.mmreader.publisher.username"));
		msg.setPublishTime(getTime(json.getLongValue(".msg.appmsg.mmreader.category.item.pub_time")));
		msg.setType(json.getString(".msg.appmsg.type"));
		msg.setUrl(json.getString(".msg.appmsg.url"));

		if (msg.getType().equals("6")) {// 收到文件
			return result;
		}
		if (msg.getPublisherUsername() == null || msg.getPublisherUsername().equals("")) {// 聊天中的，insert时想办法不记录
			return result;
		}
		if (msg.getPublisherUsername().equals("exmail_tool")) {// 邮件，insert时想办法不记录
			return result;
		}

        if (!IGNORE_PUBLISHER_USERNAMES.contains(msg.getPublisherUsername())) {
            result.add(msg);
        }
		for (int i=1;i<3;i++){
			String url = json.getString(".msg.appmsg.mmreader.category.item" + i + ".url");
			if(StringUtil.isNotBlank(url)){
				AppMsg msgTemp = new AppMsg();
				msgTemp.setAppName(json.getString(".msg.appinfo.appname"));
				msgTemp.setTitle(json.getString(".msg.appmsg.mmreader.category.item" + i + ".title"));
				msgTemp.setDigest(json.getString(".msg.appmsg.mmreader.category.item" + i + ".digest"));
				msgTemp.setPublisherUsername(json.getString(".msg.appmsg.mmreader.publisher.username"));
				msgTemp.setPublishTime(getTime(json.getLongValue(".msg.appmsg.mmreader.category.item" + i + ".pub_time")));
				msgTemp.setType(json.getString(".msg.appmsg.type"));
				msgTemp.setUrl(url);
                if (!IGNORE_PUBLISHER_USERNAMES.contains(msgTemp.getPublisherUsername())){
                    result.add(msgTemp);
                }
			}
		}
		return result;
	}

	private static String getTime(long time) {
		if (time == 0) {
			return "";
		}
		Date date = new Date(time * 1000);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(date);
	}

	public static List<File> findJsonFiles() {
		String parentDir = FileUtil.getBathPath() + "json/";
        if(!new File(parentDir).exists()){
            new File(parentDir).mkdirs();
        }
		List<File> files = new ArrayList<>();
		for (File file : new File(parentDir).listFiles()) {
			if (file.getName().endsWith("json")) {
				files.add(file);
			}
		}
		return files;
	}

	public static void uploadDelMsg(){
        File file = new File(FileUtil.getBathPath() + "sql/delete_message.sql");
        if(!file.exists()){
            return;
        }
        JSONArray delMsgs = DeleteMessageParse.parse(file);
        if (sendDelMsgToServer(delMsgs)) {
            file.renameTo(new File(FileUtil.getBathPath() + "sql/delete_message_" +
                    new SimpleDateFormat("yyyyMMddHHmm",Locale.CHINA).format(new Date()) + ".sql"));
        }
    }

    private static boolean sendDelMsgToServer(JSONArray jsonArray) {
        String url = "https://pisp.x1y2z3.com/api/wx/message/addDeleteMessage";
        String result = NetUtil.post(url, jsonArray.toJSONString());
        try {
            JSONObject json = JSONObject.parseObject(result);
            return json.getBooleanValue("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
