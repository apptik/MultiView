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

package io.apptik.widget.multiview.layoutmanagers;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.widget.multiview.common.Log;


public class SnapperLinearLayoutManager extends LinearLayoutManager {

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
                        Log.d("calculateDtToFit " + viewStart + " : " + viewEnd + " : " + boxStart + " : " + boxEnd + " : ");
                        switch (snapMethod) {
                            case SNAP_START:
                                return boxStart - viewStart;
                            case SNAP_END:
                                return boxEnd - viewEnd;
                            case SNAP_CENTER:
                                int boxMid = boxStart + (boxEnd - boxStart) / 2;
                                int viewMid = viewStart + (viewEnd - viewStart) / 2;
                                final int dt1 = boxMid - viewMid;
                                Log.d("calculateDtToFit2 " + boxMid + " : " + viewMid + " : " + dt1);
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
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        if (showOneItemOnly) {
            if (canScrollHorizontally()) {
                lp.width = getWidth();
                if(lp.height>0) {
                    lp.height = getHeight();
                }
            } else {
                lp.height = getHeight();
                if(lp.width>0) {
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
        int middleX = (int) (mRecyclerView.getX() + (mRecyclerView.getWidth() * mRecyclerView.getScaleX()) / 2);
        int middleY = (int) (mRecyclerView.getY() + (mRecyclerView.getHeight() * mRecyclerView.getScaleY()) / 2);
        View v = mRecyclerView.findChildViewUnder(middleX, middleY);
        return v;
    }

    public int getCenterItemPosition() {
        View v = getCenterItem();
        int curPosition = mRecyclerView.getChildAdapterPosition(v);
        return curPosition;
    }


    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            Log.d("onScrollStateChanged DRAGGING");
            //reset adjusted
            adjusted = false;
            prevView = getCenterItem();
            //in case of trigger we need to know where we come from
            if (flingOneItemOnly) {
                if (prevView != null) {
                    mLeft = prevView.getLeft();
                    mTop = prevView.getTop();
                }
            }

        } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            Log.d("onScrollStateChanged SETTLING");
            //check if we need to settle
            if (flingOneItemOnly && !adjusted) {
                //we need to stop here
                adjust();
            }
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.d("onScrollStateChanged IDLE");
            //check if we still need to settle
            if (!adjusted) {
                adjust();
                prevView = null;
            }

        }
    }

    protected synchronized void adjust() {
        if (smoothScroller != null && (smoothScroller.isRunning()) || isSmoothScrolling()) {
            return;
        }
        adjusted = true;
        int prevPos = mRecyclerView.getChildAdapterPosition(prevView);
        Log.d("mPositionBeforeAdjust:" + prevPos);


        //TODO take care of SNAP_METHOD as we dont want the centered view to be snapped to the top in case of SNAP_START
        int targetPosition = getCenterItemPosition();

        if (prevView != null && flingOneItemOnly) {
            targetPosition = getPosition(prevView);
            Log.d("adjust has mCurrView +");
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
            Log.d("adjust no mCurrView just centering...");
        }

        mSmoothScrollTargetPosition = targetPosition;

        if (mSmoothScrollTargetPosition != prevPos && prevPos > -1) {
            onPositionChanging(prevPos, mSmoothScrollTargetPosition);
        }
        smoothAdjustTo(mSmoothScrollTargetPosition);

        if (mSmoothScrollTargetPosition != prevPos  && prevPos > -1) {
            onPositionChanged(prevPos, mSmoothScrollTargetPosition);
        }
    }


    protected synchronized void smoothAdjustTo(int targetPosition) {
        Log.d("smoothAdjustTo position: " + targetPosition);
        int safeTargetPosition = safeTargetPosition(targetPosition, getItemCount());
        Log.d("smoothAdjustTo safe position: " + safeTargetPosition);
        if ((smoothScroller != null && smoothScroller.isRunning()) || isSmoothScrolling()) return;
        if (smoothScroller == null) {
            Log.d("smoothAdjustTo smoothScroller is null so we use default smooth scrolling");
            smoothScrollToPosition(mRecyclerView, new RecyclerView.State(), safeTargetPosition);
        } else {
            if(smoothScroller.getTargetPosition() != safeTargetPosition) {
                Log.d("smoothAdjustTo smoothScroller will start: " + smoothScroller);
                setDefaultSmoothScroller();
                smoothScroller.setTargetPosition(safeTargetPosition);
                startSmoothScroll(smoothScroller);
            } else {
                Log.d("smoothAdjustTo smoothScroller already targeting position: " + safeTargetPosition);
            }
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
}
