package com.bwie.text;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bwie.text.adapter.NewsAdapter;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.Bean;
import com.bwie.text.bean.Mybean;
import com.bwie.text.bean.NewsBean;
import com.bwie.text.fragment.Fragment1;
import com.bwie.text.fragment.Fragment2;
import com.bwie.text.fragment.MyFragment1;
import com.bwie.text.fragment.MyFragment2;
import com.bwie.text.fragment.MyFragment3;
import com.bwie.text.fragment.MyFragment4;
import com.bwie.text.fragment.MyFragment5;
import com.bwie.text.fragment.MyFragment6;
import com.bwie.text.fragment.MyFragment7;
import com.bwie.text.fragment.MyFragment8;
import com.bwie.text.view.HorizontalScollTabhost;
import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;
import com.umeng.socialize.UMShareAPI;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {

    @ViewInject(R.id.but_zuo) Button but_zuo;
    @ViewInject(R.id.but_you) Button but_you;
    @ViewInject(R.id.tabhost) HorizontalScollTabhost tabhost;
    private List<Bean> list;
    private NewsAdapter adapter;
    private SlidingMenu menu;
    private List<Mybean> listbean;
    private List<Fragment> listfragmen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initMenu();
        initList();
        initData();
        initFragment();
    }
    private void initFragment() {
        listbean = new ArrayList<>();
        listfragmen = new ArrayList<>();
        Mybean bean=new Mybean();
        bean.id="top";
        bean.name="头条";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="yule";
        bean.name="娱乐";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="shehui";
        bean.name="社会";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="tiyu";
        bean.name="体育";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="keji";
        bean.name="科技";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="caijing";
        bean.name="财经";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="shishang";
        bean.name="时尚";
        listbean.add(bean);
        bean=new Mybean();
        bean.id="junshi";
        bean.name="军事";
        listbean.add(bean);
        listfragmen.add(new MyFragment1());
        listfragmen.add(new MyFragment2());
        listfragmen.add(new MyFragment3());
        listfragmen.add(new MyFragment4());
        listfragmen.add(new MyFragment5());
        listfragmen.add(new MyFragment6());
        listfragmen.add(new MyFragment7());
        listfragmen.add(new MyFragment8());
        tabhost.diaplay(listbean,listfragmen);
    }
    private void initView() {
        but_zuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击左侧滑
              menu.showMenu();
            }
        });
        but_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //点击右侧滑动
             menu.showSecondaryMenu();
            }
        });
    }

    private void initMenu() {
        //添加左菜单
        setBehindContentView(R.layout.fragmentleft);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl,new Fragment1()).commit();

        menu = getSlidingMenu();
        //设置左右滑动
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置滑动的屏幕范围，该设置为边缘区域都可以滑动
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //划出时主页面显示的剩余宽度
        menu.setBehindOffsetRes(R.dimen.SlidingMenuRes);
        //添加右菜单
        menu.setSecondaryMenu(R.layout.fragmentright);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_fl,new Fragment2()).commit();

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
//        adapter = new NewsAdapter(MainActivity.this,list);
//        mlv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
}
