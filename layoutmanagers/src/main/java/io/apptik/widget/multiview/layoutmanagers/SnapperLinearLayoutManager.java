package io.apptik.widget.multiview.layoutmanagers;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class SnapperLinearLayoutManager extends LinearLayoutManager {

    public static final String TAG = SnapperLinearLayoutManager.class.getName();

    public static final int SNAP_START = 1;
    public static final int SNAP_END = 2;
    public static final int SNAP_CENTER = 3;
    public static final int SNAP_NONE = 0;
    public static final float DEFAULT_MILLISECONDS_PER_INCH = 50f;

    private float millisecondsPerInch = DEFAULT_MILLISECONDS_PER_INCH;

    protected RecyclerView mRecyclerView = null;

    private RecyclerView.SmoothScroller smoothScroller = null;
    private boolean flingOneItemOnly = false;

    private boolean showOneItemOnly = false;
    private int snapMethod = SNAP_CENTER;

    boolean adjusted = false;
    int mLeft;
    int mTop;
    private int mSmoothScrollTargetPosition = -1;
    private float mTriggerOffset = 0.05f;
    View prevView;

    public SnapperLinearLayoutManager(Context context) {
        super(context);

    }

    public SnapperLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public SnapperLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    private SnapperLinearLayoutManager withAdjustSmoothScroller(RecyclerView.SmoothScroller smoothScroller) {
        this.smoothScroller = smoothScroller;
        return this;
    }

    private SnapperLinearLayoutManager withAdjustScrollSpeed(float millisecondsPerInch) {
        this.millisecondsPerInch = millisecondsPerInch;
        return this;
    }

    private void setDefaultSmoothScroller() {
        smoothScroller =
                new LinearSmoothScroller(mRecyclerView.getContext()) {
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return millisecondsPerInch / displayMetrics.densityDpi;
                    }

                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return SnapperLinearLayoutManager.this
                                .computeScrollVectorForPosition(targetPosition);
                    }

                    @Override
                    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int
                            snapPreference) {
                        Log.d(TAG, "calculateDtToFit " + viewStart + " : " + viewEnd + " : " + boxStart + " : " + boxEnd + " : ");
                        switch (snapMethod) {
                            case SNAP_START:
                                return boxStart - viewStart;
                            case SNAP_END:
                                return boxEnd - viewEnd;
                            case SNAP_CENTER:
                                int boxMid = boxStart + (boxEnd - boxStart) / 2;
                                int viewMid = viewStart + (viewEnd - viewStart) / 2;
                                final int dt1 = boxMid - viewMid;
                                Log.d(TAG, "calculateDtToFit2 " + boxMid + " : " + viewMid + " : " + dt1);
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
                                throw new IllegalArgumentException("snap preference should be one of the"
                                        + " constants defined in SnapperLinearLayoutManager, starting with SNAP_");
                        }
                        return 0;
                    }

                };
    }


    public boolean isShowOneItemOnly() {
        return showOneItemOnly;
    }

    public SnapperLinearLayoutManager withShowOneItemOnly(boolean showOneItemOnly) {
        this.showOneItemOnly = showOneItemOnly;
        return this;
    }

    public boolean isFlingOneItemOnly() {
        return flingOneItemOnly;
    }

    public SnapperLinearLayoutManager withFlingOneItemOnly(boolean flingOneItemOnly) {
        this.flingOneItemOnly = flingOneItemOnly;
        if (flingOneItemOnly) this.showOneItemOnly = true;
        return this;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mRecyclerView = view;
        setDefaultSmoothScroller();
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
                    new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT) : child.getLayoutParams();

            //q: isn't it better to wrap it in a group view ?
            //a: no because we need to transfer the layoutparams containing viewholder assigned from the adapter.
            lp.width = getWidth();
            lp.height = getHeight();
            child.setLayoutParams(lp);
        }
        super.addView(child, index);
    }

    public int getCenterPosition() {
        int curPosition = -1;
        if (canScrollHorizontally()) {
            curPosition = ViewUtils.getCenterXChildPosition(mRecyclerView);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(mRecyclerView);
        }
        return curPosition;
    }


    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            Log.d(TAG, "onScrollStateChanged DRAGGING");
            //reset adjusted
            adjusted = false;

            //in case of trigger we need to know where we come from
            if (flingOneItemOnly) {
                prevView = canScrollHorizontally() ? ViewUtils.getCenterXChild(mRecyclerView) :
                        ViewUtils.getCenterYChild(mRecyclerView);
                if (prevView != null) {
                    mLeft = prevView.getLeft();
                    mTop = prevView.getTop();
                }
            }

        } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            Log.d(TAG, "onScrollStateChanged SETTLING");
            //check if we need to settle
            if (flingOneItemOnly && !adjusted) {
                //we need to stop here
                adjust();
            }
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.d(TAG, "onScrollStateChanged IDLE");
            //check if we still need to settle
            if (!adjusted) {
                //we did not swipe so just center
                prevView = null;
                adjust();
            }

        }
    }

    private synchronized void adjust() {
        adjusted = true;
        if (smoothScroller != null && (smoothScroller.isRunning()) || isSmoothScrolling()) return;
        int prevPos = mRecyclerView.getChildLayoutPosition(prevView);
        Log.d(TAG, "mPositionBeforeAdjust:" + prevPos);


        //TODO take care of SNAP_METHOD as we dont want the centered view to be snapped to the top in case of SNAP_START
        int targetPosition = getCenterPosition();

        if (prevView != null) {
            targetPosition = getPosition(prevView);
            Log.d(TAG, "adjust has mCurrView +");
            if (canScrollHorizontally()) {
                int spanX = prevView.getLeft() - mLeft;
                if (spanX > prevView.getWidth() * mTriggerOffset) {
                    targetPosition--;
                } else if (spanX <= prevView.getWidth() * -mTriggerOffset) {
                    targetPosition++;
                }
            } else {
                int spanY = prevView.getTop() - mTop;
                if (spanY > prevView.getHeight() * mTriggerOffset) {
                    targetPosition--;
                } else if (spanY <= prevView.getHeight() * -mTriggerOffset) {
                    targetPosition++;
                }
            }
        } else {
            Log.d(TAG, "adjust no mCurrView just centering...");
        }

        mSmoothScrollTargetPosition = targetPosition;
        smoothScrollTo(mSmoothScrollTargetPosition);


        if (mSmoothScrollTargetPosition != prevPos) {
            onPageChanged(mSmoothScrollTargetPosition);
        }
    }


    private synchronized void smoothScrollTo(int targetPosition) {
        if (smoothScroller != null && (smoothScroller.isRunning()) || isSmoothScrolling()) return;
        if (smoothScroller == null) {
            smoothScrollToPosition(mRecyclerView, new RecyclerView.State(), safeTargetPosition(targetPosition, getItemCount()));
        } else {
            smoothScroller.setTargetPosition(safeTargetPosition(targetPosition, getItemCount()));
            startSmoothScroll(smoothScroller);
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

    protected void onPageChanged(int page) {
        Log.d(TAG, "onPageChanged:" + mSmoothScrollTargetPosition);
    }
}
