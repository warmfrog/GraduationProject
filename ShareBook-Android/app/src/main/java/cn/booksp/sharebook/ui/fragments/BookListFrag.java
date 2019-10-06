package cn.booksp.sharebook.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.repository.UbookRepository;
import cn.booksp.sharebook.ui.adapter.BookItemAdapter;
import cn.booksp.sharebook.dummy.DummyBookInfo;
import cn.booksp.sharebook.dummy.DummyBookInfo.DummyItem;
import cn.booksp.sharebook.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnBookListClickListener}
 * interface.
 */
public class BookListFrag extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnBookListClickListener mListener;
    private String bookClass = "新鲜";
    private UbookRepository ubookRepository = UbookRepository.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFrag(){

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BookListFrag newInstance(int columnCount) {
        BookListFrag fragment = new BookListFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        UbookViewModel ubookViewModel = ViewModelProviders.of(getActivity()).get(UbookViewModel.class);


        Bundle args = getArguments();
        bookClass = (String)args.get("type");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new BookItemAdapter(DummyBookInfo.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookListClickListener) {
            mListener = (OnBookListClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBookListClickListener {
        // TODO: Update argument type and name
        void onBookListClick(DummyItem item);
    }

    class LoadBooksTask extends AsyncTask<Void, Void, List<Ubook>>{

        @Override
        protected List<Ubook> doInBackground(Void... voids) {
            return new ArrayList<Ubook>();
        }
    }
}
