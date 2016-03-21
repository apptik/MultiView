package io.apptik.multiview.layoutmanagers;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import io.apptik.multiview.common.Log;

public class AbstractPagerLLM<T extends AbstractPagerLLM<T>> extends AbstractSnapperLLM<T>  {

    PageChangeListener pageChangeListener;
    private float triggerOffset = 0.05f;
    volatile int gdx = 0;
    volatile int gdy = 0;
    volatile int vx = 0;
    volatile int vy = 0;
    volatile boolean adjustOnScroll = false;
    volatile boolean isIdle = true;
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!adjusted) {
                gdx += dx;
                gdy += dy;
                if (adjustOnScroll) {
                    adjust();
                }
            }
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    public AbstractPagerLLM(Context context) {
        super(context);
        super.withShowOneItemOnly(true);
    }

    public AbstractPagerLLM(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.withShowOneItemOnly(true);
    }

    public AbstractPagerLLM(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        super.withShowOneItemOnly(true);
    }

    public T withPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
        return (T) this;
    }

    public float getTriggerOffset() {
        return triggerOffset;
    }

    public T withTriggerOffset(float triggerOffset) {
        this.triggerOffset = triggerOffset;
        return (T) this;
    }

    @Override
    public T withShowOneItemOnly(boolean showOneItemOnly) {
        if (!showOneItemOnly) {
            throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one " +
                    "item");
        }
        return (T) this;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        view.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        view.removeOnScrollListener(onScrollListener);
        super.onDetachedFromWindow(view, recycler);
    }


    public void goToNext() {
        if(recyclerView!=null) {
            smoothAdjustTo(getCurrentPage() + 1);
        }
    }

    public void goToPrev() {
        if(recyclerView!=null) {
            smoothAdjustTo(getCurrentPage() - 1);
        }
    }

    public int getCurrentPage() {
        int pos = getCenterItemPosition();
        if(pos<0) {
            return lastPos;
        }
        return pos;
    }

    public View getCurrentPageView() {
        if (getChildCount() == 1) {
            return getChildAt(0);
        } else {
            return getCenterItem();
        }
    }

    @Override
    protected void onPositionChanging(int currPos, int newPos) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageChanging(currPos, newPos);
        }
        super.onPositionChanging(currPos, newPos);
    }

    @Override
    protected void onPositionChanged(int prevPos, int newPos) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageChanged(prevPos, newPos);
        }
        super.onPositionChanged(prevPos, newPos);
    }

    @Override
    public void onScrollStateChanged(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            isIdle = false;
            Log.d("onScrollStateChanged DRAGGING");
            //reset adjusted
            gdx = 0;
            gdy = 0;
            adjusted = false;
            //just in case
            adjustOnScroll = false;
            prevPos = getCurrentPage();
            View currView = findViewByPosition(prevPos);
            if (currView != null) {
                vx = currView.getWidth();
                vy = currView.getHeight();
            }
        } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            isIdle = false;
            Log.d("onScrollStateChanged SETTLING");
            adjust();
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            isIdle = true;
            Log.d("onScrollStateChanged IDLE");
            adjust();
        }
    }

    @Override
    protected void adjust() {
        if (adjusted) {
            Log.d("already adjusted");
            return;
        }

        if ((smoothScroller != null && (smoothScroller.isRunning())) || isSmoothScrolling()) {
            Log.d("already scrolling");
            return;
        }

        //When we scroll too fast in the DRAG state we will not have any movement
        //so we need to check on the next scroll if were fine
        if (!isIdle && gdx == 0 && gdy == 0) {
            adjustOnScroll = true;
            Log.d("adjustOnScroll active");
            return;
        }
        adjusted = true;
        adjustOnScroll = false;
        verifyPrevPos();
        Log.d("positionBeforeAdjust:" + prevPos);


        int targetPosition;
        targetPosition = prevPos;

        if (canScrollHorizontally()) {
            if (gdx > vx * triggerOffset) {
                targetPosition++;
            } else if (gdx <= vx * -triggerOffset) {
                targetPosition--;
            }
        } else {
            if (gdy > vy * triggerOffset) {
                targetPosition++;
            } else if (gdy <= vy * -triggerOffset) {
                targetPosition--;
            }
        }

        int smoothScrollTargetPosition = targetPosition;

        doScroll(prevPos, smoothScrollTargetPosition);

        prevPos = -1;
    }

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);

        void onPageChanged(int prevPage, int newPage);
    }
}
