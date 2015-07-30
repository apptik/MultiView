package io.apptik.widget.multiview.scalablerecyclerview;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import io.apptik.widget.multiview.common.Log;
import io.apptik.widget.multiview.extras.ViewUtils;
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
    private boolean allowSingleZoom = true;
    private float maxSingleZoom = 10f;
    private float zoomStep = 3f;

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

    public float getMaxSingleZoom() {
        return maxSingleZoom;
    }

    public void setMaxSingleZoom(float maxSingleZoom) {
        this.maxSingleZoom = maxSingleZoom;
    }

    public boolean isAllowSingleZoom() {
        return allowSingleZoom;
    }

    public void setAllowSingleZoom(boolean allowSingleZoom) {
        this.allowSingleZoom = allowSingleZoom;
    }

    public float getZoomStep() {
        return zoomStep;
    }

    public void setZoomStep(float zoomStep) {
        this.zoomStep = zoomStep;
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
    public View findChildViewUnder(float x, float y) {
        if(isInSingleMode()) {
            int middleX = (int) (getX() + (getWidth() * getScaleX()) / 2);
            int middleY = (int) (getY() + (getHeight() * getScaleY()) / 2);
            return super.findChildViewUnder(middleX, middleY);
        }
        return super.findChildViewUnder(x, y);
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
            currentView = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());
            if (currentView != null) {
                currentView.callOnClick();
                //in case of grid mode if pointer on a view item go to single mode scrolling to this item
                //otherwise ignore
                if (!scalableRecyclerGridView.isInSingleMode()) {

                    scalableRecyclerGridView.setSingleMode();
                    scalableRecyclerGridView.layoutManagerSingle.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
                    return true;
                }
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("onDoubleTap: " + ((e == null) ? "e==null" : e.getX() + "/" + e.getY()) + " v: " + currentView);
            if (scaleGestureDetector.isInProgress()) return true;
            //toggle zoom 10x/1x for single mode min/max col span for grid mode
            if (scalableRecyclerGridView.isInSingleMode()) {
                currentView = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());
                if (currentView != null) {
                    Log.d("onDoubleTap: initscale : " + currentView.getScaleX());


                    if (currentView.getScaleX() < scalableRecyclerGridView.getMaxSingleZoom()) {
                        Log.d("onDoubleTap: initscale : so what next ?");
                        currentView.setPivotX(e.getX() - currentView.getTranslationX());
                        currentView.setPivotY(e.getY() - currentView.getTranslationY());
                        float nextScale =  Math.min(scalableRecyclerGridView.getMaxSingleZoom(),
                                currentView.getScaleX() + scalableRecyclerGridView.getZoomStep());
                        ViewCompat.animate(currentView)
                                .scaleX(nextScale).scaleY(nextScale)
                                .start();
                    } else {
                        ViewCompat.animate(currentView)
                                .scaleX(1).scaleY(1).translationX(0).translationY(0)
                                .start();

                    }
                    currentView.invalidate();
                }
            } else {
                View tmpView = scalableRecyclerGridView.findChildViewUnder(e.getX(), e.getY());

                if (scalableRecyclerGridView.getLayoutManagerGrid().getSpanCount() >
                        scalableRecyclerGridView.getMinSpan()) {
                    scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMinSpan());
                } else {
                    scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.getMaxSpan());
                }
                if (tmpView != null) {
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
                //panAnimate(currentView, currentView.getX(),
                //        getY()-currentView.getY()
                //        0
                //);
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

            View newView = scalableRecyclerGridView.findChildViewUnder(detector.getFocusX(), detector.getFocusY());
            if (newView != null) {
                currentView = newView;
            } else {
                Log.e("onScaleBegin view under x,y is null should not allow user to do that: " + detector.getFocusX() + "/" + detector.getFocusY());
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

            //int newSpanCount = layoutManagerGrid.getSpanCount();
            if (currFactor > 1f + (1f / (float) (initSpanCount - 1)) / 2f) {
                if (initSpanCount == scalableRecyclerGridView.minSpan) {
                    scalableRecyclerGridView.setSingleMode();
                    scalableRecyclerGridView.layoutManagerSingle.scrollToPosition(scalableRecyclerGridView.getChildAdapterPosition(currentView));
                } else {
                    scalableRecyclerGridView.setSpanCount(initSpanCount - 1);
                }
            } else if (currFactor < 1f - (1f / (float) (initSpanCount + 1)) / 2f) {
                scalableRecyclerGridView.setSpanCount(initSpanCount + 1);
            } else {
                if (scalableRecyclerGridView.getLayoutManagerGrid().getSpanCount() != initSpanCount) {
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
            Log.d("handleOnScaleBeginSingle: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            //it might happen that we zoomed and current view is not really under the pointer
            //it is still fine as we know we have only one view here anyway
            if(currentView==null) {
                currentView = ViewUtils.getFirstIntersectsChild(scalableRecyclerGridView);
            }
            initialItemScale = currentView.getScaleX();

//            currentView.setPivotX(mFromX - currentView.getTranslationX());
//            currentView.setPivotY(mFromY - currentView.getTranslationY());
//        currentView.setTranslationX(currentView.getTranslationX() + (currentView.getPivotX() - detector.getFocusX()) * (1 - initialItemScale));
//            currentView.setTranslationY(currentView.getTranslationY() + (currentView.getPivotY() - detector.getFocusY()) * (1 - initialItemScale));
            Log.d("handleOnScaleBeginSingle: translation before: " + currentView.getTranslationX() + "/" +  currentView.getTranslationY() );
//            float transX = currentView.getTranslationX();
//            float transY = currentView.getTranslationY();
//            // Pivot point is top left of the view, so we need to translate
//            // to scale around focus point
//            transX -= (detector.getFocusX() - currentView.getX()) * (initialItemScale - 1f);
//            transY -= (detector.getFocusY() -currentView.getY()) * (initialItemScale - 1f);
//            currentView.setTranslationX(transX);
//            currentView.setTranslationY(transY);
            //in case pivot is not in x/y
//            currentView.setTranslationX(currentView.getTranslationX() + (currentView.getPivotX() - detector.getFocusX()) * (1f - initialItemScale));
//            currentView.setTranslationY(currentView.getTranslationY() + (currentView.getPivotY() - detector.getFocusY()) * (1f - initialItemScale));

            Log.d("handleOnScaleBeginSingle: translation after: " + currentView.getTranslationX() + "/" + currentView.getTranslationY());
//            currentView.setPivotX(detector.getFocusX());
//            currentView.setPivotY(detector.getFocusY());

//            currentView.invalidate();
        }

        private void handleOnScaleEndSingle(ScaleGestureDetector detector) {
            Log.d("handleOnScaleEndSingle: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            float currFactor = initialItemScale * detector.getScaleFactor();
            Log.d("handleOnScaleEndSingle - currFactor: " + currFactor);
            if (currFactor >= 1f) {
                currFactor = Math.min(scalableRecyclerGridView.getMaxSingleZoom(),
                        currFactor);
                //set new scale for view
                //zoomAnimate(currentView, currentView.getScaleX(), currFactor);
                //currentView.setPivotX(mFromX + currentView.getTranslationX());
                //currentView.setPivotY(mFromY + currentView.getTranslationY());

                ViewCompat.animate(currentView)
                        .scaleX(currFactor).scaleY(currFactor)
                        .start();
            } else {
                Log.d("handleOnScaleEndSingle - going to grid mode");
                resetItemScale(currentView);
                int pos = scalableRecyclerGridView.getChildAdapterPosition(currentView);
                //currentView = null;
                currScale = 1f;
                scalableRecyclerGridView.setGridMode();
                scalableRecyclerGridView.setSpanCount(scalableRecyclerGridView.minSpan);
                scalableRecyclerGridView.layoutManagerGrid.scrollToPosition(pos);
                scalableRecyclerGridView.invalidate();
            }
        }

        private void handleOnScaleSingle(ScaleGestureDetector detector) {
            Log.d("handleOnScaleSingle: " + detector.getFocusX() + "/" + detector.getFocusY() + " : " + detector.getCurrentSpan() + "/" + detector.getScaleFactor());
            Log.d("handleOnScaleSingle: pivot: " + currentView.getPivotX() + "/" + currentView.getPivotY());
            float currFactor = initialItemScale * detector.getScaleFactor();

//            currentView.setScaleX(currFactor);
//            currentView.setScaleY(currFactor);

            postScale(detector.getFocusX(), detector.getFocusY(), currFactor / currentView.getScaleX(), scalableRecyclerGridView.getWidth(), scalableRecyclerGridView.getHeight());
            currentView.invalidate();

//            ViewCompat.animate(currentView).scaleX(currFactor).scaleY(currFactor).start();
//          zoomAnimate(currentView, currentView.getScaleX(), currFactor);
        }

        void postScale(float focusX, float focusY, float postScale, int viewportWidth,
                       int viewportHeight) {
            float transX = currentView.getTranslationX();
            float transY = currentView.getTranslationY();
            // Pivot point is top left of the view, so we need to translate
            // to scale around focus point
            transX -= (focusX - scalableRecyclerGridView.getX()) * (postScale - 1f);
            transY -= (focusY - scalableRecyclerGridView.getY()) * (postScale - 1f);
            float scaleX = currentView.getScaleX() * postScale;
            float scaleY = currentView.getScaleY() * postScale;
            updateTransform(transX, transY, scaleX, scaleY, viewportWidth,
                    viewportHeight);
        }

        void updateTransform(float transX, float transY, float scaleX, float scaleY,
                             int viewportWidth, int viewportHeight) {
            float left = transX + currentView.getLeft();
            float top = transY + currentView.getTop();
            RectF r = adjustToFitInBounds(new RectF(left, top,
                            left + currentView.getWidth() * scaleX,
                            top + currentView.getHeight() * scaleY),
                    viewportWidth, viewportHeight);
            currentView.setScaleX(scaleX);
            currentView.setScaleY(scaleY);
            transX = r.left - currentView.getLeft();
            transY = r.top - currentView.getTop();
            currentView.setTranslationX(transX);
            currentView.setTranslationY(transY);
        }

        public static RectF adjustToFitInBounds(RectF rect, int viewportWidth, int viewportHeight) {
            float dx = 0, dy = 0;
            RectF newRect = new RectF(rect);
            if (newRect.width() < viewportWidth) {
                dx = viewportWidth / 2 - (newRect.left + newRect.right) / 2;
            } else {
                if (newRect.left > 0) {
                    dx = -newRect.left;
                } else if (newRect.right < viewportWidth) {
                    dx = viewportWidth - newRect.right;
                }
            }
            if (newRect.height() < viewportHeight) {
                dy = viewportHeight / 2 - (newRect.top + newRect.bottom) / 2;
            } else {
                if (newRect.top > 0) {
                    dy = -newRect.top;
                } else if (newRect.bottom < viewportHeight) {
                    dy = viewportHeight - newRect.bottom;
                }
            }
            if (dx != 0 || dy != 0) {
                newRect.offset(dx, dy);
            }
            return newRect;
        }

        private void panAnimate(final View currentView, float distanceX, float distanceY) {
            Log.d("panAnimate: " + distanceX + "/" + distanceY);
            Log.d("panAnimate: translation: " + currentView.getTranslationX() + "/" + currentView.getTranslationY());
            panned = true;
            if (mPanAnimator != null) {
                mPanAnimator.end();
            }
            float transX = currentView.getTranslationX() - distanceX;
            float transY = currentView.getTranslationY() - distanceY;

            float right = (currentView.getWidth()*currentView.getScaleX() - scalableRecyclerGridView.getWidth())/2;
            float left = -right;
            float bottom = (currentView.getHeight()*currentView.getScaleY() - scalableRecyclerGridView.getHeight())/2;
            float top = -bottom;

            Log.d("panAnimate lr-tb : " +left+ ":" +right+ "  - " +top+ ":" +bottom );

           transX = Math.min(transX, right);
            transX = Math.max(transX, left);
         transY = Math.min(transY, bottom);
            transY = Math.max(transY, top);
            Log.d("panAnimate: new translation: " + transX + "/" + transY);
            ValueAnimator panAnimatorX = ValueAnimator.ofFloat(currentView.getTranslationX(), transX);
            ValueAnimator panAnimatorY = ValueAnimator.ofFloat(currentView.getTranslationY(), transY);
            panAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translation = (Float) animation.getAnimatedValue();
                    currentView.setTranslationX(translation);
                    currentView.invalidate();


                }
            });
            panAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translation = (Float) animation.getAnimatedValue();
                    currentView.setTranslationY(translation);
                    currentView.invalidate();

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
//
//        private void zoomAnimate(final View currentView, float fromScaleFactor, float toScaleFactor) {
//            Log.d("zoomAnimate: " + fromScaleFactor + "/" + toScaleFactor + " :: " + currentView);
//            if (mZoomAnimator != null) {
//                mZoomAnimator.end();
//            }
//            mZoomAnimator = new ValueAnimator();
//            mZoomAnimator.setFloatValues(fromScaleFactor, toScaleFactor);
//            mZoomAnimator.setDuration(ZOOM_ANIMATION_DURATION_MS);
//            mZoomAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mZoomAnimator = null;
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                    mZoomAnimator = null;
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//            mZoomAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float newScaleFactor = (Float) animation.getAnimatedValue();
//                    currentView.setPivotX(mFromX + currentView.getTranslationX());
//                    currentView.setPivotY(mFromY + currentView.getTranslationY());
//                    currentView.setScaleX(newScaleFactor);
//                    currentView.setScaleY(newScaleFactor);
//                    currentView.invalidate();
//
//                }
//            });
//            mZoomAnimator.start();
//        }

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

    private static class MyScroller {
        public interface Listener {
            public void onScrollUpdate(int currX, int currY);
            public void onScrollEnd();
        }
        private final Handler mHandler;
        private final Listener mListener;
        private final Scroller mScroller;
        private final ValueAnimator mXScrollAnimator;
        private final Runnable mScrollChecker = new Runnable() {
            @Override
            public void run() {
                boolean newPosition = mScroller.computeScrollOffset();
                if (!newPosition) {
                    mListener.onScrollEnd();
                    return;
                }
                mListener.onScrollUpdate(mScroller.getCurrX(), mScroller.getCurrY());
                mHandler.removeCallbacks(this);
                mHandler.post(this);
            }
        };
        private final ValueAnimator.AnimatorUpdateListener mXScrollAnimatorUpdateListener =
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mListener.onScrollUpdate((Integer) animation.getAnimatedValue(), 0);
                    }
                };
        private final Animator.AnimatorListener mXScrollAnimatorListener =
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // Do nothing.
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mListener.onScrollEnd();
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // Do nothing.
                    }
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // Do nothing.
                    }
                };
        public MyScroller(Context ctx, Handler handler, Listener listener,
                          TimeInterpolator interpolator) {
            mHandler = handler;
            mListener = listener;
            mScroller = new Scroller(ctx);
            mXScrollAnimator = new ValueAnimator();
            mXScrollAnimator.addUpdateListener(mXScrollAnimatorUpdateListener);
            mXScrollAnimator.addListener(mXScrollAnimatorListener);
            mXScrollAnimator.setInterpolator(interpolator);
        }
        public void fling(
                int startX, int startY,
                int velocityX, int velocityY,
                int minX, int maxX,
                int minY, int maxY) {
            mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            runChecker();
        }
        public void startScroll(int startX, int startY, int dx, int dy) {
            mScroller.startScroll(startX, startY, dx, dy);
            runChecker();
        }
        /** Only starts and updates scroll in x-axis. */
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            mXScrollAnimator.cancel();
            mXScrollAnimator.setDuration(duration);
            mXScrollAnimator.setIntValues(startX, startX + dx);
            mXScrollAnimator.start();
        }
        public boolean isFinished() {
            return (mScroller.isFinished() && !mXScrollAnimator.isRunning());
        }
        public void forceFinished(boolean finished) {
            mScroller.forceFinished(finished);
            if (finished) {
                mXScrollAnimator.cancel();
            }
        }
        private void runChecker() {
            if (mHandler == null || mListener == null) {
                return;
            }
            mHandler.removeCallbacks(mScrollChecker);
            mHandler.post(mScrollChecker);
        }
    }
}
