package com.z_martin.mylibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.z_martin.mylibrary.R;


/**
 * @ describe: 自定义加载进度条
 * @ author: Martin
 * @ createTime: 2018/9/1 16:54
 * @ version: 1.1
 */
public class LoadingDialog extends Dialog {
    private Context mContext;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置Dialog弹出时不显示导航栏
//        DisplayMetrics dm = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        android.view.WindowManager.LayoutParams p = this.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.width =  dm.widthPixels;   //高度设置为屏幕
//        p.height = dm.heightPixels;    //宽度设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        Window window = this.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }
    }

    public static class Builder {

        private Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 带有提示文字的加载进度条
         * @param msg 消息字符串
         */
        public LoadingDialog create(String msg) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.layout_loading_dialog, null);    // 得到加载view
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);  // 加载布局
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
//            layout.setBackgroundResource(R.color.transparent);                     // 设置背景颜色
            ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);    // 进度图片
            TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView); // 提示文字
            // 加载动画
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.loading_animation);
            // 使用ImageView显示动画--使用的旋转动画
            spaceshipImage.startAnimation(animation);

            // 设置加载信息文字
            if(StringUtils.isEmpty(msg)) {
                tipTextView.setVisibility(View.GONE);
            } else {
                tipTextView.setVisibility(View.VISIBLE);
                tipTextView.setText(msg);
            }
            LoadingDialog loadingDialog = new LoadingDialog(mContext, R.style.loading_dialog);   // 设置自定义样式dialog
            loadingDialog.setCancelable(false);   // 不可以用“返回键”取消
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));   // 设置布局
            Window dialogWindow = loadingDialog.getWindow();
            assert dialogWindow != null;
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = AppUtils.getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高
            lp.width = d.widthPixels;
            lp.height = d.heightPixels;
            dialogWindow.getAttributes().gravity = Gravity.CENTER;
            return loadingDialog;
        }

        /**
         * 无提示文字的加载进度条
         */
        public LoadingDialog create() {
            return create("");
        }

        /**
         * 带有提示文字的加载进度条
         * @param msg 消息id
         */
        public LoadingDialog create(int msg) {
            return create((String) mContext.getText(msg));
        }
    }
}
