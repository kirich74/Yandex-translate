package com.kirich74.myyandextranslater.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirich74.myyandextranslater.ui.fragment.FavoritesFragment;
import com.kirich74.myyandextranslater.ui.fragment.SettingsFragment;
import com.kirich74.myyandextranslater.ui.fragment.TranslateFragment;
import com.kirich74.myyandextranslater.view.AppView;

@InjectViewState
public class AppPresenter extends MvpPresenter<AppView> {
    TranslateFragment mTranslateFragment;
    FavoritesFragment mFavoritesAndHistoryFragment;
    SettingsFragment mSettingsFragment;

    public void showTranslateFragment (){
        if (mTranslateFragment == null)
        mTranslateFragment = TranslateFragment.newInstance();
        getViewState().showFragment(mTranslateFragment);
    }

    public void showFavoritesAndHistoryFragment (){
        if (mFavoritesAndHistoryFragment == null)
            mFavoritesAndHistoryFragment = FavoritesFragment.newInstance();
        getViewState().showFragment(mFavoritesAndHistoryFragment);
    }

    public void showSettingsFragment (){
        if (mSettingsFragment == null)
            mSettingsFragment = SettingsFragment.newInstance();
        getViewState().showFragment(mSettingsFragment);
    }

    @Override
    protected void onFirstViewAttach() {
        showTranslateFragment();
        super.onFirstViewAttach();
    }
}
