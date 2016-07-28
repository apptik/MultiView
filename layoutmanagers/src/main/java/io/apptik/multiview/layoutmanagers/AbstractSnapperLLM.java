package io.apptik.multiview.layoutmanagers;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.multiview.common.Log;

public abstract class AbstractSnapperLLM<T extends AbstractSnapperLLM<T>> extends
        LinearLayoutManager {

    public static final int SNAP_START = 1;
    public static final int SNAP_END = 2;
    public static final int SNAP_CENTER = 3;
    public static final int SNAP_NONE = 0;
    public static final float DEFAULT_MILLISECONDS_PER_INCH = 50f;

    private float millisecondsPerInch = DEFAULT_MILLISECONDS_PER_INCH;

    protected RecyclerView recyclerView = null;

    protected RecyclerView.SmoothScroller smoothScroller = null;
    protected SmoothScrollerFactory smoothScrollerFactory = new DefaultSmoothScrollerFactory();

    private boolean showOneItemOnly = false;
    private int snapMethod = SNAP_CENTER;

    volatile boolean adjusted = false;

    int prevPos = -1;
    int lastPos = 0;
    private Context ctx;


    public AbstractSnapperLLM(Context context) {
        super(context);
        ctx = context;
    }

    public AbstractSnapperLLM(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ctx = context;
    }

    public AbstractSnapperLLM(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        ctx = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        LayoutParams nlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;

    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        LayoutParams nlp = new LayoutParams(c, attrs);
        //Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
    }


    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        LayoutParams nlp = new LayoutParams(super.generateLayoutParams(lp));
        //Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
    }

    private T withAdjustSmoothScrollerFactory(
            SmoothScrollerFactory smoothScrollerFactory) {
        this.smoothScrollerFactory = smoothScrollerFactory;
        return (T) this;
    }

    private T withAdjustScrollSpeed(float millisecondsPerInch) {
        this.millisecondsPerInch = millisecondsPerInch;
        return (T) this;
    }

    public boolean isShowOneItemOnly() {
        return showOneItemOnly;
    }

    public T withShowOneItemOnly(boolean showOneItemOnly) {
        this.showOneItemOnly = showOneItemOnly;
        return (T) this;
    }

    public int getSnapMethod() {
        return snapMethod;
    }

    public T withSnapMethod(int snapMethod) {
        this.snapMethod = snapMethod;
        return (T) this;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        recyclerView = view;
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        recyclerView = null;
    }

    @Override
    public void addView(View child, int index) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        if (showOneItemOnly) {
            if (canScrollHorizontally()) {
                lp.width = getWidth();
                if (lp.height != -2) {
                    lp.height = getHeight();
                }
            } else {
                lp.height = getHeight();
                if (lp.width != -2) {
                    lp.width = getWidth();
                }
            }
        } else {
            if (lp instanceof LayoutParams) {
                lp.width = ((LayoutParams) lp).origWidth;
                lp.height = ((LayoutParams) lp).origHeight;
            } else if (lp instanceof ScalableGridLayoutManager.LayoutParams) {
                lp.width = ((ScalableGridLayoutManager.LayoutParams) lp).getOrigWidth();
                lp.height = ((ScalableGridLayoutManager.LayoutParams) lp).getOrigHeight();
            }

        }
        super.addView(child, index);
    }


    public View getCenterItem() {
        View v = null;
        if (recyclerView != null) {
            int middleX = (int) (recyclerView.getX() + (recyclerView.getWidth() * recyclerView
                    .getScaleX()) / 2);
            int middleY = (int) (recyclerView.getY() + (recyclerView.getHeight() * recyclerView
                    .getScaleY()) / 2);
            v = recyclerView.findChildViewUnder(middleX, middleY);
        }
        return v;
    }

    public int getCenterItemPosition() {
        int curPosition = -1;
        View v = getCenterItem();
        if (recyclerView != null && v != null) {
            curPosition = recyclerView.getChildAdapterPosition(v);
        }
        return curPosition;
    }


    @Override
    public void onScrollStateChanged(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            Log.d("onScrollStateChanged DRAGGING");
            //reset adjusted
            adjusted = false;
            prevPos = lastPos;
            // prevPos = getCenterItemPosition();
        } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            Log.d("onScrollStateChanged SETTLING");
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.d("onScrollStateChanged IDLE");
            //check if we still need to settle
            if (!adjusted) {
                adjust();
            }

        }
    }

    protected void adjust() {
        if (adjusted) {
            Log.d("already adjusted");
            return;
        }

        if ((smoothScroller != null && smoothScroller.isRunning()) || isSmoothScrolling()) {
            Log.d("already scrolling");
            return;
        }

        adjusted = true;
        verifyPrevPos();
        Log.d("positionBeforeAdjust:" + prevPos);

        Log.d("adjust just centering...");
        //TODO take care of SNAP_METHOD as we dont want the centered view to be snapped to the
        // top in case of SNAP_START
        int smoothScrollTargetPosition = getCenterItemPosition();

        doScroll(prevPos, smoothScrollTargetPosition);

        prevPos = -1;
    }

    //todo clean this prevPos up its a leftover before the split
    protected final void verifyPrevPos() {
        if (prevPos < 0) {
            Log.e("verifyPrevPos: " + prevPos);
            prevPos = lastPos;
        }
    }

    protected void doScroll(int prevPos, int targetPos) {
        if (targetPos != prevPos && prevPos > -1) {
            onPositionChanging(prevPos, targetPos);
        }
        //we still may need to move even if the position has not change as there could be small
        // displacement
        // within the threshold
        if (prevPos > -1) {
            smoothAdjustTo(targetPos);
        }

        if (targetPos != prevPos && prevPos > -1) {
            onPositionChanged(prevPos, targetPos);
        }
    }


    public void smoothAdjustTo(int targetPosition) {
        Log.d("smoothAdjustTo position: " + targetPosition);
        int safeTargetPosition = safeTargetPosition(targetPosition, getItemCount());
        Log.d("smoothAdjustTo safe position: " + safeTargetPosition);
        if ((smoothScroller != null && smoothScroller.isRunning() && smoothScroller
                .getTargetPosition() != safeTargetPosition) || isSmoothScrolling())
            return;
        if (smoothScrollerFactory == null) {
            Log.d("smoothAdjustTo smoothScroller is null so we use default smooth scrolling");
            if (recyclerView != null) {
                smoothScrollToPosition(recyclerView, new RecyclerView.State(), safeTargetPosition);
                lastPos = safeTargetPosition;
            }
        } else {
            smoothScroller = smoothScrollerFactory.getSmoothScroller();
            Log.d("smoothAdjustTo smoothScroller will start: " + smoothScroller);
            smoothScroller.setTargetPosition(safeTargetPosition);
            startSmoothScroll(smoothScroller);
            lastPos = safeTargetPosition;
        }
    }

    private int safeTargetPosition(int position, int count) {
        if (position < 0) {
            return 0;
        }
        if (position >= count) {
            return count - 1;
        }
        return position;
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        lastPos = position;
    }

    protected void onPositionChanging(int currPos, int newPos) {
        Log.d("onPositionChanging:" + currPos + "/" + newPos);
    }

    protected void onPositionChanged(int prevPos, int newPos) {
        Log.d("onPositionChanged:" + prevPos + "/" + newPos);
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {

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

        public LayoutParams(ScalableGridLayoutManager.LayoutParams source) {
            super(source);
            height = origHeight = source.getOrigHeight();
            width = origWidth = source.getOrigWidth();
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

    public interface SmoothScrollerFactory {
        RecyclerView.SmoothScroller getSmoothScroller();
    }

    private class DefaultSmoothScrollerFactory implements SmoothScrollerFactory {
        @Override
        public RecyclerView.SmoothScroller getSmoothScroller() {

            return new LinearSmoothScroller(ctx) {
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return millisecondsPerInch / displayMetrics.densityDpi;
                }

                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return AbstractSnapperLLM.this
                            .computeScrollVectorForPosition(targetPosition);
                }

                @Override
                public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int
                        boxEnd, int
                                                    snapPreference) {
                    Log.d("calculateDtToFit " + viewStart + " : " + viewEnd + " : " +
                            boxStart + " : " + boxEnd + " : ");
                    switch (snapMethod) {
                        case SNAP_START:
                            return boxStart - viewStart;
                        case SNAP_END:
                            return boxEnd - viewEnd;
                        case SNAP_CENTER:
                            int boxMid = boxStart + (boxEnd - boxStart) / 2;
                            int viewMid = viewStart + (viewEnd - viewStart) / 2;
                            final int dt1 = boxMid - viewMid;
                            Log.d("calculateDtToFit2 " + boxMid + " : " + viewMid + " : " +
                                    dt1);
                            return dt1;

                        case SNAP_NONE:
                            final int dtStart = boxStart - viewStart;
                            if (dtStart > 0) {
                                return dtStart;
                            }
                            final int dtEnd = boxEnd - viewEnd;
                            if (dtEnd < 0) {
                                return dtEnd;
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("snap preference should be one" +
                                    " of the"
                                    + " constants defined in SnapperLinearLayoutManager, " +
                                    "starting with SNAP_");
                    }
                    return 0;
                }

            };
        }
    }
}
