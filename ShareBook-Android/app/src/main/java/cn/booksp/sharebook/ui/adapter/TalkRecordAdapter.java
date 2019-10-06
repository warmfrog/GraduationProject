package cn.booksp.sharebook.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.booksp.sharebook.R;

public class TalkRecordAdapter extends RecyclerView.Adapter<TalkRecordAdapter.ViewHolder> {
    final static int USERA = 1;
    final static int USERB =2;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case USERA:
                return new ViewHolderA(LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_item_a,parent,false));
            case USERB:
                return new ViewHolderB(LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_item_b,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userView;
        private TextView messageView;
        ViewHolder(View view){
            super(view);

            userView = view.findViewById(R.id.user);
            messageView = view.findViewById(R.id.message);
        }
    }
    class ViewHolderA extends ViewHolder{
        ViewHolderA(View view){
            super(view);
        }
    }
    class ViewHolderB extends ViewHolder{
        ViewHolderB(View view){
            super(view);
        }
    }
}
