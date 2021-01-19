package com.z_martin.mylibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidx.annotation.DrawableRes;

/**
 * Bitmap处理工具类
 */

public class BitmapUtils {
    /**
     * 将一个view转换成bitmap位图
     * @param view 要转换的View
     * @return view转换的bitmap
     */
    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * 获取模糊虚化的bitmap
     * @param context
     * @param bitmap 要模糊的图片
     * @param radius 模糊等级 >=0 && <=25
     * @return
     */
    public static Bitmap getBlurBitmap(Context context, Bitmap bitmap, int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return blurBitmap(context, bitmap, radius);
        }
        return bitmap;
    }

    /**
     * android系统的模糊方法
     *
     * @param bitmap 要模糊的图片
     * @param radius 模糊等级 >=0 && <=25
     */
    private static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap
                    .Config.ARGB_8888);
            //Instantiate a new Renderscript
            RenderScript rs = RenderScript.create(context);
            //Create an Intrinsic Blur Script using the Renderscript
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
            //Set the radius of the blur
            blurScript.setRadius(radius);
            //Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap);
            //recycle the original bitmap
            bitmap.recycle();
            //After finishing everything, we destroy the Renderscript.
            rs.destroy();
            return outBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * 获取drawable资源文件图片bitmap
     *
     * @param context context
     * @param id      资源文件id
     * @return 资源文件对应图片bitmap
     */
    public static Bitmap getBitmap(Context context, @DrawableRes int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * 转换bitmap宽高
     *
     * @param bitmap    bitmap
     * @param newWidth  新的bitmap宽度
     * @param newHeight 新的bitmap高度
     * @return 转换宽高后的bitmap
     */
    public static Bitmap conversionBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap b = bitmap;
        int width = b.getWidth();
        int height = b.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
    }

    /**
     * 旋转图片
     * @param angle 旋转角度
     * @param bitmap 要处理的Bitmap
     * @return 处理后的Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap)
    {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }


    /**
     *  图片镜像
     * @param bmp 要处理的Bitmap
     * @return
     */
    public static Bitmap convertBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);

        return convertBmp;
    }

    /**
     * 保存Bitmap到sdcard
     * @param dir 文件路径
     * @param bm 文件
     * @param filename 文件名称
     * @param quantity 数量
     * @param recyle 
     * @return
     */
    public static boolean saveBmpToSd(String dir, Bitmap bm, String filename,
                                      int quantity, boolean recyle) {
        boolean ret = true;
        if (bm == null) {
            return false;}
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        File file = new File(dir + filename);
        OutputStream outStream = null;
        try {
            file.createNewFile();
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, quantity, outStream);
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        } finally {
            try {
                if (outStream != null) outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (recyle && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
        }
        return ret;
    }
}
