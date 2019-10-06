package cn.booksp.sharebook.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.domain.User;
import cn.booksp.sharebook.ui.activity.RegisterActivity;
import cn.booksp.sharebook.ui.viewmodel.MainViewModel;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static cn.booksp.sharebook.BasicApp.login;


/**
 * Created by warmfrog on 2018/11/19.
 */

public class LoginFrag extends Fragment {
    public final String TAG = "LoginFrag:";
    /**
     * 全局变量
     */
    private String username;
    private String password;
    private Retrofit retrofit;
    private UserAPI userAPI;
    private Context context;
    private View view;
    //用户配置
    private SharedPreferences userConfig;
    /**
     * UI 控件
     */
    private TextView accountTextView;
    private TextView passwordTextView;
    private TextView passwordTip;
    private TextView accountTip;
    private Button login;
    private CheckBox rememberAccount;
    private CheckBox rememberPassword;
    private View mProgressView;
    private View mLoginFormView;
    private TextView registerTextView;
    MainViewModel viewModel;

    //通知Activity 有菜单
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        retrofit = BasicApp.getRetrofit();
        userAPI = retrofit.create(UserAPI.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        initUIEvent();
        return view;
    }

    private void initUI(View view) {
        /**
         * 初始化UI空间
         */
        accountTextView = view.findViewById(R.id.account);
        passwordTextView = view.findViewById(R.id.password);
        passwordTip = view.findViewById(R.id.password_tip);
        accountTip = view.findViewById(R.id.account_tip);
        login = view.findViewById(R.id.btnLogin);
        mProgressView = view.findViewById(R.id.login_progress);
        mLoginFormView = view.findViewById(R.id.login_form);
        rememberAccount = view.findViewById(R.id.rememer_account);
        rememberPassword = view.findViewById(R.id.rememer_password);
        registerTextView = view.findViewById(R.id.registerTextView);

        //读取用户配置
        userConfig = view.getContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
        if (userConfig != null) {
            username = userConfig.getString("account", "");
            password = userConfig.getString("password", "");
            accountTextView.setText(username);
            passwordTextView.setText(password);
            rememberAccount.setChecked(userConfig.getBoolean("rememeber_account", false));
            rememberPassword.setChecked(userConfig.getBoolean("remember_password", false));
        }
    }

    private void initUIEvent() {

        /**
         * UI事件: 输入帐户名
         */
        accountTextView.setOnClickListener(view -> {
            accountTip.setVisibility(View.VISIBLE);
        });
        /**
         * UI事件: 输入密码
         */
        passwordTextView.setOnClickListener(view -> {
            passwordTip.setVisibility(View.VISIBLE);
        });

        /**
         *  UI事件:用户点击登录时
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountTextView.getText().toString();
                password = passwordTextView.getText().toString();

                /**
                 * 点击登录: 首先保存用户属性,然后登录
                 */
                if (RegisterActivity.UserHelper.ifUsernameValid(account) && RegisterActivity.UserHelper.ifPswdValid(password)) {
                    SharedPreferences.Editor editor = userConfig.edit();
                    editor.putBoolean("rememeber_account", rememberAccount.isChecked());
                    editor.putBoolean("remember_password", rememberPassword.isChecked());

                    if (rememberPassword.isChecked()) {
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else if (rememberAccount.isChecked()) {
                        editor.putString("account", account);
                        editor.remove("password");
                    } else {
                        editor.remove("account");
                        editor.remove("password");
                    }
                    editor.commit();

                    showProgress(true);
                    User user = new User(account, password);
                    Call<Void> login = userAPI.login(user);
                    login.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Headers headers = response.headers();
                            Log.v("头部", headers.toString());
                            List<String> authorization = headers.values("Authorization");
                            if (authorization.size() > 0) {
                                String token = authorization.get(0).substring(6);
                                SharedPreferences.Editor editor = userConfig.edit();
                                editor.putString("current_user", account);
                                editor.putString("token", "Bearer " + token);
                                editor.commit();

                                BasicApp.setUsername(account);
                                viewModel.getUsername().postValue(account);

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelfIntroFrag()).commit();
                                login(account, account);
                                Log.v("认证", token);
                            }else {
                                showProgress(false);
                                accountTextView.setError("登录错误，账号或密码错误");
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            showProgress(false);
                            accountTextView.setError("登录错误,用户名或密码不正确");
                        }
                    });
                }
            }
        });

        /**
         * 用户点击注册时
         */
        registerTextView.setOnClickListener(view -> {
            Intent registerIntent = new Intent(context, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

