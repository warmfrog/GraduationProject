package cn.booksp.sharebook.ui.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.ui.adapter.BooksPagerAdapter;
import cn.booksp.sharebook.ui.adapter.UbookAdapter;
import cn.booksp.sharebook.ui.viewmodel.UbookViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressBar mProgressBar;
    View contentContainer;
    private RecyclerView recyclerView;
    private MyUbookFrag.OnMyUbookListener mListener;

    public MainFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFrag newInstance(String param1, String param2) {
        MainFrag fragment = new MainFrag();
        return fragment;
    }

    public void showProgress(final boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
            contentContainer.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            contentContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("MainFrag","创建视图");

        mProgressBar = view.findViewById(R.id.load_progress);
        contentContainer = view.findViewById(R.id.content_container);

        recyclerView = view.findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        UbookViewModel ubookViewModel = ViewModelProviders.of(this).get(UbookViewModel.class);

        final UbookAdapter ubookAdapter = new UbookAdapter(mListener, getContext());

        ubookViewModel.ubooks.observe(this, new Observer<PagedList<Ubook>>() {
            @Override
            public void onChanged(@Nullable PagedList<Ubook> ubooks) {
                ubookAdapter.submitList(ubooks);
            }
        });
        recyclerView.setAdapter(ubookAdapter);
        Log.d("MainFrag","适配器设置完成");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyUbookFrag.OnMyUbookListener) {
            mListener = (MyUbookFrag.OnMyUbookListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMyUbookListener");
        }
    }
}
