package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.apptik.widget.multiview.layoutmanagers.ViewUtils;


public class ScalableRecyclerView extends RecyclerView {

    private static String TAG = ScalableRecyclerView.class.getName();
    private ScaleGestureDetector mScaleDetector;
    private float mOldScaleFactor = 1.f;
    private float mScaleFactor = 1.f;
    //the point of the view that is touched for scaling
    //this one will be used to move to
    private float mFromX;
    private float mFromY;
    private boolean mIsScaling = false;

    private GridLayoutManager mLayoutManagerGrid;
    private LayoutManager mLayoutManagerSingle;

    private ScalableAdapter mScalableAdapter;
    private Adapter mAdapter;


    public ScalableRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ScalableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScalableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mScalableAdapter = new ScalableAdapter(adapter);
        mAdapter = adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(!GridLayoutManager.class.isAssignableFrom(layout.getClass())) {
            throw new RuntimeException("GridLayoutManager required.");
        }
       mLayoutManagerGrid = (GridLayoutManager) layout;
       if(super.getLayoutManager()!=mLayoutManagerSingle) {
           doScale(getItemPosition(-1, -1));
       }
    }

    private void init(Context context) {
        mLayoutManagerGrid = new GridLayoutManager(getContext(), 3);
        mLayoutManagerSingle =  new ScalableLayoutManager(getContext());
        super.setLayoutManager(mLayoutManagerGrid);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }


    //TODO do we use     ScaleGestureDetector at all ?

    //todo
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        final int action = MotionEventCompat.getActionMasked(ev);
        // Always handle the case of the touch gesture being complete.

        if (mIsScaling) {
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                // Release the scale.
                mIsScaling = false;
                doScale(getItemPosition(mFromX, mFromY));
            }
        }

        mScaleDetector.onTouchEvent(ev);
        if (mIsScaling) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }


    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        canvas.scale(mScaleFactor, mScaleFactor);
//        canvas.restore();
    }

    private synchronized void doScale(int currPos) {
        Log.d(TAG, "scale before pos: " + currPos);
        if (mScaleFactor <= 4) {

            if (mScaleFactor < 1) {
                //super.setLayoutManager(new GridLayoutManager(getContext(), 3 + Math.round((10 - mScaleFactor * 1)/2)));
                mLayoutManagerGrid.setSpanCount(3 + Math.round((10 - mScaleFactor * 10) / 2));
                mLayoutManagerGrid.requestLayout();
            } else if (mScaleFactor < 2) {
                //super.setLayoutManager(new GridLayoutManager(getContext(), 3));
                mLayoutManagerGrid.setSpanCount(3);
                mLayoutManagerGrid.requestLayout();
            } else if (mScaleFactor < 3) {
                //super.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mLayoutManagerGrid.setSpanCount(2);
                mLayoutManagerGrid.requestLayout();
            } else if (mScaleFactor <= 4) {
                //super.setLayoutManager(new LinearLayoutManager(getContext()));
                mLayoutManagerGrid.setSpanCount(1);
                mLayoutManagerGrid.requestLayout();
            }
            //check if we are not coming form single item layout
            if (mOldScaleFactor > 4) {
                super.setLayoutManager(mLayoutManagerGrid);
                super.setAdapter(mAdapter);
                getRecycledViewPool().clear();
                scrollToPosition(currPos);
            }

        } else {
            //check if we are not coming from gridlayout
            if (mOldScaleFactor <= 4) {
                super.setLayoutManager(mLayoutManagerSingle);
                super.setAdapter(mScalableAdapter);
                getRecycledViewPool().clear();
                scrollToPosition(currPos);
            }

            else {
                // zoom picture with factor mFactor -4
            }
        }

        // scrollToPosition(currPos);
        mOldScaleFactor = mScaleFactor;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "scale factor: " + mScaleFactor + ", detector: " + detector.getScaleFactor());
            if (!mIsScaling) {
                mIsScaling = true;
                mFromX = detector.getFocusX();
                mFromY = detector.getFocusY();
            }
            mScaleFactor *= detector.getScaleFactor();
            Log.d(TAG, "scale factor after: " + mScaleFactor);
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            Log.d(TAG, "scale factor end: " + mScaleFactor);
            //invalidate();
            // doScale();

            return true;
        }
    }

    //TODO we need to get the touched item position not the centered one
    public int getItemPosition(float x, float y) {
        int curPosition = -1;
        if (getLayoutManager().canScrollHorizontally()) {
            curPosition = ViewUtils.getCenterXChildPosition(this);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(this);
        }
        return curPosition;
    }
}
