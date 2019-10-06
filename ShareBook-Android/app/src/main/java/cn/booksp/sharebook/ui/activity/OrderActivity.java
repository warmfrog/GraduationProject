package cn.booksp.sharebook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Order;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.ui.activity.async_tasks.PayOrderTask;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    /**
     * 图书信息
     */
    ImageView bookImage;
    TextView bookName;
    TextView releaseTime;
    TextView isbnView;
    TextView priceView;
    TextView author;

    /**
     * 工具栏
     */
    Toolbar toolbar;
    TextView title;
    ImageButton btnBack;

    String type;
    BigDecimal price;
    Context context;

    /**
     * 订单信息
     */
    TextView orderIdView;
    TextView orderAmountView;
    TextView orderCreateTime;
    TextView orderStatus;
    TextView orderType;
    Button payNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        /**
         * 图书信息
         */
        bookImage = findViewById(R.id.bookCoverView);
        bookName = findViewById(R.id.booknameView);
        isbnView = findViewById(R.id.isbnView);
        author = findViewById(R.id.authorView);
        releaseTime = findViewById(R.id.pubdateTextView);

        /**
         * 订单信息
         */
        orderIdView = findViewById(R.id.order_id_view);
        orderAmountView = findViewById(R.id.order_amount_view);
        orderCreateTime = findViewById(R.id.order_create_time);
        orderStatus = findViewById(R.id.order_status);
        orderType = findViewById(R.id.order_type);
        payNow = findViewById(R.id.payNow);
        context = this;

        payNow.setOnClickListener( view -> {
            String orderId = orderIdView.getText().toString();
            PayOrderTask payOrderTask = new PayOrderTask(this,orderId);
            payOrderTask.execute();
            finish();
        });

        /**
         * 工具栏设置
         */
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);
        priceView = findViewById(R.id.payAmount);
        btnBack = findViewById(R.id.btn_back);
        toolbar.setTitle("");
        title.setText("创建订单");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Ubook ubook = (Ubook) intent.getSerializableExtra("ubook");
        type = intent.getStringExtra("type");

        String image = ubook.getImage();
        if (image != null) {
            Glide.with(bookImage).load(image).into(bookImage);
        } else {
            bookImage.setImageResource(R.drawable.default_book);
        }

        bookName.setText("书名:" + ubook.getBookName());
        isbnView.setText("ISBN:" + ubook.getIsbn13());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        releaseTime.setText("发布时间: " + simpleDateFormat.format(ubook.getReleaseTime()));
        if (type.equals("sell")) {
            priceView.setText(ubook.getSellPrice().toString() + " 书币");
            price = ubook.getSellPrice();
        } else {
            priceView.setText(ubook.getRentPrice().toString() + " 书币");
            price = ubook.getRentPrice();
        }

        btnBack.setOnClickListener(view -> {
            finish();
        });

        LoadOrderInfoTask orderInfoTask = new LoadOrderInfoTask(BasicApp.getUsername(),ubook.getId(),price,type );
        orderInfoTask.execute();
    }

    class LoadOrderInfoTask extends AsyncTask<Void, Void, Order> {
        String username;
        BigDecimal price;
        Integer ubookId;
        String type;

        public LoadOrderInfoTask(String username, Integer ubookId, BigDecimal price, String type) {
            this.username = username;
            this.price = price;
            this.ubookId = ubookId;
            this.type = type;
        }

        @Override
        protected Order doInBackground(Void... voids) {
            UserAPI userAPI = BasicApp.getUserAPI();
            Order data = null;
            try {
                Response<ApiResponse<Order>> response = userAPI.order(username, ubookId, type, price).execute();
                 data= response.body().getData();
                return data;
            } catch (IOException e) {
                Log.d("获取订单信息异常", e.getMessage());
            }
            return data;
        }

        @Override
        protected void onPostExecute(Order order) {
            orderIdView.append(order.getId());
            orderAmountView.append(order.getPrice().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            orderCreateTime.append(simpleDateFormat.format(order.getCreateTime()));
            orderStatus.append(order.getStatus());
            orderType.append(order.getType());
        }
    }


}
