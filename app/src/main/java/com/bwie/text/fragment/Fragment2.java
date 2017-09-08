package com.bwie.text.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.bwie.text.FreeActivity;
import com.bwie.text.MainActivity;
import com.bwie.text.R;
import com.bwie.text.linawang.NewLinaWang;

/**
 * Created by mabiao on 2017/8/30.
 */

public class Fragment2 extends Fragment {
    private View view;
    private RelativeLayout rl;
    private RelativeLayout rl_wangluo;
    private SharedPreferences sp;

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
    }


}
