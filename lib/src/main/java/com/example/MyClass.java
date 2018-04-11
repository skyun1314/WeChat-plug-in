package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyClass {
    public static String insertDeviceInfo ="http://140.143.140.41:8080/makeUser/Myservlet?cmd=insertinfo&udid=%s&mac=%s&imei=%s";


    public static String sendGetRequest(String getUrl)
    {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try
        {
            URL url = new URL(getUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setAllowUserInteraction(false);
            isr = new InputStreamReader(url.openStream());
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }



    public static <T> List<T> shuffle(List<T> list) {
        int size = list.size();
        Random random = new Random();

        for(int i = 0; i < size; i++) {
            int randomPos = random.nextInt(size);
            T temp = list.get(i);
            list.set(i, list.get(randomPos));
            list.set(randomPos, temp);
          String imei= (String) list.get(i);
            String mac = randomMac4Qemu();
            String serial = randomSerial();
            System.out.println("imei:"+imei+"  mac:"+mac+"  serial:"+serial);

            String format = String.format(insertDeviceInfo, serial, mac, imei);
            String s = sendGetRequest(format);
            System.out.println(s);

        }
        return list;
    }

    private static String SEPARATOR_OF_MAC = ":";
    public static String randomMac4Qemu() {
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x2c),
                String.format("%02x", 0x59),
                String.format("%02x", 0x8a),
                String.format("%02x", random.nextInt(0xff)),
                String.format("%02x", random.nextInt(0xff)),
                String.format("%02x", random.nextInt(0xff))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }


    public static String randomSerial(){
        Random random = new Random();
        String serial="";
        for (int i = 0; i < 16; i++) {

            serial+= String.format("%x", random.nextInt(0xf));
        }

        return serial;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        String code = "35925005000000";
   ;
        String endCode = "35925005999999";

        shuffle(beachIMEI(code,endCode));



       /* String s = genCode("35925005537524");
        System.out.println(s);*/

    }

    /**
     * 批量生成IMEI
     * @param begin
     * @param end
     * @return
     */



    static List<String> beachIMEI(String begin, String end){
        List<String> imeis = new ArrayList<String>();
        try {
            long count = Long.parseLong(end) - Long.parseLong(begin);
            Long currentCode = Long.parseLong(begin);
            String code ;
            for (int i = 0; i <= count; i++) {
                code = currentCode.toString();
                code = genCode(code);
                imeis .add(code);
                currentCode += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imeis;
    }

    /**
     * IMEI 校验码
     * @param code
     * @return
     */
    public static String genCode(String code){
        int total=0,sum1=0,sum2 =0;
        int temp=0;
        char [] chs = code.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            int num = chs[i] - '0';     // ascii to num
            //System.out.println(num);
            /*(1)将奇数位数字相加(从1开始计数)*/
            if (i%2==0) {
                sum1 = sum1 + num;
            }else{
                /*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/
                temp=num * 2 ;
                if (temp < 10) {
                    sum2=sum2+temp;
                }else{
                    sum2 = sum2 + temp + 1 -10;
                }
            }
        }
        total = sum1+sum2;
        /*如果得出的数个位是0则校验位为0,否则为10减去个位数 */
        if (total % 10 ==0) {
            return code+"0";
        }else{
            return  code+(10 - (total %10));
        }

    }

}
