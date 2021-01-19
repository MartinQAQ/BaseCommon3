package com.z_martin.mylibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.widgets.RoundedCornersTransform;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * Glide 工具类
 */
public class GlideUtils {
    private static final String PREFIX = "http";
    public static final String SD_PATH = "static";

    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView 控件
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.white) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    
    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView 控件
     */
    public static void loadImage(Context context, Uri url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.white) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    
    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView 控件
     */
    public static void loadImageCircular(Context context, String url, ImageView imageView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.white) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    
    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param resId       图片资源id
     * @param imageView 控件
     */
    public static void loadImage(Context context, int resId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.white) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(resId).apply(options).into(imageView);
    }
    
    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param pathUrl   图片url
     * @param imageView 控件
     */
    public static void loadImage(Context context, File pathUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.white) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(pathUrl).apply(options).into(imageView);
    }

    /**
     * 加载图片-错误图片
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView view
     * @param errorView 错误图
     */
    public static void loadImage(Context context, String url, ImageView imageView, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(errorView) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
    
    /**
     * 加载图片-错误图片
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView view
     * @param errorView 错误图
     */
    public static void loadImage2(Context context, String url, ImageView imageView, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(errorView) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载图片-错误图片
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView view
     * @param errorView 错误图
     */
    public static void loadImage3(Context context, String url, ImageView imageView, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .optionalCenterCrop()
                .placeholder(errorView) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * @param context     上下文
     * @param url         图片url
     * @param imageView   控件
     * @param placeholder 占位图
     * @param errorView   错误图
     */
    public static void loadImage(Context context, String url, ImageView imageView, int placeholder, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     */
    public static void loadImageRadius(Context context, String url, ImageView imageView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        loadImageRadius(context, url, imageView, 5, R.color.white, R.color.white);
    }

    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     * @param radius 圆角大小
     */
    public static void loadImageRadius(Context context, String url, ImageView imageView, int radius) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        loadImageRadius(context, url, imageView, radius, R.color.white, R.color.white);
    }
    
    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     * @param radius 圆角大小
     * @param placeholder 占位图
     * @param errorView 错误图
     */
    public static void loadImageRadius(Context context, String url, ImageView imageView, int radius, int placeholder, int errorView) {
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(DisplayUtils.dp2px(radius));
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(placeholder) // 占位图
                .error(errorView) //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);   
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     * @param radius 圆角大小
     */
    public static void loadImageLeftRadius(Context context, String url, ImageView imageView, int radius) {
        //设置图片圆角角度
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtils.dp2px(radius));
        transform.setNeedCorner(true, false, true, false);
        
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(transform)
                .placeholder(R.color.white) // 占位图
                .error(R.color.white) //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    
    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     * @param radius 圆角大小
     */
    public static void loadImageLeftRadius(Context context, int url, ImageView imageView, int radius) {
        //设置图片圆角角度
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtils.dp2px(radius));
        transform.setNeedCorner(true, false, true, false);
        
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(transform)
                .placeholder(R.color.white) // 占位图
                .error(R.color.white) //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    
    /**
     * 加载圆角图片
     * @param context 上下文
     * @param url 图片地址
     * @param imageView 图片
     * @param radius 圆角大小
     */
    public static void loadImageTopRadius(Context context, int url, ImageView imageView, int radius) {
        //设置图片圆角角度
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtils.dp2px(radius));
        transform.setNeedCorner(true, true, false, false);
        
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(transform)
                .placeholder(R.color.white) // 占位图
                .error(R.color.white) //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    
    
    
    public static void loadImageAuto(Context context, String url, ImageView imageView) {
        loadImageAuto(context, url, imageView, R.color.white, R.color.white);
    }
    
    public static void loadImageAuto(Context context, String url, ImageView imageView, int placeholder, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
//                .fitCenter()
//                .
                .placeholder(placeholder) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();

                float scale = (float) vw / (float) resource.getIntrinsicWidth();

                int vh = Math.round(resource.getIntrinsicHeight() * scale);

                params.height= vh + imageView.getPaddingTop() + imageView.getPaddingBottom();

                imageView.setLayoutParams(params);
                return false;
            }
        }).into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       图片urk
     * @param imageView 控件
     * @param errorView 错误图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(errorView)
                .error(errorView)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * banner图专用
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView 控件
     */
    public static void loadBanner(Context context, String url, ImageView imageView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        //设置图片圆角角度
        RoundedCornersTransform transform = new RoundedCornersTransform(context, DisplayUtils.dp2px(20));
        transform.setNeedCorner(false, false, true, true);

        //通过RequestOptions扩展功能
//        RequestOptions options = RequestOptions.bitmapTransform(transform)
//                .placeholder(R.color.white) // 占位图
//                .error(R.color.white) //错误图
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context)
//                .load(url)
//                .apply(options)
//                .into(imageView);
        RequestOptions options = RequestOptions.bitmapTransform(transform)
                .placeholder(R.mipmap.banner) //占位图
                .error(R.mipmap.banner)       //错误图
//                 .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
//                .transform(new RoundedCorners(10));
        Glide.with(context).load(url).apply(options).into(imageView);
//
//        RequestOptions options = RequestOptions.bitmapTransform(transform)
//                .placeholder(R.color.white) // 占位图
//                .error(R.color.white) //错误图
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(context)
//                .load(url)
//                .apply(options)
//                .into(imageView);
    }

    public static void loadImage(Context context, Object imgUrl, SimpleTarget<Drawable> target, int errorView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(errorView) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(target);
    }

    /**
     * 加载图片-错误图片
     *
     * @param context   上下文
     * @param url       图片url
     * @param imageView view
     * @param errorView 错误图
     */
    public static void loadImage6(Context context, String url, ImageView imageView, int errorView) {
        if(!StringUtils.isEmpty(url) && url.startsWith(SD_PATH)) {
            url = AppConfig.getPhotoUrl() + url;
        }
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(errorView) //占位图
                .error(errorView)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 清理磁盘缓存--需要在子线程中执行
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        //理磁盘缓存 需要在子线程中执行
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                });
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清理内存缓存--可以在UI主线程中进行
     * @param mContext 上下文
     */
    public static void clearMemory(Context mContext) {
        try {
            // 只能在主线程执行
            if (Looper.myLooper() == Looper.getMainLooper()) { 
                Glide.get(mContext).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide图片缓存大小
     * @return
     */
    public static String getCacheSize() {
        try {
            return StringUtils.getFormatSize(StringUtils.getFolderSize(
                    Glide.getPhotoCacheDir(AppUtils.getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *  暂停加载
     * @param context 上下文
     */
    public static void pauseLoad(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 重新开始加载
     * @param context 上下文
     */
    public static void resumeLoad(Context context) {
        Glide.with(context).resumeRequests();
    }
}
