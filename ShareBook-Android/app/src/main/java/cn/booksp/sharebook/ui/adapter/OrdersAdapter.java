package cn.booksp.sharebook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Order;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.UbookRepository;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.ui.activity.PayResultActivity;
import cn.booksp.sharebook.ui.activity.TalkActivity;
import cn.booksp.sharebook.ui.fragments.DeleteOrderDialog;
import cn.booksp.sharebook.ui.viewmodel.OrderViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    List<Order> orders;
    Map<Integer,Ubook> ubookMap;
    UserAPI userAPI = BasicApp.getUserAPI();
    Context context;
    FragmentManager manager;
    OrderViewModel viewModel;

    public OrdersAdapter(Context context, OrderViewModel viewModel, FragmentManager manager) {
        this.context = context;
        this.viewModel = viewModel;
        this.orders = viewModel.getOrderList();
        this.ubookMap = viewModel.getUbookMap();
        this.manager = manager;
    }

    public void setViewModel(OrderViewModel viewModel) {
        this.viewModel = viewModel;
        this.orders = viewModel.getOrderList();
        this.ubookMap = viewModel.getUbookMap();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        String orderId = order.getId();
        //店铺信息
        holder.ownerView.setText(order.getOwner() + "的发布 >");
        String status = order.getStatus();
        if (status.equals("unpaid")) {
            holder.orderTipView.setText("待支付");
            holder.orderStatus.setText("状态: 未支付");
            holder.orderBtn.setText("取消订单");
            holder.payBtn.setText("付款");
            holder.orderBtn.setOnClickListener(view ->{
                userAPI.cancel(BasicApp.getUsername(), orderId).enqueue(new Callback<ApiResponse<String>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                        ApiResponse<String> body = response.body();
                        Log.d("取消订单回复状态",body.getStatus().name());
                        Intent intent = new Intent(context, PayResultActivity.class);
                        intent.putExtra("result",body.getData());
                        intent.putExtra("title", "取消订单");
                        context.startActivity(intent);
                        new LoadOrderTask().execute();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                        Log.d("取消订单异常", t.getMessage());
                    }
                });
            });
            holder.payBtn.setOnClickListener(view ->{
                PayOrderTask payOrderTask = new PayOrderTask(context,holder, orderId);
                payOrderTask.execute();
                new LoadOrderTask().execute();
            });
        } else if (status.equals("paid")) {
            holder.orderTipView.setText("待交付");
            holder.orderStatus.setText("状态: 已支付");
            holder.orderBtn.setText("约定交付");
            holder.orderBtn.setOnClickListener(v->{
                Intent intent = new Intent(context, TalkActivity.class);
                intent.putExtra("to", order.getOwner());
                context.startActivity(intent);
            });
            holder.payBtn.setText("确认收货");
            holder.payBtn.setOnClickListener(v ->{
                userAPI.sign(BasicApp.getUsername(), orderId).enqueue(new Callback<ApiResponse<String>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                        ApiResponse<String> body = response.body();
                        String data = body.getData();
                        Log.d("确认收货回复状态", body.getStatus().name());
                        Snackbar.make(holder.orderTipView, data, Snackbar.LENGTH_SHORT).show();
                        holder.payBtn.setText(data);
                        new LoadOrderTask().execute();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

                    }
                });
            });
        } else if (status.equals("finished")) {
            holder.orderTipView.setText("订单已签收");
            holder.orderStatus.setText("状态: 已完成");
            holder.orderBtn.setText("删除订单");
            holder.payBtn.setText("已完成");
            holder.orderBtn.setOnClickListener(v ->{
                DeleteOrderDialog dialog = new DeleteOrderDialog(holder.orderTipView, orderId,new LoadOrderTask());
                Bundle args = new Bundle();
                args.putString("orderId", orderId);
                dialog.setArguments(args);
                dialog.show(manager, "delete-order");
            });
        } else if (status.equals("closed")) {
            holder.orderTipView.setText("订单已关闭");
            holder.orderStatus.setText("状态: 已关闭");
            holder.orderBtn.setText("删除订单");
            holder.payBtn.setText("订单已关闭");
            holder.orderBtn.setOnClickListener(v ->{
                DeleteOrderDialog dialog = new DeleteOrderDialog(holder.orderTipView, orderId,new LoadOrderTask());
                Bundle args = new Bundle();
                args.putString("orderId", orderId);
                dialog.setArguments(args);
                dialog.show(manager, "delete-order");
            });
        }
        //书籍信息
        holder.priceTip.setVisibility(View.GONE);
        Integer ubookId = order.getUbookId();
        Ubook ubook = ubookMap.get(ubookId);
        holder.bookName.setText("书名: " + ubook.getBookName());
        holder.bookISBN.setText("ISBN: " + ubook.getIsbn13());
        Glide.with(holder.bookImage).load(ubook.getImage()).into(holder.bookImage);

        //订单详情
        holder.orderIdView.setText(orderId);
        holder.orderAmountView.setText("金额: " + order.getPrice().toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/dd/MM");
        holder.orderCreateTime.setText("时间: " + format.format(order.getCreateTime()));
        String type = order.getType();
        if (type.equals("rent")) {
            holder.orderType.setText("类型: 租借");
        } else {
            holder.orderType.setText("类型: 购买");
        }

    }

    @Override
    public int getItemCount() {
        if (orders != null) {
            return orders.size();
        } else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        /**
         * 店铺信息
         */
        TextView ownerView;
        TextView orderTipView;

        /**
         * 书籍详情
         */
        ImageView bookImage;
        TextView bookName;
        TextView bookISBN;
        LinearLayout priceTip;

        /**
         * 订单信息
         */
        TextView orderIdView;
        TextView orderAmountView;
        TextView orderCreateTime;
        TextView orderStatus;
        TextView orderType;
        Button orderBtn;
        Button payBtn;

        ViewHolder(View view) {
            super(view);
            mView = view;
            //店铺信息
            ownerView = view.findViewById(R.id.owner_name);
            orderTipView = view.findViewById(R.id.order_tips);
            //图书信息
            bookImage = view.findViewById(R.id.book_image);
            bookName = view.findViewById(R.id.book_name);
            bookISBN = view.findViewById(R.id.book_isbn);
            priceTip = view.findViewById(R.id.priceTip);

            //订单信息
            orderIdView = view.findViewById(R.id.order_id_view);
            orderAmountView = view.findViewById(R.id.order_amount_view);
            orderCreateTime = view.findViewById(R.id.order_create_time);
            orderStatus = view.findViewById(R.id.order_status);
            orderType = view.findViewById(R.id.order_type);
            orderBtn = view.findViewById(R.id.orderBtn);
            payBtn = view.findViewById(R.id.payBtn);
        }
    }

    public class PayOrderTask extends AsyncTask<Void, Void, String> {
        String orderId;
        Context mContext;
        ViewHolder holder;
        public PayOrderTask(Context context,ViewHolder viewHolder, String orderId){
            this.mContext = context;
            this.orderId = orderId;
            this.holder = viewHolder;
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
            holder.orderTipView.setText("已支付");
            holder.orderStatus.setText("状态: 已支付");
            holder.orderBtn.setText("订单已支付");
            holder.payBtn.setText("已支付");
        }
    }
    public class LoadOrderTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Call<ApiResponse<List<Order>>> myorder = userAPI.myorder(BasicApp.getUsername());
            Response<ApiResponse<List<Order>>> response = null;
            try {
                response = myorder.execute();
            } catch (IOException e) {
                Log.d("获取订单异常", e.getMessage());
            }
            ApiResponse<List<Order>> body = response.body();
            ApiResponse.Status status = body.getStatus();
            if (status == ApiResponse.Status.ok) {
                List<Order> data = body.getData();
                ubookMap = new HashMap<>();
                UbookRepository ubookRepository = UbookRepository.getInstance();
                for (Order order : data) {
                    Integer ubookId = order.getUbookId();
                    ubookMap.put(ubookId, ubookRepository.getUbook(ubookId));
                }

                viewModel.setOrderList(data);
                viewModel.setUbookMap(ubookMap);
                return true;
            } else {
                ApiResponse.ApiError error = body.getError();
                Log.d("获取订单错误", error.getErrorMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                setViewModel(viewModel);
            } else {
                Log.d("OrderFrag", "LoadOrderTask");
            }

        }
    }
}
