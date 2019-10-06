package cn.booksp.sharebook.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.ui.adapter.UbookAdapter;
import cn.booksp.sharebook.ui.fragments.MyUbookFrag;
import cn.booksp.sharebook.ui.viewmodel.UbookViewModel;

public class DemoUbooksActivity extends AppCompatActivity implements MyUbookFrag.OnMyUbookListener {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageButton btnBack;
    private TextView title;
    private MyUbookFrag.OnMyUbookListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_ubooks);

        recyclerView = findViewById(R.id.ubook_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.toolbar_title);

        btnBack.setOnClickListener(view -> {
            finish();
        });
        toolbar.setTitle("");
        title.setText("发布列表");
        setSupportActionBar(toolbar);

        UbookViewModel ubookViewModel = ViewModelProviders.of(this).get(UbookViewModel.class);

        final UbookAdapter adapter = new UbookAdapter(this, this);

        ubookViewModel.ubooks.observe(this, new Observer<PagedList<Ubook>>() {
            @Override
            public void onChanged(@Nullable PagedList<Ubook> ubooks) {
                adapter.submitList(ubooks);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMyUbookClick(Ubook ubook) {
        Intent ubookInfoIntent = new Intent(this, UbookinfoActivity.class);
        ubookInfoIntent.putExtra("ubook", ubook);
        startActivity(ubookInfoIntent);
    }
}
