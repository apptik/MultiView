package io.apptik.widget.multiview.scalablerecyclerview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import io.apptik.widget.multiview.layoutmanagers.SnapperLinearLayoutManager;

public class ScalableLayoutManager extends SnapperLinearLayoutManager {
    public ScalableLayoutManager(Context context) {
        super(context);
        withFlingOneItemOnly(true);
    }

    public ScalableLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        withFlingOneItemOnly(true);
    }

    public ScalableLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        withFlingOneItemOnly(true);
    }

    @Override
    public void addView(View child, int index) {

//        ScalableViewGroup scalableViewGroup = new ScalableViewGroup(child.getContext());
//        //sw
//        scalableViewGroup.setLayoutParams(child.getLayoutParams());
//        child.setLayoutParams(new FrameLayout.LayoutParams(child.getLayoutParams()));
//        scalableViewGroup.addView(child);
//        //scalableViewGroup.invalidate();
//        //LinearLayoutManager
//        super.addView(scalableViewGroup, index);
        super.addView(child, index);
    }
}
