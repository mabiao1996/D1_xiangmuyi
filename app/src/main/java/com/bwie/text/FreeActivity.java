package com.bwie.text;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.text.adapter.Myadapter;
import com.bwie.text.apk.NewsApk;
import com.bwie.text.bean.Catogray;
import com.bwie.text.squite.MySQUite;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FreeActivity extends AppCompatActivity {

     private RecyclerView recy;
    private TextView tv_xiazai;
    private List<Catogray> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        initView();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        Catogray c=new Catogray();
        c.type="toutiao";
        c.name="头条";
        list.add(c);
        c=new Catogray();
        c.type="shehui";
        c.name="社会";
        list.add(c);
        c=new Catogray();
        c.type="guonei";
        c.name="国内";
        list.add(c);
        c=new Catogray();
        c.type="guoji";
        c.name="国际";
        list.add(c);
        c=new Catogray();
        c.type="yule";
        c.name="娱乐";
        list.add(c);
        c=new Catogray();
        c.type="tiyu";
        c.name="体育";
        list.add(c);
        c=new Catogray();
        c.type="junshi";
        c.name="军事";
        list.add(c);
        c=new Catogray();
        c.type="keji";
        c.name="科技";
        list.add(c);
        c=new Catogray();
        c.type="caijing";
        c.name="财经";
        list.add(c);
        c=new Catogray();
        c.type="shishang";
        c.name="时尚";
        list.add(c);
        Myadapter mp=new Myadapter(this,list);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(mp);
        mp.setOnItemClickListener(new Myadapter.OnItemClickListener() {
            @Override
            public void onIntmClickListener(int pos, View view) {
                CheckBox checkbox=view.findViewById(R.id.cb_xuanze);
                Catogray c = list.get(pos);
                if(checkbox.isChecked()){
                    checkbox.setChecked(false);
                    c.state=false;
                }else{
                    checkbox.setChecked(true);
                    c.state=true;
                }
                list.set(pos,c);
            }
        });
    }
    private void initView() {
        recy= (RecyclerView) findViewById(R.id.recy);
        tv_xiazai= (TextView) findViewById(R.id.tv_xiazai);
        tv_xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list!=null&&list.size()>0){
                    for (int i = 0; i <list.size() ; i++) {
                        if(list.get(i).state){
                          //判断是否是选中状态，如果选中则执行下载操作
                            LoadData(list.get(i).type);
                        }
                    }
                    for (int j = 0; j <list.size() ; j++) {
                        System.out.println("状态"+list.get(j).state);
                    }
                }
            }
        });
    }
    private void LoadData(final String type) {
        RequestParams params=new RequestParams(NewsApk.NEWSURL);
        params.addParameter("key",NewsApk.NEWSKEY);
        params.addParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //下载成功后保存到数据库
             ShuJuData(type,result);
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
    private void ShuJuData(String type,String result) {
        MySQUite ms=new MySQUite(this);
        SQLiteDatabase db = ms.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("content",result);
        db.insert("sj",null,values);

    }

}
