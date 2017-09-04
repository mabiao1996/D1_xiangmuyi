package com.bwie.text;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.text.bean.User;
import com.bwie.text.utils.SharedPreferencesUtil;

import java.util.Timer;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class HomeActivity extends AppCompatActivity {
    private EditText ed_zh;
    private EditText ed_mima;
    private TextView yanzheng;
    private Button but_log;
    private int time=20;
    private int dao=1000;
Handler h=new Handler();
    Runnable r=new Runnable() {
        @Override
        public void run() {
            time--;
            if(time==0){
                time=20;
                yanzheng.setEnabled(true);
                yanzheng.setText("再次获取");
            }else{
                yanzheng.setEnabled(false);
                yanzheng.setTextColor(Color.RED);
                yanzheng.setText(time+"s");
                h.postDelayed(this,dao);
            }
        }
    };
    private EventHandler eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        initData();
        initregisterSms();
    }

    private void initregisterSms() {
        //回调完成
//提交验证码成功
//获取验证码成功
//返回支持发送验证码的国家列表
        eh = new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {
                if(data instanceof Throwable){
                    Throwable throwable=(Throwable) data;
                    final String msg=throwable.getMessage();
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HomeActivity.this,msg,Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HomeActivity.this,"服务器验证成功",Toast.LENGTH_SHORT).show();
                                User user=new User();
                                user.id=ed_zh.getText().toString();
                                 user.phone=ed_zh.getText().toString();
                                SharedPreferencesUtil.putPreferences("uid",user.id);
                                SharedPreferencesUtil.putPreferences("phone",user.phone);
                            }
                        });

                    }
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HomeActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void initData() {

        but_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_zh.getText().toString().equals("")){
                    Toast.makeText(HomeActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ed_mima.getText().toString().equals("")){
                    Toast.makeText(HomeActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86",ed_zh.getText().toString(),ed_mima.getText().toString());

            }
        });
        yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_zh.getText().toString().equals("")){
                    Toast.makeText(HomeActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                h.postDelayed(r,dao);
                SMSSDK.getVerificationCode("86",ed_zh.getText().toString());
            }
        });
    }

    private void initView() {
      ed_zh= (EditText) findViewById(R.id.ed_zh);
      ed_mima= (EditText) findViewById(R.id.ed_mima);
        yanzheng= (TextView) findViewById(R.id.yanzheng);
        but_log= (Button) findViewById(R.id.but_log);
    }
}
