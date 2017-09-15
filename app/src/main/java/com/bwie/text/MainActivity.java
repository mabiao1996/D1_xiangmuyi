package com.bwie.text;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ChangedPackages;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.text.adapter.NewsAdapter;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.Bean;
import com.bwie.text.bean.Mybean;
import com.bwie.text.bean.NewsBean;
import com.bwie.text.bean.SBean;
import com.bwie.text.fragment.Fragment1;
import com.bwie.text.fragment.Fragment2;
import com.bwie.text.fragment.MyFragment1;
import com.bwie.text.fragment.MyFragment10;
import com.bwie.text.fragment.MyFragment2;
import com.bwie.text.fragment.MyFragment3;
import com.bwie.text.fragment.MyFragment4;
import com.bwie.text.fragment.MyFragment5;
import com.bwie.text.fragment.MyFragment6;
import com.bwie.text.fragment.MyFragment7;
import com.bwie.text.fragment.MyFragment8;
import com.bwie.text.fragment.MyFragment9;
import com.bwie.text.squite.NewsSQLite;
import com.bwie.text.view.HorizontalScollTabhost;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kson.slidingmenu.SlidingMenu;
import com.umeng.socialize.UMShareAPI;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.but_zuo) Button but_zuo;
    @ViewInject(R.id.but_you) Button but_you;
    @ViewInject(R.id.tabhost) HorizontalScollTabhost tabhost;
    @ViewInject(R.id.img_add)  ImageView img_add;
    private List<Bean> list;
    private NewsAdapter adapter;
    private SlidingMenu menu;
    private List<Mybean> listbean;
    private List<Fragment> listfragmen;
    private List<ChannelBean> list1;
    private SharedPreferences spf;
    private String json1;
    private List<ChannelBean> l;
    private Mybean bean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
        initMenu();
        initData();
        spf = getSharedPreferences("sss",MODE_PRIVATE);
        if(spf.getString("json", null)!=null){
          String string=  spf.getString("json", null);
           initDatas(string);
        }else {
            initFragment();
        }
        

    }
    private void initFragment() {
        listbean = new ArrayList<>();
        listfragmen = new ArrayList<>();
        bean = new Mybean();
        bean.id="top";
        bean.name="头条";
        listbean.add(bean);
        listfragmen.add(new MyFragment1());

        bean=new Mybean();
        bean.id="shehui";
        bean.name="社会";
        listbean.add(bean);
        listfragmen.add(new MyFragment2());

        bean=new Mybean();
        bean.id="guonei";
        bean.name="国内";
        listbean.add(bean);
        listfragmen.add(new MyFragment3());

        bean=new Mybean();
        bean.id="guoji";
        bean.name="国际";
        listbean.add(bean);
        listfragmen.add(new MyFragment4());

        bean=new Mybean();
        bean.id="yule";
        bean.name="娱乐";
        listbean.add(bean);
        listfragmen.add(new MyFragment5());

        bean=new Mybean();
        bean.id="tiyu";
        bean.name="体育";
        listbean.add(bean);
        listfragmen.add(new MyFragment6());

        bean=new Mybean();
        bean.id="junshi";
        bean.name="军事";
        listbean.add(bean);
        listfragmen.add(new MyFragment7());

        bean=new Mybean();
        bean.id="keji";
        bean.name="科技";
        listbean.add(bean);
        listfragmen.add(new MyFragment8());

        bean=new Mybean();
        bean.id="caijing";
        bean.name="财经";
        listbean.add(bean);
        listfragmen.add(new MyFragment9());

        bean=new Mybean();
        bean.id="shishang";
        bean.name="时尚";
        listbean.add(bean);
        listfragmen.add(new MyFragment10());

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

        menu =new SlidingMenu(this);
        //添加左菜单
        menu.setMenu(R.layout.fragmentleft);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_fl,new Fragment1()).commit();


        //设置左右滑动
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置滑动的屏幕范围，该设置为边缘区域都可以滑动
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //划出时主页面显示的剩余宽度
        menu.setBehindOffsetRes(R.dimen.SlidingMenuRes);
        //添加右菜单
        menu.setSecondaryMenu(R.layout.fragmentright);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_fl,new Fragment2()).commit();
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }
    private void initData() {
        img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        json1 = spf.getString("json", null);
                        if(json1 ==null){
                            list1 = new ArrayList<ChannelBean>();
                            for (int i = 0; i <listbean.size() ; i++) {
                                     ChannelBean bean;
                                    bean=new ChannelBean(listbean.get(i).name,true);
                                list1.add(bean);
                            }
                            ChannelActivity.startChannelActivity(MainActivity.this,list1);
                        }else{
                            Gson gson=new Gson();
                            l = gson.fromJson(json1.toString(), new TypeToken<List<ChannelBean>>(){}.getType());
                            ChannelActivity.startChannelActivity(MainActivity.this, l);
                        }
            }
        });
    }
    private void initDatas(String json) {
        Gson gson=new Gson();
        l = gson.fromJson(json.toString(), new TypeToken<List<ChannelBean>>(){}.getType());
//        getSupportFragmentManager().getFragments().removeAll(listfragmen);
        listbean=new ArrayList<>();
    //listfragmen.clear();
listfragmen=new ArrayList<>();
        for (int i = 0; i <l.size() ; i++) {
            boolean b = l.get(i).isSelect();
            if(b){
                System.out.println("sssss"+l.get(i).getName());
                String name = l.get(i).getName();
                switch (name){
                    case "头条":
                        bean=new Mybean();
                        bean.id="top";
                        bean.name="头条";
                        listbean.add(bean);
                        System.out.println("================"+bean.name);
                        listfragmen.add(new MyFragment1());
                        break;
                    case "社会":
                        bean=new Mybean();
                        bean.id="shehui";
                        bean.name="社会";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment2());
                        break;
                    case "国内":
                        bean=new Mybean();
                        bean.id="guonei";
                        bean.name="国内";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment3());
                        break;
                    case "国际":
                        bean=new Mybean();
                        bean.id="guoji";
                        bean.name="国际";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment4());
                        break;
                    case "娱乐":
                        bean=new Mybean();
                        bean.id="yule";
                        bean.name="娱乐";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment5());
                        break;
                    case "体育":
                        bean=new Mybean();
                        bean.id="tiyu";
                        bean.name="体育";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment6());
                        break;
                    case "军事":
                        bean=new Mybean();
                        bean.id="junshi";
                        bean.name="军事";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment7());
                        break;
                    case "科技":
                        bean=new Mybean();
                        bean.id="keji";
                        bean.name="科技";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment8());
                        break;
                    case "财经":
                        bean=new Mybean();
                        bean.id="caijing";
                        bean.name="财经";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment9());
                        break;
                    case "时尚":
                        bean=new Mybean();
                        bean.id="shishang";
                        bean.name="时尚";
                        listbean.add(bean);
                        listfragmen.add(new MyFragment10());
                        break;
                }
            }
        }
        tabhost.diaplay(listbean,listfragmen);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
        if(resultCode==101){
            String json = data.getExtras().getString("json");
            SharedPreferences sp=getSharedPreferences("sss",MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("json",json);
            edit.commit();
            if(spf.getString("json", null)!=null){
                String string=  spf.getString("json", null);
                initDatas(string);
            }else {
                initFragment();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}