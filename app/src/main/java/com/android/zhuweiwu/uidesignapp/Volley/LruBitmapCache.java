package com.android.zhuweiwu.uidesignapp.Volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by zhuweiwu on 5/15/2016.
 */
public class LruBitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

    public static int getDefaultLruCacheSize(){

        final int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize=maxMemory/8;
        return cacheSize;
    }

    public LruBitmapCache(){
        this(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes){
        super(sizeInKiloBytes);
    }

    @Override
    public int sizeOf(String key,Bitmap Value){
        return Value.getRowBytes()*Value.getHeight()/1024;
    }

    @Override
    public Bitmap getBitmap(String url){
        return get(url);
    }

    @Override
    public void putBitmap(String url,Bitmap bitmap){
        put(url,bitmap);
    }
}
