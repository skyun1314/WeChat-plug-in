微信6.6.1 xposed模块
====

下载地址
====
https://github.com/skyun1314/WeChat-plug-in/tree/master/wx_plug_in3/wx_plug_in3-release.apk

ScreenShots
====
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/1.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/2.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/3.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/4.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/5.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/6.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/7.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/8.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/9.jpg)

function
==== 

 * 1主动发消息  目前效果:发一条消息能发送出多条自定义消息。最后可以实现给任意好友群发消息
 * 2防撤回  接收到撤回命令拦截，然后给用户提示出谁撤回了消息。我hook了他数据库,直接调用sql命令
 * 3抢红包   我hook了他数据库的插入操作。同理只要是操作数据库的其他事件都能拦截到（比如自动回复）
 * 4骰子作弊 其他人的骰子作弊设置骰子点数的地方都在外面。我这个是仿照fzhang大神的样子,仍骰子的时候设置发几点
 * 5模拟位置  这个是整合了好几个apk的功能。从主界面能加载高德地图设置位置,在发送时时位置，和附近人都生效
 * 6步数最高   这个是反编译别人的apk，直接拿的源码。他是修改的传感器。


Hope
==== 
如果觉得有用，欢迎star
如果使用发现问题，欢迎issue

支持作者
==== 
开发插件占用了我个人大量的私人时间，如果你觉得对你有所帮助，不妨请我喝杯☕️以鼓励我开发出更优秀的插件
 ![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/alipay.jpg)
![image](https://github.com/skyun1314/AesTest/blob/master/screenshots/mm_pay.png)
