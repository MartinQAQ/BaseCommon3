package com.z_martin.mylibrary.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

/**
 * @description: 优化嵌套滑动(可保留置顶控件)
 * @project name: FGAproject
 * @author: xiaoming723@126.com
 * @createTime: 2020/4/23 1:35
 * @version: 1.0
 */
public class StickHeadScrollView extends NestedScrollView {

    public StickHeadScrollView(Context context) {
        super(context);
    }

    public StickHeadScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickHeadScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void resetHeight(final View headView, final View nestedContent) {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                StickHeadScrollView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                nestedContent.getLayoutParams().height = StickHeadScrollView.this.getHeight() - headView.getHeight();
                nestedContent.requestLayout();
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //判断是否需要优先嵌套滑动,解决触摸recyclerview 无法关闭swiperefreshlayout的bug
        if (!dispatchNestedPreScroll(dx, dy, consumed, null)) {
            //嵌套滑动代码
            boolean hiddenTop = dy > 0 && getScrollY() < getChildAt(0).getHeight() - getHeight();
            boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

            if (hiddenTop || showTop) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= getChildAt(0).getHeight() - getHeight())
            return false;
        fling((int) velocityY);
        return true;
    }
}
