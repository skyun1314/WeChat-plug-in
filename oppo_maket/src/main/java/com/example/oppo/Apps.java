package com.example.oppo;

/**
 * Created by zk on 2018/2/28.
 */

public class Apps {


    String versionName;
    String packageName;
    long versionCode;
    String md5;
    // com.example.oppo--f9f2efb25479aeb50343c0fd71eba7d6--1--1.0
    Apps(String packageName,
         String md5,
         String versionCode,
            String versionName


         ) {
        this.versionCode = Long.parseLong(versionCode);
        this.packageName = packageName;
        this.versionName = versionName;
        this.md5 = md5;

    }


}
