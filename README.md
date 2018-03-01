# IpsmapSDK-Android

[![license](https://img.shields.io/hexpm/l/plug.svg)](https://raw.githubusercontent.com/typ0520/fastdex/master/LICENSE)
[![Download](https://api.bintray.com/packages/xun/maven/com.ipsmap.robot/images/download.svg) ](https://bintray.com/xun/maven/com.ipsmap.robot/_latestVersion)
[![API](https://img.shields.io/badge/API-18%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=18)
[![Contact](https://img.shields.io/badge/Author-IpsMap-orange.svg?style=flat)](http://ipsmap.com)

IpsmapSDK-Android 是一套基于 Android 4.3 及以上版本的室内地图应用程序开发接口，供开发者在自己的Android应用中加入室内地图相关的功能，包括：地图显示（多楼层、多栋楼）、室内导航、模拟导航等功能。

## 获取验证码
请联系dev@ipsmap.com，提供相关资料获取开发者读取地图和语音的权限

## 添加依赖

```
compile ('com.ipsmap:ipsmap-robot:0.0.2.8, {
        exclude group: 'com.android.support'
    })

注意支持的cpu版本，必须在gradle中设置
ndk {
    // 设置支持的 SO 库构架，一般而言，取你所有的库支持的构架的`交集`。
    abiFilters 'armeabi','armabi-v7a'
}

```

## 加入权限
```

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_LOGS" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

```

## 使用

外部app 调用 IpsRobot

app 下载地址
http://ipsmap.oss-cn-shanghai.aliyuncs.com/android%E6%9B%B4%E6%96%B0%E5%8C%85/robot/app-release.apk
``` 
Intent intent = new Intent();
//第一种方式
ComponentName cn = new ComponentName("com.daoyixun.ipsrobot", "com.daoyixun.ipsrobot.MainActivity");
try {
    intent.setComponent(cn);
    intent.setAction("android.intent.action.MAIN");
    //需要传入参数,可以传入参数,不需要可以不传入
     //String id = "ddK075hevW";
    //intent.putExtra("target_id", id);
    startActivity(intent);
} catch (Exception e) {
    Toast.makeText(getBaseContext(),"没有安装道一循",Toast.LENGTH_SHORT).show();
    //TODO  可以在这里提示用户没有安装应用或找不到指定Activity，或者是做其他的操作
}
                
```
sdk 使用方法,
初始化
在Application 的onCreate 方法中进行初始化
``` 
IpsMapRobotSDK.init(new IpsMapRobotSDK.Configuration.Builder(context)
    .debug(true)
    //预览模式下不需要激活码
//                .preview()
    .build());
                
```

启动地图方式1(建议) 
```
IpsMapRobotSDK.openIpsMapActivity(getBaseContext());

```

启动地图方式2,(携带地图targetId)(建议) 

```
IpsMapRobotSDK.openIpsMapActivity(getBaseContext(),targetId);

```

启动地图方式3,(自定义IpsmapRobotFragment显示位置)
```
首先需要获取
Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET
动态权限申请(可以参考demo),然后去打开地图的IpsmapRobotFragment
ipsmapTVFragment = IpsmapRobotFragment.getInstance();
getSupportFragmentManager().beginTransaction()
        .add(R.id.fl_content, ipsmapTVFragment, "ipsmap")
        .commit();


```
启动地图方式4,(自定义IpsmapRobotFragment显示位置,并且携带targetId参数) 

```
ipsmapTVFragment=IpsmapRobotFragment.getInstance(targetId);
getSupportFragmentManager().beginTransaction().add(com.daoyixun.robot.R.id.fl_content,ipsmapTVFragment,"ipsmap").commit();
```
地图显示的时候,传递id,直接跳转到目的地 (自定义IpsmapRobotFragment显示位置,并且携带targetId参数) 
```
                if (ipsmapTVFragment!= null){
                    targetId = "ddK075hevW";
                    ipsmapTVFragment.queryLRDataByVocabularyId(targetId);
                }
```


在激活码提示框出来后 ,可以 通过api 直接设置秘钥 
```
                if (ipsmapTVFragment != null){
                    ipsmapTVFragment.setIpsmapKey("svQULnJTqz");
                }
```


可以通过 显示和隐藏父布局控制 fragement 的显示
```
            if (ipsmapTVFragment != null){
                    if (flContent.getVisibility() ==View.VISIBLE ){
                        flContent.setVisibility(View.GONE);
                    }else {
                        flContent.setVisibility(View.VISIBLE);
                    }

                }
```
地图显示的时候设置 搜索 的显示 和隐藏 默认 是显示的
```
                if (ipsmapTVFragment!= null){
                        ipsmapTVFragment.setSearchHintOrShow(false);
                }
```

地图显示的时候设置 home 回退键 的显示 和隐藏 默认 是显示的
```
                if (ipsmapTVFragment!= null){
                        ipsmapTVFragment.setHomeButtonShowOrHide(false);
                }
```
地图加载完毕的回调,在回调用控制父窗体的显示和隐藏
```
                ipsmapTVFragment.registerLoadMapListener(new LoadMapListener() {
                    @Override
                    public void loadMapSuccess(boolean success) {
                        L.e("dddd","load map "+ success);

//                sPkHX4LIik
//                T.showShort("load map "+ success);
                    }
                });
```
秘钥接口的显示回调,在这个里面进行设置秘钥,仅仅调用一次就好,秘钥缓存需要进入界面两次
```
                ipsmapTVFragment.registerInputKeyDialogListener(new InputKeyDialogListener() {
                    @Override
                    public void inputKeyDialog() {
                        ipsmapTVFragment.setIpsmapKey("sPkHX4LIik");
                        L.e("dddd","dialog is show  ");
                    }
                });
```
隐藏地图加载dialog 默认是显示，在地图没有加载完成，不要点击右侧搜索

```
ipsmapTVFragment.setMapLoadingDialogHideOrShow(false);

```
如果使用自定义自定义IpsmapRobotFragment显示位置,注意activity 结束时调用 ,不要为了隐藏界面 调用这个方法

```
重写一下
@Override
protected void onDestroy() {
    if (ipsmapTVFragment != null){
        ipsmapTVFragment.onDestroy();
    }
    // 注意要在  super.onDestroy(); 前调用,否则会报错
    super.onDestroy();
}

```




## 混淆
```
-keep public class com.sails.engine.patterns.IconPatterns
```
## 对接完成
```
出现激活码弹框,程序不崩溃和有冲突
```



## FAQ
1.0
![](/pic/7991511168017_.pic.jpg)
![](/pic/8021511168507_.pic.jpg)
出现上面的类似xml资源文件缺失的情况:
两种解决方案:
1. 在通过gradle 引用是加入exclude group: 'com.android.support' ,并且自己加入compile 'com.android.support:appcompat-v7:版本号'
建议方式.建议版本号25.3.1
2. 修改项目的support 支持和  compile 'com.android.support:appcompat-v7:25.3.1' 版本号一致

2.0 
```
app如果使用了okhttp ,glide ...出现第三发开源库 冲突
两种解决方案:
1.通过  exclude group: "com.squareup.okhttp3" 方式处理
然后保留项目的okhttp和glide 
2.保持和sdk的一致引入的第三方库版本号一致.否则有可能出现冲突
```
```
"glide"             : "com.github.bumptech.glide:glide:3.7.0",
"okhttp"            : "com.squareup.okhttp3:okhttp:3.8.0",
"gson"              : "com.google.code.gson:gson:2.8.2",
 ```        


 3.0
 

```


    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
            flatDir {
                dirs 'libs'
            }
        }
    }
    
    compileOptions {
         sourceCompatibility JavaVersion.VERSION_1_8
         targetCompatibility JavaVersion.VERSION_1_8
     }
```

4.0

![](/pic/3181517275554_.pic_hd.jpg) 


```
方法一:加入 依赖
compile 'com.android.support:appcompat-v7:25.3.1'
compile 'com.android.support:design:25.3.1'
方法二:这样 引入
 compile 'com.ipsmap:ipsmap-robot:0.0.1.7'
```

