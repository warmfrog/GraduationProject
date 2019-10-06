package cn.booksp.sharebook.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.domain.User;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.repository.dao.UserDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by warmfrog on 2019/2/25.
 */

public class UserRepository {
    private static UserRepository userRepository;
    private UserAPI userAPI;
    //简单的内存缓存. 暂时忽略细节
    private UserDao userDao;

    private UserRepository(){
        userAPI = BasicApp.getUserAPI();
        userDao = BasicApp.getUserDao();
    }

    public static UserRepository getInstance(){
        if(userRepository == null){
            synchronized (UserRepository.class){
                if(userRepository == null){
                    userRepository = new UserRepository();
                }
            }
        }

        return userRepository;
    }

    public LiveData<User> getUser(int userId) {
        LiveData<User> cached = userDao.get(userId);
        if (cached != null) {
            return cached;
        }

        final MutableLiveData<User> data = new MutableLiveData<>();
        userAPI.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return data;
    }

    public BigDecimal getBalance(String username){
        try {
            Response<ApiResponse<BigDecimal>> execute = userAPI.balance(username).execute();
            return execute.body().getData();
        } catch (IOException e) {
            Log.d("获取余额错误:", e.getMessage());
        }
        return null;
    }
}
