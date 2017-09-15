package com.bwie.text.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.health.PackageHealthStats;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.text.FreeActivity;
import com.bwie.text.MainActivity;
import com.bwie.text.R;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.BanBean;
import com.bwie.text.linawang.NewLinaWang;
import com.umeng.socialize.Config;

import junit.runner.Version;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static android.R.attr.version;

/**
 * Created by mabiao on 2017/8/30.
 */

public class Fragment2 extends Fragment {
    private View view;
    private RelativeLayout rl;
    private RelativeLayout rl_wangluo;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private Callback.Cancelable cancleable;
    private String url="http://125.39.134.47/r/a.gdown.baidu.com/data/wisegame/00a78c4250b8da48/jinritoutiao_607.apk";
    private RelativeLayout rl_ban;
    private TextView tv_panben;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.rightfragment, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp = getActivity().getSharedPreferences("wangluo", Context.MODE_PRIVATE);
        initView();
        initData();
        initLoad();
        initBanBean();

    }
    private void initBanBean() {
      progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "暂停", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancleable.cancel();
            }
        });


    }
    //检查版本，如果版本号大于当前app的版本号，那么我们就下载服务器文件

    /**
     * 下载服务器文件并安装新版本
     */
    private void downloadApk() {
        final RequestParams request=new RequestParams(url);
        request.setAutoResume(true);//设置是否支持断点下载
        request.setCancelFast(true);//设置是否立即取消
        //判断sdcard是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
         //sdcard存在并可用
            request.setSaveFilePath(NewsApk.VERSION_PATH);

        }
        cancleable= x.http().get(request, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                progressDialog.dismiss();
                install(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                progressDialog.show();

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
              if(isDownloading){
                  progressDialog.setMax((int) total);
                  progressDialog.setProgress((int) current);
                  progressDialog.setMessage("下载进度");
              }
            }
        });
    }

    private void install(File result) {
       //调用系统安装器
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+result.getAbsolutePath()),"application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void initLoad() {
        new NewLinaWang().verify(getActivity(), new NewLinaWang.NetWork() {
            @Override
            public void netWifiVisible() {
                //有wifi时候加载大图
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("wifi2",true);
                edit.commit();
            }

            @Override
            public void netUnVisible() {
                //没有网络加载本地缓存
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("wifi2",false);
                edit.commit();
            }

            @Override
            public void netMobileVisible() {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("wifi2",true);
                edit.commit();

            }
        });

    }

    private void initData() {
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),FreeActivity.class);
                startActivity(intent);
            }
        });
        rl_wangluo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad=new AlertDialog.Builder(getActivity())
                        .setSingleChoiceItems(new String[]{"最佳效果(下载大图)", "极省流量(不下载图)"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               if(i==0){
                                   //连接wifi加载大图
                                   SharedPreferences.Editor edit = sp.edit();
                                   edit.putBoolean("wifi",true);
                                   edit.commit();
                               }else if(i==1){
                                   //无图片
                                   SharedPreferences.Editor edit = sp.edit();
                                   edit.putBoolean("wifi",false);
                                   edit.commit();
                               }
                               dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

    }

    private void initView() {
        rl=view.findViewById(R.id.rl);
        rl_wangluo=view.findViewById(R.id.rl_wangluo);
        rl_ban = view.findViewById(R.id.rl_ban);
        tv_panben = view.findViewById(R.id.tv_panben);
        tv_panben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第一步：拿到本app的版本号
                PackageManager manager =getActivity().getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(getActivity().getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                int versionCode = info.versionCode;


                //第二步：进行网络请求，请求版本对象信息（解析json字符串转化为本地version对象）
                BanBean ban=new BanBean();
                ban.setUrl(url);

                //第三步：比较，如果服务器版本号大于本app版本号，那么下载文件，下载完成后，进行安装
                if (versionCode < ban.getVersionCode()) {
                    File file = new File(NewsApk.VERSION_PATH);
//            if (file != null && file.exists()) {
//            //走安装逻辑
//            install(file);
//            } else {
                    downloadApk();
//            }
                }
            }
        });




}

}
