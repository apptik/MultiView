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

package io.apptik.widget.multiview.decorations;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;


public class GridDividerDecoration extends ItemDecoration {


    private final Drawable mVerticalDivider;
    private final Drawable mHorizontalDivider;



    public GridDividerDecoration(Drawable divider) {
        this(divider, divider);
    }

    public GridDividerDecoration(Drawable verticalDivider, Drawable horizontalDivider) {
        mVerticalDivider = verticalDivider;
        mHorizontalDivider = horizontalDivider;

    }



//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent) {
//        final BaseLayoutManager lm = (BaseLayoutManager) parent.getLayoutManager();
//
//        final int rightWithPadding = parent.getWidth() - parent.getPaddingRight();
//        final int bottomWithPadding = parent.getHeight() - parent.getPaddingBottom();
//
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//
//            final int childLeft = lm.getDecoratedLeft(child);
//            final int childTop = lm.getDecoratedTop(child);
//            final int childRight = lm.getDecoratedRight(child);
//            final int childBottom = lm.getDecoratedBottom(child);
//
//            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//
//            final int bottomOffset = childBottom - child.getBottom() - lp.bottomMargin;
//            if (bottomOffset > 0 && childBottom < bottomWithPadding) {
//                final int left = childLeft;
//                final int top = childBottom - bottomOffset;
//                final int right = childRight;
//                final int bottom = top + mHorizontalDivider.getIntrinsicHeight();
//
//                mHorizontalDivider.setBounds(left, top, right, bottom);
//                mHorizontalDivider.draw(c);
//            }
//
//            final int rightOffset = childRight - child.getRight() - lp.rightMargin;
//            if (rightOffset > 0 && childRight < rightWithPadding) {
//                final int left = childRight - rightOffset;
//                final int top = childTop;
//                final int right = left + mVerticalDivider.getIntrinsicWidth();
//                final int bottom = childBottom;
//
//                mVerticalDivider.setBounds(left, top, right, bottom);
//                mVerticalDivider.draw(c);
//            }
//        }
//    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        //TODO
    }
}
