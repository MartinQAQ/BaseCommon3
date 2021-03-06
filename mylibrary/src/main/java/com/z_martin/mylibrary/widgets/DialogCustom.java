package com.z_martin.mylibrary.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.utils.StringUtils;


/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/9/26 12:49
 * @ version: 1.0
 */
public class DialogCustom extends Dialog {

    public DialogCustom(Context context) {
        super(context);
    }

    public DialogCustom(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public DialogCustom create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // ?????????????????????????????????
            final DialogCustom dialog = new DialogCustom(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.layout_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // ???????????????
            if(StringUtils.isEmpty(title)) {
                ((TextView) layout.findViewById(R.id.title)).setVisibility(View.GONE);
            } else {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }
            // ??????????????????
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((RelativeLayout) layout.findViewById(R.id.positiveButtonRl))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // ????????????????????????????????????????????????????????????
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
                layout.findViewById(R.id.positiveButtonRl).setVisibility(View.GONE);
                layout.findViewById(R.id.buttonLine).setVisibility(View.GONE);
            }
            // ??????????????????
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((RelativeLayout) layout.findViewById(R.id.negativeButtonRl))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // ????????????????????????????????????????????????????????????
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
                layout.findViewById(R.id.negativeButtonRl).setVisibility(View.GONE);
                layout.findViewById(R.id.buttonLine).setVisibility(View.GONE);
            }
            // ??????????????????
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // ????????????????????????
                // ???contentView?????????????????????
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(
                        contentView, new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            dialog.setCancelable(false);
            return dialog;
        }

    }
}
