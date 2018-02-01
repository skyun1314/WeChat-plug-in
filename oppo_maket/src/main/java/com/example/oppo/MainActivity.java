package com.example.oppo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.MyLog;
import com.oppo.MySerialize;
import com.oppo.cdo.card.domain.dto.BannerDto;
import com.oppo.cdo.card.domain.dto.c;
import com.oppo.cdo.card.domain.dto.xt;
import com.oppo.cdo.common.domain.dto.RequiredWrapDto;
import com.oppo.cdo.common.domain.dto.ResourceDto;
import com.oppo.lr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends Activity {




    static String splash = "https://istore.oppomobile.com/common/v1/ad/splash";;//首页内容
    static String home_url = "https://istore.oppomobile.com/card/store/v1/home?size=10&start=0";//首页内容
    static String config_url = "https://istore.oppomobile.com/common/v1/config?imei=359125053640852&model=AOSP+on+HammerHead&osVersion=19&romVersion=0";//config
    static String open_required = "https://istore.oppomobile.com/card/store/v1/open-required";//open_required
    static String struct_url = "https://istore.oppomobile.com/card/store/v1/struct";//struct_url
    static String float_url = "https://istore.oppomobile.com/common/v1/float?token=-1";//float_url
    static String update_check = "https://istore.oppomobile.com/update/v1/check";//update_check
    static String upgrade="https://api.cdo.oppomobile.com/whoops/v1/upgrade";
    static String appevent="http://sopor.game.oppomobile.com/soporcollect/appevent/v1/event";
    static String GetSDKConfig="http://c.stat.nearme.com.cn:10014/GetSDKConfig";
    static String sdkConfig="http://c.stat.nearme.com.cn:8090/sdkConfig.json";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<c> cs=new ArrayList<>();
        c c = new c();
        c.a =new HashMap<>();
            c.a    .put("haha","haha");
        c.e =new ArrayList<>();
        BannerDto bannerDto = new BannerDto();

        c.e.add(new c.b(bannerDto,1));
        cs.add(c);
        cs.add(c);
        cs.add(c);

        MyLog.ShowLog1(cs);

        new xt(10, "100", 10, new xt.xs() {
            @Override
            public List<c> a() {
                return null;
            }
        }).a();


        haha.e = "content://" + getPackageName() + "/" + "pkgMd5";


        network.response_haha response_haha = new network.response_haha() {
            @Override
            public void haha(Response response) {
                try {
                    byte[] bytes = response.body().bytes();
                    RequiredWrapDto deserializer = MySerialize.deserializer(bytes, RequiredWrapDto.class, RequiredWrapDto.class.newInstance());


                    List<lr> lrList=new ArrayList<>();
                    for (int i = 0; i < deserializer.getRequires().size(); i++) {
                        ResourceDto resourceDto = deserializer.getRequires().get(i);
                        lr  lrVar = new lr();
                        lrVar.a("i", -1, -1, -1, -1, resourceDto.getVerId(), resourceDto.getCatLev3(), i, resourceDto.getAdId(), resourceDto.getAdPos(), resourceDto.getAdContent(), resourceDto.getSrcKey());
                        lrList.add(lrVar);
                        haha.a(lrList);
                    }
                    
                    


                    Log.i("wodelog", "deSerializerResult:" + deserializer.getRequires().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("wodelog", "deSerializerResult:" + e.toString());
                }

            }
        };
        Log.e("wodelog",response_haha.getClass().getName());
        network.okhttp_get(haha.e(open_required),response_haha );





        // network.okhttp_post(haha.ads_header());
       // network.okhttp_post(haha.update_check_header());


      //  network.okhttp_post(haha.upgrade_header());
       // network.okhttp_post(haha.appevent_header());
       // network.okhttp_post(haha.GetSDKConfig_header());



     //   haha.haha(2);

    }





}
