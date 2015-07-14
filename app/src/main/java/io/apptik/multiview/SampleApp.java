package io.apptik.multiview;

import android.app.Application;

import io.apptik.multiview.common.BitmapLruCache;


public class SampleApp extends Application {

    BitmapLruCache bitmapLruCache;
    @Override
    public void onCreate() {
        super.onCreate();

    }


}
