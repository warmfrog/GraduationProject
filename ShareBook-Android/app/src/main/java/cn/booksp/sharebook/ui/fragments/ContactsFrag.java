
package cn.booksp.sharebook.ui.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easemob.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.repository.ContactRepository;
import cn.booksp.sharebook.ui.adapter.ContactAdapter;
import cn.booksp.sharebook.ui.adapter.MessageAdapter;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.ui.viewmodel.MainViewModel;

import static cn.booksp.sharebook.BasicApp.getContactDao;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMessageClickListener}
 * interface.
 */
public class ContactsFrag extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnMessageClickListener mListener;
    RecyclerView recyclerView;
    MainViewModel viewModel;
    ContactAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactsFrag() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContactsFrag newInstance(int columnCount) {
        ContactsFrag fragment = new ContactsFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ContactAdapter(getContext(), null);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
           recyclerView  = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            LoadContactsTask task = new LoadContactsTask();
            task.execute();
        }
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMessageClickListener) {
            mListener = (OnMessageClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMessageClickListener");
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
    public interface OnMessageClickListener {
        // TODO: Update argument type and name
        void OnMessageClick(EMMessage item);
    }
    class LoadContactsTask extends AsyncTask<Void,Void,LiveData<List<Contact>>>{

        @Override
        protected LiveData<List<Contact>> doInBackground(Void... voids) {
            LiveData<List<Contact>> all = ContactRepository.getInstance().getContacts(viewModel.getUsername().getValue());
            return all;
        }

        @Override
        protected void onPostExecute(LiveData<List<Contact>> contacts) {
            contacts.observe(getActivity(), new Observer<List<Contact>>() {
                @Override
                public void onChanged(@Nullable List<Contact> contacts) {
                    adapter.setContacts(contacts);
                }
            });
        }
    }
}
