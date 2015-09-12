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

package io.apptik.widget.multiview.scrollers;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class DefaultSmoothScroller extends FlexiSmoothScroller {

    public static final float DEFAULT_MILLISECONDS_PER_INCH = 50f;

    private float millisecondsPerInch = DEFAULT_MILLISECONDS_PER_INCH;

    public DefaultSmoothScroller(Context context) {
        super(context);
        millisecondsPerInch = calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
            RecyclerView.LayoutManager lm = getLayoutManager();
            if (getChildCount() == 0) {
                return null;
            }
            final int firstChildPos = lm.getPosition(lm.getChildAt(0));
            final int direction = targetPosition < firstChildPos != false ? -1 : 1;

            return new PointF(direction, 0);
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return DEFAULT_MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
    }
}
