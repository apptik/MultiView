package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.widget.multiview.common.Log;

public class ScalableGridLayoutManager extends GridLayoutManager {

    RecyclerView mRecyclerView;

    private float tmpScale = 1.0f;
    protected int initSpanCount = -1;


    public ScalableGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ScalableGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        init();
    }

    public ScalableGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        init();
    }

    private void init() {
       initSpanCount = getSpanCount();
    }

    public float getTmpScale() {
        return tmpScale;
    }

    public void setTmpScale(float tmpScale) {
        this.tmpScale = tmpScale;
    }



    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        LayoutParams nlp =  new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;

    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        LayoutParams nlp =  new LayoutParams(c, attrs);
        //Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
    }


    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        LayoutParams nlp = new LayoutParams(super.generateLayoutParams(lp));
        //Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
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
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void layoutDecorated(View child, int left, int top, int right, int bottom) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();

        super.layoutDecorated(child, left, top,
                right, bottom);
    }

    @Override
    public void measureChild(View child, int widthUsed, int heightUsed) {
        super.measureChild(child, widthUsed, heightUsed);
    }

    @Override
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public int getDecoratedMeasuredWidth(View child) {
        //child.setScaleX(getScale());
        //child.setScaleY(getScale());
        return (int) (super.getDecoratedMeasuredWidth(child)
                //*getScale()
        );
    }

    @Override
    public int getDecoratedMeasuredHeight(View child) {
        //child.setScaleX(getScale());
        //child.setScaleY(getScale());
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        Log.d("getDecoratedMeasuredHeight: lp: " + lp.width + "/" + lp.height);
        lp.height= (int) (lp.origHeight*getScale());
//        lp.width*=getScale();
//        child.setLayoutParams(lp);
        return (super.getDecoratedMeasuredHeight(child)
        );
    }




    @Override
    public void setSpanCount(int spanCount) {
        super.setSpanCount(spanCount);
//        if(mRecyclerView!=null && mRecyclerView.getAdapter()!=null) {
//            mRecyclerView.getAdapter().notifyDataSetChanged();
//        }
        int childCount = getChildCount();
        if(childCount>0) {
            mRecyclerView.getAdapter()
                    .notifyItemRangeChanged(mRecyclerView.getChildAdapterPosition(getChildAt(0)), childCount * 2);
//            for (int i = 0; i < childCount; i++) {
//            LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
//            lp.height= (int) (lp.origHeight*getScale());
//            getChildAt(i).invalidate();
//            }
        }
    }

    protected float getScale() {
        return ((float)initSpanCount/(float)getSpanCount())*tmpScale;
        //return 1f;
    }
    public static class LayoutParams extends GridLayoutManager.LayoutParams {

        private int origHeight = 0;

        private int origWidth = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            origHeight = height;
            origWidth = width;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            origHeight = height;
            origWidth = width;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            origHeight = height;
            origWidth = width;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            origHeight = height;
            origWidth = width;
        }

        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
            origHeight = height;
            origWidth = width;
        }

        public int getOrigHeight() {
            return origHeight;
        }
        public int getOrigWidth() {
            return origWidth;
        }
    }

}
