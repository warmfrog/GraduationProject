package cn.booksp.sharebook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.BookRepository;
import cn.booksp.sharebook.repository.dao.FileDao;

public class UbookinfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toobarTitle;
    private ImageButton back;
    private ImageView bookCoverView;
    private TextView isbnTextView;
    private TextView idTextView;
    private TextView booknameTextView;
    private TextView authorTextView;
    private TextView pubdateTextView;
    private TextView ubookIntro;
    private TextView rentPriceView;
    private TextView sellPriceView;
    private TextView bookCategoryView;
    private Button rentBtn;
    private Button buyBtn;
    private Button talkBtn;

    private Context context;
    private LoadBookTask loadBookTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubookinfo);

        initUI();
    }

    private void initUI() {
        context = this;
        Ubook ubook = (Ubook) getIntent().getSerializableExtra("ubook");
        loadBookTask = new LoadBookTask(ubook.getIsbn13());
        loadBookTask.execute();

        /**
         * UI控件初始化
         */
        bookCategoryView = findViewById(R.id.bookCategory);
        rentPriceView = findViewById(R.id.rentPrice);
        sellPriceView = findViewById(R.id.sellPrice);
        bookCoverView = findViewById(R.id.bookCoverView);
        isbnTextView = findViewById(R.id.isbnView);
        idTextView = findViewById(R.id.idTextView);
        booknameTextView = findViewById(R.id.booknameView);
        authorTextView = findViewById(R.id.authorView);
        pubdateTextView = findViewById(R.id.pubdateTextView);
        ubookIntro = findViewById(R.id.bookIntroView);

        ubookIntro.append(ubook.getBookIntro());
        String image = ubook.getImage();
        if (image != null) {
            Glide.with(this).load(image).into(bookCoverView);
        } else {
            bookCoverView.setImageResource(R.drawable.default_book);
        }

        String type;
        rentPriceView.setText(ubook.getRentPrice().toString() + " 书币");
        sellPriceView.setText(ubook.getSellPrice().toString() + " 书币");

        /**
         * 设置工具栏
         */
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toobarTitle = findViewById(R.id.toolbar_title);
        back = findViewById(R.id.btn_back);
        toobarTitle.setText("《" + ubook.getBookName() + "》");
        back.setOnClickListener(view -> {
            finish();
        });
        setSupportActionBar(toolbar);


        rentBtn = findViewById(R.id.rentBtn);
        buyBtn = findViewById(R.id.buyBtn);
        talkBtn = findViewById(R.id.talkBtn);

        String username = ubook.getUsername();
        String user = BasicApp.getUsername();
        if (user != null) {
            if (user.equals(username)) {
                rentBtn.setVisibility(View.GONE);
                buyBtn.setVisibility(View.GONE);
                talkBtn.setVisibility(View.GONE);
            } else {
                rentBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("ubook", ubook);
                    intent.putExtra("type", "rent");
                    context.startActivity(intent);
                    finish();
                });
                buyBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("ubook", ubook);
                    intent.putExtra("type", "sell");
                    context.startActivity(intent);
                    finish();
                });
                talkBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(context, TalkActivity.class);
                    intent.putExtra("to", username);
                    context.startActivity(intent);
                });
            }
        }
    }



    class LoadBookTask extends AsyncTask<Void, Void, Book> {
        String isbn13;
        BookRepository bookRepository;

        LoadBookTask(String isbn13) {
            this.isbn13 = isbn13;
            bookRepository = BookRepository.getInstance();
        }

        @Override
        protected Book doInBackground(Void... voids) {
            Book book = bookRepository.getBook(isbn13);
            Log.d("图书信息", null == book ? "空" : book.toString());
            return book;
        }

        @Override
        protected void onPostExecute(Book book) {
            if (null == book) {
                Toast.makeText(context, "未查到图书相关信息", Toast.LENGTH_SHORT);
            }else {
                setBook(book);
            }
        }
    }

    private void setBook(Book book){
        idTextView.setText(book.getId().toString());
        isbnTextView.append(book.getIsbn13());
        booknameTextView.append(book.getTitle());
        authorTextView.append(book.getAuthors() == null ? "" : book.getAuthors());
        pubdateTextView.append(book.getPubdate());
        bookCategoryView.setText(book.getCatalog());
    }
}
