package com.example.oppo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.oppo.cdo.card.domain.dto.c;
import com.oppo.cdo.common.domain.dto.ResourceDto;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;

public class MainActivity extends Activity {

    String  down_url=null;
    static String splash = "https://istore.oppomobile.com/common/v1/ad/splash";
    ;//首页内容
    static String home_url = "https://istore.oppomobile.com/card/store/v1/home?size=10&start=0";//首页内容

    static String open_required = "https://istore.oppomobile.com/card/store/v1/open-required";//open_required
    static String struct_url = "https://istore.oppomobile.com/card/store/v1/struct";//struct_url
    static String float_url = "https://istore.oppomobile.com/common/v1/float?token=-1";//float_url
    static String update_check = "https://istore.oppomobile.com/update/v1/check";//update_check
    static String upgrade = "https://api.cdo.oppomobile.com/whoops/v1/upgrade";
    static String appevent = "http://sopor.game.oppomobile.com/soporcollect/appevent/v1/event";
    static String GetSDKConfig = "http://c.stat.nearme.com.cn:10014/GetSDKConfig";
    static String sdkConfig = "http://c.stat.nearme.com.cn:8090/sdkConfig.json";
    static String strategy = "http://data.ads.oppomobile.com/proxy/strategy/";
    static String set2 = "http://data.ads.oppomobile.com/upload/set2";
    static String kv_stat ="http://kv.stat.nearme.com.cn/stat/event";
    static String generate_204 ="http://conn1.oppomobile.com/generate_204";
    static String ClientPageVisit ="http://i.stat.nearme.com.cn:10018/statistics/ClientPageVisit";
    static String search ="https://istore.oppomobile.com/search/v1/home";
    static String force_app ="https://istore.oppomobile.com/common/v1/force-app?colorOs=0&phoneVersion=0";
    static String ClientStartUpload ="http://i.stat.nearme.com.cn:10018/statistics/ClientStartUpload";


    public static boolean isFisrt=true;

    public List<ResourceDto> search_resourceDto=new ArrayList<>();



