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

package io.apptik.multiview.extras;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class ViewUtils {


    private ViewUtils() {
    }

    /**
     * Get center child in X Axes
     */
    public static View getCenterXChild(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterX(recyclerView, child)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Get position of center child in X Axes
     */
    public static int getCenterXChildPosition(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterX(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }

    /**
     * Get center child in Y Axes
     */
    public static View getCenterYChild(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterY(recyclerView, child)) {
                    return child;
                }
            }
        }
        return null;
    }

    public static View getFirstIntersectsChild(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                Rect rect1=new Rect(recyclerView.getLeft(),recyclerView.getTop(), recyclerView.getRight(), recyclerView.getBottom());
                Rect rect2=new Rect(child.getLeft(),child.getTop(), child.getRight(), child.getBottom());
                if (Rect.intersects(rect1, rect2)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Get position of center child in Y Axes
     */
    public static int getCenterYChildPosition(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterY(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }

    public static boolean isChildInCenterX(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleX = (int) (lvLocationOnScreen[0] + recyclerView.getWidth() * recyclerView.getScaleX() / 2);
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[0] <= middleX && vLocationOnScreen[0] + view.getWidth() * view.getScaleX() >= middleX) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChildInCenterY(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] rvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(rvLocationOnScreen);
        int middleY = (int) (rvLocationOnScreen[1] + recyclerView.getHeight() * recyclerView.getScaleY() / 2);
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[1] <= middleY && vLocationOnScreen[1] + view.getHeight() * view.getScaleY() >= middleY) {
                return true;
            }
        }
        return false;
    }

    public static int getCenterItemPosition(RecyclerView recyclerView, boolean horisontal) {
        int curPosition = -1;
        if (horisontal) {
            curPosition = ViewUtils.getCenterXChildPosition(recyclerView);
        } else {
            curPosition = ViewUtils.getCenterYChildPosition(recyclerView);
        }
        return curPosition;
    }

    public static View getCenterItem(RecyclerView recyclerView, boolean horisontal) {
        if (horisontal) {
            return ViewUtils.getCenterXChild(recyclerView);
        } else {
            return ViewUtils.getCenterYChild(recyclerView);
        }
    }



    //gets item at a specific point
    public static View getItemAtPoint(RecyclerView recyclerView, float x, float y) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isItemAtPoint(child, x, y)) {
                    return child;
                }
            }
        }
        return null;
    }

    public static int getItemPositionAtPoint(RecyclerView recyclerView, float x, float y) {
        View v = getItemAtPoint(recyclerView, x, y);
        int res = recyclerView.getChildAdapterPosition(v);
        Log.d("GGG", "pos: " + res + " :: " + x + "/" + y);
        return res;
    }

    public static boolean isItemAtPoint(View view, float x, float y) {
        return (view.getLeft() < x) && (view.getLeft() + view.getWidth() * view.getScaleX() > x)
                && (view.getTop() < y) && (view.getTop() + view.getHeight() * view.getScaleY() > y)
                ;

    }

}
