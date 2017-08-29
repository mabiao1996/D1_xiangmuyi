package com.bwie.text.applic;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by mabiao on 2017/8/29.
 */

public class NewsAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        initImageLoader();
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
    private void initXutils() {
        x.Ext.init(this);
    }
}
