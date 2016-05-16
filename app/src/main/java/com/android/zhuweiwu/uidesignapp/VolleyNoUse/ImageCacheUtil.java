package com.android.zhuweiwu.uidesignapp.VolleyNoUse;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.android.zhuweiwu.uidesignapp.DiskLruCache.DiskLruCache;
import com.android.zhuweiwu.uidesignapp.MD5.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhuweiwu on 5/15/2016.
 */
public class ImageCacheUtil implements ImageLoader.ImageCache {

    private static final String TAG = "ImageCacheUtil";

    //缓存类
    private static LruCache<String, Bitmap> mLruCache;

    private static DiskLruCache mDiskLruCache;

    //磁盘缓存大小
    private static final int DISKMAXSIZE = 10 * 1024 * 1024;


    public ImageCacheUtil(Context context) {
        // 获取应用可占内存的1/8作为缓存
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        // 实例化LruCaceh对象
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        try {
            // 获取DiskLruCahce对象
            //mDiskLruCache = DiskLruCache.open(getDiskCacheDir(MyApplication.newInstance(), "xxxxx"), getAppVersion(MyApplication.newInstance()), 1, DISKMAXSIZE);
            mDiskLruCache = DiskLruCache.open(getDiskCacheDir(context.getApplicationContext(), "xxxxx"), getAppVersion(context), 1, DISKMAXSIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓存（内存缓存，磁盘缓存）中获取Bitmap
     */
    @Override
    public Bitmap getBitmap(String url) {
        if (mLruCache.get(url) != null) {
            // 从LruCache缓存中取
            Log.i(TAG, "从LruCahce获取");
            return mLruCache.get(url);
        } else {
            String key = MD5Utils.md5(url);
            try {
                if (mDiskLruCache.get(key) != null) {
                    // 从DiskLruCahce取
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    Bitmap bitmap = null;
                    if (snapshot != null) {
                        bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                        // 存入LruCache缓存
                        mLruCache.put(url, bitmap);
                        Log.i(TAG,"从DiskLruCahce获取");
                    }
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 存入缓存（内存缓存，磁盘缓存）
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        // 存入LruCache缓存
        mLruCache.put(url, bitmap);

        Log.i(TAG, "put in cache");

        // 判断是否存在DiskLruCache缓存，若没有存入
        String key = MD5Utils.md5(url);

        Log.i(TAG, "MD5 : " + key);

        try {
            if (mDiskLruCache.get(key) == null) {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 该方法会判断当前sd卡是否存在，然后选择缓存地址
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;




        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        Log.i(TAG, "Path : " + cachePath + File.separator + uniqueName);

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
