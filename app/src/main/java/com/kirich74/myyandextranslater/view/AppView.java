package com.kirich74.myyandextranslater.view;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AppView extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void showFragment (MvpFragment fragment);
}
