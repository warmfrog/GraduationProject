package cn.booksp.sharebook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.booksp.sharebook.R;

public class PayResultActivity extends AppCompatActivity {
    /**
     * 工具栏
     */
    Toolbar toolbar;
    TextView title;
    ImageButton btnBack;

    TextView payResultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        payResultView = findViewById(R.id.payResultView);

        Intent intent = getIntent();
        String message = intent.getStringExtra("result");
        String titleText = intent.getStringExtra("title");
        if(message != null){
            payResultView.setText(message);
        }

        /**
         * 工具栏设置
         */
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);
        btnBack = findViewById(R.id.btn_back);
        toolbar.setTitle("");
        if(titleText != null){
            title.setText(titleText);
        }else {
            title.setText("支付结果");
        }
        setSupportActionBar(toolbar);

        btnBack.setOnClickListener(v->{
            finish();
        });

    }

}
