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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import io.apptik.widget.multiview.common.Log;

public class ScalableGridLayoutManager extends GridLayoutManager {

    RecyclerView mRecyclerView;

    protected int initSpanCount = -1;
    private boolean animateItemChangedOnScaleChange = false;


    public ScalableGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ScalableGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        init();
    }

    public ScalableGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        init();
    }

    private void init() {
       initSpanCount = getSpanCount();
    }

    public boolean isAnimateItemChangedOnScaleChange() {
        return animateItemChangedOnScaleChange;
    }

    public void setAnimateItemChangedOnScaleChange(boolean animateItemChangedOnScaleChange) {
        this.animateItemChangedOnScaleChange = animateItemChangedOnScaleChange;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        LayoutParams nlp =  new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;

    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        LayoutParams nlp =  new LayoutParams(c, attrs);
        //Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
    }


    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        Log.d("generateLayoutParams: lp: " + lp.toString() + ":: " + lp.width + "/" + lp.height);
        LayoutParams nlp;
        if (lp instanceof LayoutParams) {
            nlp = new LayoutParams((LayoutParams)lp);
        } else if (lp instanceof SnapperLinearLayoutManager.LayoutParams) {
            nlp = new LayoutParams((SnapperLinearLayoutManager.LayoutParams)lp);
        } else {
            nlp = new LayoutParams(lp);
        }
        Log.d("generateLayoutParams: nlp: " + nlp.width + "/" + nlp.height);
        return nlp;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mRecyclerView = view;
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        mRecyclerView = null;
    }

    @Override
    public void addView(View child, int index) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        Log.d("addView: lp old: " + lp.width + "/" + lp.height + " :: " + lp.origWidth + "/" + lp.origHeight);

        if(lp.origHeight>0) {
            lp.height = (int) (lp.origHeight * getScale());
        }
        if(lp.origWidth>0) {
            lp.width = (int) (lp.origWidth * getScale());
        }
        Log.d("addView: lp new: " + lp.width + "/" + lp.height + " :: " + lp.origWidth + "/" + lp.origHeight);
        super.addView(child, index);
    }

    @Override
    public void setSpanCount(int spanCount) {
        if(spanCount==getSpanCount()) return;
        super.setSpanCount(spanCount);
        int childCount = getChildCount();
        if(childCount>0 && animateItemChangedOnScaleChange) {
            mRecyclerView.getAdapter()
                    .notifyItemRangeChanged(mRecyclerView.getChildAdapterPosition(getChildAt(0)), childCount * 2);
        }
    }

    protected float getScale() {
        return ((float)initSpanCount/(float)getSpanCount());
        //return 1f;
    }
    public static class LayoutParams extends GridLayoutManager.LayoutParams {

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

        public LayoutParams(SnapperLinearLayoutManager.LayoutParams source) {
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
