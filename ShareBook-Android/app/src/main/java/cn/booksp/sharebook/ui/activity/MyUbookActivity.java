package cn.booksp.sharebook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.ui.fragments.MyUbookFrag;

public class MyUbookActivity extends AppCompatActivity implements MyUbookFrag.OnMyUbookListener {

    private Toolbar toolbar;
    private ImageButton btnBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myubook);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.toolbar_title);

        btnBack.setOnClickListener(view -> {
            finish();
        });
        toolbar.setTitle("");
        title.setText("我的发布");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myubook, menu);
        MenuItem manageItem = menu.findItem(R.id.manage_ubook);
        manageItem.setOnMenuItemClickListener(v ->{
            Snackbar.make(toolbar, R.string.manage_tip, Snackbar.LENGTH_LONG).setAction(
                    "提示", e-> Log.d("action", "do some action")
            ).show();
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMyUbookClick(Ubook ubook) {
        Intent ubookInfoIntent = new Intent(this, UbookinfoActivity.class);
        ubookInfoIntent.putExtra("ubook", ubook);
        startActivity(ubookInfoIntent);
    }
}
