package cn.booksp.sharebook.ui.activity.async_tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.ui.activity.PayResultActivity;
import retrofit2.Response;

public class PayOrderTask extends AsyncTask<Void, Void, String> {
    String orderId;
    Context mContext;
    public PayOrderTask(Context context, String orderId){
        this.mContext = context;
        this.orderId = orderId;
    }
    @Override
    protected String doInBackground(Void... voids) {
        UserAPI userAPI = BasicApp.getUserAPI();
        try {
            Response<ApiResponse<String>> response = userAPI.pay(BasicApp.getUsername(), orderId).execute();
            ApiResponse<String> body = response.body();
            String data = body.getData();
            Log.d("支付信息", data);
            return data;
        } catch (IOException e) {
            Log.d("支付异常",e.getMessage());
        }
        return "支付异常";
    }

    @Override
    protected void onPostExecute(String message) {
        Intent intent = new Intent(mContext, PayResultActivity.class);
        intent.putExtra("result",message);
        mContext.startActivity(intent);
    }
}