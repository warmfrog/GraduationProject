package cn.booksp.sharebook.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
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
import cn.booksp.sharebook.ui.adapter.OrdersAdapter;
import cn.booksp.sharebook.ui.viewmodel.OrderViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class OrdersFrag extends Fragment {
    List<Order> data;
    Map<Integer, Ubook> ubookMap;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private UserAPI userAPI = BasicApp.getUserAPI();
    private Context context;
    private OrderViewModel orderViewModel;
    private View view;
    private ImageButton btnBack;
    private TextView title;
    private OrdersAdapter adapter ;
    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        context = getContext();
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        adapter = new OrdersAdapter(context, orderViewModel, getActivity().getSupportFragmentManager());
        btnBack = view.findViewById(R.id.btn_back);
        title = view.findViewById(R.id.toolbar_title);
        mProgressView = view.findViewById(R.id.login_progress);

        btnBack.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelfIntroFrag()).commit();
        });

        title.setText("我的订单");
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        recyclerView = view.findViewById(R.id.order_list);

        Log.d("context", recyclerView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<Order> orderList = orderViewModel.getOrderList();
        if (orderList == null) {
            LoadOrderTask loadOrderTask = new LoadOrderTask();
            loadOrderTask.execute();
        }
        recyclerView.setAdapter(adapter);
        showProgress(true);
        return view;
    }

    class LoadOrderTask extends AsyncTask<Void, Void, Boolean> {
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
            if(body!=null) {
                ApiResponse.Status status = body.getStatus();
                if (status == ApiResponse.Status.ok) {
                    data = body.getData();
                    ubookMap = new HashMap<>();
                    UbookRepository ubookRepository = UbookRepository.getInstance();
                    for (Order order : data) {
                        Integer ubookId = order.getUbookId();
                        ubookMap.put(ubookId, ubookRepository.getUbook(ubookId));
                    }
                    orderViewModel.setOrderList(data);
                    orderViewModel.setUbookMap(ubookMap);
                    return true;
                } else {
                    ApiResponse.ApiError error = body.getError();
                    Log.d("获取订单错误", error.getErrorMessage());
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
//                OrdersAdapter adapter = new OrdersAdapter(context, orderViewModel, getActivity().getSupportFragmentManager());
                adapter.setViewModel(orderViewModel);
                showProgress(false);
            } else {
                Log.d("OrderFrag", "LoadOrderTask");
            }

        }
    }
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

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
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
