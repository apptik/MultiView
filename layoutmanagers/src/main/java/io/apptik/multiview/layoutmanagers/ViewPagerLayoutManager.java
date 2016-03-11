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
import android.util.AttributeSet;
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
        smoothAdjustTo(getCenterItemPosition() + 1);
    }

    public void goToPrev() {
        smoothAdjustTo(getCenterItemPosition() - 1);
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

    public interface PageChangeListener {
        void onPageChanging(int currPage, int newPage);
        void onPageChanged(int prevPage, int newPage);
    }
}
