package com.kirich74.myyandextranslater.ui.view;

import com.kirich74.myyandextranslater.R;
import com.kirich74.myyandextranslater.data.FavouritesDBContract;
import com.kirich74.myyandextranslater.databinding.FavouriteItemBinding;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kirill Pilipenko on 20.04.2017.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private Cursor mCursor;

    private OnActionListener mOnActionListener;

    public FavouritesAdapter(OnActionListener onActionListener, Cursor cursor) {
        mOnActionListener = onActionListener;
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.favourite_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FavouriteItemBinding itemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bindCursor(final Cursor cursor) {
            itemBinding.inputText.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBContract.Favourites.COLUMN_INPUT)
            ));
            itemBinding.outputText.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBContract.Favourites.COLUMN_OUTPUT)
            ));
            itemBinding.langs.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBContract.Favourites.COLUMN_LANG_FROM))
                    + '-' + cursor.getString(
                    cursor.getColumnIndexOrThrow(FavouritesDBContract.Favourites.COLUMN_LANG_TO)));
            itemBinding.favouriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnActionListener.onRemove(cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    FavouritesDBContract.Favourites.COLUMN_HASH)),
                            getAdapterPosition());
                }
            });
        }
    }


}
