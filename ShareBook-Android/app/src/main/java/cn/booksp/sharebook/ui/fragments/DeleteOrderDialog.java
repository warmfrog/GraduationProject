package cn.booksp.sharebook.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.ui.activity.OrderActivity;
import cn.booksp.sharebook.ui.adapter.OrdersAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class DeleteOrderDialog extends DialogFragment {
    private String orderId;
    private UserAPI userAPI = BasicApp.getUserAPI();
    private View view;
    OrdersAdapter.LoadOrderTask task;

    public DeleteOrderDialog(View view, String orderId, OrdersAdapter.LoadOrderTask task) {
        this.view = view;
        this.orderId = orderId;
        this.task = task;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认删除订单吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userAPI.delete(BasicApp.getUsername(), orderId).enqueue(new Callback<ApiResponse<String>>() {
                            @Override
                            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                                ApiResponse<String> body = response.body();
                                if(body!=null){
                                    String data = body.getData();
                                    Log.d("删除订单回复信息", data);
                                    Snackbar.make(view, data, Snackbar.LENGTH_SHORT).show();
                                    task.execute();
                                }else {
                                    Log.d("错误","服务器没有回复数据");
                                }

                            }

                            @Override
                            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                                Log.d("删除订单失败", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
