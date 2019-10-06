package cn.booksp.sharebook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.booksp.sharebook.dummy.DummyBookInfo;
import cn.booksp.sharebook.ui.fragments.BookListFrag;
import cn.booksp.sharebook.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyBookInfo.DummyItem} and makes a call to the
 * specified {@link BookListFrag.OnBookListClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

    private final List<DummyBookInfo.DummyItem> mValues;
    private final BookListFrag.OnBookListClickListener mListener;

    public BookItemAdapter(List<DummyBookInfo.DummyItem> items, BookListFrag.OnBookListClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.book_image.setImageResource(mValues.get(position).book_image);
        holder.book_name.setText(mValues.get(position).book_name);
        holder.book_isbn.setText("作者:" + mValues.get(position).book_author);
//        holder.book_price.setText("价格:" + mValues.get(position).book_price);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBookListClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public  final ImageView book_image;
        public final TextView book_name;
        public final TextView book_isbn;
//        public final TextView book_price;
        public DummyBookInfo.DummyItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            book_image = (ImageView) view.findViewById(R.id.book_image);
            book_name = (TextView) view.findViewById(R.id.book_name);
            book_isbn= (TextView) view.findViewById(R.id.book_isbn);
//            book_price = (TextView) view.findViewById(R.id.book_price);

        }
    }
}
