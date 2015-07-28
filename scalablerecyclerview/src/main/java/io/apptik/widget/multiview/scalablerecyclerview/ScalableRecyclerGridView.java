package io.apptik.widget.multiview.scalablerecyclerview;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import io.apptik.widget.multiview.common.Log;
import io.apptik.widget.multiview.layoutmanagers.ScalableGridLayoutManager;
import io.apptik.widget.multiview.layoutmanagers.ViewPagerLayoutManager;


/**
 * Scalable Recyclerview that accept only gridviewlayoutmanager
 */


public class ScalableRecyclerGridView extends RecyclerView {

    protected ScalableGridLayoutManager layoutManagerGrid;
    protected ViewPagerLayoutManager layoutManagerSingle;
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
        layoutManagerGrid = new ScalableGridLayoutManager(context, initSpanCount);
        layoutManagerSingle = new ViewPagerLayoutManager(context, RecyclerView.HORIZONTAL, false);

        super.setLayoutManager(layoutManagerGrid);
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
        int oldSpanCount = layoutManagerGrid.getSpanCount();
        if (oldSpanCount != spanCount) {
            layoutManagerGrid.setSpanCount(spanCount);
            if (scaleListener != null) {
                scaleListener.onSpanChange(spanCount, oldSpanCount);
            }
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        throw new IllegalStateException("cannot set Layout manager.ScalableGridLayoutManager or " +
                "ViewPagerLayoutManager must be set separately");
    }

    public void setSingleMode() {
        if (!isInSingleMode()) {
            super.setLayoutManager(layoutManagerSingle);
            if (scaleListener != null) {
                scaleListener.onModeChange(true);
            }
        }
    }

    public void setGridMode() {
        if (isInSingleMode()) {
            super.setLayoutManager(layoutManagerGrid);
            if (scaleListener != null) {
                scaleListener.onModeChange(false);
            }
        }
    }

    public ScalableGridLayoutManager getLayoutManagerGrid() {
        return layoutManagerGrid;
    }

    public void setLayoutManagerGrid(ScalableGridLayoutManager layoutManagerGrid) {
        this.layoutManagerGrid = layoutManagerGrid;
    }

    public ViewPagerLayoutManager getLayoutManagerSingle() {
        return layoutManagerSingle;
    }

    public void setLayoutManagerSingle(ViewPagerLayoutManager layoutManagerSingle) {
        this.layoutManagerSingle = layoutManagerSingle;
    }

    public boolean isInSingleMode() {
        return getLayoutManager().equals(layoutManagerSingle);
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
                || getLayoutManagerGrid() == null || getLayoutManagerSingle() == null) {
            return super.onInterceptTouchEvent(ev);
        }

