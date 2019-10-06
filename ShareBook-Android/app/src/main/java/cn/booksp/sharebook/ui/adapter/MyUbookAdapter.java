package cn.booksp.sharebook.ui.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.dummy.DummyContent.DummyItem;
import cn.booksp.sharebook.repository.BookRepository;
import cn.booksp.sharebook.repository.UbookRepository;
import cn.booksp.sharebook.repository.dao.FileDao;
import cn.booksp.sharebook.ui.fragments.MyUbookFrag.OnMyUbookListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnMyUbookListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUbookAdapter extends RecyclerView.Adapter<MyUbookAdapter.ViewHolder> {
    static BookRepository bookRepository;
    static UbookRepository ubookRepository;
    private final List<Ubook> ubooks;
    private final OnMyUbookListener mListener;
    private final Context appContext;
    FileDao fileDao;

    public MyUbookAdapter(Context appContext, List<Ubook> ubooks, OnMyUbookListener listener) {
        this.appContext = appContext;
        fileDao = FileDao.getInstance(appContext);
        bookRepository = BookRepository.getInstance();
        ubookRepository = UbookRepository.getInstance();
        this.ubooks = ubooks;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myubook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.ubook = ubooks.get(position);
        if (holder.ubook != null) {
            if (holder.ubook.getImage() != null) {
                Glide.with(holder.bookImageView).load(holder.ubook.getImage()).into(holder.bookImageView);
                Log.d("图片地址", holder.ubook.getImage());
            } else {
                holder.bookImageView.setImageResource(R.drawable.default_book);
            }

            holder.bookNameView.setText("书名: 《" + ubooks.get(position).getBookName() + "》");
            holder.sellPriceView.setText(ubooks.get(position).getSellPrice().toString());
            holder.rentPriceView.setText(ubooks.get(position).getRentPrice().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            holder.releaseTimeView.setText("发布时间: " + simpleDateFormat.format(ubooks.get(position).getReleaseTime()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onMyUbookClick(holder.ubook);
                    }
                }
            });

            holder.manageCheckBox.setOnCheckedChangeListener((v, checked) -> {
                if (checked) {
                    Snackbar.make(holder.mView, "确定要删除" + ubooks.get(position).getBookName() + "吗？", Snackbar.LENGTH_LONG)
                            .setAction("删除", e -> {
                                DeleteUbookTask deleteUbookTask = new DeleteUbookTask(holder.mView, ubooks.get(position));
                                deleteUbookTask.execute();
                            })
                            .show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null != ubooks) {
            return ubooks.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView bookImageView;
        public final TextView bookNameView;
        public final TextView releaseTimeView;
        public final TextView sellPriceView;
        public final TextView rentPriceView;
        public final CheckBox manageCheckBox;
        Ubook ubook;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            bookImageView = view.findViewById(R.id.book_image);
            bookNameView = view.findViewById(R.id.book_name);
            releaseTimeView = view.findViewById(R.id.release_time);
            sellPriceView = view.findViewById(R.id.sellPrice);
            rentPriceView = view.findViewById(R.id.rentPrice);
            manageCheckBox = view.findViewById(R.id.manageCheckBox);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    class DeleteUbookTask extends AsyncTask<Void, Void, Boolean> {
        private Ubook ubook;
        private View view;

        public DeleteUbookTask(View view, Ubook ubook) {
            this.view = view;
            this.ubook = ubook;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return ubookRepository.delete(ubook);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Snackbar.make(view, "删除" + ubook.getBookName() + "成功", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
