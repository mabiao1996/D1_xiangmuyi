package com.bwie.text.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.text.HomeActivity;
import com.bwie.text.R;
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
    }
}
