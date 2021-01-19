package com.z_martin.mylibrary.base.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.vise.log.ViseLog;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.annotations.ContentView;
import com.z_martin.mylibrary.annotations.DialogGravity;
import com.z_martin.mylibrary.annotations.DialogHeight;
import com.z_martin.mylibrary.annotations.DialogWidth;
import com.z_martin.mylibrary.annotations.ResId;
import com.z_martin.mylibrary.base.fast.FastBaseAdapter;

import java.lang.reflect.Method;

import androidx.fragment.app.Fragment;

/**
 * @ describe:注入管理
 * @ author: Martin
 * @ createTime: 2019/3/27 20:55
 * @ version: 1.0
 */
public class InjectManager {

    /**
     *  activity注入
     * @param activity activity
     */
    public static void inject(Activity activity) {
        // 布局注入
        injectLayout(activity);
    }
    /**
     *  fragment注入
     * @param fragment fragment
     */
    public static int inject(Fragment fragment) {
        // 布局注入
        return injectLayout(fragment);
    }

    /**
     *  adapter注入
     * @param adapter adapter
     */
    public static int inject(FastBaseAdapter adapter) {
        // 布局注入
       return injectLayout(adapter);
    }

    /**
     *  dialog注入
     * @param dialog 对话框
     * @param context 上下文
     */
    public static void inject(Dialog dialog, Context context) {
        // 布局注入
        injectLayoutDialog(dialog, context);
    }

    private static void injectLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
       
        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz);
                throw new RuntimeException(getClassName(clazz) + activity.getString(R.string.layout_id_error_activity));
            }
            if (layoutId == -2) {
                
            } else {
            // 第一种方法
                activity.setContentView(layoutId);
//                try {
//                    Method method = clazz.getMethod("setContentView", int.class);
//                    method.invoke(activity, layoutId);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            log(clazz);
            throw new NullPointerException(getClassName(clazz) + activity.getString(R.string.content_view_empty_activity));
        }
    }

    private static int injectLayout(Fragment fragment) {
        // 获取类
        Class<? extends Fragment> clazz = fragment.getClass();

        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz);
                throw new RuntimeException(getClassName(clazz) + fragment.getString(R.string.layout_id_error_fragment));
            }
            // 第一种方法
            return layoutId;
//            fragment.setLayoutId(layoutId);
        } else {
            log(clazz);
            throw new NullPointerException(getClassName(clazz) + fragment.getString(R.string.content_view_empty_activity));
        }
    }
    private static int injectLayout(FastBaseAdapter adapter) {
        // 获取类
        Class<? extends FastBaseAdapter> clazz = adapter.getClass();

        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz);
                ViseLog.e(R.string.layout_id_error_activity);
                throw new RuntimeException(getClassName(clazz) + BaseApp.getContext().getString(R.string.layout_id_error_adapter));
            }
            // 第一种方法
            return layoutId;
//            fragment.setLayoutId(layoutId);
        } else {
            log(clazz);
            ViseLog.e(R.string.layout_id_error_activity);
            throw new NullPointerException(getClassName(clazz) + BaseApp.getContext().getString(R.string.content_view_empty_adapter));
        }
    }


    /**
     *  dialog注入
     * @param dialog 对话框
     * @param context 上下文
     */
    private static void injectLayoutDialog(Dialog dialog, Context context) {
        // 获取类
        Class<? extends Dialog> clazz = dialog.getClass();
       
        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz);
                throw new RuntimeException(getClassName(clazz) + context.getString(R.string.dialog_layout_id_error));
            }
            // 第一种方法
            // activity.setContentView(layoutId);

            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(dialog, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log(clazz);
            throw new NullPointerException(getClassName(clazz) + context.getString(R.string.dialog_content_view_empty));
        }

        // 获取类的高度 注解
        DialogHeight dialogHeight = clazz.getAnnotation(DialogHeight.class);
        Window dialogWindow = dialog.getWindow();
        assert dialogWindow != null;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高
        lp.height = d.heightPixels;
        lp.width = d.widthPixels;
        if(null != dialogHeight) {
            double height = dialogHeight.value();
            lp.height = (int) (d.heightPixels * height); // 设置高度
        }
        DialogWidth dialogWidth = clazz.getAnnotation(DialogWidth.class);
        if(null != dialogWidth) {
            double width = dialogWidth.value();
            lp.width = (int) (d.widthPixels * width); // 设置宽度
        }

        DialogGravity dialogGravity = clazz.getAnnotation(DialogGravity.class);
        if(null != dialogGravity) {
            int gravity = dialogGravity.value();
            if (gravity == Gravity.TOP || gravity == Gravity.BOTTOM || gravity == Gravity.LEFT || gravity == Gravity.RIGHT || gravity == Gravity.CENTER) {
                dialogWindow.getAttributes().gravity = dialogGravity.value(); // 设置DiaLog显示位置
            } else {
                throw new RuntimeException(getClassName(clazz) + context.getString(R.string.dialog_gravity_error));
            }
        }
        
        dialogWindow.setAttributes(lp);
    }

    private static void log(Class<?> clazz) {
        //生成指向java的字符串 加入到TAG标签里面
//        StackTraceElement[] s = Thread.currentThread().getStackTrace();
//        for (StackTraceElement value : s) {
//            if (value.getClassName().startsWith("lambda")) {
//                return;
//            }
//        }
        String TAG = "Error Activity" + "(" + getClassName(clazz) + ".java:" + 1 + ")";
        Log.d(TAG, "\t");
    }
    
    private static String getClassName(Class clazz) {
        String className = clazz.getName();
        if (className.contains("$")) { //用于内部类的名字解析
            className = className.substring(className.lastIndexOf(".") + 1, className.indexOf("$"));
        } else {
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }
        return className;
    }
}
