package com.tm.mywechat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;

import com.tm.mywechat.main_page.AddFragment;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by SJL on 2017/6/11.
 */

public class FragmentUtils {

    public static void pickAlbum(AddFragment fragment, int reqCode) {

        Intent intent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        fragment.startActivityForResult(intent, reqCode);

    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }


    public static Bitmap decodeFileAndCompressBy(String imgpath, int reqWidth,
                                                 int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgpath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgpath, options);

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                            / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static String bitmapToString(Bitmap bitmap) {
        // 保存
        // BitmapDrawable drawble = (BitmapDrawable) cv_head.getDrawable();

        // 第一步 将bitmap 转换成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);

        // 利用base64将字节数组转换成字符串
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        Log.d("aaaaaaaaa",imgString);
        return imgString;

    }
    public static Bitmap stringToImage(String imgStr) {
        //Log.d("aaaaaaaaa",imgStr);
        byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        // 生成jpeg图片
        return bitmap;

    }


}
