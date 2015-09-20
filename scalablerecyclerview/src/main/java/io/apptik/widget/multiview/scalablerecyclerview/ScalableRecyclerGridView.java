/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import io.apptik.widget.multiview.common.Log;
import io.apptik.widget.multiview.layoutmanagers.ScalableGridLayoutManager;
import io.apptik.widget.multiview.scrollers.BaseSmoothScroller;
import io.apptik.widget.multiview.scrollers.FlexiSmoothScroller;


/**
 * Scalable Recyclerview that accept only gridviewlayoutmanager
 */


public class ScalableRecyclerGridView extends RecyclerView {

    private int minSpan = 2;
    private int maxSpan = 5;

    InteractionListener interactionListener;

    public static final int ZOOM_ANIMATION_DURATION_MS = 200;
    public ScaleListener scaleListener;


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
        super.setLayoutManager(new ScalableGridLayoutManager(context, initSpanCount));
        interactionListener = new InteractionListener(context, this);
    }


    public ScaleListener getScaleListener() {
        return scaleListener;
    }

    public void setScaleListener(ScaleListener scaleListener) {
        this.scaleListener = scaleListener;
    }


    public int getMaxSpan() {
        return maxSpan;
    }

    public void setMaxSpan(int mMaxSpan) {
        this.maxSpan = mMaxSpan;
        if (((ScalableGridLayoutManager) getLayoutManager()) != null && ((ScalableGridLayoutManager) getLayoutManager()).getSpanCount() > mMaxSpan) {
            ((ScalableGridLayoutManager) getLayoutManager()).setSpanCount(mMaxSpan);
        }
    }

    public int getMinSpan() {
        return minSpan;
    }

    public void setMinSpan(int mMinSpan) {
        this.minSpan = mMinSpan;
        if (((ScalableGridLayoutManager) getLayoutManager()) != null && ((ScalableGridLayoutManager) getLayoutManager()).getSpanCount() < mMinSpan) {
            ((ScalableGridLayoutManager) getLayoutManager()).setSpanCount(mMinSpan);
        }
    }

    public void setSpanCount(int spanCount) {
        if (spanCount < minSpan) {
            spanCount = minSpan;
        }
        if (spanCount > maxSpan) {
            spanCount = maxSpan;
        }
        int oldSpanCount = ((ScalableGridLayoutManager) getLayoutManager()).getSpanCount();
        if (oldSpanCount != spanCount) {
            ((ScalableGridLayoutManager) getLayoutManager()).setSpanCount(spanCount);
            if (scaleListener != null) {
                scaleListener.onSpanChange(spanCount, oldSpanCount);
            }
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout.getClass().isAssignableFrom(ScalableGridLayoutManager.class)) {
            throw new IllegalStateException("LAyoutManager has to extend ScalableGridLayoutManager ");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isGood = interactionListener.onTouchEvent(ev);

        if (!isGood) {
            return super.onTouchEvent(ev);
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isAttachedToWindow() || getAdapter() == null || getLayoutManager() == null
            //||getScrollState() != SCROLL_STATE_IDLE
                ) {
            return super.onInterceptTouchEvent(ev);
        } else {
            onTouchEvent(ev);
        }

        return false;
    }

    @Override
    public void onDraw(Canvas c) {
        if (interactionListener.currentView != null && interactionListener.scaleGestureDetector.isInProgress()) {
            float px;
            float py;

            //pivot for zooming depends on the ratio of the distance of left and
            // right(top and bottom) of the child to left and right(top and bottom) of the RV
            float dl = interactionListener.currentView.getX() - getX();
            float dw = getWidth() - interactionListener.currentView.getWidth();
            px = interactionListener.currentView.getX() + (dl / dw) * interactionListener.currentView.getWidth();
            float dt = interactionListener.currentView.getY() - getY();
            float dh = getHeight() - interactionListener.currentView.getHeight();
            py = interactionListener.currentView.getY() + (dt / dh) * interactionListener.currentView.getHeight();
            //however we also do not want to have half appearing views so we choose a side
            float midX = getX() + getWidth() / 2;
            float midY = getY() + getHeight() / 2;
            if (getLayoutManager().canScrollHorizontally()) {
                //fix py
                if (py < midY) {
                    py = 0;
                } else {
                    py = getY() + getHeight();
                }
            } else {
                //fix px
                if (px < midX) {
                    px = 0;
                } else {
                    px = getX() + getWidth();
                }
            }

            c.scale(getCurrScale(), getCurrScale(), px, py);
        }
        super.onDraw(c);
    }

    private float getCurrScale() {
        return interactionListener.currScale;
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    public static class InteractionListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

        private Context context;
        private ScalableRecyclerGridView scalableRecyclerGridView;
        private GestureDetectorCompat gestureDetector;
        private ScaleGestureDetector scaleGestureDetector;
        private float currScale = 1f;
        //initial span count when scale started
        volatile int initSpanCount = 0;
        volatile View currentView;
        //used to normalise the raw factor when scaling in not allowed direction
        volatile private float factorOffset;

        public InteractionListener(Context context, ScalableRecyclerGridView scalableRecyclerGridView) {
            this.context = context;
            this.scalableRecyclerGridView = scalableRecyclerGridView;
            init();
        }


        private void init() {
            gestureDetector = new GestureDetectorCompat(context, this);
            gestureDetector.setOnDoubleTapListener(this);
            scaleGestureDetector = new ScaleGestureDetector(context, this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                scaleGestureDetector.setQuickScaleEnabled(false);
            }
        }

        public boolean onTouchEvent(MotionEvent ev) {
            scaleGestureDetector.onTouchEvent(ev);
            //if were in scale ignore the rest
            if (scaleGestureDetector.isInProgress()) {
                return true;
            } else {
                //return false;
                return gestureDetector.onTouchEvent(ev);
            }

        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("onSingleTapConfirmed: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("onDoubleTap: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()) + " v: " + currentView);
            if (scaleGestureDetector.isInProgress()) return false;
            //toggle min/max col span for grid mode

            View tmpView = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());

            if (((ScalableGridLayoutManager) scalableRecyclerGridView.getLayoutManager()).getSpanCount() >
                    scalableRecyclerGridView.getMinSpan()) {
                scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMinSpan());
            } else {
                scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMaxSpan());
            }
            if (tmpView != null) {
                scalableRecyclerGridView.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(tmpView));
            }
            scalableRecyclerGridView.requestLayout();


            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("onDoubleTapEvent: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return false;
            //TODO do we ?
            //not in use now but we have to return true
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
            //just ignore
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("onSingleTapUp: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + distanceX + "/" + distanceY);
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("onFling: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + velocityX + "/" + velocityY);
            if (scaleGestureDetector.isInProgress()) return true;
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("onLongPress: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            //maybe menu
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("onScale: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + "/" + factorOffset
                    + " : " + detector.getEventTime());

            float currFactor = detector.getScaleFactor() + factorOffset;

            int newSpanCount = ((ScalableGridLayoutManager) scalableRecyclerGridView.getLayoutManager()).getSpanCount();
            if (
                    (initSpanCount == newSpanCount) &&
                            ((currFactor > 1 && newSpanCount == scalableRecyclerGridView.getMinSpan())
                                    || (currFactor < 1 && currScale <= 1 && newSpanCount == scalableRecyclerGridView.getMaxSpan()))
                    ) {
                factorOffset = 1 - detector.getScaleFactor();
                return false;
            }

            float newScale = currFactor;

            if (currFactor > 1.05f) {
                //zoomin
                //check if we did zoom out before
                if (newSpanCount > initSpanCount) {
                    newSpanCount = initSpanCount;
                }
                Log.d("onScale: zoomin " + newSpanCount);
                if (initSpanCount == 1) {
                    //change to single LM here
                    //setSingleMode();
                    //newScale = 1;
                } else {
                    newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                }

            } else if (currFactor < 0.95) {
                //zoomout
                newSpanCount = initSpanCount + 1;
                Log.d("onScale: zoomout " + newSpanCount);
                newScale = 1f + currFactor - (float) initSpanCount / (float) newSpanCount;
                newScale = Math.max(1f, newScale);
            } else {
                //in between dont change span count
                //check if we were zoomed out before so we have overflowing items
                if (newSpanCount > initSpanCount) {
                    newScale = 1f + currFactor - (float) initSpanCount / (float) newSpanCount;
                    newScale = Math.max(1f, newScale);
                } else {
                    if (initSpanCount == 1) {

                    } else {
                        newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                    }
                }
            }
            if (newSpanCount != ((ScalableGridLayoutManager)
                    scalableRecyclerGridView.getLayoutManager()).getSpanCount()) {
                Log.d("onScale: will set " + newSpanCount);
                scalableRecyclerGridView.setSpanCount(newSpanCount);
                scalableRecyclerGridView.getLayoutManager().requestLayout();
                if (initSpanCount != ((ScalableGridLayoutManager)
                        scalableRecyclerGridView.getLayoutManager()).getSpanCount()) {
                    SmoothScroller scroller = new FlexiSmoothScroller(context)
                            .setVerticalSnapPreference(BaseSmoothScroller.SNAP_TO_CENTER)
                            .setHorizontalSnapPreference(BaseSmoothScroller.SNAP_TO_CENTER);
                    scroller.setTargetPosition(
                            scalableRecyclerGridView.getChildAdapterPosition(currentView));
                    scalableRecyclerGridView.getLayoutManager().startSmoothScroll(scroller);
                }
            }
            currScale = newScale;
            scalableRecyclerGridView.invalidate();


            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d("onScaleBegin: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + " : " + detector.getEventTime());
            currentView = scalableRecyclerGridView.findChildViewUnder(detector.getFocusX(), detector.getFocusY());
            initSpanCount = ((ScalableGridLayoutManager) scalableRecyclerGridView.getLayoutManager()).getSpanCount();
            factorOffset = 0f;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d("onScaleEnd: " + detector.getFocusX() + "/" + detector.getFocusY()
                    + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor()
                    + "/" + factorOffset
                    + " : " + detector.getEventTime());

            float currFactor = detector.getScaleFactor() + factorOffset;

            if (currFactor > 1f + (1f / (float) (initSpanCount - 1)) / 2f) {
                if (initSpanCount == scalableRecyclerGridView.minSpan) {
                    //do nothing we reached our max span
                } else {
                    scalableRecyclerGridView.setSpanCount(initSpanCount - 1);
                }
            } else if (currFactor < 1f - (1f / (float) (initSpanCount + 1)) / 2f) {
                scalableRecyclerGridView.setSpanCount(initSpanCount + 1);
            } else {
                if (((ScalableGridLayoutManager) scalableRecyclerGridView.getLayoutManager()).getSpanCount() != initSpanCount) {
                    scalableRecyclerGridView.setSpanCount(initSpanCount);
                }
            }


            if (initSpanCount != ((ScalableGridLayoutManager) scalableRecyclerGridView.getLayoutManager()).getSpanCount()) {
                scalableRecyclerGridView.getLayoutManager().scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
            }
            currScale = 1f;
            initSpanCount = 0;
            scalableRecyclerGridView.getLayoutManager().requestLayout();
            scalableRecyclerGridView.invalidate();

            currentView = null;
        }

    }

    public interface ScaleListener {
        void onScaleBegin(ScaleGestureDetector detector);

        void onScaleEnd(ScaleGestureDetector detector);

        void onScale(ScaleGestureDetector detector);

        void onSpanChange(int newSpan, int oldSpan);
    }

    public static class VoidScaleListener implements ScaleListener {

        @Override
        public void onScaleBegin(ScaleGestureDetector detector) {

        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

        @Override
        public void onScale(ScaleGestureDetector detector) {

        }

        @Override
        public void onSpanChange(int newSpan, int oldSpan) {

        }

    }





}
