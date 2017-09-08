package com.bwie.text.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.text.HomeActivity;
import com.bwie.text.MainActivity;
import com.bwie.text.R;
import com.example.city_picker.CityListActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

/**
 * Created by mabiao on 2017/8/30.
 */

public class Fragment1 extends Fragment {
    private View view;
    private TextView tv_log;
    private ImageView img_qq;
    private LinearLayout ll_yejian;
    private ImageView img_yejian;
    private TextView tv_yejian;
    private RelativeLayout rl_fankui;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
          view=  inflater.inflate(R.layout.leftfragment,container,false);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initdianji();
    }

    private void initdianji() {
        ll_yejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //日夜间模式的状态
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                //夜间模式
                if(currentNightMode==Configuration.UI_MODE_NIGHT_YES){
                      //状态换成日间
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }else{
                       //如果是日间模式状态变成夜间
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }

            }
        });
    }

    private void initData() {
        tv_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });


        img_qq.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
    }
});
        rl_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CityListActivity.class);
                startActivity(intent);
            }
        });

    }
    UMAuthListener umAuthListener=new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //授权开始回调
        }
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String pohotoUrl=map.get("iconurl");
            ImageLoader.getInstance().displayImage(pohotoUrl,img_qq);
        }
        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
        }


    };
    private void initView() {
        tv_log = view.findViewById(R.id.tv_log);
        img_qq=view.findViewById(R.id.img_qq);
        ll_yejian = view.findViewById(R.id.ll_yejian);
        img_yejian = view.findViewById(R.id.img_yejian);
        tv_yejian=view.findViewById(R.id.tv_yejian);
        rl_fankui=view.findViewById(R.id.rl_fangui);

    }
}
