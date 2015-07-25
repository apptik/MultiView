package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.apptik.widget.multiview.common.Log;
import io.apptik.widget.multiview.layoutmanagers.ViewPagerLayoutManager;


/**
 * Scalable Recyclerview that accept only gridviewlayoutmanager
 */
public class ScalableRecyclerGridView extends RecyclerView {

    protected ScalableGridLayoutManager layoutManagerGrid;
    protected ViewPagerLayoutManager mLayoutManagerSingle;
    private int minSpan = 1;
    private int maxSpan = 5;
    private float currScale = 1f;
    private GestureDetectorCompat gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;


    public ScalableRecyclerGridView(Context context, int intialSpan) {
        super(context);
        init(context, intialSpan);
    }

    public ScalableRecyclerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, 3);
    }

    public ScalableRecyclerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, 3);
    }

    private void init(Context context, int initSpanCount) {
        layoutManagerGrid = new ScalableGridLayoutManager(context, initSpanCount);
        super.setLayoutManager(layoutManagerGrid);
        InteractionListener interactionListener = new InteractionListener();
        gestureDetector = new GestureDetectorCompat(context, interactionListener);
        scaleGestureDetector = new ScaleGestureDetector(context, interactionListener);
    }

    public int getMaxSpan() {
        return maxSpan;
    }

    public void setMaxSpan(int mMaxSpan) {
        this.maxSpan = mMaxSpan;
        if (layoutManagerGrid != null && layoutManagerGrid.getSpanCount() > mMaxSpan) {
            layoutManagerGrid.setSpanCount(mMaxSpan);
        }
    }

    public int getMinSpan() {
        return minSpan;
    }

    public void setMinSpan(int mMinSpan) {
        this.minSpan = mMinSpan;
        if (layoutManagerGrid != null && layoutManagerGrid.getSpanCount() < mMinSpan) {
            layoutManagerGrid.setSpanCount(mMinSpan);
        }
    }

    public void setSpanCount(int spanCount) {
        if (spanCount < minSpan) {
            spanCount = minSpan;
        }
        if (spanCount > maxSpan) {
            spanCount = maxSpan;
        }
        layoutManagerGrid.setSpanCount(spanCount);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        throw new IllegalStateException("cannot set Layout manager it is always ScalableGridLayoutManager");
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        if (scaleGestureDetector.isInProgress()) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public void onDraw(Canvas c) {
        c.scale(currScale, currScale, 0, 0);
        super.onDraw(c);
    }

    private class InteractionListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

        int initSpanCount = 0;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("onSingleTapConfirmed: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("onDoubleTap: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("onDoubleTapEvent: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("onDown: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("onShowPress: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("onSingleTapUp: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + distanceX + "/" + distanceY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("onLongPress: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("onFling: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + velocityX + "/" + velocityY);
            return false;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("onScale: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            float currFactor = detector.getScaleFactor();

            int newSpanCount = layoutManagerGrid.getSpanCount();
            float newScale = currFactor;

            if (currFactor > 1.05f) {
                //zoomin
                //check if we did zoom out before
                if(newSpanCount>initSpanCount) {
                    newSpanCount = initSpanCount;
                }
                Log.d("onScale: zoomin " + newSpanCount);
                if(initSpanCount==1) {
                    //chnage to single LM here
                    newScale = 1;
                } else {
                    newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                }

            } else if(currFactor<0.95) {
                //zoomout
                newSpanCount = initSpanCount+1;
                Log.d("onScale: zoomout " + newSpanCount);
               // newScale = Math.max(currFactor,(float)initSpanCount/(float)(initSpanCount+1));
                newScale = 1f + currFactor - (float)initSpanCount/(float)newSpanCount;
                newScale = Math.max(1f,newScale);
            } else {
                //do nothing
            }

            if(newSpanCount != layoutManagerGrid.getSpanCount()) {
                setSpanCount(newSpanCount);
                layoutManagerGrid.requestLayout();
            }
            {
                //we don't go less then 1 as the LM already downscaled the items
                currScale = newScale;

                invalidate();
            }
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d("onScaleBegin: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            initSpanCount = layoutManagerGrid.getSpanCount();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d("onScaleEnd: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            //now set the final span;
            float currFactor = detector.getScaleFactor();


            //int newSpanCount = layoutManagerGrid.getSpanCount();
            if (currFactor > 1f) {
                setSpanCount(initSpanCount-1);
            } else {
                setSpanCount(initSpanCount+1);
            }
            layoutManagerGrid.requestLayout();
            currScale = 1f;
            initSpanCount = 0;
            invalidate();
        }
    }
}
