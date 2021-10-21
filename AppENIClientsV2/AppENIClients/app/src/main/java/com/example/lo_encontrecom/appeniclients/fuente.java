package com.example.lo_encontrecom.appeniclients;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by javi on 20/01/2018.
 */

public class fuente extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fuentes/Verdana.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
