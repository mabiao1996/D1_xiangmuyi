package com.bwie.text.applic;

import android.app.Application;
import android.content.Context;

import com.bwie.text.apk.NewsApk;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by mabiao on 2017/8/29.
 */

public class NewsAPP extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        initXutils();
        initImageLoader();
        MobSDK.init(this, NewsApk.APPKEY, NewsApk.APPSECRET);
        mContext=this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    //imageloader
    private void initImageLoader() {
        DisplayImageOptions op=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration con=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(op)
                .build();
        ImageLoader.getInstance().init(con);
    }
//xutils
    private void initXutils(){
        x.Ext.init(this);
    }
}
