package com.z_martin.mylibrary.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

/**
 * 列表的Adapter封装，包括ViewHolder、item显示、数据加??
 *
 * @Description:
 * @ClassName: BaseAdapter
 * @author: zhm
 * @date:
 *
 * @param <T>
 */
public abstract class BaseAdapter<T> extends ArrayAdapter<T> {
	protected Context mContext;

	public BaseAdapter(Context context, List<T> dataList) {
		super(context, 0, dataList);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		T data = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					getViewLayoutId(), parent,false);
			viewHolder = new ViewHolder(getContext(), convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		initData(viewHolder, data, position);
		// super.getView(position, convertView, parent)

		return convertView;
	}

	protected abstract int getViewLayoutId();

	protected void initItemView(ViewHolder viewHolder, T data) {

	}

	protected abstract void initData(ViewHolder viewHolder, T data, int position);

	public static class ViewHolder {
		private SparseArray<View> mViews;
		private View mItemView;
		private int mLayoutId;

		public ViewHolder(Context context, View itemView) {
			mItemView = itemView;
			mViews = new SparseArray<View>();
		}

		/**
		 * 通过viewId获取控件
		 *
		 * @param viewId
		 * @return
		 */
		public <T extends View> T getView(int viewId) {
			View view = mViews.get(viewId);
			if (view == null) {
				view = mItemView.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return (T) view;
		}
		public ViewHolder setText(@IdRes int viewId, CharSequence value) {
			TextView view = getView(viewId);
			view.setText(value);
			return this;
		}

		public ViewHolder setText(@IdRes int viewId, @StringRes int strId) {
			TextView view = getView(viewId);
			view.setText(strId);
			return this;
		}

		/**
		 * Will set the image of an ImageView from a resource id.
		 *
		 * @param viewId     The view id.
		 * @param imageResId The image resource id.
		 * @return The BaseViewHolder for chaining.
		 */
		public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
			ImageView view = getView(viewId);
			view.setImageResource(imageResId);
			return this;
		}

		/**
		 * Will set background of a view.
		 *
		 * @param viewId        The view id.
		 * @param backgroundRes A resource to use as a background.
		 * @return The BaseViewHolder for chaining.
		 */
		public ViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
			View view = getView(viewId);
			view.setBackgroundResource(backgroundRes);
			return this;
		}

		/**
		 * Set a view visibility to VISIBLE (true) or GONE (false).
		 *
		 * @param viewId  The view id.
		 * @param visible True for VISIBLE, false for GONE.
		 * @return The BaseViewHolder for chaining.
		 */
		public ViewHolder setGone(@IdRes int viewId, boolean visible) {
			View view = getView(viewId);
			view.setVisibility(visible ? View.VISIBLE : View.GONE);
			return this;
		}

		/**
		 * Will set text color of a TextView.
		 *
		 * @param viewId    The view id.
		 * @param textColor The text color (not a resource id).
		 * @return The BaseViewHolder for chaining.
		 */
		public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
			TextView view = getView(viewId);
			view.setTextColor(textColor);
			return this;
		}

		public View getConvertView() {
			return mItemView;
		}

		public int getLayoutId() {
			return mLayoutId;
		}

		/**
		 * 关于事件
		 */
		public ViewHolder setOnClickListener(int viewId,
											 View.OnClickListener listener) {
			View view = getView(viewId);
			view.setOnClickListener(listener);
			return this;
		}

		public ViewHolder setOnTouchListener(int viewId,
											 View.OnTouchListener listener) {
			View view = getView(viewId);
			view.setOnTouchListener(listener);
			return this;
		}
		public ViewHolder setOnLongClickListener(int viewId,
												 View.OnLongClickListener listener) {
			View view = getView(viewId);
			view.setOnLongClickListener(listener);
			return this;
		}
	}
}
