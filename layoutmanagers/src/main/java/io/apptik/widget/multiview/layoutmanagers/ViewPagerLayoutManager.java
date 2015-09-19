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
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewPagerLayoutManager extends SnapperLinearLayoutManager {

    PageChangeListener pageChangeListener;

    public ViewPagerLayoutManager(Context context) {
        super(context);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        super.withFlingOneItemOnly(true);
    }

    public ViewPagerLayoutManager withPageChangeListener(PageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
        return this;
    }

    @Override
    public SnapperLinearLayoutManager withFlingOneItemOnly(boolean flingOneItemOnly) {
        if(!flingOneItemOnly) {
            throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one item");
        }
        return this;
    }

    @Override
    public SnapperLinearLayoutManager withShowOneItemOnly(boolean showOneItemOnly) {
        if(!showOneItemOnly) {
            throw new IllegalStateException("ViewPagerLayoutManager can show and fling only one item");
        }
        return this;
    }

    public void goToNext() {
        scrollToPosition(getCenterItemPosition() + 1);
    }

    public void goToPrev() {
        scrollToPosition(getCenterItemPosition() - 1);
    }

    public int getCurrentPage() {
        return getCenterItemPosition();
    }

    public View getCurrentPageView() {
        if(getChildCount()==1) {
            return getChildAt(0);
        } else {
            return getCenterItem();
        }
    }

    @Override
    protected void onPositionChanging(int currPos, int newPos) {
        if(pageChangeListener!=null) {
            pageChangeListener.onPageChanging(currPos, newPos);
        }
        super.onPositionChanging(currPos, newPos);
    }

    @Override
    protected void onPositionChanged(int prevPos, int newPos) {
        if(pageChangeListener!=null) {
            pageChangeListener.onPageChanged(prevPos, newPos);
        }
        super.onPositionChanged(prevPos, newPos);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int layoutDirection = dx > 0 ? 1 : -1;
        View currView = getCurrentPageView();
        if(mRecyclerView != null && currView != null && currView.canScrollHorizontally(layoutDirection)
                && currView.getLeft() == 0 && currView.getRight() == currView.getWidth()
                ) {
            if(lastTouchEvent!=null) {
                currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent=null;
            }
            ViewCompat.setOverScrollMode(mRecyclerView, ViewCompat.OVER_SCROLL_NEVER);
            return 0;
        } else {
            ViewCompat.setOverScrollMode(mRecyclerView, ViewCompat.OVER_SCROLL_ALWAYS);
            return super.scrollHorizontallyBy(dx, recycler, state);
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        final int layoutDirection = dy > 0 ? 1 : -1;
        View currView = getCurrentPageView();
        if(mRecyclerView != null && currView != null && currView.canScrollVertically(layoutDirection)) {
            if(lastTouchEvent != null) {
               currView.dispatchTouchEvent(lastTouchEvent);
                lastTouchEvent=null;
            }
            ViewCompat.setOverScrollMode(mRecyclerView, ViewCompat.OVER_SCROLL_NEVER);
            return 0;
        } else {
            ViewCompat.setOverScrollMode(mRecyclerView, ViewCompat.OVER_SCROLL_ALWAYS);
            return super.scrollVerticallyBy(dy, recycler, state);
        }

    }

    private volatile MotionEvent lastTouchEvent;

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

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);
        void onPageChanged(int prevPage, int newPage);
    }
}
