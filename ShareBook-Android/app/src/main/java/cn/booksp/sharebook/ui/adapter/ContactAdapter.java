package cn.booksp.sharebook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.ui.activity.TalkActivity;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<Contact> contacts;
    Context context;

   public ContactAdapter(Context context, List<Contact> contacts) {
       this.context = context;
        this.contacts = contacts;
    }

    public void setContacts(List<Contact> contacts){
       this.contacts = contacts;
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.imageView.setImageResource(R.drawable.aboutme);
        holder.usernameView.setText(contact.getName());
        String first_message = contact.getFirstMessage() == null ? " " : contact.getFirstMessage() ;
        holder.lastMessageView.setText(first_message);
        holder.view.setOnClickListener(v->{
            Intent intent = new Intent(context, TalkActivity.class);
            intent.putExtra("to", contact.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
       if(contacts != null) {
           return contacts.size();
       }else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView usernameView;
        public final TextView lastMessageView;
        View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.avatar);
            usernameView = view.findViewById(R.id.username);
            lastMessageView = view.findViewById(R.id.last_message);
        }


    }
}
