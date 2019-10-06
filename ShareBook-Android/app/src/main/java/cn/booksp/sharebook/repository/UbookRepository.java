package cn.booksp.sharebook.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.NetworkState;
import cn.booksp.sharebook.repository.api.UbookAPI;
import cn.booksp.sharebook.repository.dao.BookDao;
import cn.booksp.sharebook.repository.dao.FileDao;
import cn.booksp.sharebook.repository.dao.UbookDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by warmfrog on 2019/2/25.
 */
public class UbookRepository {
    private static UbookRepository ubookRepository;
    private BasicApp.AppDatabase appDatabase = BasicApp.getAppDatabase();
    private UbookAPI ubookAPI = BasicApp.getUbookAPI();
    private UbookDao ubookDao = BasicApp.getUbookDao();
    private BookDao bookDao = BasicApp.getBookDao();
    private FileDao fileDao = BasicApp.getFileDao();
    private Executor DISK_IO = BasicApp.getDiskIO();
    private Executor NETWORK_IO = BasicApp.getNetworkIO();

    private UbookRepository() {
    }

    public static UbookRepository getInstance() {
        if (ubookRepository == null) {
            synchronized (UbookRepository.class) {
                if (ubookRepository == null) {
                    ubookRepository = new UbookRepository();
                }
            }
        }
        return ubookRepository;
    }

    public boolean delete(Ubook ubook) {
        if (null != ubook) {
            ubookDao.delete(ubook);
            Log.d("删除Ubook", "从本地删除");
            Call<ApiResponse<Void>> deleteUbook = ubookAPI.deleteUbook(ubook.getId());
            try {
                Response<ApiResponse<Void>> deleteResponse = deleteUbook.execute();
                ApiResponse<Void> body = deleteResponse.body();
                if (null != body) {
                    if (body.getStatus() == ApiResponse.Status.ok) {
                        Log.d("从网络删除", "删除成功");
                        return true;
                    }
                }
            } catch (IOException e) {
                Log.d("从网络删除异常", e.getMessage());
            }
        }
        return false;
    }

    public void insert(List<Ubook> ubooks) {
        DISK_IO.execute(new Runnable() {
            @Override
            public void run() {
                ubookDao.insertAll(ubooks);
            }
        });

    }

    public List<Ubook> getUserUbook(String username) {
        Call<ApiResponse<List<Ubook>>> responseCall = ubookAPI.getUbooks(username);
        try {
            Response<ApiResponse<List<Ubook>>> response = responseCall.execute();
            ApiResponse<List<Ubook>> body = response.body();
            if (body != null) {
                if (body.getStatus() == ApiResponse.Status.ok) {
                    Log.d("网络正常", "从网络成功加载数据");
                    List<Ubook> data = body.getData();
                    return data;
                } else {
                    Log.d("获取用户图书异常", body.getError().toString());
                }
            } else {
                Log.d("访问错误", "服务器没有响应");
            }

        } catch (IOException e) {
            Log.d("获取用户图书异常", e.getMessage());
        }
        return null;
    }

    public Ubook getUbook(Integer ubookId){
        Ubook ubook = ubookDao.getUbook(ubookId);
        if(ubook == null){
            try {
                Response<ApiResponse<Ubook>> response = ubookAPI.getUbook(ubookId).execute();
                ApiResponse<Ubook> body = response.body();
                if(body.getStatus() == ApiResponse.Status.ok){
                    ubook = body.getData();
                }
            } catch (IOException e) {
                Log.d("根据ID获取ubook信息异常", e.getMessage());
            }
        }
        return ubook;
    }

    public LiveData<PagedList<Ubook>> getUbooks() {
        UbookBoundaryCallback callback = new UbookBoundaryCallback(ubookAPI, NETWORK_IO, DISK_IO, 6, this);
        LiveData<PagedList<Ubook>> liveData = new LivePagedListBuilder(ubookDao.getUbooks(), 6).setBoundaryCallback(callback).build();

        return liveData;
    }
}
