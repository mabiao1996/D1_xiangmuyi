package com.bwie.text.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.text.R;
import com.bwie.text.adapter.NewsAdapter;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.Bean;
import com.bwie.text.bean.NewsBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import view.xlistview.XListView;

/**
 * Created by mabiao on 2017/8/31.
 */

public class MyFragment2 extends Fragment implements XListView.IXListViewListener{
    private View view;
    private List<Bean> list;
    private NewsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment1,null);
        return view;
    }
    XListView xlv;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv=view.findViewById(R.id.xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
        initList();
        initData();
    }

    private void initList() {
        list = new ArrayList<>();
    }

    private void initData() {
        RequestParams params=new RequestParams(NewsApk.NEWSURL);
        params.addQueryStringParameter("key",NewsApk.NEWSKEY);
        params.addQueryStringParameter("type","shehui");
        x.http().post(params, new Callback.CommonCallback<String>() {
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
                        String date = dataBean.getDate();
                        String author_name = dataBean.getAuthor_name();
                        String thumbnail_pic_s = dataBean.getThumbnail_pic_s();
                        bean.setTitle(title);
                        bean.setAuthor_name(author_name);
                        bean.setDate(date);
                        bean.setThumbnail_pic_s(thumbnail_pic_s);
                        list.add(bean);
                    }
                }
                Xianshi();
                xlv.stopLoadMore();
                xlv.stopRefresh();
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
        if(adapter==null){
            adapter=new NewsAdapter(getActivity(),list);
            xlv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }



    }


    @Override
    public void onRefresh() {
        if(list!=null){
            list.clear();
            initData();
        }
    }

    @Override
    public void onLoadMore() {
        initData();
    }

}
