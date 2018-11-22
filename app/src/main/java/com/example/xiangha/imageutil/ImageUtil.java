package com.example.xiangha.imageutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ImageUtil {
    /**
     * @param context
     * @param url 图片链接
     * @param imgWidth 图片宽度
     * @param imgHeight 图片高度
     * @param tailorHeight 切图的高度
     * @param callback
     */
    public static void tailorImageByUrl(final Context context, final String url, final int imgWidth, final int imgHeight, final int tailorHeight, final OnResourceCallback callback) {
        if (context == null || TextUtils.isEmpty(url) || tailorHeight <= 0) {
            if (callback != null) {
                callback.onResource(null);
            }
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(context).load(url).downloadOnly(imgWidth, imgHeight).get();
                    if (callback != null) {
                        callback.onResource(tailorImage(file, tailorHeight));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static ArrayList<Bitmap> tailorImage(File file, int tailorHeight) {
        if (file == null && !file.exists()) {
            return null;
        }
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file), 1024);
            BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(bis, true);
            final int imgWidth = brd.getWidth();
            final int imgHeight = brd.getHeight();
            final int count = (int) Math.ceil(imgHeight * 1.00 / tailorHeight);
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            Rect rect = new Rect();
            int top = 0;
            int bottom = 0;
            for (int i = 0; i < count; i ++) {
                top = tailorHeight * i;
                bottom = top + tailorHeight;
                bottom = bottom >  imgHeight ? imgHeight : bottom;
                rect.set(0, top, imgWidth, bottom);
                Bitmap bitmap = brd.decodeRegion(rect, bfo);
                if (bitmaps == null) {
                    bitmaps = new ArrayList<>();
                }
                bitmaps.add(bitmap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmaps;
    }

    public interface OnResourceCallback {
        void onResource (ArrayList<Bitmap> bitmaps);
    }
}
