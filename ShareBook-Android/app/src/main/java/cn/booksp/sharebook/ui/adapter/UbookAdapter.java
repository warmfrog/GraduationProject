package cn.booksp.sharebook.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.ui.activity.OrderActivity;
import cn.booksp.sharebook.ui.fragments.MyUbookFrag;

/**
 * Created by warmfrog on 2019/3/9.
 */

public class UbookAdapter extends PagedListAdapter<Ubook, UbookAdapter.UbookViewHolder> {
    private final MyUbookFrag.OnMyUbookListener mListener;
    private final Context context;
    private static DiffUtil.ItemCallback<Ubook> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Ubook>() {
                @Override
                public boolean areItemsTheSame(Ubook oldItem, Ubook newItem) {
                    return oldItem.getIsbn13() == newItem.getIsbn13();
                }

                @Override
                public boolean areContentsTheSame(Ubook oldItem, Ubook newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public UbookAdapter(MyUbookFrag.OnMyUbookListener listener, Context context) {
        super(DIFF_CALLBACK);
        this.mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public UbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        Log.d("UbookViewHolder","加载Ubook Item 视图");
        return new UbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UbookViewHolder holder, int position) {
        holder.ubook = getItem(position);

        if (holder.ubook != null) {
            Log.d("图书信息", holder.ubook.toString());
            holder.bindTo(holder.ubook);
        } else {
            holder.clear();
        }
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(null != mListener){
                    mListener.onMyUbookClick(holder.ubook);
                    Log.d("Ubook Item","点击事件");
                }
            }
        });
        Log.d("onBindViewHolder","绑定到视图");
    }

    public class UbookViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView book_image;
        public final TextView book_name;
        public final TextView book_isbn;
        public final TextView rent_price;
        public final TextView sell_price;

        Ubook ubook;

        public UbookViewHolder(View view) {
            super(view);
            mView = view;
            book_image = (ImageView) view.findViewById(R.id.book_image);
            book_name = (TextView) view.findViewById(R.id.book_name);
            book_isbn = (TextView) view.findViewById(R.id.book_isbn);
            rent_price = (TextView) view.findViewById(R.id.rentPrice);
            sell_price = (TextView) view.findViewById(R.id.sellPrice);

            Log.d("构造器", "UbookViewHolder");
        }

        public void bindTo(Ubook ubook) {
            String image = ubook.getImage();
            if(image!=null){
                Glide.with(book_image).load(image).into(book_image);
            }else {
                book_image.setImageResource(R.drawable.default_book);
            }
            book_name.setText("书名: " + ubook.getBookName());
            book_isbn.setText("ISBN: " + ubook.getIsbn13() );
            rent_price.setText(ubook.getRentPrice().toString());
            sell_price.setText(ubook.getSellPrice().toString());
        }

        public void clear() {

        }

    }
}
