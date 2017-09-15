package com.bwie.text;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class WebActivity extends BaseActivity {
   private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();

    }

    private void initData() {
        Intent intent=getIntent();
        String url = intent.getStringExtra("url");

        wv.loadUrl(url);
        wv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 scrollToFinishActivity();
            }
        });
    }

    private void initView() {
        wv= (WebView) findViewById(R.id.wv);


    }
}
