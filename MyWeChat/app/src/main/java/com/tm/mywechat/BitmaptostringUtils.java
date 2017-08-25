package com.tm.mywechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by SJL on 2017/6/11.
 */

public class BitmaptostringUtils {

    public static String bitmapToString(Context context, BitmapDrawable drawble) {
        // 保存
        // BitmapDrawable drawble = (BitmapDrawable) cv_head.getDrawable();
        Bitmap bitmap = drawble.getBitmap();

        // 第一步 将bitmap 转换成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);

        // 利用base64将字节数组转换成字符串
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        return imgString;

    }

}
