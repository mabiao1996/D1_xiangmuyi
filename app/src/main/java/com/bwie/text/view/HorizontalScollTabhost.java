package com.bwie.text.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.text.R;
import com.bwie.text.bean.Mybean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabiao on 2017/8/31.
 */

public class HorizontalScollTabhost extends LinearLayout implements ViewPager.OnPageChangeListener{
private Context mContext;
    private int color;
    private HorizontalScrollView hsv;
    private ViewPager vp;
    private LinearLayout ll;
    private List<Mybean> listbean;
    private List<Fragment> listfragment;
    private int count;
    private List<TextView> topViews;


    public HorizontalScollTabhost(Context context) {
        this(context,null);

    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init(context,attrs);
    }
/**
 * 初始化自定义属性和view
 *
 */
private void init(Context context,AttributeSet attrs){
    TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.HorizontalScollTabhost);
    color = typedArray.getColor(R.styleable.HorizontalScollTabhost_top_back, 0xffffff);
    //销毁
    typedArray.recycle();
    initView();
}

    /**
     * 初始化view
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_scrollview,this,true);
//        addView(view);
        hsv = view.findViewById(R.id.hsv);
        vp = view.findViewById(R.id.vp);
        vp.addOnPageChangeListener(this);
        ll = view.findViewById(R.id.ll);

    }
   public void diaplay(List<Mybean> listbean,List<Fragment> listfragment){
       this.listbean=listbean;
       this.listfragment=listfragment;
       this.count=listbean.size();
       topViews = new ArrayList<>(count);
       drawUi();


   }

    private void drawUi() {
        drawHorizontal();
        drawViewpager();
        
    }

    /**
     * 绘制viewpager
     */
    private void drawViewpager() {
        Mypager pager=new Mypager(((FragmentActivity)mContext).getSupportFragmentManager());
        vp.setAdapter(pager);
    }

    /**
     * 绘制横向滑动菜单
     */
    private void drawHorizontal() {
        ll.setBackgroundColor(color);
        for (int i = 0; i <count ; i++) {
            Mybean mybean = listbean.get(i);
            final TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.news_top_tv_item, null);
            tv.setText(mybean.name);
            final int finalI=i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击移动到当前
                     vp.setCurrentItem(finalI);
                    //点击让文字居中
                    moveItemToCente(tv);
                }
            });
         ll.addView(tv);
            topViews.add(tv);

        }
        //默认设置第一项为选中（news_top_tv_item.xml里面，android:textColor引用
        // drawable的selector）
        topViews.get(0).setSelected(true);
    }

    /**
     * 移动view对象到中间
     */
    private void moveItemToCente(TextView tv) {
        DisplayMetrics dm=getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int [] locations=new int[2];
        tv.getLocationInWindow(locations);
        int width = tv.getWidth();
        hsv.smoothScrollBy((locations[0]+width/2-widthPixels/2),0);
    }
    /**
     * vp优化事件
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    //不懂
    @Override
    public void onPageSelected(int position) {
        if(ll!=null&&ll.getChildCount()>0){
            for (int i = 0; i <ll.getChildCount() ; i++) {
                if(i==position){
                    ll.getChildAt(i).setSelected(true);
                }else{
                    ll.getChildAt(i).setSelected(false);
                }
                //移动view，水平居中不懂
                moveItemToCente(topViews.get(position));
            }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * vp适配器
     */

    class Mypager extends FragmentPagerAdapter{

        public Mypager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listfragment.get(position);
        }

        @Override
        public int getCount() {
            return listfragment.size();
        }
    }
}
