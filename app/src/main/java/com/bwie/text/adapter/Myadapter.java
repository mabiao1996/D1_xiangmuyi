package com.bwie.text.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.bwie.text.R;
import com.bwie.text.bean.Catogray;
import java.util.List;
/**
 * Created by mabiao on 2017/9/5.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{
    private Context context;
    private List<Catogray> list;
    private OnItemClickListener onItemClickListener;

    public Myadapter(Context context, List<Catogray> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         //初始化条目view
        View view = LayoutInflater.from(context).inflate(R.layout.lixian, null);
        MyViewHolder mvh=new MyViewHolder(view);
        return mvh;
    }

    /**
     * 这个方法主要用于处理逻辑（绘制ui数据）
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).name);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onIntmClickListener(position,holder.itemView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 自己定义的Viewholder,继承RecyclerViewholder,优点是：可以扩展，代码简洁
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
          private TextView tv_name;
          private CheckBox cb_xuanze;

         public MyViewHolder(View itemView) {
             super(itemView);
            tv_name= itemView.findViewById(R.id.tv_name);
            cb_xuanze= itemView.findViewById(R.id.cb_xuanze);
         }
     }
    /**
     * 供调用者调用的接口（所以声明为public）
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击事件接口（recyclerview本身不支持点击事件需要自己写）
     */
public interface OnItemClickListener{
        void onIntmClickListener(int pos,View view);
    }

}
