package com.kirich74.myyandextranslater.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface TranslateView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showTranslate(String text);

    @StateStrategyType(SkipStrategy.class)
    void bindLangsWithSpinners();

    @StateStrategyType(SkipStrategy.class)
    void makeToast(String s);

    @StateStrategyType(SkipStrategy.class)
    void setLangFromSpinnerPosition(int position);
}
