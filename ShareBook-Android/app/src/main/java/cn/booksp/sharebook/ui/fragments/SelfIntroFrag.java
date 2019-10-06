package cn.booksp.sharebook.ui.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.repository.dao.BookDao;
import cn.booksp.sharebook.repository.dao.FileDao;
import cn.booksp.sharebook.repository.dao.UbookDao;
import cn.booksp.sharebook.ui.activity.DemoUbooksActivity;
import cn.booksp.sharebook.ui.activity.MyUbookActivity;
import cn.booksp.sharebook.ui.activity.WalletActivity;
import cn.booksp.sharebook.ui.viewmodel.MainViewModel;

import static cn.booksp.sharebook.BasicApp.getContactDao;
import static cn.booksp.sharebook.BasicApp.getMessageDao;
import static cn.booksp.sharebook.BasicApp.logout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelfIntroFrag.OnSelfIntroListener
 * } interface
 * to handle interaction events.
 * Use the {@link SelfIntroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfIntroFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Context context;
    private UbookDao ubookDao = BasicApp.getUbookDao();
    private BookDao bookDao = BasicApp.getBookDao();
    /**
     * UI控件
     */
    private Button btnLogout;
    private Button btnMyUbook;
    private Button btnMyWallet;
    private Button btnMyOrders;
    private Button btnClearBuffer;
    private Button btnUbookList;
    private View view;
    MainViewModel viewModel;

    public SelfIntroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfIntroFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfIntroFrag newInstance(String param1, String param2) {
        SelfIntroFrag fragment = new SelfIntroFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        SharedPreferences userConfig = getContext().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
        viewModel.getUsername().setValue(userConfig.getString("current_user", null));
//        username = userConfig.getString("current_user", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_self_intro, container, false);
        TextView usernameTextview = view.findViewById(R.id.username);
            usernameTextview.setText(viewModel.getUsername().getValue());
        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                usernameTextview.setText(s);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        btnMyUbook = getActivity().findViewById(R.id.btnMyUbook);
        btnMyUbook.setOnClickListener(view -> {
            Intent myubookIntent = new Intent(context, MyUbookActivity.class);
            startActivity(myubookIntent);
        });
        btnMyWallet = getActivity().findViewById(R.id.btnMyWallet);
        btnMyWallet.setOnClickListener(view -> {
            Intent intent = new Intent(context, WalletActivity.class);
            intent.putExtra("username", viewModel.getUsername().getValue());
            startActivity(intent);
        });
        btnLogout = getActivity().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view -> {
            SharedPreferences userConfig = getActivity().getSharedPreferences("UserConfig", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = userConfig.edit();
            edit.remove("current_user");
            edit.commit();

            logout();
            (new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... v) {
                    ubookDao.deleteAll();
                    bookDao.deleteAll();
                    getContactDao().deleteAll();
                    getMessageDao().deleteAll();
                    return null;
                }
            }).execute();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NoLoginFrag()).commit();
        });
        btnUbookList = getActivity().findViewById(R.id.btnUbookList);
        btnUbookList.setOnClickListener(view -> {
            Intent intent = new Intent(context, DemoUbooksActivity.class);
            startActivity(intent);
        });
        btnUbookList.setVisibility(View.GONE);
        btnMyOrders = getActivity().findViewById(R.id.btnMyOrders);
        btnMyOrders.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFrag()).commit();
        });
        btnClearBuffer = getActivity().findViewById(R.id.btnClearBuffer);
        btnClearBuffer.setOnClickListener(view -> {
            new ClearBufferTask().execute();
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelfIntroListener
        ) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelfIntroListener" +
                    "");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSelfIntroListener {
        // TODO: Update argument type and name
        void onSelfIntro(Uri uri);
    }

    class ClearBufferTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            FileDao.clearBuffer();
            ubookDao.deleteAll();
            bookDao.deleteAll();
            getContactDao().deleteAll();
            getMessageDao().deleteAll();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Snackbar.make(view, "清除缓存成功", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
