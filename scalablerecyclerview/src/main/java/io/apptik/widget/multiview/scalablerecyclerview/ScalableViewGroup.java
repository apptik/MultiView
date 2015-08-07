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

package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ScalableViewGroup extends FrameLayout {


    private float mScaleFactor = 1.f;

    public ScalableViewGroup(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getScaleFactor() {
        return mScaleFactor;
    }

    public void setScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
//        /canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        //canvas.restore();
        super.onDraw(canvas);

    }


}
