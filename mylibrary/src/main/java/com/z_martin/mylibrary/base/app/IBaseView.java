package com.z_martin.mylibrary.base.app;

/**
 * BaseView
 */
public interface IBaseView {

    /**
     * 显示toast消息
     *
     * @param msg 要显示的toast消息字符串
     */
    void showToast(String msg);

    /**
     * 显示toast消息
     *
     * @param msg 要显示的toast消息id
     */
    void showToast(int msg);

    /**
     * 显示等待dialog
     *
     * @param waitMsg 等待消息字符串
     */
    void showDialog(String waitMsg);
    /**
     * 显示等待dialog
     *
     * @param waitMsg 等待消息id
     */
    void showDialog(int waitMsg);

    /**
     * 显示等待dialog
     */
    void showDialog();

    /**
     * 隐藏等待dialog
     */
    void hideDialog();

    /**
     * 隐藏键盘
     */
    void hideKeyboard();

    /**
     * 回退
     */
    void back();

    /**
     * 显示网络错误
     */
    void showNetworkError();

    /**
     * 显示网络错误
     * @param msg 消息字符串
     */
    void showNetworkError(String msg);
    
    /**
     * 显示网络错误
     * @param msg 消息id
     */
    void showNetworkError(int msg);
}
