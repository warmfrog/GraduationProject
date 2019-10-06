package cn.booksp.sharebook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Message;
import cn.booksp.sharebook.domain.Order;
import cn.booksp.sharebook.dummy.DummyMessage.DummyItem;
import cn.booksp.sharebook.ui.fragments.ContactsFrag;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ContactsFrag.OnMessageClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    static final int ME=0;
    static final int OTHER=1;
    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case ME:
                 view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_item, parent, false);
                 break;
            case OTHER:
                 view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_item2, parent, false);
                 break;
        }

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.message_item, parent, false);
//        return new ViewHolder(view);

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String from = message.getFrom();
        String username = BasicApp.getUsername();
        if(username.equals(from)){
            return OTHER;
        }else {
            return ME;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = messages.get(position);
        holder.imageView.setImageResource(R.drawable.aboutme);
        holder.usernameView.setText(messages.get(position).getFrom());
        holder.messageView.setText(messages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        public final TextView usernameView;
        public final TextView messageView;
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.avatar);
            usernameView = view.findViewById(R.id.username);
            messageView = view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + messageView.getText() + "'";
        }
    }
}
