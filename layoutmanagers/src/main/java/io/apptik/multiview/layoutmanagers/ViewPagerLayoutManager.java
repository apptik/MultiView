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

package io.apptik.multiview.layoutmanagers;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import io.apptik.multiview.common.Log;

public class ViewPagerLayoutManager extends SnapperLinearLayoutManager {

    PageChangeListener pageChangeListener;
    private float triggerOffset = 0.05f;
    private volatile int gdx = 0;
    private volatile int gdy = 0;
    private volatile int vx = 0;
    private volatile int vy = 0;
    private volatile boolean adjustOnScroll = false;
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


    public ViewPagerLayoutManager(Context context) {
        super(context);
        super.withShowOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.withShowOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        super.withShowOneItemOnly(true);
    }

    public ViewPagerLayoutManager withPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
        return this;
    }

    @Override
    public SnapperLinearLayoutManager withShowOneItemOnly(boolean showOneItemOnly) {
        if (!showOneItemOnly) {
            throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one " +
                    "item");
        }
        return this;
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
        smoothAdjustTo(getCenterItemPosition() + 1);
    }

    public void goToPrev() {
        smoothAdjustTo(getCenterItemPosition() - 1);
    }

    public int getCurrentPage() {
        return getCenterItemPosition();
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
            Log.d("onScrollStateChanged DRAGGING");
            //reset adjusted
            gdx = 0;
            gdy = 0;
            adjusted = false;
            //just in case
            adjustOnScroll = false;
            prevPos = getCenterItemPosition();
            View currView = findViewByPosition(prevPos);
            if (currView != null) {
                vx = currView.getWidth();
                vy = currView.getHeight();
            }
        } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            Log.d("onScrollStateChanged SETTLING");
            adjust();
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.d("onScrollStateChanged IDLE");
            adjust();
        }
    }

    @Override
    protected void adjust() {
        if (adjusted || (smoothScroller != null && (smoothScroller.isRunning())) ||
                isSmoothScrolling()
                ) {
            return;
        }

        //When we scroll too fast in the DRAG state we will not have any movement
        //so we need to check on the next scroll if were fine
        if ((gdx == 0 && gdy == 0)) {
            adjustOnScroll = true;
            return;
        }
        adjusted = true;
        adjustOnScroll = false;
        Log.d("mPositionBeforeAdjust:" + prevPos);


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

        if (smoothScrollTargetPosition != prevPos && prevPos > -1) {
            onPositionChanging(prevPos, smoothScrollTargetPosition);
        }

        smoothAdjustTo(smoothScrollTargetPosition);

        if (smoothScrollTargetPosition != prevPos && prevPos > -1) {
            onPositionChanged(prevPos, smoothScrollTargetPosition);
        }

        prevPos = -1;
    }

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);

        void onPageChanged(int prevPage, int newPage);
    }
}
