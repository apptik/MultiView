package io.apptik.multiview.layoutmanagers;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class TouchChildPagerLayoutManager extends ViewPagerLayoutManager {
    private volatile MotionEvent lastTouchEvent;
    private int dOffset = 0;
    private static final float TRIGG = 0.2f;

    private float trigg;
    int triggPx;
    int triggPy;
    private Context ctx;

    public TouchChildPagerLayoutManager(Context context) {
        super(context);
        ctx = context;
        withInitTriggerOffset(TRIGG);
    }

    public TouchChildPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ctx = context;
        withInitTriggerOffset(TRIGG);
    }

    public TouchChildPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        ctx = context;
        withInitTriggerOffset(TRIGG);
    }

    /**
     * @return the trigger offset in inches needed to initiate movement.
     * Used to give priority to other touch events.
     */
    public float getInitTriggerOffset() {
        return trigg;
    }

    /**
     * @param triggerOffset the trigger offset in inches needed to initiate movement.
     * Used to give priority to other touch events.
     */
    public TouchChildPagerLayoutManager withInitTriggerOffset(float triggerOffset) {
        this.trigg = triggerOffset;
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        triggPx = Math.round(dm.xdpi*trigg);
        triggPy = Math.round(dm.ydpi*trigg);
        return this;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int layoutDirection = dx > 0 ? 1 : -1;
        View currView = getCurrentPageView();
        //check if we need to work with the child view
        if (recyclerView != null && currView != null && currView.canScrollHorizontally(layoutDirection)
                && ((layoutDirection == 1 && currView.getLeft() <= 0) ||
                (layoutDirection == -1 && currView.getRight() >= currView.getWidth()))
                ) {
            dOffset = 0;
            if (lastTouchEvent != null) {
                currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent = null;
            }
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_NEVER);

            if ((layoutDirection == 1 && currView.getLeft() < 0) || (layoutDirection == -1 && currView.getRight() > currView.getWidth())) {
                adjust();
            }
            return 0;
        } else if (Math.abs(dOffset + dx) < triggPx &&
                recyclerView != null && currView != null &&
                currView.getLeft() == 0 && currView.getRight() == currView.getWidth()
                ) {
            dOffset += dx;
            if (lastTouchEvent != null) {
                currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent = null;
            }
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_NEVER);
            return 0;
        } else {
            dOffset = 0;
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_ALWAYS);
            return super.scrollHorizontallyBy(dx, recycler, state);
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int layoutDirection = dy > 0 ? 1 : -1;
        View currView = getCurrentPageView();
        //check if we need to work with the child view
        if (recyclerView != null && currView != null && currView.canScrollVertically(layoutDirection)
                && ((layoutDirection == 1 && currView.getTop() <= 0) ||
                (layoutDirection == -1 && currView.getBottom() >= currView.getHeight()))
                ) {
            dOffset = 0;
            if (lastTouchEvent != null) {
                currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent = null;
            }
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_NEVER);
            if ((layoutDirection == 1 && currView.getTop() < 0) ||
                    (layoutDirection == -1 && currView.getBottom() > currView.getHeight())) {
                adjust();
            }
            return 0;
        } else if (Math.abs(dOffset + dy) < triggPy &&
                recyclerView != null && currView != null &&
                currView.getTop() == 0 && currView.getBottom() == currView.getHeight()
                ) {
            dOffset += dy;
            if (lastTouchEvent != null) {
                currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent = null;
            }
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_NEVER);
            return 0;
        } else {
            dOffset = 0;
            ViewCompat.setOverScrollMode(recyclerView, ViewCompat.OVER_SCROLL_ALWAYS);
            return super.scrollVerticallyBy(dy, recycler, state);
        }
    }


    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        view.addOnItemTouchListener(touchSaver);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        view.removeOnItemTouchListener(touchSaver);
        super.onDetachedFromWindow(view, recycler);
    }

    private RecyclerView.OnItemTouchListener touchSaver = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            lastTouchEvent = e;
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}
