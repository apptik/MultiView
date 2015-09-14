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

package io.apptik.widget.multiview.galleryview;


import android.content.Context;
import android.util.AttributeSet;

import io.apptik.widget.multiview.scalablerecyclerview.ScalableRecyclerGridView;

public class SimpleGalleryView extends ScalableRecyclerGridView {
    public SimpleGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleGalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SimpleGalleryView(Context context, int intialSpan) {
        super(context, intialSpan);
    }
}
