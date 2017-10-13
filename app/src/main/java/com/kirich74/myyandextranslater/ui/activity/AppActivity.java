package com.kirich74.myyandextranslater.ui.activity;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirich74.myyandextranslater.R;
import com.kirich74.myyandextranslater.presenter.AppPresenter;
import com.kirich74.myyandextranslater.view.AppView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;


public class AppActivity extends MvpActivity implements AppView {

    public static final String TAG = "AppActivity";

    @InjectPresenter
    AppPresenter mAppPresenter;


    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, AppActivity.class);

        return intent;
    }

    @Override
    public void showFragment(final MvpFragment fragment) {
        Log.d("LOG", "showFragment");
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_translate:
                                mAppPresenter.showTranslateFragment();
                                break;
                            case R.id.bottom_favorites_and_history:
                                mAppPresenter.showFavoritesAndHistoryFragment();
                                break;
                            case R.id.bottom_settings:
                                mAppPresenter.showSettingsFragment();
                                break;
                        }
                        return true;
                    }
                });
//        mAppPresenter.showTranslateFragment();
    }

}
