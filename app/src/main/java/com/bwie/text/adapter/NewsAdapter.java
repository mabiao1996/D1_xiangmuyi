package com.bwie.text.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.text.R;
import com.bwie.text.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by mabiao on 2017/8/29.
 */

public class NewsAdapter extends BaseAdapter{
    private Context context;
    private List<Bean> list;

    public NewsAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder h=null;
        if(view==null){
            h=new ViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.item,null);
            h.tv=view.findViewById(R.id.tv);
            h.img=view.findViewById(R.id.img);
            view.setTag(h);
        }else{
            h= (ViewHolder) view.getTag();
        }
        h.tv.setText(list.get(i).getTitle());
        ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),h.img);
        return view;
    }
    //优化
    public class ViewHolder{
       public TextView tv;
        public ImageView img;
    }
}