        return true;
    }

    @Override
    public void onDraw(Canvas c) {
        //TODO why not handle it by interactionListener ??
        if (!isInSingleMode() && interactionListener.currentView != null && interactionListener.scaleGestureDetector.isInProgress()) {
            float px;
            float py;

            //pivot for zooming depends on the ratio oft he distance of left and right(top and bottom) of the child to left and right(top and bottom) of the RV
            float dl = interactionListener.currentView.getX() - getX();
            float dw = getWidth() - interactionListener.currentView.getWidth();
            px = interactionListener.currentView.getX() + (dl / dw) * interactionListener.currentView.getWidth();
            float dt = interactionListener.currentView.getY() - getY();
            float dh = getHeight() - interactionListener.currentView.getHeight();
            py = interactionListener.currentView.getY() + (dt / dh) * interactionListener.currentView.getHeight();
            //however we also do not want to have half appearing views so we choose a side
            float midX = getX() + getWidth() / 2;
            float midY = getY() + getHeight() / 2;
            if (layoutManagerGrid.canScrollHorizontally()) {
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


    public static class InteractionListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

        private Context context;
        private ScalableRecyclerGridView scalableRecyclerGridView;
        private GestureDetectorCompat gestureDetector;
        private ScaleGestureDetector scaleGestureDetector;
        private float currScale = 1f;
        private AnimatorSet mPanAnimator;
        private ValueAnimator mZoomAnimator;
        //initial span count when scale started
        volatile int initSpanCount = 0;
        //initial item scale when scaling in single mode started
        volatile float initialItemScale = 1f;
        volatile View currentView;
        volatile float mFromX;
        volatile float mFromY;

        volatile boolean panned = false;

        public InteractionListener(Context context, ScalableRecyclerGridView scalableRecyclerGridView) {
            this.context = context;
            this.scalableRecyclerGridView = scalableRecyclerGridView;
            init();
        }

        private void init() {
            gestureDetector = new GestureDetectorCompat(context, this);
            gestureDetector.setOnDoubleTapListener(this);
            scaleGestureDetector = new ScaleGestureDetector(context, this);
        }

        public boolean onTouchEvent(MotionEvent ev) {
            scaleGestureDetector.onTouchEvent(ev);
            //if were in scale ignore the rest
            if (scaleGestureDetector.isInProgress()) {
                return true;
            } else if (scalableRecyclerGridView.isInSingleMode() && this.currentView != null && this.currentView.getScaleX() > 1f) {
                gestureDetector.onTouchEvent(ev);
                //let it be
                return true;
            } else {
                return gestureDetector.onTouchEvent(ev);
            }
            //return true;

        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("onSingleTapConfirmed: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            View v = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());
            if (v != null) {
                v.callOnClick();
                //in case of grid mode if pointer on a view item go to single mode scrolling to this item
                //otherwise ignore
                if (!scalableRecyclerGridView.isInSingleMode()) {

                    scalableRecyclerGridView.setSingleMode();
                    scalableRecyclerGridView.layoutManagerSingle.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(v));
                    return false;
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("onDoubleTap: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            //toggle zoom 10x/1x for single mode min/max col span for grid mode
            if(scalableRecyclerGridView.isInSingleMode()) {
                if(currentView!=null) {
                    if(currentView.getScaleX()<10) {
                        panAnimate(currentView, currentView.getTranslationX()-e.getX(), currentView.getTranslationY() - e.getY());
                        zoomAnimate(currentView, currentView.getScaleX(), 10);
                    } else {
                        panAnimate(currentView,currentView.getTranslationX(), currentView.getTranslationY());
                        zoomAnimate(currentView, currentView.getScaleX(), 1);
                    }
                }
            } else {
                View tmpView = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());

                if(scalableRecyclerGridView.getLayoutManagerGrid().getSpanCount() >
                        scalableRecyclerGridView.getMinSpan()) {
                    scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMinSpan());
                } else {
                    scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMaxSpan());
                }
                if(tmpView != null) {
                    scalableRecyclerGridView.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(tmpView));
                }
                scalableRecyclerGridView.requestLayout();

            }
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("onDoubleTapEvent: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            if (scaleGestureDetector.isInProgress()) return true;
            //not in use now but we handle
            return true;
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
            if (scalableRecyclerGridView.isInSingleMode() && panned && currentView != null && currentView.getX() > 0
                //||
                //currentView.getY()<getY()
                    ) {
                //adjust it
                panAnimate(currentView, currentView.getX(),
                        //        getY()-currentView.getY()
                        0
                );
                panned = false;
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + distanceX + "/" + distanceY);
            if (scaleGestureDetector.isInProgress()) return true;
            if (scalableRecyclerGridView.isInSingleMode() && currentView != null && currentView.getScaleX() > 1f) {
                panAnimate(currentView, distanceX, distanceY);
                return true;
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("onFling: " + ((e1 == null) ? "e1==null" : e1.getX() + "/" + e1.getY()) + " - " + ((e2 == null) ? "e2==null" : e2.getX() + "/" + e2.getY()) + " :: " + velocityX + "/" + velocityY);
            if (scaleGestureDetector.isInProgress()) return true;
            if (scalableRecyclerGridView.isInSingleMode() && currentView != null && currentView.getScaleX() > 1f) {
                // panAnimate(currentView, distanceX, distanceY);
                return true;
            }
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("onLongPress: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()));
            //maybe menu
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (scalableRecyclerGridView.isInSingleMode()) {
                handleOnScaleSingle(detector);
            } else {
                handleOnScaleGrid(detector);
            }
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d("onScaleBegin: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            mFromX = detector.getFocusX();
            mFromY = detector.getFocusY();
            View newView = scalableRecyclerGridView.findChildViewUnder(mFromX, mFromY);
            if (newView != null) {
                currentView = newView;
            } else {
                Log.e("onScaleBegin view under x,y is null should not allow user to do that");
            }
            if (scalableRecyclerGridView.isInSingleMode()) {
                handleOnScaleBeginSingle(detector);
            } else {
                handleOnScaleBeginGrid(detector);
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (scalableRecyclerGridView.isInSingleMode()) {
                handleOnScaleEndSingle(detector);
            } else {
                handleOnScaleEndGrid(detector);
            }
        }

        private void handleOnScaleBeginGrid(ScaleGestureDetector detector) {
            Log.d("handleOnScaleBeginGrid: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            initSpanCount = scalableRecyclerGridView.layoutManagerGrid.getSpanCount();

        }

        private void handleOnScaleEndGrid(ScaleGestureDetector detector) {
            Log.d("handleOnScaleEndGrid: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            //now set the final span;
            float currFactor = detector.getScaleFactor();

float tt = 1f -( 1f/(float)(initSpanCount+1))/2f;
            Log.d("handleOnScaleEndGrid: test: " + currFactor + " : " + tt);

            //int newSpanCount = layoutManagerGrid.getSpanCount();
            if (currFactor > 1f + (1f/(float) (initSpanCount - 1))/2f) {
                if (initSpanCount == scalableRecyclerGridView.minSpan) {
                    scalableRecyclerGridView.setSingleMode();
                    scalableRecyclerGridView.layoutManagerSingle.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
                } else {
                    scalableRecyclerGridView.setSpanCount(initSpanCount - 1);
                }
            } else if(currFactor < 1f -( 1f/(float)(initSpanCount+1))/2f) {
                scalableRecyclerGridView.setSpanCount(initSpanCount + 1);
            } else {
                if(scalableRecyclerGridView.getLayoutManagerGrid().getSpanCount()!=initSpanCount) {
                    scalableRecyclerGridView.setSpanCount(initSpanCount);
                }
            }


            if (initSpanCount != scalableRecyclerGridView.layoutManagerGrid.getSpanCount()) {
                scalableRecyclerGridView.layoutManagerGrid.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
            }
            currScale = 1f;
            initSpanCount = 0;
            scalableRecyclerGridView.layoutManagerGrid.requestLayout();
            scalableRecyclerGridView.invalidate();
        }

        private void handleOnScaleGrid(ScaleGestureDetector detector) {
            Log.d("handleOnScaleGrid: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            float currFactor = detector.getScaleFactor();

            int newSpanCount = scalableRecyclerGridView.layoutManagerGrid.getSpanCount();
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
                Log.d("handleOnScaleGrid: zoomout " + newSpanCount);
                // newScale = Math.max(currFactor,(float)initSpanCount/(float)(initSpanCount+1));
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
                        //chnage to single LM here
                        //setSingleMode();
                        //newScale = 1;
                    } else {
                        newScale = Math.min(currFactor, (float) (initSpanCount) / (float) (initSpanCount - 1));
                    }
                }
            }

            if (newSpanCount != scalableRecyclerGridView.layoutManagerGrid.getSpanCount()) {
                scalableRecyclerGridView.setSpanCount(newSpanCount);
                if (initSpanCount != scalableRecyclerGridView.layoutManagerGrid.getSpanCount()) {
                    scalableRecyclerGridView.layoutManagerGrid.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
                }
                scalableRecyclerGridView.layoutManagerGrid.requestLayout();
            }
            currScale = newScale;
            scalableRecyclerGridView.invalidate();
        }

        private void handleOnScaleBeginSingle(ScaleGestureDetector detector) {
            initialItemScale = currentView.getScaleX();
        }

        private void handleOnScaleEndSingle(ScaleGestureDetector detector) {
            Log.d("handleOnScaleEndSingle: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            float currFactor = initialItemScale * detector.getScaleFactor();
            Log.d("handleOnScaleEndSingle - currFactor: " + currFactor);
            if (currFactor >= 1f) {
                //set new scale for view
                zoomAnimate(currentView, currentView.getScaleX(), currFactor);
            } else {
                Log.d("handleOnScaleEndSingle - going to grid mode");
                resetItemScale(currentView);
                int pos = scalableRecyclerGridView.getChildAdapterPosition(currentView);
                currentView = null;
                currScale = 1f;
                scalableRecyclerGridView.setGridMode();
                scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.minSpan);
                scalableRecyclerGridView.layoutManagerGrid.scrollToPosition(pos);
                scalableRecyclerGridView.invalidate();
                //scalableRecyclerGridView.layoutManagerGrid.requestLayout();
                //scalableRecyclerGridView.requestLayout();
            }
        }

        private void handleOnScaleSingle(ScaleGestureDetector detector) {
            Log.d("handleOnScaleSingle: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            float currFactor = initialItemScale * detector.getScaleFactor();
            currentView.setPivotX(mFromX + currentView.getTranslationX());
            currentView.setPivotY(mFromY + currentView.getTranslationY());
            currentView.setScaleX(currFactor);
            currentView.setScaleY(currFactor);
            currentView.invalidate();
//          zoomAnimate(currentView, currentView.getScaleX(), currFactor);
        }

        private void panAnimate(final View currentView, float distanceX, float distanceY) {
            Log.d("panAnimate: " + distanceX + "/" + distanceY + " :: " + currentView);
            panned = true;
            if (mPanAnimator != null) {
                mPanAnimator.end();
            }
            ValueAnimator panAnimatorX = ValueAnimator.ofFloat(currentView.getTranslationX(), currentView.getTranslationX() - distanceX);
            ValueAnimator panAnimatorY = ValueAnimator.ofFloat(currentView.getTranslationY(), currentView.getTranslationY() - distanceY);
            panAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translation = (Float) animation.getAnimatedValue();
                    currentView.setTranslationX(translation);
                    currentView.invalidate();
                    //  currentView.setScrollX((int) translation);

                }
            });
            panAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translation = (Float) animation.getAnimatedValue();
                    currentView.setTranslationY(translation);
                    currentView.invalidate();
                    //currentView.setScrollX((int) translation);
                }
            });

            mPanAnimator = new AnimatorSet();
            mPanAnimator.play(panAnimatorX).with(panAnimatorY);
            mPanAnimator.setDuration(ZOOM_ANIMATION_DURATION_MS);
            mPanAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mPanAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mPanAnimator = null;

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            mPanAnimator.start();
        }

        private void zoomAnimate(final View currentView, float fromScaleFactor, float toScaleFactor) {
            Log.d("zoomAnimate: " + fromScaleFactor + "/" + toScaleFactor + " :: " + currentView);
            if (mZoomAnimator != null) {
                mZoomAnimator.end();
            }
            mZoomAnimator = new ValueAnimator();
            mZoomAnimator.setFloatValues(fromScaleFactor, toScaleFactor);
            mZoomAnimator.setDuration(ZOOM_ANIMATION_DURATION_MS);
            mZoomAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mZoomAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mZoomAnimator = null;

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mZoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float newScaleFactor = (Float) animation.getAnimatedValue();
                    currentView.setPivotX(mFromX + currentView.getTranslationX());
                    currentView.setPivotY(mFromY + currentView.getTranslationY());
                    currentView.setScaleX(newScaleFactor);
                    currentView.setScaleY(newScaleFactor);
                    currentView.invalidate();

                }
            });
            mZoomAnimator.start();
        }

        void resetItemScale(View view) {
            if (view == null) return;
            view.setScaleX(1f);
            view.setScaleY(1f);
            view.setTranslationX(0f);
            view.setTranslationY(0f);
        }
    }

    public interface ScaleListener {
        void onScaleBegin(ScaleGestureDetector detector);

        void onScaleEnd(ScaleGestureDetector detector);

        void onScaleGrid(float newScale, float oldScale);

        void onSpanChange(int newSpan, int oldSpan);

        void onModeChange(boolean isSingle);
    }

    public static class VoidScaleListener implements ScaleListener {

        @Override
        public void onScaleBegin(ScaleGestureDetector detector) {

        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

        @Override
        public void onScaleGrid(float newScale, float oldScale) {

        }

        @Override
        public void onSpanChange(int newSpan, int oldSpan) {

        }

        @Override
        public void onModeChange(boolean isSingle) {

        }
    }
}
