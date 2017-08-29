package com.bwie.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.bwie.text.adapter.NewsAdapter;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.Bean;
import com.bwie.text.bean.NewsBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
@ViewInject(R.id.lv)   ListView mlv;
    private List<Bean> list;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initList();
        initData();
    }

    private void initList() {
        list = new ArrayList<>();
    }
    private void initData() {
        RequestParams params=new RequestParams(NewsApk.NEWSURL);
        params.addQueryStringParameter("key",NewsApk.NEWSKEY);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                NewsBean newsBean = gson.fromJson(result, NewsBean.class);
                NewsBean.ResultBean result1 = newsBean.getResult();
                List<NewsBean.ResultBean.DataBean> data = result1.getData();
                if(data!=null&&data.size()>0){
                    for (int i = 0; i <data.size() ; i++) {
                        NewsBean.ResultBean.DataBean dataBean = data.get(i);
                        Bean bean=new Bean();
                        String title = dataBean.getTitle();
                        String thumbnail_pic_s = dataBean.getThumbnail_pic_s();
                        bean.setTitle(title);
                        bean.setThumbnail_pic_s(thumbnail_pic_s);
                        list.add(bean);
                    }
                }
                Xianshi();

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
        });
    }
    private void Xianshi() {
        adapter = new NewsAdapter(MainActivity.this,list);
        mlv.setAdapter(adapter);


    }
}
