package com.z_martin.mylibrary.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.z_martin.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/9/2 13:04
 * @ version: 1.0
 */
public class ActionSheetDialog {

    private Context mContext;
    private Display mDisplay;
    private Dialog dialog;
    /** 标题 */
    private TextView txt_title;
    /** 标题和内容中间的线 */
    private View line_view;
    /** 取消按钮 */
    private TextView txt_cancel;
    /** 条目布局 */
    private LinearLayout lLayout_content;
    /** ScrollView滑动 */
    private ScrollView sLayout_content;
    /** 是否显示头部 */
    private boolean showTitle = false;
    //
    private List<SheetItem> sheetItemList;

    public ActionSheetDialog(Context mContext) {
        this.mContext = mContext;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取DiaLog布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dailog_actionsheet, null);
        // 设置DiaLog最小宽度为屏幕宽度
        view.setMinimumWidth(mDisplay.getWidth());
        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        line_view = (View) view.findViewById(R.id.line_view);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 定义DiaLog布局和参数
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        // 设置弹出的窗体的位置
        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        // 获取属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    /**
     * 设置头部的文字
     */
    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        line_view.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }
    
    /**
     * 设置头部的文字id
     */
    public ActionSheetDialog setTitle(int title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        line_view.setVisibility(View.VISIBLE);
        txt_title.setText(mContext.getString(title));
        return this;
    }
    
    /**
     * 设置头部的文字颜色
     */
    public ActionSheetDialog setTitleColor(String color) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        line_view.setVisibility(View.VISIBLE);
        txt_title.setTextColor(Color.parseColor(color));
        return this;
    }
    
    /**
     * 设置头部的文字颜色
     */
    public ActionSheetDialog setTitleColor(int color) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        line_view.setVisibility(View.VISIBLE);
        txt_title.setTextColor(color);
        return this;
    }

    /**
     * 设置取消按钮的颜色
     */
    public ActionSheetDialog setCancalColor(String color) {
        txt_cancel.setTextColor(Color.parseColor(color));
        return this;
    }
    
    /**
     * 设置取消按钮的颜色
     */
    public ActionSheetDialog setCancalColor(int color) {
        txt_cancel.setTextColor(color);
        return this;
    }
    
    /**
     * 设置取消按钮大小
     */
    public ActionSheetDialog setCancalSize(float size) {
        txt_cancel.setTextSize(size);
        return this;
    }

    /**
     * 设置是否可以取消
     */
    public ActionSheetDialog setCancleable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 是否在外面撤销
     */
    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 动态添加条目
     *
     * @param strItem 条目名称
     * @param color 字体颜色，默认的是蓝色
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null || sheetItemList.size() == 0) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(strItem, -1, color, listener));
        return this;
    }
    
    /**
     * 动态添加条目
     *
     * @param strItem 条目id
     * @param color 字体颜色，默认的是蓝色
     * @param listener
     */
    public ActionSheetDialog addSheetItem(int strItem, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null || sheetItemList.size() == 0) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(mContext.getString(strItem), -1, color, listener));
        return this;
    }
    /**
     * 动态添加条目
     *
     * @param strItem 条目id
     * @param identify 标识
     * @param color 字体颜色，默认的是蓝色
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(int[] strItem, int[] identify, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null || sheetItemList.size() == 0) {
            sheetItemList = new ArrayList<>();
        }
        for (int i = 0; i < strItem.length; i++) {
            sheetItemList.add(new SheetItem(mContext.getString(strItem[i]), identify[i], color, listener));
        }
        return this;
    }
    /**
     * 动态添加条目
     *
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(List<SheetItem> list, OnSheetItemClickListener listener) {
        if (sheetItemList == null || sheetItemList.size() == 0) {
            sheetItemList = new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            sheetItemList.add(new SheetItem(list.get(i).name, list.get(i).identify, list.get(i).color, listener));
        }
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        int size = sheetItemList.size();
        if (sheetItemList == null || size <= 0) {
            return;
        }

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        // if (size >= 7) {
        // LinearLayout.LayoutParams params = (LayoutParams)
        // sLayout_content.getLayoutParams();
        // params.height = mDisplay.getHeight() / 2;
        // sLayout_content.setLayoutParams(params);
        // }

        for (int i = 1; i <= size; i++) {
            final int index = i;
            final SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            // 获取当前屏幕的密度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (50 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index, sheetItem.identify, dialog);
                    dialog.dismiss();
                }
            });

            // 添加到Layout中
            lLayout_content.addView(textView);
            if(index != size) { 
                View view1 = new View(mContext);
                view1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                view1.setBackgroundColor(Color.parseColor("#E9E9E9"));
                lLayout_content.addView(view1);
            }
        }
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int position, int identify, Dialog dialog);
    }

    /**
     * 单个条目
     */
    public static class SheetItem {
        /** 条目名称 */
        String name;
        /** 标识 */
        int identify;
        /** 条目点击事件 */
        OnSheetItemClickListener itemClickListener;
        /** 单个条目的颜色 */
        SheetItemColor color;

        public SheetItem(String name, int identify, SheetItemColor color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.identify = identify;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
        public SheetItem(String name, int identify, SheetItemColor color) {
            this.name = name;
            this.identify = identify;
            this.color = color;
        }
    }

    public enum SheetItemColor {
        Blue("#007AFF"), Red("#FD4A2E"), ;

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
