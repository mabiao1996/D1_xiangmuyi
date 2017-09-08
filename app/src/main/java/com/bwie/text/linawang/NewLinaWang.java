package com.bwie.text.linawang;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mabiao on 2017/9/5.
 */

public class NewLinaWang {
    private Context context;


    /**
     * 网络判断
     */
    public void verify(Context context,NetWork netWork) {
        this.context = context;
        //网络连接管理
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络可用对象
        NetworkInfo info= manager.getActiveNetworkInfo();
        //如果可用对象不为空，说明有活动的网络对象
        if(info!=null){
            //判断可用网络对象是否属于手机信号（基站信号）
            if(info.getType()==ConnectivityManager.TYPE_MOBILE){
                //不让加载图片
                netWork.netMobileVisible();
                //判断可用网络对象是否属于wifi信号
            }else if(info.getType()==ConnectivityManager.TYPE_WIFI){
                //加载大图片
                netWork.netWifiVisible();
            }else {
                //没有有效网络
                netWork.netUnVisible();
            }
        }else{
            //没有有效的网络
            netWork.netUnVisible();
        }
    }

    //自定义网络判断回调接口
    public interface NetWork{
        //有wifi信号时的逻辑
        void netWifiVisible();
        //无网络的逻辑
        void netUnVisible();
        //有手机信号时的逻辑
        void netMobileVisible();
    }

}
