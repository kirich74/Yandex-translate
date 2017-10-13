package com.kirich74.myyandextranslater.ui.fragment;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.kirich74.myyandextranslater.R;
import com.kirich74.myyandextranslater.data.FavouritesDBContract;
import com.kirich74.myyandextranslater.data.FavouritesDBSQLiteHelper;
import com.kirich74.myyandextranslater.presenter.TranslatePresenter;
import com.kirich74.myyandextranslater.view.TranslateView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TranslateFragment extends MvpFragment implements TranslateView {

    public static final String TAG = "TranslateFragment";

    @InjectPresenter(type = PresenterType.GLOBAL, tag = TranslatePresenter.TAG)
    TranslatePresenter mTranslatePresenter;

    private Button translateButton;

    private EditText inputText;

    private TextView translatedText;

    private Spinner mLangToSpinner;

    private Spinner mLangFromSpinner;

    private ImageButton swapButton;

    private ImageView bookmark;

    private ImageView bookmarkBordered;

    public static TranslateFragment newInstance() {
        TranslateFragment fragment = new TranslateFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void showTranslate(String text) {
        translatedText.setText(text);
        SQLiteDatabase database = new FavouritesDBSQLiteHelper(getContext()).getReadableDatabase();
        String[] selectionArgs = {"%" + mTranslatePresenter.getHash() + "%"};
        Cursor cursor = database.rawQuery(FavouritesDBContract.SELECT_FAVOURITE_WITH_HASH, selectionArgs);
        if (cursor.getCount() == 0) {
            bookmark.setVisibility(View.INVISIBLE);
            bookmarkBordered.setVisibility(View.VISIBLE);
        } else {
            bookmark.setVisibility(View.VISIBLE);
            bookmarkBordered.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void bindLangsWithSpinners() {
        ArrayAdapter<String> firstLangAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, mTranslatePresenter.getLangsFrom());
        firstLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLangFromSpinner.setAdapter(firstLangAdapter);

        ArrayAdapter<String> secondLangAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, mTranslatePresenter.getLangsTo());
        firstLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLangToSpinner.setAdapter(secondLangAdapter);
        mLangFromSpinner.setSelection(mTranslatePresenter.getPositionLangFrom());
        mLangToSpinner.setSelection(mTranslatePresenter.getPositionLangTo());
        AdapterView.OnItemSelectedListener itemSelectedListener
                = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent == mLangFromSpinner) {
                    mTranslatePresenter.setPositionLangFrom(position);
                    if (position == 0) {
                        mTranslatePresenter.setLangFrom("auto");
                    } else {
                        mTranslatePresenter
                                .setLangFrom(mTranslatePresenter.getLangCode(position - 1));
                    }
                } else {
                    mTranslatePresenter.setPositionLangTo(position);
                    mTranslatePresenter.setLangTo(mTranslatePresenter.getLangCode(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        mLangFromSpinner.setOnItemSelectedListener(itemSelectedListener);
        mLangToSpinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void makeToast(final String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
    }

    @Override
    public void setLangFromSpinnerPosition(final int position) {
        mLangFromSpinner.setSelection(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        mLangFromSpinner = (Spinner) view.findViewById(R.id.first_lang_spinner);
        mLangToSpinner = (Spinner) view.findViewById(R.id.second_lang_spinner);
        swapButton = (ImageButton) view.findViewById(R.id.swap_lang_button);
        inputText = (EditText) view.findViewById(R.id.inputText);
        bookmarkBordered = (ImageView) view.findViewById(R.id.ic_bookmark_border);
        bookmark = (ImageView) view.findViewById(R.id.ic_bookmark);
        translatedText = (TextView) view.findViewById(R.id.translated_text);
        translateButton = (Button) view.findViewById(R.id.translate_button);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLangFromSpinner.getSelectedItemPosition() != 0) {
                    int t = mLangFromSpinner.getSelectedItemPosition() - 1;
                    mLangFromSpinner.setSelection(mLangToSpinner.getSelectedItemPosition() + 1);
                    mLangToSpinner.setSelection(t);
                }

            }
        });
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.getText() != null) {
                    mTranslatePresenter.translate(inputText.getText().toString());
                }
            }
        });
        bookmarkBordered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (translatedText.getText() != null) {
                    bookmarkBordered.setVisibility(View.INVISIBLE);
                    bookmark.setVisibility(View.VISIBLE);
                    saveToDB();
                }
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setVisibility(View.INVISIBLE);
                bookmarkBordered.setVisibility(View.VISIBLE);
                deleteFromDB();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTranslatePresenter != null && mTranslatePresenter.getIsAvaliableLangs()) {
            bindLangsWithSpinners();
        }
    }

    private void saveToDB() {
        SQLiteDatabase database = new FavouritesDBSQLiteHelper(getContext()).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavouritesDBContract.Favourites.COLUMN_INPUT,
                mTranslatePresenter.getLastTranslateInput());
        values.put(FavouritesDBContract.Favourites.COLUMN_OUTPUT,
                mTranslatePresenter.getLastTranslateOutput());
        values.put(FavouritesDBContract.Favourites.COLUMN_LANG_FROM,
                mTranslatePresenter.getLastTranslateLangFrom());
        values.put(FavouritesDBContract.Favourites.COLUMN_LANG_TO,
                mTranslatePresenter.getLastTranslateLangTo());
        values.put(FavouritesDBContract.Favourites.COLUMN_HASH, mTranslatePresenter.getHash());

        database.insert(FavouritesDBContract.Favourites.TABLE_NAME, null, values);
    }

    private void deleteFromDB() {
        SQLiteDatabase database = new FavouritesDBSQLiteHelper(getContext()).getWritableDatabase();

        database.delete(FavouritesDBContract.Favourites.TABLE_NAME,
                FavouritesDBContract.Favourites.COLUMN_HASH + " = ?",
                new String[]{mTranslatePresenter.getHash()});
    }
}
