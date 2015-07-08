package io.apptik.widget.multiview.layoutmanagers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class SnapperLinearLayoutManager extends LinearLayoutManager {

    public static final int SNAP_START = 1;
    public static final int SNAP_END = 2;
    public static final int SNAP_CENTER = 3;
    public static final int SNAP_NONE = 0;

    protected RecyclerView mRecyclerView = null;
    private boolean flingOneItemOnly = false;
    private boolean showOneItemOnly = false;
    private int snapMethod = SNAP_CENTER;

    public SnapperLinearLayoutManager(Context context) {
        super(context);
    }

    public SnapperLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SnapperLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mRecyclerView = view;
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        mRecyclerView = null;
    }

    @Override
    public void addView(View child, int index) {
        if (showOneItemOnly) {
            ViewGroup.LayoutParams lp = child.getLayoutParams() == null ?
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) : child.getLayoutParams();
            if(canScrollHorizontally()) {
                lp.width = getWidth();
            }
            if (canScrollVertically()) {
                lp.height = getHeight();
            }
            child.setLayoutParams(lp);
        }
        super.addView(child, index);
    }

    public int getCurrentPosition() {
        int curPosition = -1;
        if (canScrollHorizontally()) {
            curPosition = ViewUtils.getCenterXChildPosition(mRecyclerView);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(mRecyclerView);
        }
        return curPosition;
    }
}
