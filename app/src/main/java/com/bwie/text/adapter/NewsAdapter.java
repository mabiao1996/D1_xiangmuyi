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
    private  int num=2;
    private  final  int one=0;
    private  final  int two=1;

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
    public int getItemViewType(int position) {
        if(position%2==0){
            return one;
        }else{
            return two;
        }
    }

    @Override
    public int getViewTypeCount() {
        return num;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder h=null;
        ViewHolder2 h2=null;
        int itemViewType = getItemViewType(i);
        if(view==null){
            switch (itemViewType){
                case one:
                    h2=new ViewHolder2();
                    view=LayoutInflater.from(context).inflate(R.layout.item2,null);
                    h2.img2=view.findViewById(R.id.img2);
                    h2.tv_bt=view.findViewById(R.id.tv_bt);
                    h2.tv_wz=view.findViewById(R.id.tv_wz);
                    h2.tv_sj=view.findViewById(R.id.tv_sj);

                    h2.tv_bt.setText(list.get(i).getTitle());
                    h2.tv_sj.setText(list.get(i).getDate());
                    h2.tv_wz.setText(list.get(i).getAuthor_name());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),h2.img2);

                    view.setTag(h2);
                    break;
                case  two:
                    h=new ViewHolder();
                    view=LayoutInflater.from(context).inflate(R.layout.item,null);
                    h.bt_tv=view.findViewById(R.id.bt_tv);
                    h.sj_tv=view.findViewById(R.id.sj_tv);
                    h.wz_tv=view.findViewById(R.id.wz_tv);
                    h.iv_img=view.findViewById(R.id.iv_img);

                    h.bt_tv.setText(list.get(i).getTitle());
                    h.wz_tv.setText(list.get(i).getAuthor_name());
                    h.sj_tv.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),h.iv_img);

                    view.setTag(h);
                    break;

            }

        }else{
            switch (itemViewType){
                case one:
                    h2= (ViewHolder2) view.getTag();
                    h2.tv_bt.setText(list.get(i).getTitle());
                    h2.tv_sj.setText(list.get(i).getDate());
                    h2.tv_wz.setText(list.get(i).getAuthor_name());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),h2.img2);
                    break;
                case  two:
                    h= (ViewHolder) view.getTag();
                    h.bt_tv.setText(list.get(i).getTitle());
                    h.wz_tv.setText(list.get(i).getAuthor_name());
                    h.sj_tv.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),h.iv_img);
                    break;
            }
        }
        return view;
    }
    //优化
    public class ViewHolder{
       public TextView bt_tv,wz_tv,sj_tv;
        public ImageView iv_img;
    }
    public class ViewHolder2{
        public ImageView img2;
        public TextView tv_bt,tv_wz,tv_sj;

    }
}
