package io.apptik.multiview.common;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class BitmapLruCache extends LruCache<Uri, Bitmap> {

    private static BitmapLruCache inst;

    public static BitmapLruCache get() {
        if(inst==null) {
            inst = new BitmapLruCache();
        }
        return inst;
    }

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }


    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    public BitmapLruCache() {
        this(getDefaultLruCacheSize());
    }

    @Override
    protected int sizeOf(Uri key, Bitmap value) {
        return value.getByteCount() / 1024;
    }


    public Bitmap getBitmap(Uri uri, Context ctx, int maxW, int maxH) {
        Bitmap res = get(uri);
        if (res == null) {
            InputStream stream = null;
            try {
                stream = ctx.getContentResolver().openInputStream(uri);

                res = DecodeUtils.decode(ctx, uri, maxW, maxH);
            } catch (Exception e) {
                Log.w("BitmapLruCache", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("BitmapLruCache", "Unable to close content: " + uri, e);
                    }
                }
            }

            putBitmap(uri, res);
        }
        return res;
    }


    public void putBitmap(Uri url, Bitmap bitmap) {
        if(url==null || bitmap==null) return;
        put(url, bitmap);
    }


}