    private String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }





    public static String getRomName() {
        String COLOR_OS_ROM_NAME = null;
        if (TextUtils.isEmpty(COLOR_OS_ROM_NAME)) {
            try {
                Class cls = Class.forName("android.os.SystemProperties");
                COLOR_OS_ROM_NAME = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.build.display.id", ""});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return COLOR_OS_ROM_NAME;
    }

    /**
     *
     */
    List<c> list_c = new ArrayList<>();
   public  static  List listapps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Map<String, Object> map =new HashMap<String, Object>();
        map.put("Charset","UTF-8");
        map.put("Content-Encoding","gzip");
        map.put("Accept-Encoding","gzip");
        map.put("User-Agent","Dalvik/1.6.0 (Linux; U; Android 4.4.4; AOSP on HammerHead Build/KTU84P)");








        FormBody build = new FormBody.Builder()
                .add("channelCode", 15+"")
                .add("cid", "5c0a5496119b053b9dd0c4ca14a6fdb8")
                .add("clientId", "ffffffff-e3a8-946a-1d17-6294213f44dd")
                .add("clientSign", 2+"")
                .add("clientVersion", 5300+"")
                .add("osVersion", "4.4.4")
                .add("phoneType",  "AOSP+on+HammerHead" )
                .add("sign","92d24826558e89637115c5ace569e479")
                .add("ticket", "04b433c86315e6fc6baa29ce433808f0")
                .add("ttkn","2018.4.9++14%3A50" )
                .add("userId", "305223")
                .build();




        String parm="channelCode=15&cid=5c0a5496119b053b9dd0c4ca14a6fdb8&clientId=ffffffff-e3a8-946a-1d17-6294213f44dd&clientSign=2&clientVersion=5300&osVersion=4.4.4&phoneType=AOSP+on+HammerHead&sign=92d24826558e89637115c5ace569e479&ticket=04b433c86315e6fc6baa29ce433808f0&ttkn=2018.4.9++14%3A50&userId=305223";




        map.put("postxx",parm);
        map.put("MediaType","application/x-www-form-urlencoded; charset=UTF-8");//这是 Content-Type  请求头
        map.put("Url","http://www.teyuntong.cn//plat/plat/user/simulatedLogin");

        MyNetwork.okhttp_post(map);






/*        Log.e("wodelog", Build.ID);
        Log.e("wodelog", Build.BRAND);
        Log.e("wodelog", Build.MODEL);
        Log.e("wodelog", getRomName());*/

        int page = 1;

String token="e99c0357160e9954bd511c91995663df";



/*
e99c0357160e9954bd511c91995663df#page=2#timestamp=1522335057#token=e99c0357160e9954bd511c91995663df#version=1.3.6#1522335057


        Connection: keep-alive
        Content-Length: 123*/
/*
        for (int i = 1; i < 10; i++) {
            Map<String, Object> response_interface2 = new HashMap<>();
      //  response_interface2.put("MediaType", "text");
        response_interface2.put("Url", "https://api.zyb56.com/index.php?s=app/ShipperCar/get_car_list");
   *//*     response_interface2.put("Host", "api.zyb56.com");
        response_interface2.put("Connection", "keep-alive");
        response_interface2.put("Content-Length", "123");
        response_interface2.put("Accept", "application/json");
      response_interface2.put("Origin", "file://");
        response_interface2.put("X-Requested-With", "XMLHttpRequest");
        response_interface2.put("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.4; AOSP on HammerHead Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 Html5Plus/1.0");
        response_interface2.put("Content-Type", "application/x-www-form-urlencoded");
       // response_interface2.put("Accept-Encoding", "gzip,deflate");
       response_interface2.put("Accept-Language", "zh-CN,en-US;q=0.8");
        response_interface2.put("Cookie", "PHPSESSID=lr74ddhh0ftod0gnqf2lthlti7");*//*









                page=i;
            Log.e("wodelog","page:"+page);
                long timestamp = System.currentTimeMillis()/1000;
                String signature=MD5(token+"#page="+page+"#timestamp="+timestamp+"#token="+token+"#version=1.3.6#"+timestamp).toLowerCase();
            //   response_interface2.put("postxx", ("page="+page+"&token="+token+"&version=1.3.6&timestamp="+timestamp+"&signature="+signature+"").toString());

            FormBody build = new FormBody.Builder()
                    .add("page", page+"")
                    .add("token", token)
                    .add("version", "1.3.6")
                    .add("timestamp", timestamp+"")
                    .add("signature", signature)
                    .build();

            response_interface2.put("postxx",build);



            MyNetwork.okhttp_post(response_interface2);
            Log.e("wodelog"," " );
        }*/
    }
/*
        listapps=new ArrayList();
        try {
            InputStream instream = getAssets().open("haha.txt");
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while (( line = buffreader.readLine()) != null) {
                Log.e("wodelog","讀文件"+line);

                String[] split = line.split("--");

                Apps apps = new Apps(split[0], split[1], split[2], split[3]);
                listapps.add(apps);

            }
            instream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }




        new Thread(new Runnable() {
            @Override
            public void run() {



              *//*  final shebei shebei1=new shebei(
                        "Android",
                        "0",OppoNetworks.getCarrierName(OppoNetworks.netname.中国电信),
                        "359125053640852",    "AOSP on HammerHead",1920,1080,
                        "aosp_hammerhead-userdebug 4.4.4 KTU84P eng.zk.20170810.152219 test-keys",
                        "4.4.4",19, "WIFI","wify","KTU84P"
                );*//*



                final shebei   shebei=new shebei(
                        "vivo",
                        "0",OppoNetworks.getCarrierName(OppoNetworks.netname.中国电信),
                        "865044034318230",    "vivo X9s Plus",1920,1080,
                        "NMF26F release-keys",
                        "7.1.1", 25, "WIFI","wify","NMF26F","连连看","好玩连连看"
                );



                final MyNetwork.response_interface response_interface2 = new MyNetwork.response_interface() {

                    @Override
                    public Object do_response(Response response) {
                        try {

                            MediaType mediaType = response.body().contentType();
                            String content= response.body().string();
                            Log.e("wodelog", content);
                             response = response.newBuilder()
                                    .body(ResponseBody.create(mediaType, content))
                                    .build();


                            OppoNetworks.b(shebei,response, null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                final MyNetwork.response_interface response_interface5 = new MyNetwork.response_interface() {

                    @Override
                    public Object do_response(Response response) {
                        try {


                             down_url = response.request().url().toString();

                            MediaType mediaType = response.body().contentType();
                            String content= response.body().string();
                            Log.e("wodelog", content);
                             response = response.newBuilder()
                                    .body(ResponseBody.create(mediaType, content))
                                    .build();


                            OppoNetworks.b(shebei,response, null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                MyNetwork.response_interface response_interface4 = new MyNetwork.response_interface() {

                    @Override
                    public Object do_response(Response response) {
                        try {
                            byte[] bytes = response.body().bytes();
                            OppoNetworks.b(shebei,response, null);

                            String xx=new String(bytes);

                            ViewLayerWrapDto deserializer1 = MySerialize.deserializer(bytes, ViewLayerWrapDto.class, ViewLayerWrapDto.class.newInstance());
                            List<Object> cardDtos1 = deserializer1.getCards();
                            for (int i = 0; i < cardDtos1.size(); i++) {


                                if (cardDtos1.get(i) instanceof SearchCardDto) {

                                }else{
                                    List<ResourceDto> apps = ((AppListCardDto) cardDtos1.get(i)).getApps();
                                    search_resourceDto.addAll(apps);
                                    if (apps != null) {
                                        for (int j = 0; j < apps.size(); j++) {
                                            ResourceDto resourceDto = apps.get(j);

                                            if(resourceDto.getAppName().equals(shebei.app_name)){
                                                long appId = resourceDto.getAppId();
                                                MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.click_appid(appId)), response_interface2);
                                                MyNetwork.okhttp_post(OppoNetworks.downloadInfo(j,shebei,resourceDto,null,"7000","main","10003"));
                                                MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.down_app(resourceDto.getUrl())), response_interface5);

                                                MyNetwork.okhttp_post(OppoNetworks.downloadInfo(j,shebei,resourceDto,OppoNetworks.downloading(down_url,"142",null),"7005","Thread-DownloadCallback","10003"));
                                                MyNetwork.okhttp_post(OppoNetworks.downloadInfo(j,shebei,resourceDto,OppoNetworks.downloading(down_url,null,null),"7009","Thread-DownloadCallback","10003"));
                                                MyNetwork.okhttp_post(OppoNetworks.downloadInfo(j,shebei,resourceDto,OppoNetworks.downloading(down_url,null,"installPackage exception:-10000"),"7011","Thread-DownloadCallback","10003"));


                                            }


                                        }
                                    }
                                }




                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };


                MyNetwork.response_interface splash_get = new MyNetwork.response_interface() {

                    @Override
                    public Object do_response(Response response) {
                        try {
                            String string = response.body().string();
                            JSONObject jsonObject=new JSONObject(string);
                            Log.e("wodelog",jsonObject.toString());
                            JSONObject ad =jsonObject.getJSONObject("ad");
                            String transparent = ad.getString("transparent");
                            JSONObject transparent_=new JSONObject(transparent);
                            Log.e("wodelog",transparent_.toString());

                            String adId = transparent_.getString("adId");
                            String adType = transparent_.getString("adType");
                            String price = transparent_.getString("price");
                            String grade = transparent_.getString("grade");
                            String adOwner = transparent_.getString("adOwner");
                            String planId = transparent_.getString("planId");
                            String parEvtId = transparent_.getString("parEvtId");
                            String displayTime = transparent_.getString("displayTime");
                            String creativeId = transparent_.getString("creativeId");

                            String str = OppoNetworks.set0(shebei,adId, adType, price, grade, adOwner, planId, parEvtId, displayTime, creativeId);

                            //String str = "[{\"headers\":{\"evtTime\":1519783412234,\"dataType\":\"bd-expose\"},\"body\":\"865044034318230\t\tvivo X9s Plus\tNMF26F RELEASE-KEYS\t\t7.1.1\t141\t1\t2\t10003\t5.2.1\tWIFI\t1519783412\t\t1\tbd-expose\t0\t1\t0,,,40,,,,,1519783412222,,16212,21,0,1,1000005155,,,,,734,ba25219bbbceafd11546c75c35e67427,,,,1519783411000,,8423\"}]";
                            byte[] compress = MyNetwork.GZIPUtils.compress(str);



                            MyNetwork.okhttp_post(OppoNetworks.set0_header(shebei,compress));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                      ///  OppoNetworks.b(response, null);
                        return null;
                    }
                };


                MyNetwork.response_interface response_interface1 = new MyNetwork.response_interface() {//首页数据解析

                    *//**
                     * @param response
                     *//*
                    @Override
                    public Object do_response(Response response) {

                        JSONObject jsonObject = null;

                        try {
                            byte[] bytes = response.body().bytes();
                            OppoNetworks.b(shebei,response, null);
                            ViewLayerWrapDto deserializer = MySerialize.deserializer(bytes, ViewLayerWrapDto.class, ViewLayerWrapDto.class.newInstance());

                            List<Object> cardDtos = deserializer.getCards();
                            final c c = new c(null, 0, 0, 0);

                            for (int i = 0; i < cardDtos.size(); i++) {

                                if (cardDtos.get(i) instanceof NavCardDto) {
                                    List<BannerDto> banners = ((NavCardDto) cardDtos.get(i)).getBanners();


                                    if (banners != null) {
                                        for (int j = 0; j < banners.size(); j++) {
                                            c.e.add(new c.b(banners.get(j), j));
                                        }
                                    }
                                } else if (cardDtos.get(i) instanceof AppListCardDto) {
                                    List<ResourceDto> apps = ((AppListCardDto) cardDtos.get(i)).getApps();


                                    if (apps != null) {
                                        for (int j = 0; j < apps.size(); j++) {
                                            c.f.add(new c.a(apps.get(j), j));
                                        }
                                    }

                                } else if (cardDtos.get(i) instanceof BannerCardDto) {
                                    List<ResourceDto> apps = ((BannerCardDto) cardDtos.get(i)).getApps();

                                    if (apps != null) {
                                        for (int j = 0; j < apps.size(); j++) {
                                            c.f.add(new c.a(apps.get(j), j));
                                        }
                                    }


                                    List<BannerDto> banners = ((BannerCardDto) cardDtos.get(i)).getBanners();
                                    if (banners != null) {
                                        for (int j = 0; j < banners.size(); j++) {
                                            c.e.add(new c.b(banners.get(j), j));
                                        }
                                    }

                                }

                            }


                            list_c.add(c);
                            List<lr> list_lr = new xt(100, "10", 100, new xt.xs() {
                                @Override
                                public List<c> a() {

                                    return list_c;
                                }
                            }).a();

                            jsonObject = OppoNetworks.analysis_home_page(shebei,list_lr);


                            Log.e("wodelog", "byte:" + deserializer.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("wodelog", "Exception:" + e.toString());
                        }
                        return jsonObject;
                    }
                };
                //
                String kv_stat_request;
                if(isFisrt){

                    MyNetwork.okhttp_post(OppoNetworks.ads_header(shebei));

                    OppoNetworks.stat_event(shebei,4);

                    kv_stat_request = OppoNetworks.packBaseEvent(shebei);
                    MyNetwork.okhttp_post(OppoNetworks.kv_stat_header(kv_stat_request.getBytes()));
                    MyNetwork.okhttp_post(OppoNetworks.appevent_header(shebei));
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,struct_url), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,open_required), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.getconfig(shebei)), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,force_app), response_interface2);
                    MyNetwork.okhttp_post(OppoNetworks.upgrade_header(shebei));
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,float_url), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.generate_204_header(), response_interface2);
                    //
                    OppoNetworks.stat_event(shebei,1);
                    OppoNetworks.stat_event(shebei,2);
                    OppoNetworks.stat_event(shebei,3);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,home_url), response_interface1);
                    MyNetwork.okhttp_post(OppoNetworks.update_check_header(shebei));
                    MyNetwork.okhttp_post(OppoNetworks.GetSDKConfig_header(shebei));
                    MyNetwork.okhttp_get(OppoNetworks.setsdkConfigHeader(sdkConfig), null);



*//*
                    MyNetwork.okhttp_post(OppoNetworks.ClientPageVisit_header(shebei));
                    MyNetwork.okhttp_post(OppoNetworks.ClientStartUpload_header(shebei));*//*



                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,search), response_interface2);

        *//*            MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword("W")), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword("WW")), response_interface2);
                    *//*
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword(shebei.key_word)), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword_ok(shebei.key_word)), response_interface4);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword_ok(shebei.key_word)), response_interface4);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword_ok(shebei.key_word)), response_interface4);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.serch_keyword_ok(shebei.key_word)), response_interface4);

                    kv_stat_request = OppoNetworks.packBaseEvent(shebei);
                    MyNetwork.okhttp_post(OppoNetworks.kv_stat_header(kv_stat_request.getBytes()));

                }else{
                    MyNetwork.okhttp_get(OppoNetworks.generate_204_header(), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.splash_header(shebei), splash_get);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,OppoNetworks.getconfig(shebei)), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,struct_url), response_interface2);
                    MyNetwork.okhttp_post(OppoNetworks.upgrade_header(shebei));
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,float_url), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.generate_204_header(), response_interface2);
                    MyNetwork.okhttp_get(OppoNetworks.setRequestHeader(shebei,home_url), response_interface1);
                    MyNetwork.okhttp_post(OppoNetworks.update_check_header(shebei));
                    MyNetwork.okhttp_post(OppoNetworks.appevent_header(shebei));


                    OppoNetworks.stat_event(shebei,1);
                    OppoNetworks.stat_event(shebei,2);
                    OppoNetworks.stat_event(shebei,3);

                    kv_stat_request = OppoNetworks.packBaseEvent(shebei);
                    MyNetwork.okhttp_post(OppoNetworks.kv_stat_header(kv_stat_request.getBytes()));
                }



            }
        }).start();;






    }*/


}
