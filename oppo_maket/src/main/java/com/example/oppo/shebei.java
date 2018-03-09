package com.example.oppo;

/**
 * Created by zk on 2018/1/3.
 */

public class shebei {


    public String Build_BRAND="Android";//vivo  手机是 vivo
    public String app_version_name = "5.2.1";// 软件的版本 固定值  2.2.5
    public String pkgName = "com.oppo.market";// 软件的包名 固定值  com.szqd.dialer
    public String ssoid = "0";
    public String carrier = OppoNetworks.getCarrierName(OppoNetworks.netname.中国电信);
    public String imei = "359125053640852";// imei

    public String model = "AOSP on HammerHead";// 设备型号

    public int appId = 2;
    public String channel = "1";
    public int sdkVersion = 501;
    public int screen_h = 1920;//屏幕高
    public int screen_w = 1080;//宽

    public String romVersion = "aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys";
    public String androidVersion = "4.4.4";
    public int oSIntVersion = 19;
    public String access = "WIFI";//{UNKNOWN,WIFI,UNKNOWN,2G,3G,4G}
    public String networkID = "wify";

    public String Build_ID = "KTU84P";// android.os.Build.ID:设备版本号。
    public String oak = "23a8ba872e430653";
    public String key_word = "连连看";
    public String app_name = "好玩连连看";

    public shebei( String build_BRAND, String ssoid, String carrier, String imei, String model, int screen_h, int screen_w, String romVersion, String androidVersion,  int oSIntVersion, String access, String networkID, String Build_ID,String key_word,String app_name) {
        Build_BRAND = build_BRAND;

        this.ssoid = ssoid;
        this.carrier = carrier;
        this.imei = imei;
        this.model = model;

        this.screen_h = screen_h;
        this.screen_w = screen_w;
        this.romVersion = romVersion;
        this.androidVersion = androidVersion;
        this.oSIntVersion = oSIntVersion;
        this.access = access;
        this.networkID = networkID;
        this.Build_ID = Build_ID;
        this.key_word = key_word;
        this.app_name = app_name;

    }
}
