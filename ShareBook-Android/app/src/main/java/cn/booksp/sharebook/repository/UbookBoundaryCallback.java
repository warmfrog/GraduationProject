package cn.booksp.sharebook.repository;

import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UbookAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbookBoundaryCallback extends PagedList.BoundaryCallback<Ubook> {
    private UbookAPI ubookAPI;
    private Executor NETWORK_IO;
    private Executor DISK_IO;
    private Integer networkPageSize;
    private UbookRepository ubookRepository;

    public UbookBoundaryCallback(UbookAPI ubookAPI, Executor NETWORK_IO, Executor DISK_IO, Integer networkPageSize, UbookRepository ubookRepository) {
        this.ubookAPI = ubookAPI;
        this.NETWORK_IO = NETWORK_IO;
        this.DISK_IO = DISK_IO;
        this.networkPageSize = networkPageSize;
        this.ubookRepository = ubookRepository;
    }

    /**
     * 将从网络获取的数据存入数据库
     */
    Callback<ApiResponse<List<Ubook>>> callback = new Callback<ApiResponse<List<Ubook>>>() {
        @Override
        public void onResponse(Call<ApiResponse<List<Ubook>>> call, Response<ApiResponse<List<Ubook>>> response) {
            ApiResponse<List<Ubook>> body = response.body();
            if(body != null){
                ubookRepository.insert(body.getData());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse<List<Ubook>>> call, Throwable t) {

        }
    };

    @Override
    @MainThread
    public void onZeroItemsLoaded() {
        DISK_IO.execute(new Runnable() {
            @Override
            public void run() {
                ubookAPI.getTop(10).enqueue(callback);
                Log.d("初次加载","本地没有数据从网络获取前10条数据");
            }
        });

    }

    @Override
    /**
     * 只从数据库加载
     */
    public void onItemAtFrontLoaded(@NonNull Ubook itemAtFront) {
    }

    @Override
        public void onItemAtEndLoaded(@NonNull Ubook itemAtEnd) {
        DISK_IO.execute(new Runnable() {
            @Override
            public void run() {
                ubookAPI.getAfter(itemAtEnd.getId(), 10).enqueue(callback);
                Log.d("向后加载","从网络获取后十条记录");
            }
        });

    }
}
