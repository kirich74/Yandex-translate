package com.kirich74.myyandextranslater.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirich74.myyandextranslater.R;
import com.kirich74.myyandextranslater.view.SettingsView;
import com.kirich74.myyandextranslater.presenter.SettingsPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class SettingsFragment extends MvpFragment implements SettingsView {

    public static final String TAG = "SettingsFragment";

    @InjectPresenter
    SettingsPresenter mSettingsPresenter;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
