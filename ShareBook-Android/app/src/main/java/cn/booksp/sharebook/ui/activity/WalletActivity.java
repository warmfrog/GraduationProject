package cn.booksp.sharebook.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.repository.UserRepository;

public class WalletActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton btnBack;
    private TextView title;
    private TextView balanceTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.toolbar_title);
        balanceTextview = findViewById(R.id.balanceTextView);

        String username = getIntent().getStringExtra("username");
        GetBalanceTask getBalanceTask = new GetBalanceTask(username);
        getBalanceTask.execute();

        btnBack.setOnClickListener(view -> {
            finish();
        });
        toolbar.setTitle("");
        toolbar.setBackgroundResource(R.color.btnPrimary);
        toolbar.setElevation(0.0f);
        title.setText("余额");
        setSupportActionBar(toolbar);
    }

    class GetBalanceTask extends AsyncTask<Void, Void, BigDecimal> {
        String username;

        GetBalanceTask(String username) {
            this.username = username;
        }

        @Override
        protected BigDecimal doInBackground(Void... voids) {
            UserRepository userRepository = UserRepository.getInstance();
            BigDecimal balance = userRepository.getBalance(username);
            return balance;
        }

        @Override
        protected void onPostExecute(BigDecimal banlance) {
            if (banlance != null) {
                balanceTextview.setText(banlance.toString());
                Log.d("账户余额", banlance.toString());
            } else {
                balanceTextview.setText("0");
            }


        }
    }
}
