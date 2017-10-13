package com.kirich74.myyandextranslater.data;

import android.provider.BaseColumns;

/**
 * Created by Kirill Pilipenko on 09.06.2017.
 */

public final class FavouritesDBContract {
    private FavouritesDBContract(){}

    public static final String SELECT_FAVOURITES = "SELECT * " +
            "FROM " + Favourites.TABLE_NAME;

    public static final String SELECT_FAVOURITE_WITH_HASH = "SELECT " +Favourites.COLUMN_HASH +
            " FROM " + Favourites.TABLE_NAME + " WHERE " + Favourites.COLUMN_HASH + " like ?";

    public static class Favourites implements BaseColumns {
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_LANG_FROM = "lang_from";
        public static final String COLUMN_LANG_TO = "lang_to";
        public static final String COLUMN_INPUT = "input";
        public static final String COLUMN_OUTPUT = "output";
        public static final String COLUMN_HASH = "hash";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HASH + " TEXT, " +
                COLUMN_LANG_FROM + " TEXT, " +
                COLUMN_LANG_TO + " TEXT, " +
                COLUMN_INPUT + " TEXT, " +
                COLUMN_OUTPUT + " TEXT" + ")";
    }
}
