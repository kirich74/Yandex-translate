package com.kirich74.myyandextranslater.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirich74.myyandextranslater.cloudclient.CloudClient;
import com.kirich74.myyandextranslater.cloudclient.ICloudClient;
import com.kirich74.myyandextranslater.cloudclient.LanguagesModel;
import com.kirich74.myyandextranslater.cloudclient.TranlateModel;
import com.kirich74.myyandextranslater.view.TranslateView;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView>  {

    public static final String TAG = "TranslatePresenter";

    static final String KEY
            = "trnsl.1.1.20170413T142503Z.bfda741011ebf301.90b39dd090ef46da0632c3ce1b5054f5bec8bf34";

    private static ICloudClient mICloudClient;

    private String langFrom = "auto";

    private String langTo;

    private String hash = "";

    private String lastTranslateLangFrom;

    private String lastTranslateLangTo;

    private String lastTranslateInput;

    private String lastTranslateOutput;

    private int positionLangFrom;

    private boolean isLangsAvaliable = false;

    public String getLastTranslateInput() {
        return lastTranslateInput;
    }

    public String getLastTranslateOutput() {
        return lastTranslateOutput;
    }

    public String getHash() {
        return hash;
    }

    public String getLastTranslateLangFrom() {
        return lastTranslateLangFrom;
    }

    public String getLastTranslateLangTo() {
        return lastTranslateLangTo;
    }

    public boolean getIsAvaliableLangs() {
        return isLangsAvaliable;
    }

    public int getPositionLangFrom() {
        return positionLangFrom;
    }

    public void setPositionLangFrom(final int positionLangFrom) {
        this.positionLangFrom = positionLangFrom;
    }

    public int getPositionLangTo() {
        return positionLangTo;
    }

    public void setPositionLangTo(final int positionLangTo) {
        this.positionLangTo = positionLangTo;
    }

    private int positionLangTo;

    private Map<String, String> langs;

    private String[] langsCodes;

    private String[] langsFrom, langsTo;

    public TranslatePresenter() {
        mICloudClient = CloudClient.getApi();
        langs = null;
    }

    public String[] getLangsFrom() {
        return langsFrom;
    }

    public String[] getLangsTo() {
        return langsTo;
    }

    public String getLangCode(int position) {
        return langsCodes[position];
    }

    public void setLangFrom(final String langFrom) {
        this.langFrom = langFrom;
    }

    public void setLangTo(final String langTo) {
        this.langTo = langTo;
    }

    public void detectAutoLang (String lang){
        langFrom = lang;
        for (int position = 0; position< langsCodes.length; position++){
            if (Objects.equals(langsCodes[position], lang)){
                getViewState().setLangFromSpinnerPosition(position + 1);
                break;
            }
        }
    }

    public void translate(@NonNull final String text) {
        String langsPair;
        if (langFrom == "auto") {
            langsPair = langTo;
        } else {
            langsPair = langFrom + '-' + langTo;
        }
        mICloudClient.getTranslate(KEY, text, langsPair).enqueue(new Callback<TranlateModel>() {
            @Override
            public void onResponse(Call<TranlateModel> call, Response<TranlateModel> response) {
                if (response.body() == null) {
                    getViewState().makeToast("empty input");
                    return;
                }
                switch (response.body().getCode()) {
                    case 200:
                        detectAutoLang(response.body().getLang().substring(0, 2));
                        lastTranslateLangFrom = response.body().getLang().substring(0, 2);
                        lastTranslateLangTo = response.body().getLang().substring(3, 5);
                        lastTranslateOutput = response.body().getText().get(0);
                        lastTranslateInput = text;
                        hash = lastTranslateInput+lastTranslateOutput+lastTranslateLangFrom+lastTranslateLangTo;
                        getViewState().showTranslate(getLastTranslateOutput());
                    case 401:
                        getViewState().makeToast("401 Error");
                    case 402:
                        getViewState().makeToast("402 Error");
                    case 404:
                        getViewState().makeToast("404 Error");
                    case 413:
                        getViewState().makeToast("413 Error");
                    case 422:
                        getViewState().makeToast("422 Error");
                    case 501:
                        getViewState().makeToast("501 Error");
                }
            }

            @Override
            public void onFailure(Call<TranlateModel> call, Throwable t) {
                getViewState().makeToast("An error occurred during networking");
            }
        });
    }

    public void getAvaliableLanguages(String ui) {
        mICloudClient.getLangs(KEY, ui).enqueue(new Callback<LanguagesModel>() {
            @Override
            public void onResponse(Call<LanguagesModel> call, Response<LanguagesModel> response) {
                if (response.body().getLangs() != null) {
                    langs = response.body().getLangs();
                    langsCodes = langs.keySet().toArray(new String[langs.size()]);
                    langTo = langsCodes[0];
                    positionLangTo = 0;
                    positionLangFrom = 0;
                    langsTo = langs.values()
                            .toArray(new String[langs.size()]);
                    langsFrom = new String[langs.size() + 1];
                    langsFrom[0] = "auto";
                    for (int i = 0; i < langsTo.length; ++i) {
                        langsFrom[i + 1] = langsTo[i];
                    }
                    isLangsAvaliable = true;
                    getViewState().bindLangsWithSpinners();
                }
            }

            @Override
            public void onFailure(final Call<LanguagesModel> call, final Throwable t) {

            }
        });
    }

    @Override
    protected void onFirstViewAttach() {
        getAvaliableLanguages("ru");
        super.onFirstViewAttach();
    }

}
