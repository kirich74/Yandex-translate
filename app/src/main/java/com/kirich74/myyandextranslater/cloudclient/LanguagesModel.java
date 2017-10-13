package com.kirich74.myyandextranslater.cloudclient;

/**
 * Created by Kirill Pilipenko on 19.04.2017.
 */

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguagesModel {

    @SerializedName("dirs")
    @Expose
    private List<String> dirs = null;

    @SerializedName("langs")
    @Expose
    private Map<String, String> langs;

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

   public Map<String, String> getLangs() {
        return langs;
    }

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }
}