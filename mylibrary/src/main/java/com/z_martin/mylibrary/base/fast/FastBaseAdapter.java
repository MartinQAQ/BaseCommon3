package com.z_martin.mylibrary.base.fast;

import android.os.Handler;
import android.view.View;

import com.z_martin.mylibrary.base.app.InjectManager;
import com.z_martin.mylibrary.common.adapterHelper.BaseQuickAdapter;
import com.z_martin.mylibrary.common.adapterHelper.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @ describe: 快速适配器
 * @ author: Martin
 * @ createTime: 2018/9/2 2:22
 * @ version: 1.0
 */
public abstract class FastBaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public FastBaseAdapter() {
        super(-1);
        this.mLayoutResId = InjectManager.inject(this);
        init();
    }
    
    @Override
    protected abstract void convert(BaseViewHolder helper, T item);

    private void init(){
//        setLoadMoreView(new RvLoadMoreView());
        setEnableLoadMore(true);
//        openLoadAnimation();//开启默认动画载入（仅开启加载新item时开启动画）
//        openLoadAnimation(BaseQuickAdapter.SCALEIN);
        closeLoadAnimation();
    }

    /**
     *  清除数据
     */
    public void setDataFirst(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
            mData.addAll(data);
            notifyDataSetChanged();
        } else {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }
    
    public void addDatas(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
            mData.addAll(data);
            notifyDataSetChanged();
        } else {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }
    
    /**
     *  清除数据
     */
    public void clearData() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);
    }
    
    public interface onFastItemClickListener<T>{
        void onItemClick(T item);
    }
    
    public onFastItemClickListener<T> mOnFastItemClickListener;

    public void setOnFastItemClickListener(onFastItemClickListener onFastItemClickListener) {
        mOnFastItemClickListener = onFastItemClickListener;
    }


    public interface onFastItemDeleteListener<T>{
        void onItemClick(T item, int position);
    }
    public onFastItemDeleteListener<T> mOnFastItemDeleteListener;
    public void setOnFastItemDeleteListener(onFastItemDeleteListener deleteListener) {
        
        mOnFastItemDeleteListener = deleteListener;
    }


    /**
     *  快速点击事件
     */
    protected void clickListener(BaseViewHolder helper, int id, final T item) {
        helper.getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFastItemClickListener != null) {
                    mOnFastItemClickListener.onItemClick(item);
                }
            }
        });
    }
    /**
     *  快速删除事件
     */
    protected void deleteListener(View view, final T item, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFastItemDeleteListener != null) {
                    mOnFastItemDeleteListener.onItemClick(item, position);
                }
            }
        });
    }
}
 
