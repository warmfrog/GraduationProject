package cn.booksp.sharebook.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dommy.qrcode.util.Constant;
import com.easemob.chat.EMMessage;
import com.google.zxing.activity.CaptureActivity;

import java.lang.reflect.Field;
import java.util.List;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.dummy.DummyBookInfo;
import cn.booksp.sharebook.repository.ContactRepository;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.GbookAPI;
import cn.booksp.sharebook.ui.fragments.AboutMeFrag;
import cn.booksp.sharebook.ui.fragments.BookListFrag;
import cn.booksp.sharebook.ui.fragments.ContactsFrag;
import cn.booksp.sharebook.ui.fragments.MainFrag;
import cn.booksp.sharebook.ui.fragments.MyDialog;
import cn.booksp.sharebook.ui.fragments.MyUbookFrag;
import cn.booksp.sharebook.ui.fragments.NoLoginFrag;
import cn.booksp.sharebook.ui.fragments.SelfIntroFrag;
import cn.booksp.sharebook.ui.viewmodel.MainViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.booksp.sharebook.BasicApp.getUsername;
import static cn.booksp.sharebook.BasicApp.login;
import static cn.booksp.sharebook.repository.api.ApiResponse.Status.ok;

public class MainActivity extends AppCompatActivity implements
        BookListFrag.OnBookListClickListener,
        ContactsFrag.OnMessageClickListener,
        NoLoginFrag.OnWantLoginListener,
        AboutMeFrag.OnAboutMeListener,
        SelfIntroFrag.OnSelfIntroListener,
        MyUbookFrag.OnMyUbookListener {

    BottomNavigationView navigation;
    /**
     * Fragment页面
     */
    MainFrag mainFrag;
    ContactsFrag contactsFrag;
    AboutMeFrag aboutMeFrag;
    SelfIntroFrag selfIntroFrag;
    NoLoginFrag noLoginFrag;

    /**
     * 全局变量
     */
    Context activityContext;
    Context appContext;

    MainViewModel viewModel;
    /**
     * 底部导航栏侦听器
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * UI事件: 响应底部导航栏
         * @param item
         * @return
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.share_book:
                    if (mainFrag == null) {
                        mainFrag = new MainFrag();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFrag).commit();
                    Log.d("导航菜单共享书", "MainFragment");
                    return true;
                case R.id.release:
                    MyDialog dialog = new MyDialog();
                    dialog.show(getSupportFragmentManager(), "hello");
                    return true;
                case R.id.message:

                    if (contactsFrag == null) {
                        contactsFrag = new ContactsFrag();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactsFrag).addToBackStack(null).commit();
                    new LoadContactTask().execute();
                    return true;
                case R.id.aboutme:
                    if (null == aboutMeFrag) {
                        aboutMeFrag = new AboutMeFrag();
                    }
                    if (null == selfIntroFrag) {
                        selfIntroFrag = new SelfIntroFrag();
                    }
                    if (null == noLoginFrag) {
                        noLoginFrag = new NoLoginFrag();
                    }
                    SharedPreferences userConfig = getSharedPreferences("UserConfig", MODE_PRIVATE);
                    String current_user = userConfig.getString("current_user", null);
                    String token = userConfig.getString("token", null);
                    if (null != current_user) {
                        Log.v("当前登录用户: ", current_user == null ? "null" : current_user);
                        Log.v("用户token: ", token == null ? "null" : token);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutMeFrag).add(R.id.fragment_container, selfIntroFrag).addToBackStack(null).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutMeFrag).add(R.id.fragment_container, noLoginFrag).addToBackStack(null).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    /**
     * 禁止导航栏多于3个时的奇怪行为
     *
     * @param view
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getUsername().setValue(getUsername());
        String user = viewModel.getUsername().getValue();
        login(user, user);
        activityContext = this;
        appContext = getApplicationContext();
        mainFrag = new MainFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFrag).commit();

        navigation = findViewById(R.id.navigation);
        disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onWantLoginClick() {
    }

    @Override
    public void onAboutMe(Uri uri) {

    }

    @Override
    public void onSelfIntro(Uri uri) {

    }

    @Override
    public void onMyUbookClick(Ubook ubook) {
        Intent ubookInfoIntent = new Intent(this, UbookinfoActivity.class);
        ubookInfoIntent.putExtra("ubook", ubook);
        startActivity(ubookInfoIntent);
    }

    @Override
    public void onBookListClick(DummyBookInfo.DummyItem item) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String isbn = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);

            getBook(isbn);
            //将扫描出的信息显示出来
            Log.v("条形码:", isbn);
        }
    }

    public void getBook(String isbn) {
        GbookAPI gbookAPI = BasicApp.getGbookAPI();
        Call<ApiResponse<Book>> bookCall = gbookAPI.getBook(isbn);

        mainFrag.showProgress(true);
        bookCall.enqueue(new Callback<ApiResponse<Book>>() {
            @Override
            public void onResponse(Call<ApiResponse<Book>> call, Response<ApiResponse<Book>> response) {
                Log.d("isbn响应", response.toString());
                ApiResponse<Book> body = response.body();
                Log.d("响应body", body == null ? "服务器未响应" : body.toString());

                if (body != null) {
                    ApiResponse.Status status = body.getStatus();
                    if (status == ok) {
                        Book book = body.getData();
                        Intent releaseIntent = new Intent(activityContext, ReleaseActivity.class);
                        releaseIntent.putExtra("book", book);
                        startActivity(releaseIntent);
                    } else {
                        String errorMessage = body.getError().getErrorMessage();
                        Snackbar.make(navigation, errorMessage, Snackbar.LENGTH_SHORT).show();
                    }
                    mainFrag.showProgress(false);
                } else {
                    Snackbar.make(navigation, "错误，您可能尚未登录", Snackbar.LENGTH_LONG).show();
                    mainFrag.showProgress(false);
                    Log.e("服务器未响应", "服务器未响应");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Book>> call, Throwable t) {
                Log.e("反序列化错误", "MainActivity");
                Log.e("网络错误", "无法连接到服务器");
                Snackbar.make(navigation, "无法连接到服务器", Snackbar.LENGTH_LONG).show();
                mainFrag.showProgress(false);
                Log.e("error", t.getMessage());
            }
        });
    }

    /**
     * 申请权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                // 文件读写权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // 扫码返回结果
    private void startQrCode() {
        // 申请相机权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    public void OnMessageClick(EMMessage item) {

    }

    class LoadContactTask extends AsyncTask<Void, Void, LiveData<List<Contact>>> {
        @Override
        protected LiveData<List<Contact>> doInBackground(Void... voids) {
            Log.d("ContactFrag","从服务器加载通讯录信息");
            LiveData<List<Contact>> contacts = ContactRepository.getInstance().getContacts(viewModel.getUsername().getValue());
            return null;
        }
    }
}
