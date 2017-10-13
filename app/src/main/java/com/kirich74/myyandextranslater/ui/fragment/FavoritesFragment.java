package com.kirich74.myyandextranslater.ui.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirich74.myyandextranslater.R;
import com.kirich74.myyandextranslater.data.FavouritesDBContract;
import com.kirich74.myyandextranslater.data.FavouritesDBSQLiteHelper;
import com.kirich74.myyandextranslater.presenter.FavoritesPresenter;
import com.kirich74.myyandextranslater.ui.view.FavouritesAdapter;
import com.kirich74.myyandextranslater.ui.view.OnActionListener;
import com.kirich74.myyandextranslater.view.FavoritesView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class FavoritesFragment extends MvpFragment implements FavoritesView, OnActionListener {

    public static final String TAG = "FavoritesFragment";
    RecyclerView mRecyclerView;

    @InjectPresenter
    FavoritesPresenter mFavoritesPresenter;

    private FavouritesAdapter mFavoritesAdapter;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favourites_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SQLiteDatabase database = new FavouritesDBSQLiteHelper(getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery(FavouritesDBContract.SELECT_FAVOURITES, new String[]{});
        mFavoritesAdapter = new FavouritesAdapter(this, cursor);
        mRecyclerView.setAdapter(mFavoritesAdapter);
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void deleteFromDB(String hash) {
        SQLiteDatabase database = new FavouritesDBSQLiteHelper(getContext()).getWritableDatabase();

        database.delete(FavouritesDBContract.Favourites.TABLE_NAME,
                FavouritesDBContract.Favourites.COLUMN_HASH + " = ?",
                new String[]{hash});
        database.close();
    }
    @Override
    public void onRemove(final String hash, final int position) {
        mFavoritesAdapter.notifyItemRemoved(position);
        deleteFromDB(hash);
        mFavoritesAdapter.notifyDataSetChanged();
    }
}
