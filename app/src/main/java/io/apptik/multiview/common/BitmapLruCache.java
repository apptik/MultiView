/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

