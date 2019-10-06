package cn.booksp.sharebook.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.UbookRepository;
import cn.booksp.sharebook.ui.adapter.MyUbookAdapter;
import cn.booksp.sharebook.ui.viewmodel.MyUbookViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMyUbookListener}
 * interface.
 */
public class MyUbookFrag extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    SharedPreferences userConfig;
    String username;
    MyUbookViewModel viewModel;
    RecyclerView recyclerView;
    Context appContext;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnMyUbookListener mListener;
    private UbookRepository ubookRepository = UbookRepository.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyUbookFrag() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyUbookFrag newInstance(int columnCount) {
        MyUbookFrag fragment = new MyUbookFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getActivity().getApplicationContext();
        userConfig = getActivity().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
        username = userConfig.getString("current_user", "");
        viewModel = ViewModelProviders.of(this).get(MyUbookViewModel.class);
        LoadDataTask loadDataTask = new LoadDataTask();
        loadDataTask.execute();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myubook_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (viewModel.getUbooks() != null) {
                recyclerView.setAdapter(new MyUbookAdapter(appContext, viewModel.getUbooks(), mListener));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyUbookListener) {
            mListener = (OnMyUbookListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMyUbookListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMyUbookListener {
        void onMyUbookClick(Ubook ubook);
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if (null != username) {
                List<Ubook> userUbook = ubookRepository.getUserUbook(username);
                viewModel.setUbooks(userUbook);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            recyclerView.setAdapter(new MyUbookAdapter(appContext, viewModel.getUbooks(), mListener));
        }
    }
}
