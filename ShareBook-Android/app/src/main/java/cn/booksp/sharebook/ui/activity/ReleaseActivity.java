package cn.booksp.sharebook.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.BookAPI;
import cn.booksp.sharebook.repository.api.UbookAPI;
import cn.booksp.sharebook.repository.dao.BookDao;
import cn.booksp.sharebook.repository.dao.FileDao;
import cn.booksp.sharebook.repository.dao.UbookDao;
import cn.booksp.sharebook.ui.viewmodel.ReleaseUbookViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseActivity extends AppCompatActivity {
    /**
     * UI控件
     */
    ImageView bookCoverView;
    TextView isbnTextView;
    TextView booknameTextView;
    TextView authorTextView;
    TextView pubdateTextView;
    Button btnRelease;
    EditText rentPriceView;
    EditText sellPriceView;
    EditText bookIntroView;
    Toolbar toolbar;
    TextView title;
    ImageButton btnBack;

    Context context;
    ReleaseUbookViewModel viewModel;
    UbookAPI ubookAPI;
    BookAPI bookAPI;
    Ubook ubook;
    Book book;
    BookDao bookDao;
    UbookDao ubookDao;
    FileDao fileDao;
    SharedPreferences userConfig;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_form);

        userConfig = getSharedPreferences("UserConfig", MODE_PRIVATE);
        username = userConfig.getString("current_user", null);
        context = this;
        ubookAPI = BasicApp.getUbookAPI();
        bookAPI = BasicApp.getBookAPI();
        viewModel = ViewModelProviders.of(this).get(ReleaseUbookViewModel.class);
        bookDao = BasicApp.getBookDao();
        ubookDao = BasicApp.getUbookDao();
        fileDao = BasicApp.getFileDao();
        book = (Book) getIntent().getSerializableExtra("book");
        //当是配置改变而重启Activity时
        if (book == null) {
            book = viewModel.getBook();
        } else {
            //当是新打开的Activity时
            viewModel.setBook(book);
        }
        ubook = ReleaseUbookViewModel.getUbook();
        if (ubook == null) {
            ubook = new Ubook();
            viewModel.setUbook(ubook);
        }

        SaveBookTask saveBookTask = new SaveBookTask(book);
        saveBookTask.execute();
        initView(book);
        ubook.setBookId(book.getId());
        BasicApp.AppDatabase db = BasicApp.getAppDatabase();
    }

    /**
     * 初始化控件并赋值
     *
     * @param book
     */
    void initView(Book book) {
        /**
         * 初始化控件
         */
        bookCoverView = findViewById(R.id.bookCoverView);
        isbnTextView = findViewById(R.id.isbnView);
        booknameTextView = findViewById(R.id.booknameView);
        authorTextView = findViewById(R.id.authorView);
        pubdateTextView = findViewById(R.id.pubdateTextView);
        btnRelease = findViewById(R.id.btnRelease);
        rentPriceView = findViewById(R.id.rentPrice);
        sellPriceView = findViewById(R.id.sellPrice);
        bookIntroView = findViewById(R.id.bookIntroView);

        /**
         * 工具栏
         */
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.release);
        setSupportActionBar(toolbar);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        /**
         * 赋值
         */
        if (book != null) {
            String image = book.getImage();
            if (image != null) {
                Glide.with(context).load(image).into(bookCoverView);
            } else {
                bookCoverView.setImageResource(R.drawable.default_book);
            }

            isbnTextView.append(book.getIsbn13());
            booknameTextView.append(book.getTitle() == null ? "" : book.getTitle());
            authorTextView.append(book.getAuthors() == null ? "" : book.getAuthors());
            pubdateTextView.append(book.getPubdate() == null ? "" : book.getPubdate());

            /**
             * 用户点击发布时
             */
            btnRelease.setOnClickListener(view -> {
                BigDecimal rent_price = BigDecimal.valueOf(Double.valueOf(rentPriceView.getText().toString()));
                BigDecimal sell_price = BigDecimal.valueOf(Double.valueOf(sellPriceView.getText().toString()));
                ubook.setStatus("normal");
                ubook.setRentPrice(rent_price);
                ubook.setSellPrice(sell_price);
                ubook.setIsbn13(book.getIsbn13());
                ubook.setImage(image);
                ubook.setBookName(book.getTitle());
                ubook.setUsername(username);
                ubook.setBookIntro(bookIntroView.getText().toString());

                /**
                 * 发送到服务器
                 */
                Call<ApiResponse<Integer>> ubookCall = ubookAPI.addUbook(ubook);
                ubookCall.enqueue(new Callback<ApiResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                        ApiResponse<Integer> body = response.body();
                        ApiResponse.Status status = body.getStatus();
                        if (status == ApiResponse.Status.ok) {
                            /**
                             * 保存到本地数据库
                             */
                            SaveUbookTask saveUbookTask = new SaveUbookTask(ubook);
                            saveUbookTask.execute();

                            finish();
                            Intent myubookIntent = new Intent(context, MyUbookActivity.class);
                            startActivity(myubookIntent);
                        } else {
                            Log.v("响应错误", body.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                        Log.d("响应失败", t.getMessage());
                    }
                });
            });
        } else {
            Snackbar.make(toolbar, "抱歉，未查到相关图书信息", Snackbar.LENGTH_SHORT);
        }
    }

    class SaveBookTask extends AsyncTask<Void, Void, Boolean> {
        Book book;

        SaveBookTask(Book book) {
            this.book = book;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Book byISBN13 = bookDao.findByISBN13(book.getIsbn13());
            if (byISBN13 == null) {
                bookDao.insertAll(book);
            }
            Log.d("保存图书", "成功");
            return true;
        }
    }

    class SaveUbookTask extends AsyncTask<Void, Void, Boolean> {
        Ubook ubook;

        SaveUbookTask(Ubook ubook) {
            this.ubook = ubook;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ubookDao.insertAll(ubook);
            Log.d("保存Ubook", "成功");
            return true;
        }
    }

}
